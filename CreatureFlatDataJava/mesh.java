// automatically generated, do not modify

package CreatureFlatData;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class mesh extends Table {
  public static mesh getRootAsmesh(ByteBuffer _bb) { return getRootAsmesh(_bb, new mesh()); }
  public static mesh getRootAsmesh(ByteBuffer _bb, mesh obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public mesh __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public float points(int j) { int o = __offset(4); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int pointsLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer pointsAsByteBuffer() { return __vector_as_bytebuffer(4, 4); }
  public float uvs(int j) { int o = __offset(6); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int uvsLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer uvsAsByteBuffer() { return __vector_as_bytebuffer(6, 4); }
  public int indices(int j) { int o = __offset(8); return o != 0 ? bb.getInt(__vector(o) + j * 4) : 0; }
  public int indicesLength() { int o = __offset(8); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer indicesAsByteBuffer() { return __vector_as_bytebuffer(8, 4); }
  public meshRegion regions(int j) { return regions(new meshRegion(), j); }
  public meshRegion regions(meshRegion obj, int j) { int o = __offset(10); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int regionsLength() { int o = __offset(10); return o != 0 ? __vector_len(o) : 0; }

  public static int createmesh(FlatBufferBuilder builder,
      int points,
      int uvs,
      int indices,
      int regions) {
    builder.startObject(4);
    mesh.addRegions(builder, regions);
    mesh.addIndices(builder, indices);
    mesh.addUvs(builder, uvs);
    mesh.addPoints(builder, points);
    return mesh.endmesh(builder);
  }

  public static void startmesh(FlatBufferBuilder builder) { builder.startObject(4); }
  public static void addPoints(FlatBufferBuilder builder, int pointsOffset) { builder.addOffset(0, pointsOffset, 0); }
  public static int createPointsVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startPointsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addUvs(FlatBufferBuilder builder, int uvsOffset) { builder.addOffset(1, uvsOffset, 0); }
  public static int createUvsVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startUvsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addIndices(FlatBufferBuilder builder, int indicesOffset) { builder.addOffset(2, indicesOffset, 0); }
  public static int createIndicesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addInt(data[i]); return builder.endVector(); }
  public static void startIndicesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addRegions(FlatBufferBuilder builder, int regionsOffset) { builder.addOffset(3, regionsOffset, 0); }
  public static int createRegionsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startRegionsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endmesh(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

