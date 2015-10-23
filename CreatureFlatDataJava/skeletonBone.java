// automatically generated, do not modify

package CreatureFlatData;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class skeletonBone extends Table {
  public static skeletonBone getRootAsskeletonBone(ByteBuffer _bb) { return getRootAsskeletonBone(_bb, new skeletonBone()); }
  public static skeletonBone getRootAsskeletonBone(ByteBuffer _bb, skeletonBone obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public skeletonBone __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public int id() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public float restParentMat(int j) { int o = __offset(8); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int restParentMatLength() { int o = __offset(8); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer restParentMatAsByteBuffer() { return __vector_as_bytebuffer(8, 4); }
  public float localRestStartPt(int j) { int o = __offset(10); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int localRestStartPtLength() { int o = __offset(10); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer localRestStartPtAsByteBuffer() { return __vector_as_bytebuffer(10, 4); }
  public float localRestEndPt(int j) { int o = __offset(12); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int localRestEndPtLength() { int o = __offset(12); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer localRestEndPtAsByteBuffer() { return __vector_as_bytebuffer(12, 4); }
  public int children(int j) { int o = __offset(14); return o != 0 ? bb.getInt(__vector(o) + j * 4) : 0; }
  public int childrenLength() { int o = __offset(14); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer childrenAsByteBuffer() { return __vector_as_bytebuffer(14, 4); }

  public static int createskeletonBone(FlatBufferBuilder builder,
      int name,
      int id,
      int restParentMat,
      int localRestStartPt,
      int localRestEndPt,
      int children) {
    builder.startObject(6);
    skeletonBone.addChildren(builder, children);
    skeletonBone.addLocalRestEndPt(builder, localRestEndPt);
    skeletonBone.addLocalRestStartPt(builder, localRestStartPt);
    skeletonBone.addRestParentMat(builder, restParentMat);
    skeletonBone.addId(builder, id);
    skeletonBone.addName(builder, name);
    return skeletonBone.endskeletonBone(builder);
  }

  public static void startskeletonBone(FlatBufferBuilder builder) { builder.startObject(6); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addId(FlatBufferBuilder builder, int id) { builder.addInt(1, id, 0); }
  public static void addRestParentMat(FlatBufferBuilder builder, int restParentMatOffset) { builder.addOffset(2, restParentMatOffset, 0); }
  public static int createRestParentMatVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startRestParentMatVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addLocalRestStartPt(FlatBufferBuilder builder, int localRestStartPtOffset) { builder.addOffset(3, localRestStartPtOffset, 0); }
  public static int createLocalRestStartPtVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startLocalRestStartPtVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addLocalRestEndPt(FlatBufferBuilder builder, int localRestEndPtOffset) { builder.addOffset(4, localRestEndPtOffset, 0); }
  public static int createLocalRestEndPtVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startLocalRestEndPtVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addChildren(FlatBufferBuilder builder, int childrenOffset) { builder.addOffset(5, childrenOffset, 0); }
  public static int createChildrenVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addInt(data[i]); return builder.endVector(); }
  public static void startChildrenVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endskeletonBone(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

