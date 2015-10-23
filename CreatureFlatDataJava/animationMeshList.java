// automatically generated, do not modify

package CreatureFlatData;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class animationMeshList extends Table {
  public static animationMeshList getRootAsanimationMeshList(ByteBuffer _bb) { return getRootAsanimationMeshList(_bb, new animationMeshList()); }
  public static animationMeshList getRootAsanimationMeshList(ByteBuffer _bb, animationMeshList obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationMeshList __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public animationMeshTimeSample timeSamples(int j) { return timeSamples(new animationMeshTimeSample(), j); }
  public animationMeshTimeSample timeSamples(animationMeshTimeSample obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int timeSamplesLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }

  public static int createanimationMeshList(FlatBufferBuilder builder,
      int timeSamples) {
    builder.startObject(1);
    animationMeshList.addTimeSamples(builder, timeSamples);
    return animationMeshList.endanimationMeshList(builder);
  }

  public static void startanimationMeshList(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addTimeSamples(FlatBufferBuilder builder, int timeSamplesOffset) { builder.addOffset(0, timeSamplesOffset, 0); }
  public static int createTimeSamplesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startTimeSamplesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endanimationMeshList(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

