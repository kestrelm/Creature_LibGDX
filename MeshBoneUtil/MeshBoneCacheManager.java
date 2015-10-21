package MeshBoneUtil;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

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


public class MeshBoneCacheManager {
    public java.util.Vector<java.util.Vector<MeshBoneCache> > bone_cache_table;
    public java.util.Vector<Boolean> bone_cache_data_ready;
    public int start_time, end_time;
    public boolean is_ready;


    public MeshBoneCacheManager()
    {
        is_ready = false;
        bone_cache_table = null;
        bone_cache_data_ready = null;
        bone_cache_table = new java.util.Vector<java.util.Vector<MeshBoneCache> > ();
        bone_cache_data_ready = new java.util.Vector<Boolean> ();
    }

    public void init(int start_time_in, int end_time_in)
    {
        start_time = start_time_in;
        end_time = end_time_in;

        int num_frames = end_time - start_time + 1;
        bone_cache_table.clear();

        bone_cache_data_ready.clear();
        for(int i = 0; i < num_frames; i++) {
            bone_cache_table.add(new java.util.Vector<MeshBoneCache>());
            bone_cache_data_ready.add(false);
        }

        is_ready = false;
    }

    public int getStartTime()
    {
        return start_time;
    }

    public int getEndime()
    {
        return end_time;
    }

    public int getIndexByTime(int time_in)
    {
        int retval = time_in - start_time;
        retval = (int)MathUtils.clamp((float) retval,
                (float) 0,
                (float) (bone_cache_table.size()) - 1);


        return retval;
    }

    public void retrieveValuesAtTime(float time_in,
                                     HashMap<String, MeshBone> bone_map)
    {
        int base_time = getIndexByTime((int)Math.floor((double)time_in));
        int end_time = getIndexByTime((int)Math.ceil((double)time_in));

        float ratio = (time_in - (float)base_time);

        if(bone_cache_data_ready.size() == 0) {
            return;
        }

        if((bone_cache_data_ready.get(base_time) == false)
                || (bone_cache_data_ready.get(end_time) == false))
        {
            return;
        }

        java.util.Vector<MeshBoneCache> base_cache = bone_cache_table.get(base_time);
        java.util.Vector<MeshBoneCache> end_cache = bone_cache_table.get(end_time);

        for(int i = 0; i < base_cache.size(); i++) {
            MeshBoneCache base_data = base_cache.get(i);
            MeshBoneCache end_data = end_cache.get(i);
            String cur_key = base_data.getKey();

            Vector3 final_world_start_pt = base_data.getWorldStartPt().cpy().scl(1.0f - ratio).add(
                    end_data.getWorldStartPt().cpy().scl(ratio));

            Vector3 final_world_end_pt = base_data.getWorldEndPt().cpy().scl(1.0f - ratio).add(
                    end_data.getWorldEndPt().cpy().scl(ratio));

            /*
            Vector3 final_world_start_pt = ((1.0f - ratio) * base_data.getWorldStartPt()) +
                    (ratio * end_data.getWorldStartPt());

            Vector3 final_world_end_pt = ((1.0f - ratio) * base_data.getWorldEndPt()) +
                    (ratio * end_data.getWorldEndPt());
            */

            bone_map.get(cur_key).setWorldStartPt(final_world_start_pt);
            bone_map.get(cur_key).setWorldEndPt(final_world_end_pt);
        }
    }

    public boolean allReady()
    {
        if(is_ready) {
            return true;
        }
        else {
            int num_frames = end_time - start_time + 1;
            int ready_cnt = 0;
            for(int i = 0; i < bone_cache_data_ready.size(); i++) {
                if(bone_cache_data_ready.get(i)) {
                    ready_cnt++;
                }
            }

            if(ready_cnt == num_frames) {
                is_ready = true;
            }
        }

        return is_ready;
    }

    public void makeAllReady()
    {
        for(int i = 0; i < bone_cache_data_ready.size(); i++) {
            bone_cache_data_ready.set(i, true);
        }
    }
}

