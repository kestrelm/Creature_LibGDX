package MeshBoneUtil;
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

public class MeshRenderBoneComposition {
    public MeshBone root_bone;
    public HashMap<String, MeshBone> bones_map;
    public java.util.Vector<MeshRenderRegion> regions;
    public HashMap<String, MeshRenderRegion> regions_map;

    public MeshRenderBoneComposition()
    {
        root_bone = null;
        bones_map = new HashMap<String, MeshBone>();
        regions = new java.util.Vector<MeshRenderRegion>();
        regions_map = new HashMap<String, MeshRenderRegion>();
    }

    public void addRegion(MeshRenderRegion region_in)
    {
        regions.add(region_in);
    }

    public void setRootBone(MeshBone root_bone_in)
    {
        root_bone = root_bone_in;
    }

    public MeshBone getRootBone()
    {
        return root_bone;
    }

    public void initBoneMap()
    {
        bones_map = MeshRenderBoneComposition.genBoneMap(root_bone);
    }

    public void initRegionsMap()
    {
        regions_map.clear();
        for(int i = 0; i < regions.size(); i++) {
            String cur_key = regions.get(i).getName();
            regions_map.put(cur_key, regions.get(i));
        }
    }

    public static HashMap<String, MeshBone> genBoneMap(MeshBone input_bone)
    {
        HashMap<String, MeshBone> ret_map = new HashMap<String, MeshBone>();
        java.util.Vector<String> all_keys = input_bone.getAllBoneKeys();
        for(int i = 0; i < all_keys.size(); i++) {
            String cur_key = all_keys.get(i);
            ret_map.put(cur_key, input_bone.getChildByKey(cur_key));
        }

        return ret_map;
    }

    public HashMap<String, MeshBone> getBonesMap()
    {
        return bones_map;
    }

    public HashMap<String, MeshRenderRegion> getRegionsMap()
    {
        return regions_map;
    }

    public java.util.Vector<MeshRenderRegion> getRegions()
    {
        return regions;
    }

    public MeshRenderRegion getRegionWithId(int id_in)
    {
        for(int i = 0; i < regions.size(); i++) {
            MeshRenderRegion cur_region = regions.get(i);
            if(cur_region.getTagId() == id_in) {
                return cur_region;
            }
        }

        return null;
    }

    public void resetToWorldRestPts()
    {
        getRootBone().initWorldPts();
    }

    public void updateAllTransforms(boolean update_parent_xf)
    {
        if(update_parent_xf) {
            getRootBone().computeParentTransforms();
        }

        getRootBone().computeWorldDeltaTransforms();
        getRootBone().fixDQs(getRootBone().getWorldDq());
    }
}

