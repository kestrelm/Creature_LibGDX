package MeshBoneUtil;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;
import java.util.Set;

/******************************************************************************
 * Creature Runtimes License
 *
 * Copyright (c) 2015, Kestrel Moon Studios
 * All rights reserved.
 *
 * Preamble: This Agreement governs the relationship between Licensee and Kestrel Moon Studios(Hereinafter: Licensor).
 * This Agreement sets the terms, rights, restrictions and obligations on using [Creature Runtimes] (hereinafter: The Software) created and owned by Licensor,
 * as detailed herein:
 * License Grant: Licensor hereby grants Licensee a Sublicensable, Non-assignable & non-transferable, Commercial, Royalty free,
 * Including the rights to create but not distribute derivative works, Non-exclusive license, all with accordance with the terms set forth and
 * other legal restrictions set forth in 3rd party software used while running Software.
 * Limited: Licensee may use Software for the purpose of:
 * Running Software on Licensee’s Website[s] and Server[s];
 * Allowing 3rd Parties to run Software on Licensee’s Website[s] and Server[s];
 * Publishing Software’s output to Licensee and 3rd Parties;
 * Distribute verbatim copies of Software’s output (including compiled binaries);
 * Modify Software to suit Licensee’s needs and specifications.
 * Binary Restricted: Licensee may sublicense Software as a part of a larger work containing more than Software,
 * distributed solely in Object or Binary form under a personal, non-sublicensable, limited license. Such redistribution shall be limited to unlimited codebases.
 * Non Assignable & Non-Transferable: Licensee may not assign or transfer his rights and duties under this license.
 * Commercial, Royalty Free: Licensee may use Software for any purpose, including paid-services, without any royalties
 * Including the Right to Create Derivative Works: Licensee may create derivative works based on Software,
 * including amending Software’s source code, modifying it, integrating it into a larger work or removing portions of Software,
 * as long as no distribution of the derivative works is made
 *
 * THE RUNTIMES IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE RUNTIMES OR THE USE OR OTHER DEALINGS IN THE
 * RUNTIMES.
 *****************************************************************************/

// Class for managing a collection of animations and a creature character
public class CreatureManager {
    public HashMap<String, CreatureAnimation> animations;
    public Creature target_creature;
    public String active_animation_name;
    public boolean is_playing;
    public float run_time;
    public float time_scale;
    public Vector<float[] > blend_render_pts;
    public boolean do_blending;
    public float blending_factor;
    public Vector<String> active_blend_animation_names;
    public Vector<String> auto_blend_names;
    public HashMap<String, Float> active_blend_run_times;
	public Boolean do_auto_blending;
	public float auto_blend_delta;
    public boolean use_custom_time_range;
    public int custom_start_time, custom_end_time;
    public boolean should_loop;

    public interface BonesCallback
    {
        public void run(HashMap<String, MeshBone> bones_map);
    }

    public BonesCallback bones_override_callback;

    public CreatureManager(Creature target_creature_in)
    {
        target_creature = target_creature_in;
        is_playing = false;
        run_time = 0;
        time_scale = 60.0f;
        blending_factor = 0;
        animations = new HashMap<String, CreatureAnimation> ();
        use_custom_time_range = false;
        custom_start_time = custom_end_time = 0;
        should_loop = true;
        bones_override_callback = null;

        blend_render_pts = new Vector<float[] >();
        blend_render_pts.add(new float[1]);
        blend_render_pts.add(new float[1]);

        active_blend_animation_names = new Vector<String>();
        active_blend_animation_names.add("");
        active_blend_animation_names.add("");
        
		do_auto_blending = false;
		auto_blend_delta = 0.0f;

		auto_blend_names = new Vector<String>();
		auto_blend_names.add("");
		auto_blend_names.add("");
        
        active_blend_run_times = new HashMap<String, Float>();
    }
    
