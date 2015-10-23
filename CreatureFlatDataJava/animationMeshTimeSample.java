// automatically generated, do not modify

package CreatureFlatData;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class animationMeshTimeSample extends Table {
  public static animationMeshTimeSample getRootAsanimationMeshTimeSample(ByteBuffer _bb) { return getRootAsanimationMeshTimeSample(_bb, new animationMeshTimeSample()); }
  public static animationMeshTimeSample getRootAsanimationMeshTimeSample(ByteBuffer _bb, animationMeshTimeSample obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationMeshTimeSample __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public animationMesh meshes(int j) { return meshes(new animationMesh(), j); }
  public animationMesh meshes(animationMesh obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int meshesLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public int time() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createanimationMeshTimeSample(FlatBufferBuilder builder,
      int meshes,
      int time) {
    builder.startObject(2);
    animationMeshTimeSample.addTime(builder, time);
    animationMeshTimeSample.addMeshes(builder, meshes);
    return animationMeshTimeSample.endanimationMeshTimeSample(builder);
  }

  public static void startanimationMeshTimeSample(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addMeshes(FlatBufferBuilder builder, int meshesOffset) { builder.addOffset(0, meshesOffset, 0); }
  public static int createMeshesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startMeshesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addTime(FlatBufferBuilder builder, int time) { builder.addInt(1, time, 0); }
  public static int endanimationMeshTimeSample(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

