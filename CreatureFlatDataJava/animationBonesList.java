// automatically generated, do not modify

package CreatureFlatData;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class animationBonesList extends Table {
  public static animationBonesList getRootAsanimationBonesList(ByteBuffer _bb) { return getRootAsanimationBonesList(_bb, new animationBonesList()); }
  public static animationBonesList getRootAsanimationBonesList(ByteBuffer _bb, animationBonesList obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationBonesList __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public animationBonesTimeSample timeSamples(int j) { return timeSamples(new animationBonesTimeSample(), j); }
  public animationBonesTimeSample timeSamples(animationBonesTimeSample obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int timeSamplesLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }

  public static int createanimationBonesList(FlatBufferBuilder builder,
      int timeSamples) {
    builder.startObject(1);
    animationBonesList.addTimeSamples(builder, timeSamples);
    return animationBonesList.endanimationBonesList(builder);
  }

  public static void startanimationBonesList(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addTimeSamples(FlatBufferBuilder builder, int timeSamplesOffset) { builder.addOffset(0, timeSamplesOffset, 0); }
  public static int createTimeSamplesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startTimeSamplesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endanimationBonesList(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

