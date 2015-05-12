package MeshBoneUtil;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

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

public class MeshUVWarpCacheManager {
    public java.util.Vector<java.util.Vector<MeshUVWarpCache> > uv_cache_table;
    public java.util.Vector<Boolean> uv_cache_data_ready;
    public int start_time, end_time;
    public boolean is_ready;

    public MeshUVWarpCacheManager()
    {
        is_ready = false;
        uv_cache_table = null;
        uv_cache_data_ready = null;
        uv_cache_table = new java.util.Vector<java.util.Vector<MeshUVWarpCache> > ();
        uv_cache_data_ready = new java.util.Vector<Boolean> ();
    }

    public void init(int start_time_in, int end_time_in)
    {
        start_time = start_time_in;
        end_time = end_time_in;

        int num_frames = end_time - start_time + 1;
        uv_cache_table.clear();

        uv_cache_data_ready.clear();
        for(int i = 0; i < num_frames; i++) {
            uv_cache_table.add(new java.util.Vector<MeshUVWarpCache>());
            uv_cache_data_ready.add(false);
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
        retval = (int)MathUtils.clamp((float)retval,
                (float)0,
                (float)(uv_cache_table.size()) - 1);


        return retval;
    }

    public void retrieveValuesAtTime(float time_in,
                                     HashMap<String, MeshRenderRegion> regions_map)
    {
        int base_time = getIndexByTime((int)Math.floor(time_in));
        int end_time = getIndexByTime((int)Math.ceil(time_in));

        float ratio = (time_in - (float)base_time);

        if(uv_cache_data_ready.size() == 0) {
            return;
        }

        if((uv_cache_data_ready.get(base_time) == false)
                || (uv_cache_data_ready.get(end_time) == false))
        {
            return;
        }

        java.util.Vector<MeshUVWarpCache> base_cache = uv_cache_table.get(base_time);
        java.util.Vector<MeshUVWarpCache> end_cache = uv_cache_table.get(end_time);

        for(int i = 0; i < base_cache.size(); i++) {
            MeshUVWarpCache base_data = base_cache.get(i);
            MeshUVWarpCache end_data = end_cache.get(i);
            String cur_key = base_data.getKey();

            MeshRenderRegion set_region = regions_map.get(cur_key);
            if(set_region.getUseUvWarp()) {
                Vector2 final_local_offset = base_data.getUvWarpLocalOffset().cpy().scl(1.0f - ratio).add(
                        end_data.getUvWarpLocalOffset().cpy().scl(ratio));

                Vector2 final_global_offset = base_data.getUvWarpGlobalOffset().cpy().scl(1.0f - ratio).add(
                        end_data.getUvWarpGlobalOffset().cpy().scl(ratio));

                Vector2 final_scale = base_data.getUvWarpScale().cpy().scl(1.0f - ratio).add(
                        end_data.getUvWarpScale().cpy().scl(ratio));
                /*
                Vector2 final_local_offset = ((1.0f - ratio) * base_data.getUvWarpLocalOffset()) +
                        (ratio * end_data.getUvWarpLocalOffset());

                Vector2 final_global_offset = ((1.0f - ratio) * base_data.getUvWarpGlobalOffset()) +
                        (ratio * end_data.getUvWarpGlobalOffset());

                Vector2 final_scale = ((1.0f - ratio) * base_data.getUvWarpScale()) +
                        (ratio * end_data.getUvWarpScale());

                */


                set_region.setUvWarpLocalOffset(final_local_offset);
                set_region.setUvWarpGlobalOffset(final_global_offset);
                set_region.setUvWarpScale(final_scale);
            }
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
            for(int i = 0; i < uv_cache_data_ready.size(); i++) {
                if(uv_cache_data_ready.get(i)) {
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
        for(int i = 0; i < uv_cache_data_ready.size(); i++) {
            uv_cache_data_ready.set(i, true);
        }
    }

}

