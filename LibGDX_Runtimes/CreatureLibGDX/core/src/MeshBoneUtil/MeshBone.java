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


public class MeshBone
{
    public Matrix4 rest_parent_mat, rest_parent_inv_mat;
    public Matrix4 rest_world_mat, rest_world_inv_mat;
    public Matrix4 bind_world_mat, bind_world_inv_mat, parent_world_mat, parent_world_inv_mat;
    public Vector3 local_rest_start_pt, local_rest_end_pt;
    public Vector3 local_rest_dir, local_rest_normal_dir, local_binormal_dir;
    public Vector3 world_rest_start_pt, world_rest_end_pt;
    public Vector3 world_rest_pos;
    public float world_rest_angle;
    public float rest_length;
    public String key;
    public int tag_id;

    public Vector3 world_start_pt, world_end_pt;
    public Matrix4 world_delta_mat;
    public dualQuat world_dq;

    public java.util.Vector<MeshBone> children;

    public MeshBone(
            String key_in,
            Vector3 start_pt_in,
            Vector3 end_pt_in,
            Matrix4 parent_transform)
    {
        key = key_in;
        world_rest_angle = 0;
        rest_parent_mat = new Matrix4();
        rest_parent_inv_mat = new Matrix4();
        rest_world_mat = new Matrix4();
        rest_world_inv_mat = new Matrix4();
        bind_world_mat = new Matrix4();
        bind_world_inv_mat = new Matrix4();
        parent_world_mat = new Matrix4();
        parent_world_inv_mat = new Matrix4();

        setRestParentMat(parent_transform, null);
        setLocalRestStartPt(start_pt_in);
        setLocalRestEndPt(end_pt_in);
        setParentWorldInvMat(new Matrix4());
        setParentWorldMat(new Matrix4());
        local_binormal_dir = new Vector3(0,0,1);
        tag_id = 0;
        children = new java.util.Vector<MeshBone>();
    }

    public void setRestParentMat(Matrix4 transform_in,
                                 Matrix4 inverse_in)
    {
        rest_parent_mat = transform_in;

        if(inverse_in == null) {
            rest_parent_inv_mat = new Matrix4(rest_parent_mat);
            rest_parent_inv_mat.inv();
            //Matrix4.Invert(ref rest_parent_mat, out rest_parent_inv_mat);
        }
        else {
            rest_parent_inv_mat = new Matrix4(inverse_in);
        }
    }

    public void setParentWorldMat(Matrix4 transform_in)
    {
        parent_world_mat = transform_in;
    }

    public void setParentWorldInvMat(Matrix4 transform_in)
    {
        parent_world_inv_mat = transform_in;
    }

    public Vector3 getLocalRestStartPt()
    {
        return local_rest_start_pt;
    }

    public Vector3 getLocalRestEndPt()
    {
        return local_rest_end_pt;
    }

    public void setLocalRestStartPt(Vector3 world_pt_in)
    {
        //local_rest_start_pt = Vector3.Transform(world_pt_in, rest_parent_inv_mat);
        local_rest_start_pt = world_pt_in.cpy().traMul(rest_parent_inv_mat);
        calcRestData();
    }

    public void setLocalRestEndPt(Vector3 world_pt_in)
    {
        //local_rest_end_pt = Vector3.Transform(world_pt_in, rest_parent_inv_mat);
        local_rest_end_pt = world_pt_in.cpy().traMul(rest_parent_inv_mat);
        calcRestData();
    }

    public void calcRestData()
    {
        if(local_rest_start_pt == null || local_rest_end_pt == null)
        {
            return;
        }

        Tuple<Vector3, Vector3>
                calc = computeDirs(local_rest_start_pt, local_rest_end_pt);

        local_rest_dir = calc.x;
        local_rest_normal_dir = calc.y;

        computeRestLength();
    }

    public void setWorldStartPt(Vector3 world_pt_in)
    {
        world_start_pt = world_pt_in;
    }

    public void setWorldEndPt(Vector3 world_pt_in)
    {
        world_end_pt = world_pt_in;
    }

    public void fixDQs(dualQuat ref_dq)
    {
//        if( Quaternion.Dot(world_dq.real, ref_dq.real) < 0) {
        if( world_dq.real.dot(ref_dq.real) < 0) {
            world_dq.real = world_dq.real.cpy().mul(-1);
            world_dq.imaginary = world_dq.imaginary.cpy().mul(-1);
        }

        for(int i = 0; i < children.size(); i++) {
            MeshBone cur_child = children.get(i);
            cur_child.fixDQs(world_dq);
        }
    }

    public void initWorldPts()
    {
        setWorldStartPt(getWorldRestStartPt());
        setWorldEndPt(getWorldRestEndPt());

        for(int i = 0; i < children.size(); i++) {
            children.get(i).initWorldPts();
        }
    }

