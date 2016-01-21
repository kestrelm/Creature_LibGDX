// automatically generated, do not modify

package CreatureFlatDataJava;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class uvSwapItemMesh extends Table {
  public static uvSwapItemMesh getRootAsuvSwapItemMesh(ByteBuffer _bb) { return getRootAsuvSwapItemMesh(_bb, new uvSwapItemMesh()); }
  public static uvSwapItemMesh getRootAsuvSwapItemMesh(ByteBuffer _bb, uvSwapItemMesh obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public uvSwapItemMesh __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public uvSwapItemData items(int j) { return items(new uvSwapItemData(), j); }
  public uvSwapItemData items(uvSwapItemData obj, int j) { int o = __offset(6); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int itemsLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }

  public static int createuvSwapItemMesh(FlatBufferBuilder builder,
      int name,
      int items) {
    builder.startObject(2);
    uvSwapItemMesh.addItems(builder, items);
    uvSwapItemMesh.addName(builder, name);
    return uvSwapItemMesh.enduvSwapItemMesh(builder);
  }

  public static void startuvSwapItemMesh(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addItems(FlatBufferBuilder builder, int itemsOffset) { builder.addOffset(1, itemsOffset, 0); }
  public static int createItemsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startItemsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int enduvSwapItemMesh(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