	// Create a point cache for a specific animation
	// This speeds up playback but you will lose the ability to directly
	// manipulate the bones.
	public void
	MakePointCache(String animation_name_in, int gapStep)
	{
		if (gapStep < 1) {
			gapStep = 1;
		}

		float store_run_time = getRunTime();
		CreatureAnimation cur_animation = animations.get(animation_name_in);
		if(cur_animation.hasCachePts())
		{
			// cache already generated, just exit
			return;
		}
		
		SetActiveAnimationName(animation_name_in, false);
				
		//for(int i = (int)cur_animation.start_time; i <= (int)cur_animation.end_time; i++)
		int i = (int)cur_animation.start_time;
		while(true)
		{
			run_time = (float)i;
			float[] new_pts = new float[target_creature.total_num_pts * 3];
			PoseCreature(animation_name_in, new_pts, getRunTime());

			int realStep = gapStep;
			if(i + realStep > cur_animation.end_time)
			{
				realStep = (int)cur_animation.end_time - i;
			}

			/*
			bool firstCase = realStep > 1;
			bool secondCase = cache_pts_list.Count >= 1;
			if(firstCase && secondCase)
			{
				// fill in the gaps
				var prev_pts = cache_pts_list[cache_pts_list.Count - 1];
				for(int j = 0; j < realStep; j++)
				{
					float factor = (float)j / (float)realStep;
					var gap_pts = InterpFloatList(prev_pts, new_pts, factor);
					cache_pts_list.Add (gap_pts);
				}
			}
			*/
			
			cur_animation.addCachePts((int)run_time, new_pts);
			i += realStep;

			if(i > cur_animation.end_time || realStep == 0)
			{
				break;
			}
		}
		
		setRunTime(store_run_time);
	}

    // Create an animation
    public void CreateAnimation(JsonValue load_data,
                                String name_in)
    {
        CreatureAnimation new_animation = new CreatureAnimation(load_data,
                name_in);
        AddAnimation(new_animation);
    }

    // Create all animations
    public void CreateAllAnimations(JsonValue load_data)
    {
        Vector<String> all_animation_names = CreatureModuleUtils.GetAllAnimationNames (load_data);
        for(String cur_name : all_animation_names)
        {
            CreateAnimation(load_data, cur_name);
        }

        SetActiveAnimationName (all_animation_names.get(0), false);
    }

    // Add an animation
    public void AddAnimation(CreatureAnimation animation_in)
    {
        animations.put(animation_in.name, animation_in);
        active_blend_run_times.put(animation_in.name, animation_in.start_time);
    }

    // Return an animation
    public CreatureAnimation
    GetAnimation(String name_in)
    {
        return animations.get(name_in);
    }

    // Return the creature
    public Creature
    GetCreature()
    {
        return target_creature;
    }

    // Returns all the animation names
    public Vector<String> GetAnimationNames()
    {
        Vector<String> ret_names = new Vector<String> ();
        for(String cur_name : animations.keySet()) {
            ret_names.add(cur_name);
        }

        return ret_names;
    }

    // Decides whether to use a custom user defined time range or not
    public void SetUseCustomTimeRane(boolean flag_in)
    {
        use_custom_time_range = flag_in;
    }

    // Sets the user defined custom time range
    public void SetCustomTimeRange(int start_time_in, int end_time_in)
    {
        custom_start_time = start_time_in;
        custom_end_time = end_time_in;
    }

    // Sets the bone overide callback for modifying/changing bone positions during playback
    public void SetBonesOverrideCallback(BonesCallback callback_in)
    {
        bones_override_callback = callback_in;
    }

    // Sets the current animation to be active by name
    public boolean SetActiveAnimationName(String name_in, boolean check_already_set)
    {
        if (name_in == null || (animations.containsKey(name_in) == false)) {
            return false;
        }

        if(check_already_set)
        {
            if(active_animation_name.equals(name_in))
            {
                return false;
            }
        }

        active_animation_name = name_in;
        CreatureAnimation cur_animation = animations.get(active_animation_name);
        run_time = cur_animation.start_time;

        UpdateRegionSwitches(name_in);
        
        return true;
    }