    public Vector3 getWorldRestStartPt()
    {
        //Vector3 ret_vec = Vector3.Transform(local_rest_start_pt, rest_parent_mat);
        Matrix4 tmp_mat = rest_parent_mat.cpy();
        tmp_mat.tra();
        Vector3 ret_vec = local_rest_start_pt.cpy().traMul(tmp_mat);

        return ret_vec;
    }

    public Vector3 getWorldRestEndPt()
    {
//        Vector3 ret_vec = Vector3.Transform(local_rest_end_pt, rest_parent_mat);
        Matrix4 tmp_mat = rest_parent_mat.cpy();
        tmp_mat.tra();
        Vector3 ret_vec = local_rest_end_pt.cpy().traMul(tmp_mat);

        return ret_vec;
    }

    public float getWorldRestAngle()
    {
        return world_rest_angle;
    }

    public Vector3 getWorldRestPos()
    {
        return world_rest_pos;
    }

    public Vector3 getWorldStartPt()
    {
        return world_start_pt;
    }

    public Vector3 getWorldEndPt()
    {
        return world_end_pt;
    }

    public Matrix4 getRestParentMat()
    {
        return rest_parent_mat;
    }

    public Matrix4 getRestWorldMat()
    {
        return rest_world_mat;
    }

    public Matrix4 getWorldDeltaMat()
    {
        return world_delta_mat;
    }

    public Matrix4 getParentWorldMat()
    {
        return parent_world_mat;
    }

    public Matrix4 getParentWorldInvMat()
    {
        return parent_world_inv_mat;
    }

    public dualQuat getWorldDq()
    {
        return world_dq;
    }

    public void computeRestParentTransforms()
    {
        Vector3 cur_tangent = new Vector3(local_rest_dir.x, local_rest_dir.y, 0);
        Vector3 cur_binormal = new Vector3(local_binormal_dir.x, local_binormal_dir.y, local_binormal_dir.z);
        Vector3 cur_normal = new Vector3(local_rest_normal_dir.x, local_rest_normal_dir.y, 0);

        Matrix4 cur_translate = new Matrix4();
        cur_translate.setTranslation(local_rest_end_pt.x, local_rest_end_pt.y, 0);

        Matrix4 cur_rotate = new Matrix4();
        /*
        cur_rotate.Right = cur_tangent;
        cur_rotate.Up = cur_normal;
        cur_rotate.Backward = cur_binormal;
        */
        cur_rotate.set(cur_tangent, cur_normal, cur_binormal, new Vector3(0,0,0));
        cur_rotate.tra();

        //Matrix4 cur_final = cur_translate * cur_rotate;
        Matrix4 cur_final = cur_translate.cpy().mul(cur_rotate);

        //rest_world_mat = rest_parent_mat * cur_final;
        rest_world_mat = rest_parent_mat.cpy().mul(cur_final);

        rest_world_inv_mat = rest_world_mat.cpy();
        rest_world_inv_mat.inv();
        //Matrix4.Invert(ref rest_world_mat, out rest_world_inv_mat);

        Vector3 world_rest_dir = getWorldRestEndPt().cpy().sub( getWorldRestStartPt());
        world_rest_dir.nor();
        world_rest_angle = Utils.angleVec4(world_rest_dir);
        world_rest_pos = getWorldRestStartPt();


        Matrix4 bind_translate = new Matrix4();
        bind_translate.setTranslation(getWorldRestStartPt().x, getWorldRestStartPt().y, 0);

        Matrix4 bind_rotate = Utils.calcRotateMat(getWorldRestEndPt().cpy().sub( getWorldRestStartPt()));
        //Matrix4 cur_bind_final = bind_translate * bind_rotate;
        Matrix4 cur_bind_final = bind_translate.cpy().mul(bind_rotate);

        bind_world_mat = cur_bind_final.cpy();
        bind_world_inv_mat = bind_world_mat.cpy();
        bind_world_inv_mat.inv();
        //Matrix4.Invert(ref bind_world_mat, out bind_world_inv_mat);

        for(int i = 0; i < children.size(); i++) {
            MeshBone cur_bone = children.get(i);
            cur_bone.setRestParentMat(rest_world_mat, rest_world_inv_mat);
            cur_bone.computeRestParentTransforms();
        }
    }

    public void computeParentTransforms()
    {
        Matrix4 translate_parent = new Matrix4();
        translate_parent.setTranslation(getWorldEndPt().x, getWorldEndPt().y, 0);

        Matrix4 rotate_parent = Utils.calcRotateMat(getWorldEndPt().cpy().sub(getWorldStartPt()));

//			Matrix4 final_transform = translate_parent * rotate_parent;
        Matrix4 final_transform = translate_parent.cpy().mul(rotate_parent);

        Matrix4 final_inv_transform = final_transform.cpy();
        final_inv_transform.inv();
        //Matrix4.Invert(ref final_transform, out final_inv_transform);

        for(int i = 0; i < children.size(); i++) {
            MeshBone cur_bone = children.get(i);
            cur_bone.setParentWorldMat(final_transform);
            cur_bone.setParentWorldInvMat(final_inv_transform);
            cur_bone.computeParentTransforms();
        }
    }

