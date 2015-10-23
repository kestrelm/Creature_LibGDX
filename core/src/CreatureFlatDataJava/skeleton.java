// automatically generated, do not modify

package CreatureFlatDataJava;

import java.nio.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class skeleton extends Table {
  public static skeleton getRootAsskeleton(ByteBuffer _bb) { return getRootAsskeleton(_bb, new skeleton()); }
  public static skeleton getRootAsskeleton(ByteBuffer _bb, skeleton obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public skeleton __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public skeletonBone bones(int j) { return bones(new skeletonBone(), j); }
  public skeletonBone bones(skeletonBone obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int bonesLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }

  public static int createskeleton(FlatBufferBuilder builder,
      int bones) {
    builder.startObject(1);
    skeleton.addBones(builder, bones);
    return skeleton.endskeleton(builder);
  }

  public static void startskeleton(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addBones(FlatBufferBuilder builder, int bonesOffset) { builder.addOffset(0, bonesOffset, 0); }
  public static int createBonesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startBonesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endskeleton(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

