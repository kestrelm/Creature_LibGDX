// automatically generated, do not modify

package CreatureFlatData;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class animationBone extends Table {
  public static animationBone getRootAsanimationBone(ByteBuffer _bb) { return getRootAsanimationBone(_bb, new animationBone()); }
  public static animationBone getRootAsanimationBone(ByteBuffer _bb, animationBone obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationBone __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public float startPt(int j) { int o = __offset(6); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int startPtLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer startPtAsByteBuffer() { return __vector_as_bytebuffer(6, 4); }
  public float endPt(int j) { int o = __offset(8); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int endPtLength() { int o = __offset(8); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer endPtAsByteBuffer() { return __vector_as_bytebuffer(8, 4); }

  public static int createanimationBone(FlatBufferBuilder builder,
      int name,
      int start_pt,
      int end_pt) {
    builder.startObject(3);
    animationBone.addEndPt(builder, end_pt);
    animationBone.addStartPt(builder, start_pt);
    animationBone.addName(builder, name);
    return animationBone.endanimationBone(builder);
  }

  public static void startanimationBone(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addStartPt(FlatBufferBuilder builder, int startPtOffset) { builder.addOffset(1, startPtOffset, 0); }
  public static int createStartPtVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startStartPtVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addEndPt(FlatBufferBuilder builder, int endPtOffset) { builder.addOffset(2, endPtOffset, 0); }
  public static int createEndPtVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startEndPtVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endanimationBone(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