    // Returns the name of the currently active animation
    public String GetActiveAnimationName()
    {
        return active_animation_name;
    }

    // Returns the table of all animations
    public HashMap<String, CreatureAnimation >
    GetAllAnimations()
    {
        return animations;
    }

 // Update the region switching properties
    private void UpdateRegionSwitches(String animation_name_in)
    {
    	if (animations.containsKey(animation_name_in)) {
    		CreatureAnimation cur_animation = animations.get(animation_name_in);
    		
    		MeshBoneUtil.MeshDisplacementCacheManager displacement_cache_manager = cur_animation.displacement_cache;
    		Vector<MeshBoneUtil.MeshDisplacementCache> displacement_table =
    			displacement_cache_manager.displacement_cache_table.get(0);
    		
    		MeshBoneUtil.MeshUVWarpCacheManager uv_warp_cache_manager = cur_animation.uv_warp_cache;
    		Vector<MeshBoneUtil.MeshUVWarpCache> uv_swap_table =
    			uv_warp_cache_manager.uv_cache_table.get(0);
    		
    		MeshBoneUtil.MeshRenderBoneComposition render_composition =
    			target_creature.render_composition;
    		Vector<MeshBoneUtil.MeshRenderRegion> all_regions = render_composition.getRegions();

    		int index = 0;
    		for(int i = 0; i < all_regions.size(); i++) 
    		{
    			MeshBoneUtil.MeshRenderRegion cur_region = all_regions.get(i);
    			// Setup active or inactive displacements
    			Boolean use_local_displacements = !(displacement_table.get(index).getLocalDisplacements().size() == 0);
    			Boolean use_post_displacements = !(displacement_table.get(index).getPostDisplacements().size() == 0);
    			cur_region.setUseLocalDisplacements(use_local_displacements);
    			cur_region.setUsePostDisplacements(use_post_displacements);
    			
    			// Setup active or inactive uv swaps
    			cur_region.setUseUvWarp(uv_swap_table.get(index).getEnabled());
    			
    			index++;
    		}
    	}
    }

    // Returns if animation is playing
    boolean GetIsPlaying()
    {
        return is_playing;
    }

    // Sets whether the animation is playing
    public void SetIsPlaying(boolean flag_in)
    {
        is_playing = flag_in;
    }

    // Sets whether the animation loops or not
    public void SetShouldLoop(boolean flag_in) { should_loop = flag_in; }

    // Sets the run time of the animation
    public void setRunTime(float time_in)
    {
        run_time = time_in;
        correctTime ();
    }

    // Increments the run time of the animation by a delta value
    public void increRunTime(float delta_in)
    {
        run_time += delta_in;
        correctTime ();
    }

    public void correctTime()
    {
        CreatureAnimation cur_animation = animations.get(active_animation_name);

        float anim_start_time = cur_animation.start_time;
        float anim_end_time = cur_animation.end_time;

        if(use_custom_time_range)
        {
            anim_start_time = custom_start_time;
            anim_end_time = custom_end_time;
        }

        if(run_time > anim_end_time)
        {
            if(should_loop) {
                run_time = anim_start_time;
            }
            else {
                run_time = anim_end_time;
            }
        }
        else if(run_time < anim_start_time)
        {
            if(should_loop) {
                run_time = anim_end_time;
            }
            else {
                run_time = anim_start_time;
            }
        }
    }

    // Returns the current run time of the animation
    public float getRunTime()
    {
        return run_time;
    }

    // Runs a single step of the animation for a given delta timestep
    public void Update(float delta)
    {
        if(!is_playing)
        {
            return;
        }

        increRunTime(delta * time_scale);
        
        if(do_auto_blending) {
        	ProcessAutoBlending();
            IncreAutoBlendRunTimes(delta * time_scale);
        }

        RunCreature ();
    }

    public void RunAtTime(float time_in)
    {
        if(!is_playing)
        {
            return;
        }

        setRunTime (time_in);
        RunCreature ();
    }