    public void computeWorldDeltaTransforms()
    {
        Tuple<Vector3, Vector3> calc = computeDirs(world_start_pt, world_end_pt);
        Vector3 cur_tangent = new Vector3(calc.x.x, calc.x.y, 0);
        Vector3 cur_normal = new Vector3(calc.y.x, calc.y.y, 0);
        Vector3 cur_binormal = new Vector3(local_binormal_dir.x, local_binormal_dir.y, local_binormal_dir.z);

        Matrix4 cur_rotate = new Matrix4();
        /*
        cur_rotate.Right = cur_tangent;
        cur_rotate.Up = cur_normal;
        cur_rotate.Backward = cur_binormal;
        */
        cur_rotate.set(cur_tangent, cur_normal, cur_binormal, new Vector3(0,0,0));
        cur_rotate.tra();

        Matrix4 cur_translate = new Matrix4();
        cur_translate.setTranslation(world_start_pt.x, world_start_pt.y, 0);

			/*
			world_delta_mat = (cur_translate * cur_rotate)
				* bind_world_inv_mat;
		   */

        world_delta_mat = (cur_translate.cpy().mul(cur_rotate)).mul(bind_world_inv_mat);
        //world_delta_mat = bind_world_inv_mat.cpy().mul(cur_rotate.cpy().mul(cur_translate));


//        Quaternion cur_quat = XnaGeometry.Quaternion.CreateFromRotationMatrix(world_delta_mat);
        Quaternion cur_quat = new Quaternion().setFromMatrix(world_delta_mat);
        if(cur_quat.z < 0) {
            //cur_quat = -cur_quat;
        }


        Vector3 tmp_pos = new Vector3();
        world_delta_mat.getTranslation(tmp_pos);
        world_dq = new dualQuat(cur_quat, tmp_pos);

        for(int i = 0; i < children.size(); i++) {
            MeshBone cur_bone = children.get(i);
            cur_bone.computeWorldDeltaTransforms();
        }
    }

    public void addChild(MeshBone bone_in)
    {
        bone_in.setRestParentMat(rest_world_mat, rest_world_inv_mat);
        children.add(bone_in);
    }

    public java.util.Vector<MeshBone> getChildren() {
        return children;
    }

    public boolean hasBone(MeshBone bone_in)
    {
        for(int i = 0; i < children.size(); i++) {
            MeshBone cur_bone = children.get(i);
            if(cur_bone == bone_in) {
                return true;
            }
        }

        return false;
    }

    public MeshBone getChildByKey(String search_key)
    {
        if(key.equals(search_key)) {
            return this;
        }

        MeshBone ret_data = null;
        for(int i = 0; i < children.size(); i++) {
            MeshBone cur_bone = children.get(i);

            MeshBone result = cur_bone.getChildByKey(search_key);
            if(result != null) {
                ret_data = result;
                break;
            }
        }

        return ret_data;
    }

    public String getKey()
    {
        return key;
    }

    public java.util.Vector<String> getAllBoneKeys()
    {
        java.util.Vector<String> ret_data = new java.util.Vector<String>();
        ret_data.add(getKey());

        for(int i = 0; i < children.size(); i++) {
            java.util.Vector<String> append_data = children.get(i).getAllBoneKeys();
            ret_data.addAll( append_data);
        }

        return ret_data;
    }

    public java.util.Vector<MeshBone> getAllChildren()
    {
        java.util.Vector<MeshBone> ret_data = new java.util.Vector<MeshBone>();
        ret_data.add(this);
        for(int i = 0; i < children.size(); i++) {
            java.util.Vector<MeshBone> append_data = children.get(i).getAllChildren();
            ret_data.addAll(append_data);
        }

        return ret_data;
    }

    public int getBoneDepth(MeshBone bone_in, int depth)
    {
        if(bone_in == this) {
            return depth;
        }

        for(int i = 0; i < children.size(); i++) {
            MeshBone cur_bone = children.get(i);
            int ret_val = cur_bone.getBoneDepth(bone_in, depth + 1);
            if(ret_val != -1) {
                return ret_val;
            }
        }

        return -1;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    public void deleteChildren()
    {
        for(int i = 0; i < children.size(); i++) {
            MeshBone cur_bone = children.get(i);
            cur_bone.deleteChildren();
        }

        children.clear();
    }

    public void setTagId(int value_in)
    {
        tag_id = value_in;
    }

    public int getTagId()
    {
        return tag_id;
    }

    public Tuple<Vector3, Vector3>
    computeDirs(Vector3 start_pt, Vector3 end_pt)
    {
        Vector3 tangent = end_pt.cpy().sub(start_pt);
        tangent.nor();

        Vector3 normal = Utils.rotateVec4_90(tangent);

        return new Tuple<Vector3, Vector3> (tangent, normal);
    }

    public void computeRestLength()
    {
        Vector3 tmp_dir = local_rest_end_pt.cpy().sub(local_rest_start_pt);
        rest_length = (float)tmp_dir.len();
    }

}



