
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

package MeshBoneUtil;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;

import java.util.HashMap;
import java.util.Vector;

public class MeshRenderRegion {
    public int start_pt_index, end_pt_index;
    public int start_index, end_index;
    public java.util.Vector<Integer> store_indices;
    public float[] store_rest_pts,
            store_uvs;
    public java.util.Vector<Vector2> local_displacements;
    public boolean use_local_displacements;
    public java.util.Vector<Vector2> post_displacements;
    public boolean use_post_displacements;
    public boolean use_uv_warp;
    public Vector2 uv_warp_local_offset, uv_warp_global_offset, uv_warp_scale;
    public java.util.Vector<Vector2> uv_warp_ref_uvs;
    public HashMap<String, float[] > normal_weight_map;
    public java.util.Vector<float[] > fast_normal_weight_map;
    public Vector<MeshBone> fast_bones_map;
    public Vector<Vector<Integer> > relevant_bones_indices;
    public String main_bone_key;
    public MeshBone main_bone;
    public boolean use_dq;
    public String name;
    public int tag_id;

    public MeshRenderRegion(java.util.Vector<Integer> indices_in,
                            float[] rest_pts_in,
                            float[] uvs_in,
                            int start_pt_index_in,
                            int end_pt_index_in,
                            int start_index_in,
                            int end_index_in)
    {
        store_indices = indices_in;
        store_rest_pts = rest_pts_in;
        store_uvs = uvs_in;

        use_local_displacements = false;
        use_post_displacements = false;
        use_uv_warp = false;
        uv_warp_local_offset = new Vector2(0,0);
        uv_warp_global_offset = new Vector2(0,0);
        uv_warp_scale = new Vector2(1,1);
        start_pt_index = start_pt_index_in;
        end_pt_index = end_pt_index_in;
        start_index = start_index_in;
        end_index = end_index_in;
        main_bone = null;
        local_displacements = new java.util.Vector<Vector2>();
        post_displacements = new java.util.Vector<Vector2>();
        uv_warp_ref_uvs = new java.util.Vector<Vector2>();
        normal_weight_map = new HashMap<String, float[]>();
        fast_normal_weight_map = new java.util.Vector<float[]>();
        fast_bones_map = new Vector<MeshBone>();
        relevant_bones_indices = new Vector<Vector<Integer> >();
        use_dq = true;
        tag_id = -1;

        initUvWarp();
    }

    public int getIndicesIndex()
    {
        // return store_indices + (start_index);
        return start_index;
    }

    public int getRestPtsIndex()
    {
        // return store_rest_pts + (3 * start_pt_index);
        return 3 * start_pt_index;
    }

    public int getUVsIndex()
    {
        // return store_uvs + (2  * start_pt_index);
        return 2  * start_pt_index;
    }

    public int getNumPts()
    {
        return end_pt_index - start_pt_index + 1;
    }

    public int getStartPtIndex()
    {
        return start_pt_index;
    }

    public int getEndPtIndex()
    {
        return end_pt_index;
    }

    public int getNumIndices()
    {
        return end_index - start_index + 1;
    }

    public int getStartIndex()
    {
        return start_index;
    }

    public int getEndIndex()
    {
        return end_index;
    }

