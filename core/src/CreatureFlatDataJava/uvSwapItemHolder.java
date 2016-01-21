// automatically generated, do not modify

package CreatureFlatDataJava;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class uvSwapItemHolder extends Table {
  public static uvSwapItemHolder getRootAsuvSwapItemHolder(ByteBuffer _bb) { return getRootAsuvSwapItemHolder(_bb, new uvSwapItemHolder()); }
  public static uvSwapItemHolder getRootAsuvSwapItemHolder(ByteBuffer _bb, uvSwapItemHolder obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public uvSwapItemHolder __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public uvSwapItemMesh meshes(int j) { return meshes(new uvSwapItemMesh(), j); }
  public uvSwapItemMesh meshes(uvSwapItemMesh obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int meshesLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }

  public static int createuvSwapItemHolder(FlatBufferBuilder builder,
      int meshes) {
    builder.startObject(1);
    uvSwapItemHolder.addMeshes(builder, meshes);
    return uvSwapItemHolder.enduvSwapItemHolder(builder);
  }

  public static void startuvSwapItemHolder(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addMeshes(FlatBufferBuilder builder, int meshesOffset) { builder.addOffset(0, meshesOffset, 0); }
  public static int createMeshesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startMeshesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int enduvSwapItemHolder(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

