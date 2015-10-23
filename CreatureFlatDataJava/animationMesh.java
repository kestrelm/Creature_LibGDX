// automatically generated, do not modify

package CreatureFlatData;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class animationMesh extends Table {
  public static animationMesh getRootAsanimationMesh(ByteBuffer _bb) { return getRootAsanimationMesh(_bb, new animationMesh()); }
  public static animationMesh getRootAsanimationMesh(ByteBuffer _bb, animationMesh obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationMesh __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public boolean useDq() { int o = __offset(6); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }
  public boolean useLocalDisplacements() { int o = __offset(8); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }
  public boolean usePostDisplacements() { int o = __offset(10); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }
  public float localDisplacements(int j) { int o = __offset(12); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int localDisplacementsLength() { int o = __offset(12); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer localDisplacementsAsByteBuffer() { return __vector_as_bytebuffer(12, 4); }
  public float postDisplacements(int j) { int o = __offset(14); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int postDisplacementsLength() { int o = __offset(14); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer postDisplacementsAsByteBuffer() { return __vector_as_bytebuffer(14, 4); }

  public static int createanimationMesh(FlatBufferBuilder builder,
      int name,
      boolean use_dq,
      boolean use_local_displacements,
      boolean use_post_displacements,
      int local_displacements,
      int post_displacements) {
    builder.startObject(6);
    animationMesh.addPostDisplacements(builder, post_displacements);
    animationMesh.addLocalDisplacements(builder, local_displacements);
    animationMesh.addName(builder, name);
    animationMesh.addUsePostDisplacements(builder, use_post_displacements);
    animationMesh.addUseLocalDisplacements(builder, use_local_displacements);
    animationMesh.addUseDq(builder, use_dq);
    return animationMesh.endanimationMesh(builder);
  }

  public static void startanimationMesh(FlatBufferBuilder builder) { builder.startObject(6); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addUseDq(FlatBufferBuilder builder, boolean useDq) { builder.addBoolean(1, useDq, false); }
  public static void addUseLocalDisplacements(FlatBufferBuilder builder, boolean useLocalDisplacements) { builder.addBoolean(2, useLocalDisplacements, false); }
  public static void addUsePostDisplacements(FlatBufferBuilder builder, boolean usePostDisplacements) { builder.addBoolean(3, usePostDisplacements, false); }
  public static void addLocalDisplacements(FlatBufferBuilder builder, int localDisplacementsOffset) { builder.addOffset(4, localDisplacementsOffset, 0); }
  public static int createLocalDisplacementsVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startLocalDisplacementsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addPostDisplacements(FlatBufferBuilder builder, int postDisplacementsOffset) { builder.addOffset(5, postDisplacementsOffset, 0); }
  public static int createPostDisplacementsVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startPostDisplacementsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endanimationMesh(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

