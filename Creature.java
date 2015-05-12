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
    public Vector<Float> global_pts, global_uvs;
    public Vector<Float> render_pts;
    public Vector<Float> render_colours;
    public int total_num_pts, total_num_indices;
    public MeshRenderBoneComposition render_composition;

    public Creature(JsonValue load_data)
    {
        total_num_pts = 0;
        total_num_indices = 0;
        global_indices = null;
        global_pts = null;
        global_uvs = null;
        render_pts = null;
        render_colours = null;
        render_composition = null;

        LoadFromData(load_data);
    }

    // Fills entire mesh with (r,g,b,a) colours
    public void FillRenderColours(float r, float g, float b, float a)
    {
        for(int i = 0; i < total_num_pts; i++)
        {
            int cur_colour_index = i * 4;
            render_colours.set(0 + cur_colour_index, r);
            render_colours.set(1 + cur_colour_index, g);
            render_colours.set(2 + cur_colour_index, b);
            render_colours.set(3 + cur_colour_index, a);
        }
    }

    public void LoadFromData(JsonValue load_data)
    {
        // Load points and topology
        JsonValue json_mesh = load_data.get("mesh");

        global_pts = CreatureModuleUtils.ReadFloatArray3DJSON(json_mesh, "points");
        total_num_pts = global_pts.size() / 3;

        global_indices = CreatureModuleUtils.ReadIntArrayJSON (json_mesh, "indices");
        total_num_indices = global_indices.size();

        global_uvs = CreatureModuleUtils.ReadFloatArrayJSON (json_mesh, "uvs");
        /*
        // Flip UVs
        for (int i = 0; i < global_uvs.size(); i+=2) {
            global_uvs.set(i + 1, 1.0f - global_uvs.get(i + 1));
        }
        */

        float[] tmp_bytes = new float[total_num_pts * 4];
        render_colours = new Vector<Float>();
        for(int i = 0; i < tmp_bytes.length; i++)
        {
            render_colours.add(tmp_bytes[i]);
        }
        FillRenderColours(1, 1, 1, 1);

        render_pts = new Vector<Float>(global_pts);

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
    }
}
