package MeshBoneUtil;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Gdx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
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


// Class for the creature character
public class Creature {
    // mesh and skeleton data
    public Vector<Integer> global_indices;
    public float[] global_pts, global_uvs;
    public float[] render_pts;
    public float[] render_colours;
    public int total_num_pts, total_num_indices;
    public MeshRenderBoneComposition render_composition;
    public HashMap<String, java.util.Vector<CreatureUVSwapPacket>> uv_swap_packets;
    public HashMap<String, Integer> active_uv_swap_actions;
	public HashMap<String, Vector2> anchor_point_map;
	public Boolean anchor_points_active;
	
    public Creature(JsonValue load_data)
    {
    	StartInit();
        LoadFromData(load_data);
    }
    
    public Creature(CreatureFlatDataJava.rootData flatRoot)
    {
    	StartInit();
    	LoadFromDataFlat(flatRoot);
    }
    
    private void StartInit()
    {
        total_num_pts = 0;
        total_num_indices = 0;
        global_indices = null;
        global_pts = null;
        global_uvs = null;
        render_pts = null;
        render_colours = null;
        render_composition = null;
		uv_swap_packets = new  HashMap<String, java.util.Vector<CreatureUVSwapPacket>>();
		active_uv_swap_actions = new HashMap<String, Integer>();
		anchor_point_map = new HashMap<String, Vector2>();
		anchor_points_active = false;
    }

    // Fills entire mesh with (r,g,b,a) colours
    public void FillRenderColours(float r, float g, float b, float a)
    {
        for(int i = 0; i < total_num_pts; i++)
        {
            int cur_colour_index = i * 4;
            render_colours[0 + cur_colour_index] = r;
            render_colours[1 + cur_colour_index] = g;
            render_colours[2 + cur_colour_index] = b;
            render_colours[3 + cur_colour_index] = a;
        }
    }
    
    public void LoadFromData(JsonValue load_data)
    {
        // Load points and topology
        JsonValue json_mesh = load_data.get("mesh");

        global_pts = CreatureModuleUtils.ReadFloatArray3DJSON(json_mesh, "points");
        total_num_pts = global_pts.length / 3;

        global_indices = CreatureModuleUtils.ReadIntArrayJSON (json_mesh, "indices");
        total_num_indices = global_indices.size();

        global_uvs = CreatureModuleUtils.ReadFloatArrayJSON (json_mesh, "uvs");
        /*
        // Flip UVs
        for (int i = 0; i < global_uvs.size(); i+=2) {
            global_uvs.set(i + 1, 1.0f - global_uvs.get(i + 1));
        }
        */

        render_colours = new float[total_num_pts * 4];
        FillRenderColours(1, 1, 1, 1);

        render_pts = Arrays.copyOf(global_pts, global_pts.length);

        // Load bones
        MeshBone root_bone = CreatureModuleUtils.CreateBones(load_data,
                "skeleton");


        // Load regions
        Vector<MeshRenderRegion> regions = CreatureModuleUtils.CreateRegions(json_mesh,
                "regions",
                global_indices,
                global_pts,
                global_uvs);

        // Add into composition
        render_composition = new MeshRenderBoneComposition();
        render_composition.setRootBone(root_bone);
        render_composition.getRootBone().computeRestParentTransforms();

        for(MeshRenderRegion cur_region : regions) {
            cur_region.setMainBoneKey(root_bone.getKey());
            cur_region.determineMainBone(root_bone);
            render_composition.addRegion(cur_region);
        }

        render_composition.initBoneMap();
        render_composition.initRegionsMap();

        for(MeshRenderRegion cur_region : regions) {
            cur_region.initFastNormalWeightMap(render_composition.bones_map);
        }

        render_composition.resetToWorldRestPts();
        
        // Fill up uv swap packets
        uv_swap_packets = CreatureModuleUtils.FillSwapUvPacketMap(load_data, "uv_swap_items");
        
        // Load Anchor Points
        anchor_point_map = CreatureModuleUtils.FillAnchorPointMap(load_data, "anchor_point_items");
    }
    
    public void LoadFromDataFlat(CreatureFlatDataJava.rootData flatRoot)
    {
    	CreatureFlatDataJava.mesh flat_mesh = flatRoot.dataMesh();
    	CreatureFlatDataJava.skeleton flat_skeleton = flatRoot.dataSkeleton();
    	
        global_pts = flat_mesh.ReadPoints(); //CreatureModuleUtils.ReadFloatArray3DJSON(json_mesh, "points");
        total_num_pts = global_pts.length / 3;

        global_indices = flat_mesh.ReadIndices(); //CreatureModuleUtils.ReadIntArrayJSON (json_mesh, "indices");
        total_num_indices = global_indices.size();

        global_uvs = flat_mesh.ReadUvs(); //CreatureModuleUtils.ReadFloatArrayJSON (json_mesh, "uvs");
        /*
        // Flip UVs
        for (int i = 0; i < global_uvs.size(); i+=2) {
            global_uvs.set(i + 1, 1.0f - global_uvs.get(i + 1));
        }
        */

        render_colours = new float[total_num_pts * 4];
        FillRenderColours(1, 1, 1, 1);

        render_pts = Arrays.copyOf(global_pts, global_pts.length);

        // Load bones
        MeshBone root_bone = CreatureModuleUtils.CreateBonesFlat(flat_skeleton);


        // Load regions
        Vector<MeshRenderRegion> regions = CreatureModuleUtils.CreateRegionsFlat(flat_mesh,
                global_indices,
                global_pts,
                global_uvs);

        // Add into composition
        render_composition = new MeshRenderBoneComposition();
        render_composition.setRootBone(root_bone);
        render_composition.getRootBone().computeRestParentTransforms();

        for(MeshRenderRegion cur_region : regions) {
            cur_region.setMainBoneKey(root_bone.getKey());
            cur_region.determineMainBone(root_bone);
            render_composition.addRegion(cur_region);
        }

        render_composition.initBoneMap();
        render_composition.initRegionsMap();

        for(MeshRenderRegion cur_region : regions) {
            cur_region.initFastNormalWeightMap(render_composition.bones_map);
        }

        render_composition.resetToWorldRestPts();
        
        // Fill up uv swap packets
        CreatureFlatDataJava.uvSwapItemHolder flat_uv_swap_item_holder = flatRoot.dataUvSwapItem();
        uv_swap_packets = CreatureModuleUtils.FillSwapUvPacketMapFlat(flat_uv_swap_item_holder);
        
        // Load Anchor Points
        CreatureFlatDataJava.anchorPointsHolder flat_anchor_holder = flatRoot.dataAnchorPoints();
        anchor_point_map = CreatureModuleUtils.FillAnchorPointMapFlat(flat_anchor_holder);
    }

	public void SetActiveItemSwap(String region_name, int swap_idx)
	{
		active_uv_swap_actions.put(region_name, swap_idx);
	}

	public void RemoveActiveItemSwap(String region_name)
	{
		active_uv_swap_actions.remove(region_name);
	}
	

	public Vector2 GetAnchorPoint(String anim_clip_name_in)
	{
		if(anchor_point_map.get(anim_clip_name_in) != null)
		{
			return anchor_point_map.get(anim_clip_name_in);
		}

		return new Vector2(0,0);
	}
}