    public void RunCreature()
    {
        if(do_blending)
        {
            for(int i = 0; i < 2; i++) {
				String cur_animation_name = active_blend_animation_names.get(i);
				CreatureAnimation cur_animation = animations.get(cur_animation_name);
				float cur_animation_run_time = active_blend_run_times.get(cur_animation_name);
				
            	if(cur_animation.hasCachePts())
            	{
        			cur_animation.poseFromCachePts(cur_animation_run_time, blend_render_pts.get(i), target_creature.total_num_pts);
					ApplyUVSwapsAndColorChanges(cur_animation_name, blend_render_pts.get(i), cur_animation_run_time);
					PoseJustBones(cur_animation_name, cur_animation_run_time);            		
            	}
            	else {
					UpdateRegionSwitches(active_blend_animation_names.get(i));
					PoseCreature(active_blend_animation_names.get(i), blend_render_pts.get(i), cur_animation_run_time);            		
            	}
            }

            for(int j = 0; j < target_creature.total_num_pts * 3; j++)
            {
                int set_data_index = j;
                float read_data_1 = blend_render_pts.get(0)[j];
                float read_data_2 = blend_render_pts.get(1)[j];
/*
                target_creature.render_pts[set_data_index] =
                        ((1.0f - blending_factor) * (read_data_1)) +
                                (blending_factor * (read_data_2));
*/
                target_creature.render_pts[set_data_index] =
                        ((1.0f - blending_factor) * (read_data_1)) +
                                (blending_factor * (read_data_2));

            }
        }
        else {
        	CreatureAnimation cur_animation = animations.get(active_animation_name);
        	if(cur_animation.hasCachePts())
        	{
    			cur_animation.poseFromCachePts(getRunTime(), target_creature.render_pts, target_creature.total_num_pts);
				ApplyUVSwapsAndColorChanges(active_animation_name, target_creature.render_pts, getRunTime());
				PoseJustBones(cur_animation.name, getRunTime());        		
        	}
        	else 
        	{
                PoseCreature(active_animation_name, target_creature.render_pts, getRunTime());        		
        	}
        }
        
        RunUVItemSwap();
    }

    // Sets scaling for time
    public void SetTimeScale(float scale_in)
    {
        time_scale = scale_in;
    }

    // Enables/Disables blending
    public void SetBlending(boolean flag_in)
    {
        do_blending = flag_in;

        if (do_blending) {
            if (blend_render_pts.get(0).length == 1) {
                float[] new_vec = new float[target_creature.total_num_pts * 3];
                for(int i = 0; i < target_creature.total_num_pts * 3; i++)
                {
                    new_vec[i] = 0;
                }

                blend_render_pts.set(0, new_vec);
            }

            if (blend_render_pts.get(1).length == 1) {
                float[] new_vec = new float[target_creature.total_num_pts * 3];
                for(int i = 0; i < target_creature.total_num_pts * 3; i++)
                {
                    new_vec[i] = 0;
                }

                blend_render_pts.set(1, new_vec);
            }

        }
    }


	// Sets auto blending
	public void SetAutoBlending(Boolean flag_in)
	{
		do_auto_blending = flag_in;
		SetBlending(flag_in);
		
		if(do_auto_blending)
		{
			AutoBlendTo(active_animation_name, 0.1f);
		}
	}

	// Use auto blending to blend to the next animation
	public void AutoBlendTo(String animation_name_in, float blend_delta)
	{
		if(animation_name_in == auto_blend_names.get(1))
		{
			// already blending to that so just return
			return;
		}
		
		ResetBlendTime(animation_name_in);
		
		auto_blend_delta = blend_delta;
		auto_blend_names.set(0, active_animation_name);
		auto_blend_names.set(1, animation_name_in);
		blending_factor = 0;
		
		active_animation_name = animation_name_in;
		
		SetBlendingAnimations(auto_blend_names.get(0), auto_blend_names.get(1));
	}

	public void ResetBendTime(String name_in)
	{
		CreatureAnimation cur_animation = animations.get(name_in);
		active_blend_run_times.put(name_in, cur_animation.start_time);
	}

