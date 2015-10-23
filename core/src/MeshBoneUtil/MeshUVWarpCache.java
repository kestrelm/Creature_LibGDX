package MeshBoneUtil;
import com.badlogic.gdx.math.Vector2;

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


public class MeshUVWarpCache {
    public String key;
    public Vector2 uv_warp_local_offset, uv_warp_global_offset, uv_warp_scale;
    public boolean enabled;

    public MeshUVWarpCache(String key_in)
    {
        uv_warp_global_offset = new Vector2(0, 0);
        uv_warp_local_offset = new Vector2(0, 0);
        uv_warp_scale = new Vector2(-1,-1);
        key = key_in;
        enabled = false;
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

    public String getKey() {
        return key;
    }

    public void setEnabled(boolean flag_in)
    {
        enabled = flag_in;
    }

    public boolean getEnabled() {
        return enabled;
    }
}