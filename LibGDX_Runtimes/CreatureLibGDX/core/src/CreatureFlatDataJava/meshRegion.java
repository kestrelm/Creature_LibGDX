// automatically generated, do not modify

package CreatureFlatDataJava;

import java.nio.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class meshRegion extends Table {
  public static meshRegion getRootAsmeshRegion(ByteBuffer _bb) { return getRootAsmeshRegion(_bb, new meshRegion()); }
  public static meshRegion getRootAsmeshRegion(ByteBuffer _bb, meshRegion obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public meshRegion __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public int startPtIndex() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int endPtIndex() { int o = __offset(8); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int startIndex() { int o = __offset(10); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int endIndex() { int o = __offset(12); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public int id() { int o = __offset(14); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public meshRegionBone weights(int j) { return weights(new meshRegionBone(), j); }
  public meshRegionBone weights(meshRegionBone obj, int j) { int o = __offset(16); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int weightsLength() { int o = __offset(16); return o != 0 ? __vector_len(o) : 0; }

  public static int createmeshRegion(FlatBufferBuilder builder,
      int name,
      int start_pt_index,
      int end_pt_index,
      int start_index,
      int end_index,
      int id,
      int weights) {
    builder.startObject(7);
    meshRegion.addWeights(builder, weights);
    meshRegion.addId(builder, id);
    meshRegion.addEndIndex(builder, end_index);
    meshRegion.addStartIndex(builder, start_index);
    meshRegion.addEndPtIndex(builder, end_pt_index);
    meshRegion.addStartPtIndex(builder, start_pt_index);
    meshRegion.addName(builder, name);
    return meshRegion.endmeshRegion(builder);
  }

  public static void startmeshRegion(FlatBufferBuilder builder) { builder.startObject(7); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addStartPtIndex(FlatBufferBuilder builder, int startPtIndex) { builder.addInt(1, startPtIndex, 0); }
  public static void addEndPtIndex(FlatBufferBuilder builder, int endPtIndex) { builder.addInt(2, endPtIndex, 0); }
  public static void addStartIndex(FlatBufferBuilder builder, int startIndex) { builder.addInt(3, startIndex, 0); }
  public static void addEndIndex(FlatBufferBuilder builder, int endIndex) { builder.addInt(4, endIndex, 0); }
  public static void addId(FlatBufferBuilder builder, int id) { builder.addInt(5, id, 0); }
  public static void addWeights(FlatBufferBuilder builder, int weightsOffset) { builder.addOffset(6, weightsOffset, 0); }
  public static int createWeightsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startWeightsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endmeshRegion(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