	// Resets animation to start time
	public void ResetToStartTimes()
	{
		if(animations.containsKey(active_animation_name) == false)
		{
			return;
		}
		
		// reset non blend time
		CreatureAnimation cur_animation = animations.get(active_animation_name);
		run_time = cur_animation.start_time;
		
		// reset blend times too
		for (Map.Entry<String, Float> entry : active_blend_run_times.entrySet()) {
			ResetBlendTime(entry.getKey());
		}	
	}
	
	private void ResetBlendTime(String name_in)
	{
		// currently do nothing
	}

	private void ProcessAutoBlending()
	{
		// process blending factor
		blending_factor += auto_blend_delta;
		if(blending_factor > 1)
		{
			blending_factor = 1;
		}
	}

	private void IncreAutoBlendRunTimes(float delta_in)
	{
		String set_animation_name = "";
		for(int i = 0; i < auto_blend_names.size(); i++)
		{
			String cur_animation_name = auto_blend_names.get(i);
			if ((animations.containsKey(cur_animation_name)) 
			    && (set_animation_name.equals(cur_animation_name) == false))
			{
				float cur_run_time = active_blend_run_times.get(cur_animation_name);
				cur_run_time += delta_in;
				cur_run_time = correctRunTime(cur_run_time, cur_animation_name);

				active_blend_run_times.put(cur_animation_name, cur_run_time);
				
				set_animation_name = cur_animation_name;
			}
		}
	}    


	private float correctRunTime(float time_in, String animation_name)
	{
		float ret_time = time_in;
		CreatureAnimation cur_animation = animations.get(animation_name);
		float anim_start_time = cur_animation.start_time;
		float anim_end_time = cur_animation.end_time;
		
		if (ret_time > anim_end_time)
		{
			if (should_loop)
			{
				ret_time = anim_start_time;
			}
			else {
				ret_time = anim_end_time;
			}
		}
		else if (ret_time < anim_start_time)
		{
			if (should_loop)
			{
				ret_time = anim_end_time;
			}
			else {
				ret_time = anim_start_time;
			}
		}
		
		return ret_time;
	}

    // Sets blending animation names
    public void SetBlendingAnimations(String name_1, String name_2)
    {
        active_blend_animation_names.set(0, name_1);
        active_blend_animation_names.set(1, name_2);
    }

    // Sets the blending factor
    public void SetBlendingFactor(float value_in)
    {
        blending_factor = value_in;
    }

    // Given a set of coordinates in local creature space,
    // see if any bone is in contact
    public String IsContactBone(Vector2 pt_in,
                                float radius)
    {
        MeshBone cur_bone = target_creature.render_composition.getRootBone();
        return ProcessContactBone(pt_in, radius, cur_bone);
    }

    public String ProcessContactBone(Vector2 pt_in,
                                     float radius,
                                     MeshBone bone_in)
    {
        String ret_name = "";
        Vector3 diff_vec = bone_in.getWorldEndPt().cpy().sub(bone_in.getWorldStartPt());

        Vector2 cur_vec = new Vector2(diff_vec.x, diff_vec.y);
        float cur_length = (float)cur_vec.len();

        Vector2 unit_vec = cur_vec.cpy();
        unit_vec.nor();

        Vector2 norm_vec = new Vector2(unit_vec.y, unit_vec.x);

        Vector2 src_pt = new Vector2(bone_in.getWorldStartPt().x, bone_in.getWorldStartPt().y);
        Vector2 rel_vec = pt_in.cpy().sub(src_pt);
        float proj = (float)rel_vec.dot(unit_vec);

        if( (proj >= 0) && (proj <= cur_length))
        {
            float norm_proj = (float)rel_vec.dot(norm_vec);
            if(norm_proj <= radius)
            {
                return bone_in.getKey();
            }
        }

        Vector<MeshBone> cur_children = bone_in.getChildren();
        for(MeshBone cur_child : cur_children)
        {
            ret_name = ProcessContactBone(pt_in, radius, cur_child);
            if(!(ret_name.equals(""))) {
                break;
            }
        }

        return ret_name;
    }
    
