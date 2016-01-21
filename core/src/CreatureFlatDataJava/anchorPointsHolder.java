// automatically generated, do not modify

package CreatureFlatDataJava;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class anchorPointsHolder extends Table {
  public static anchorPointsHolder getRootAsanchorPointsHolder(ByteBuffer _bb) { return getRootAsanchorPointsHolder(_bb, new anchorPointsHolder()); }
  public static anchorPointsHolder getRootAsanchorPointsHolder(ByteBuffer _bb, anchorPointsHolder obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public anchorPointsHolder __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public anchorPointData anchorPoints(int j) { return anchorPoints(new anchorPointData(), j); }
  public anchorPointData anchorPoints(anchorPointData obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int anchorPointsLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }

  public static int createanchorPointsHolder(FlatBufferBuilder builder,
      int anchorPoints) {
    builder.startObject(1);
    anchorPointsHolder.addAnchorPoints(builder, anchorPoints);
    return anchorPointsHolder.endanchorPointsHolder(builder);
  }

  public static void startanchorPointsHolder(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addAnchorPoints(FlatBufferBuilder builder, int anchorPointsOffset) { builder.addOffset(0, anchorPointsOffset, 0); }
  public static int createAnchorPointsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startAnchorPointsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endanchorPointsHolder(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

