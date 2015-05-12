package MeshBoneUtil;

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


import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;

public class Utils {
    public static Vector3 rotateVec4_90(Vector3 vec_in)
    {
        return new Vector3(-vec_in.y, vec_in.x, vec_in.z);
    }

    public static Matrix4 calcRotateMat(Vector3 vec_in)
    {
        Vector3 dir = vec_in.cpy();
        dir.nor();

        Vector3 pep_dir = rotateVec4_90(dir);

        Vector3 cur_tangent = new Vector3(dir.x, dir.y, 0);
        Vector3 cur_normal = new Vector3(pep_dir.x, pep_dir.y, 0);
        Vector3 cur_binormal = new Vector3(0, 0, 1);

        //XnaGeometry.Matrix cur_rotate(cur_tangent, cur_normal, cur_binormal, glm::vec4(0,0,0,1));
        Matrix4 cur_rotate = new Matrix4();
        //cur_rotate = Matrix.Identity; // Already identity by default

        /*
        cur_rotate.Right = cur_tangent;
        cur_rotate.Up = cur_normal;
        cur_rotate.Backward = cur_binormal;
        */

        cur_rotate.set(cur_tangent, cur_normal, cur_binormal, new Vector3(0,0,0));
        cur_rotate.tra();

        return cur_rotate;
    }

    public static Matrix4 mulMat(Matrix4 mat_in, float factor) {
        Matrix4 ret_mat = mat_in.cpy();
        float val[] = ret_mat.getValues();

        for(int i = 0; i < 16; i++)
        {
            val[i] *= factor;
        }

        ret_mat.set(val);

        return ret_mat;
    }

    public static Matrix4 addMat(Matrix4 mat1, Matrix4 mat2)
    {
        float vals[] = new float[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        Matrix4 ret_mat = new Matrix4(vals);
        for(int i = 0; i < 16; i++)
        {
            ret_mat.val[i] = mat1.val[i] + mat2.val[i];
        }

        return ret_mat;
    }

    public static float angleVec4(Vector3 vec_in)
    {
        float theta = (float)Math.atan2(vec_in.y, vec_in.x);
        if(theta < 0) {
            theta += 2.0f * (float)Math.PI;
        }

        return theta;
    }
}
