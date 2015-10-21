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

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;


public class dualQuat {
    public Quaternion real, imaginary;

    public dualQuat()
    {
        real = new Quaternion();

        real.w = 0;
        real.x = 0;
        real.y = 0;
        real.z = 0;

        imaginary = real.cpy();
    }

    public dualQuat(Quaternion q0, Vector3 t)
    {
        real = q0;
        imaginary = new Quaternion();
        imaginary.w = -0.5f * ( t.x * q0.x + t.y * q0.y + t.z * q0.z);
        imaginary.x =  0.5f * ( t.x * q0.w + t.y * q0.z - t.z * q0.y);
        imaginary.y =  0.5f * (-t.x * q0.z + t.y * q0.w + t.z * q0.x);
        imaginary.z =  0.5f * ( t.x * q0.y - t.y * q0.x + t.z * q0.w);
    }
    
    public void zeroOut()
    {
        real.w = 0;
        real.x = 0;
        real.y = 0;
        real.z = 0;

        imaginary.w = 0;
        imaginary.x = 0;
        imaginary.y = 0;
        imaginary.z = 0;
    }

    public void add(dualQuat quat_in, float real_factor, float imaginary_factor)
    {
        real = real.add((quat_in.real.cpy().mul(real_factor)));
        imaginary = imaginary.add(quat_in.imaginary.cpy().mul(imaginary_factor));

    }

    public void convertToMat(Matrix4 m)
    {
        float cur_length = (float)real.dot(real);
        float w = (float)real.w , x = (float)real.x, y = (float)real.y, z = (float)real.z;
        float t0 = (float)imaginary.w, t1 = (float)imaginary.x, t2 = (float)imaginary.y, t3 = (float)imaginary.z;

        m.val[Matrix4.M01] = w*w + x*x - y*y - z*z;
        m.val[Matrix4.M02] = 2 * x * y - 2 * w * z;
        m.val[Matrix4.M03] = 2 * x * z + 2 * w * y;

        m.val[Matrix4.M11] = 2 * x * y + 2 * w * z;
        m.val[Matrix4.M12] = w * w + y * y - x * x - z * z;
        m.val[Matrix4.M13] = 2 * y * z - 2 * w * x;

        m.val[Matrix4.M21] = 2 * x * z - 2 * w * y;
        m.val[Matrix4.M22] = 2 * y * z + 2 * w * x;
        m.val[Matrix4.M23] = w * w + z * z - x * x - y * y;

        m.val[Matrix4.M31] = -2 * t0 * x + 2 * w * t1 - 2 * t2 * z + 2 * y * t3;
        m.val[Matrix4.M32] = -2 * t0 * y + 2 * t1 * z - 2 * x * t3 + 2 * w * t2;
        m.val[Matrix4.M33] = -2 * t0 * z + 2 * x * t2 + 2 * w * t3 - 2 * t1 * y;

        // ??
        m.val[Matrix4.M03] = 0;
        m.val[Matrix4.M13] = 0;
        m.val[Matrix4.M23] = 0;
        m.val[Matrix4.M33] = cur_length;

        for(int i = 0; i < 16; i++) {
            m.val[i] /= cur_length;
        }
        //m /= cur_length;
    }

    public void normalize()
    {
        float norm = (float)Math.sqrt(real.w * real.w + real.x * real.x + real.y * real.y + real.z * real.z);

        real = real.mul((float)1.0 / norm);
        imaginary = imaginary.mul((float)1.0 / norm);
    }

    public Vector3 transform(Vector3 p)
    {
        Vector3 v0 = new Vector3(0,0,0);
        v0.x = real.x; v0.y = real.y; v0.z = real.z;

        Vector3 ve = new Vector3(0,0,0);
        ve.x = imaginary.x; ve.y = imaginary.y; ve.z = imaginary.z;

        Vector3 trans;
        //trans = (ve*real.w - v0*imaginary.w + Vector3.Cross(v0, ve)) * 2.0f;

        Vector3 tmpVec1 = v0.cpy().scl((float)imaginary.w);
        Vector3 tmpVec2 = v0.cpy().crs(ve);
        Vector3 tmpVec0 = ve.cpy().scl(real.w);
        trans = tmpVec0.sub(tmpVec1).add(tmpVec2);
        trans.scl(2.0f);

        Vector3 rot = real.transform(p.cpy());

        //return (XnaGeometry.Vector3.Transform(p, real)) + trans;
        return rot.add(trans);
    }


};