    private void UpdateRegionColours()
    {
    	MeshBoneUtil.MeshRenderBoneComposition render_composition =
    			target_creature.render_composition;
		Vector<MeshBoneUtil.MeshRenderRegion> cur_regions = render_composition.getRegions();
		
		for (int i = 0; i < cur_regions.size(); i++) {
			MeshBoneUtil.MeshRenderRegion cur_region = cur_regions.get(i);
			float set_opacity = cur_region.opacity * 0.01f;

			int cur_rgba_index = cur_region.getStartPtIndex() * 4;
			for(int j = 0; j < cur_region.getNumPts(); j++)
			{
				target_creature.render_colours[cur_rgba_index] = set_opacity;
				target_creature.render_colours[cur_rgba_index + 1] = set_opacity;
				target_creature.render_colours[cur_rgba_index + 2] = set_opacity;
				target_creature.render_colours[cur_rgba_index + 3] = set_opacity;

				cur_rgba_index += 4;
			}

		}
    }

	private void ApplyUVSwapsAndColorChanges(String animation_name_in,
            float[] target_pts,
            float input_run_time)
	{
		CreatureAnimation cur_animation = animations.get(animation_name_in);
		
		MeshBoneUtil.MeshUVWarpCacheManager uv_warp_cache_manager = cur_animation.uv_warp_cache;
		MeshBoneUtil.MeshOpacityCacheManager opacity_cache_manager = cur_animation.opacity_cache;
		
		MeshBoneUtil.MeshRenderBoneComposition render_composition =
		target_creature.render_composition;
		
		HashMap<String, MeshBoneUtil.MeshRenderRegion> regions_map =
		render_composition.getRegionsMap();
		
		uv_warp_cache_manager.retrieveValuesAtTime(input_run_time,
		                  regions_map);
		
		opacity_cache_manager.retrieveValuesAtTime(input_run_time,
		                  regions_map);
		
		UpdateRegionColours ();
		
		Vector<MeshBoneUtil.MeshRenderRegion> cur_regions = render_composition.getRegions();
		for(int j = 0; j < cur_regions.size(); j++) {
			MeshBoneUtil.MeshRenderRegion cur_region = cur_regions.get(j);
		
			if(cur_region.use_uv_warp)
			{
				cur_region.runUvWarp();
			}
		
			// add in z offsets for different regions
			/*
			for(int k = cur_region.getStartPtIndex() * 3;
					k <= cur_region.getEndPtIndex() * 3; 
					k+=3)
			{
				target_pts[k + 2] = j * region_offsets_z;
			}
			*/
		}
	}

    
    public void PoseJustBones(String animation_name_in,
            float input_run_time)
    {
    	CreatureAnimation cur_animation = animations.get(animation_name_in);
    	MeshBoneUtil.MeshRenderBoneComposition render_composition =
    			target_creature.render_composition;

    	MeshBoneUtil.MeshBoneCacheManager bone_cache_manager = cur_animation.bones_cache;
    	HashMap<String, MeshBone> bones_map =
    			render_composition.getBonesMap();

    	bone_cache_manager.retrieveValuesAtTime(input_run_time, bones_map);

    	AlterBonesByAnchor(bones_map, animation_name_in); 
    	
    	if(bones_override_callback != null) 
    	{
    		bones_override_callback.run(bones_map);
    	}
	}
    