    public void poseFinalPts(float[] output_pts,
                             int output_start_index,
                             HashMap<String, MeshBone> bones_map)
    {
        int read_pt_index = getRestPtsIndex();
        int write_pt_index = output_start_index;

        // point posing
        int readNumPts = getNumPts();
        dualQuat accum_dq = new dualQuat();

        for(int i = 0; i < readNumPts; i++) {
            Vector3 cur_rest_pt =
                    new Vector3(store_rest_pts[0 + read_pt_index],
                            store_rest_pts[1 + read_pt_index],
                            store_rest_pts[2 + read_pt_index]);

            if(use_local_displacements) {
                cur_rest_pt.x += local_displacements.get(i).x;
                cur_rest_pt.y += local_displacements.get(i).y;
            }

            accum_dq.zeroOut();
            
            Vector<Integer> bone_indices = relevant_bones_indices.get(i);
            for(int k = 0; k < bone_indices.size(); k++)
            {
            	int j = bone_indices.get(k);
            	MeshBone cur_bone = fast_bones_map.get(j);
            	float cur_weight_val = fast_normal_weight_map.get(j)[i];
            	float cur_im_weight_val  = cur_weight_val;
            	
            	dualQuat world_dq = cur_bone.getWorldDq();
            	accum_dq.add(world_dq, cur_weight_val, cur_im_weight_val);
            }

            Vector3 final_pt = new Vector3(0,0,0);
            accum_dq.normalize();
            Vector3 tmp_pt = new Vector3(cur_rest_pt.x, cur_rest_pt.y, cur_rest_pt.z);
            final_pt = accum_dq.transform(tmp_pt);

            // debug start

            // debug end

            if(use_post_displacements) {
                /*
                output_pts.get(0 + write_pt_index) += (float)post_displacements.get(i).x;
                output_pts.get(1 + write_pt_index) += (float)post_displacements.get(i).y;
                */
                final_pt.x += post_displacements.get(i).x;
                final_pt.y += post_displacements.get(i).y;
            }

            output_pts[0 + write_pt_index] = final_pt.x;
            output_pts[1 + write_pt_index] = final_pt.y;
            output_pts[2 + write_pt_index] = final_pt.z;

            /*
				output_pts[0 + write_pt_index] = (float)final_pt.X;
				output_pts[1 + write_pt_index] = (float)final_pt.Y;
				output_pts[2 + write_pt_index] = (float)final_pt.Z;
            */



            read_pt_index += 3;
            write_pt_index += 3;
        }

        // uv warping
        if(use_uv_warp) {
            runUvWarp();
        }
    }

    public void setMainBoneKey(String key_in)
    {
        main_bone_key = key_in;
    }

    public void determineMainBone(MeshBone root_bone_in)
    {
        main_bone = root_bone_in.getChildByKey(main_bone_key);
    }

    public void setUseDq(boolean flag_in)
    {
        use_dq = flag_in;
    }

    public void setName(String name_in)
    {
        name = name_in;
    }

    public String getName()
    {
        return name;
    }

    public void setUseLocalDisplacements(boolean flag_in)
    {
        use_local_displacements = flag_in;
        if((local_displacements.size() != getNumPts())
                && use_local_displacements)
        {
            local_displacements.clear();
            for(int i = 0; i < getNumPts(); i++) {
                local_displacements.add (new Vector2(0,0));
            }
        }
    }

    public boolean getUseLocalDisplacements()
    {
        return use_local_displacements;
    }

    public void setUsePostDisplacements(boolean flag_in)
    {
        use_post_displacements = flag_in;
        if((post_displacements.size() != getNumPts())
                && use_post_displacements)
        {
            post_displacements.clear();
            for(int i = 0; i < getNumPts(); i++) {
                post_displacements.add (new Vector2(0,0));
            }
        }
    }

    public boolean getUsePostDisplacements()
    {
        return use_post_displacements;
    }

    public Vector2 getRestLocalPt(int index_in)
    {
        int read_pt_index = getRestPtsIndex() + (3 * index_in);
        Vector2 return_pt = new Vector2(store_rest_pts[0 + read_pt_index],
                                        store_rest_pts[1 + read_pt_index]);
        return return_pt;
    }

    public int getLocalIndex(int index_in)
    {
        int read_index = getIndicesIndex() + index_in;
        return store_indices.get(read_index);
    }

    public void clearLocalDisplacements()
    {
        for(int i = 0; i < local_displacements.size(); i++) {
            local_displacements.set(i, new Vector2(0,0));
        }
    }

    public void clearPostDisplacements()
    {
        for(int i = 0; i < post_displacements.size(); i++) {
            post_displacements.set(i, new Vector2(0,0));
        }
    }

    public void setUseUvWarp(boolean flag_in)
    {
        use_uv_warp = flag_in;
        if(use_uv_warp == false) {
            restoreRefUv();
        }
    }

    public boolean getUseUvWarp()
    {
        return use_uv_warp;
    }

