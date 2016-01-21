// automatically generated, do not modify

package CreatureFlatDataJava;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class uvSwapItemData extends Table {
  public static uvSwapItemData getRootAsuvSwapItemData(ByteBuffer _bb) { return getRootAsuvSwapItemData(_bb, new uvSwapItemData()); }
  public static uvSwapItemData getRootAsuvSwapItemData(ByteBuffer _bb, uvSwapItemData obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public uvSwapItemData __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public float localOffset(int j) { int o = __offset(4); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int localOffsetLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer localOffsetAsByteBuffer() { return __vector_as_bytebuffer(4, 4); }
  public float globalOffset(int j) { int o = __offset(6); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int globalOffsetLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer globalOffsetAsByteBuffer() { return __vector_as_bytebuffer(6, 4); }
  public float scale(int j) { int o = __offset(8); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int scaleLength() { int o = __offset(8); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer scaleAsByteBuffer() { return __vector_as_bytebuffer(8, 4); }
  public int tag() { int o = __offset(10); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createuvSwapItemData(FlatBufferBuilder builder,
      int local_offset,
      int global_offset,
      int scale,
      int tag) {
    builder.startObject(4);
    uvSwapItemData.addTag(builder, tag);
    uvSwapItemData.addScale(builder, scale);
    uvSwapItemData.addGlobalOffset(builder, global_offset);
    uvSwapItemData.addLocalOffset(builder, local_offset);
    return uvSwapItemData.enduvSwapItemData(builder);
  }

  public static void startuvSwapItemData(FlatBufferBuilder builder) { builder.startObject(4); }
  public static void addLocalOffset(FlatBufferBuilder builder, int localOffsetOffset) { builder.addOffset(0, localOffsetOffset, 0); }
  public static int createLocalOffsetVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startLocalOffsetVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addGlobalOffset(FlatBufferBuilder builder, int globalOffsetOffset) { builder.addOffset(1, globalOffsetOffset, 0); }
  public static int createGlobalOffsetVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startGlobalOffsetVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addScale(FlatBufferBuilder builder, int scaleOffset) { builder.addOffset(2, scaleOffset, 0); }
  public static int createScaleVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startScaleVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addTag(FlatBufferBuilder builder, int tag) { builder.addInt(3, tag, 0); }
  public static int enduvSwapItemData(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

