// automatically generated, do not modify

package CreatureFlatDataJava;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class anchorPointData extends Table {
  public static anchorPointData getRootAsanchorPointData(ByteBuffer _bb) { return getRootAsanchorPointData(_bb, new anchorPointData()); }
  public static anchorPointData getRootAsanchorPointData(ByteBuffer _bb, anchorPointData obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public anchorPointData __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public float point(int j) { int o = __offset(4); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int pointLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer pointAsByteBuffer() { return __vector_as_bytebuffer(4, 4); }
  public String animClipName() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer animClipNameAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }

  public static int createanchorPointData(FlatBufferBuilder builder,
      int point,
      int anim_clip_name) {
    builder.startObject(2);
    anchorPointData.addAnimClipName(builder, anim_clip_name);
    anchorPointData.addPoint(builder, point);
    return anchorPointData.endanchorPointData(builder);
  }

  public static void startanchorPointData(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addPoint(FlatBufferBuilder builder, int pointOffset) { builder.addOffset(0, pointOffset, 0); }
  public static int createPointVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startPointVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addAnimClipName(FlatBufferBuilder builder, int animClipNameOffset) { builder.addOffset(1, animClipNameOffset, 0); }
  public static int endanchorPointData(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