    public void setUvWarpLocalOffset(Vector2 vec_in)
    {
        uv_warp_local_offset = vec_in;
    }

    public void setUvWarpGlobalOffset(Vector2 vec_in)
    {
        uv_warp_global_offset = vec_in;
    }

    public void setUvWarpScale(Vector2 vec_in)
    {
        uv_warp_scale = vec_in;
    }

    public Vector2 getUvWarpLocalOffset()
    {
        return uv_warp_local_offset;
    }

    public Vector2 getUvWarpGlobalOffset()
    {
        return uv_warp_global_offset;
    }

    public Vector2 getUvWarpScale()
    {
        return uv_warp_scale;
    }

    public void runUvWarp()
    {
        int cur_uvs_index = getUVsIndex();
        for(int i = 0; i < uv_warp_ref_uvs.size(); i++) {
            Vector2 set_uv = uv_warp_ref_uvs.get(i).cpy();
            set_uv.sub(uv_warp_local_offset);
            set_uv.scl(uv_warp_scale);
            set_uv.add(uv_warp_global_offset);

            /*
            set_uv -= uv_warp_local_offset;
            set_uv *= uv_warp_scale;
            set_uv += uv_warp_global_offset;
            */

            store_uvs[0 + cur_uvs_index] = set_uv.x;
            store_uvs[1 + cur_uvs_index] = set_uv.y;

            /*
            store_uvs[0 + cur_uvs_index] = (float)set_uv.X;
            store_uvs[1 + cur_uvs_index] = (float)set_uv.Y;
            */


            cur_uvs_index += 2;
        }
    }

    public void restoreRefUv()
    {
        int cur_uvs_index = getUVsIndex();
        for(int i = 0; i < uv_warp_ref_uvs.size(); i++) {
            Vector2 set_uv = uv_warp_ref_uvs.get(i);

            store_uvs[0 + cur_uvs_index] = set_uv.x;
            store_uvs[1 + cur_uvs_index] = set_uv.y;

            /*
            store_uvs[0 + cur_uvs_index] = (float)set_uv.X;
            store_uvs[1 + cur_uvs_index] = (float)set_uv.Y;
            */

            cur_uvs_index += 2;
        }
    }

    public int getTagId()
    {
        return tag_id;
    }

    public void setTagId(int value_in)
    {
        tag_id = value_in;
    }

    public void initFastNormalWeightMap(HashMap<String, MeshBone> bones_map)
    {
    	fast_normal_weight_map.clear();
    	fast_bones_map.clear();
    	relevant_bones_indices.clear();
    	
        for (HashMap.Entry<String, MeshBone> cur_iter : bones_map.entrySet()) {
            String cur_key = cur_iter.getKey();
            float[] values = normal_weight_map.get(cur_key);
            fast_normal_weight_map.add(values);
            
            fast_bones_map.add(bones_map.get(cur_key));
        }
        
        for(int i = 0; i < getNumPts(); i++)
        {
        	Vector<Integer> relevant_array = new Vector<Integer>();
        	float cutoff_val = 0.05f;
        	for(int j = 0; j < fast_normal_weight_map.size(); j++)
        	{
        		float sample_val = fast_normal_weight_map.get(j)[i];
        		if(sample_val > cutoff_val)
        		{
        			relevant_array.add(j);
        		}
        	}
        	
        	relevant_bones_indices.add(relevant_array);
        }
    }

    public void initUvWarp()
    {
        int cur_uvs_index = getUVsIndex();
//        uv_warp_ref_uvs = new java.util.Vector<Vector2>(new Vector2[getNumPts()]);
        uv_warp_ref_uvs = new java.util.Vector<Vector2>();

        for(int i = 0; i < getNumPts(); i++) {
            uv_warp_ref_uvs.add(new Vector2(0,0));
/*
            uv_warp_ref_uvs[i] = new Vector2(store_uvs[cur_uvs_index],
                    store_uvs[cur_uvs_index + 1]);
*/
            uv_warp_ref_uvs.set(i, new Vector2(store_uvs[cur_uvs_index],
                    store_uvs[cur_uvs_index + 1]));

            cur_uvs_index += 2;
        }
    }

}