	public void RunUVItemSwap()
	{
		MeshBoneUtil.MeshRenderBoneComposition render_composition =
			target_creature.render_composition;			
		HashMap<String, MeshBoneUtil.MeshRenderRegion> regions_map =
			render_composition.getRegionsMap();

		HashMap<String, Vector<CreatureUVSwapPacket>> swap_packets = target_creature.uv_swap_packets;
		HashMap<String, Integer> active_swap_actions = target_creature.active_uv_swap_actions;

		if(swap_packets.isEmpty() || active_swap_actions.isEmpty())
		{
			return;
		}

		for(String actionKey : active_swap_actions.keySet())
		{
			if(regions_map.get(actionKey) != null)
			{
				int swap_tag = active_swap_actions.get(actionKey);
				Vector<CreatureUVSwapPacket> swap_list = swap_packets.get(actionKey);
				
				for(int j = 0; j < swap_list.size(); j++)
				{
					CreatureUVSwapPacket cur_item = swap_list.get(j);
					if(cur_item.tag == swap_tag)
					{
						// Perform UV Item Swap
						MeshRenderRegion cur_region = regions_map.get(actionKey);
						cur_region.setUvWarpLocalOffset(cur_item.local_offset);
						cur_region.setUvWarpGlobalOffset(cur_item.global_offset);
						cur_region.setUvWarpScale(cur_item.scale);
						cur_region.runUvWarp();

						break;
					}
				}
			}
		}
	}
	
	public void AlterBonesByAnchor(HashMap<String, MeshBoneUtil.MeshBone> bones_map, String animation_name_in)
	{
		if(target_creature.anchor_points_active == false)
		{
			return;
		}

		Vector2 anchor_point = target_creature.GetAnchorPoint(animation_name_in);
		Vector3 anchor_vector =  new Vector3(anchor_point.x, anchor_point.y, 0);
		
		for(String curKey : bones_map.keySet())
		{
			MeshBone cur_bone = bones_map.get(curKey);
			Vector3 start_pt = cur_bone.getWorldStartPt();
			Vector3 end_pt = cur_bone.getWorldEndPt();

			start_pt.sub(anchor_vector);
			end_pt.sub(anchor_vector);

			cur_bone.setWorldStartPt(start_pt);
			cur_bone.setWorldEndPt(end_pt);
		}
	}

    public void PoseCreature(String animation_name_in,
                             float[] target_pts,
                             float input_run_time)
    {
        CreatureAnimation cur_animation = animations.get(animation_name_in);

        MeshBoneUtil.MeshBoneCacheManager bone_cache_manager = cur_animation.bones_cache;
        MeshBoneUtil.MeshDisplacementCacheManager displacement_cache_manager = cur_animation.displacement_cache;
        MeshBoneUtil.MeshUVWarpCacheManager uv_warp_cache_manager = cur_animation.uv_warp_cache;

        MeshBoneUtil.MeshRenderBoneComposition render_composition =
                target_creature.render_composition;

        // Extract values from caches
        HashMap<String, MeshBone> bones_map =
                render_composition.getBonesMap();
        HashMap<String, MeshRenderRegion> regions_map =
                render_composition.getRegionsMap();

        bone_cache_manager.retrieveValuesAtTime(input_run_time,
                 bones_map);
        
        AlterBonesByAnchor(bones_map, animation_name_in);

        if(bones_override_callback != null)
        {
            bones_override_callback.run(bones_map);
        }

        displacement_cache_manager.retrieveValuesAtTime(input_run_time,
                regions_map);
        uv_warp_cache_manager.retrieveValuesAtTime(input_run_time,
                regions_map);


        // Do posing, decide if we are blending or not
        Vector<MeshRenderRegion> cur_regions =
                render_composition.getRegions();
        HashMap<String, MeshBone> cur_bones =
                render_composition.getBonesMap();

        render_composition.updateAllTransforms(false);
        for(int j = 0; j < cur_regions.size(); j++) {
            MeshBoneUtil.MeshRenderRegion cur_region = cur_regions.get(j);

            int cur_pt_index = cur_region.getStartPtIndex();


            cur_region.poseFinalPts(target_pts,
                    cur_pt_index * 3,
                    cur_bones);

            // add in z offsets for different regions
            /*
            for(int k = cur_region.getStartPtIndex() * 3;
                k <= cur_region.getEndPtIndex() * 3;
                k+=3)
            {
                target_pts.set(k + 2, j * 0.001f);
            }
            */
        }
    }


}
