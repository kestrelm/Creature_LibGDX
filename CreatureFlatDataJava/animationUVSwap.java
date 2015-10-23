// automatically generated, do not modify

package CreatureFlatData;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class animationUVSwap extends Table {
  public static animationUVSwap getRootAsanimationUVSwap(ByteBuffer _bb) { return getRootAsanimationUVSwap(_bb, new animationUVSwap()); }
  public static animationUVSwap getRootAsanimationUVSwap(ByteBuffer _bb, animationUVSwap obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationUVSwap __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public float localOffset(int j) { int o = __offset(6); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int localOffsetLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer localOffsetAsByteBuffer() { return __vector_as_bytebuffer(6, 4); }
  public float globalOffset(int j) { int o = __offset(8); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int globalOffsetLength() { int o = __offset(8); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer globalOffsetAsByteBuffer() { return __vector_as_bytebuffer(8, 4); }
  public float scale(int j) { int o = __offset(10); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int scaleLength() { int o = __offset(10); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer scaleAsByteBuffer() { return __vector_as_bytebuffer(10, 4); }
  public boolean enabled() { int o = __offset(12); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }

  public static int createanimationUVSwap(FlatBufferBuilder builder,
      int name,
      int local_offset,
      int global_offset,
      int scale,
      boolean enabled) {
    builder.startObject(5);
    animationUVSwap.addScale(builder, scale);
    animationUVSwap.addGlobalOffset(builder, global_offset);
    animationUVSwap.addLocalOffset(builder, local_offset);
    animationUVSwap.addName(builder, name);
    animationUVSwap.addEnabled(builder, enabled);
    return animationUVSwap.endanimationUVSwap(builder);
  }

  public static void startanimationUVSwap(FlatBufferBuilder builder) { builder.startObject(5); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addLocalOffset(FlatBufferBuilder builder, int localOffsetOffset) { builder.addOffset(1, localOffsetOffset, 0); }
  public static int createLocalOffsetVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startLocalOffsetVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addGlobalOffset(FlatBufferBuilder builder, int globalOffsetOffset) { builder.addOffset(2, globalOffsetOffset, 0); }
  public static int createGlobalOffsetVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startGlobalOffsetVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addScale(FlatBufferBuilder builder, int scaleOffset) { builder.addOffset(3, scaleOffset, 0); }
  public static int createScaleVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startScaleVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addEnabled(FlatBufferBuilder builder, boolean enabled) { builder.addBoolean(4, enabled, false); }
  public static int endanimationUVSwap(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

