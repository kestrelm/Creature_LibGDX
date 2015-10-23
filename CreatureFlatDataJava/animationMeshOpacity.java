// automatically generated, do not modify

package CreatureFlatData;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class animationMeshOpacity extends Table {
  public static animationMeshOpacity getRootAsanimationMeshOpacity(ByteBuffer _bb) { return getRootAsanimationMeshOpacity(_bb, new animationMeshOpacity()); }
  public static animationMeshOpacity getRootAsanimationMeshOpacity(ByteBuffer _bb, animationMeshOpacity obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationMeshOpacity __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public float opacity() { int o = __offset(6); return o != 0 ? bb.getFloat(o + bb_pos) : 0; }

  public static int createanimationMeshOpacity(FlatBufferBuilder builder,
      int name,
      float opacity) {
    builder.startObject(2);
    animationMeshOpacity.addOpacity(builder, opacity);
    animationMeshOpacity.addName(builder, name);
    return animationMeshOpacity.endanimationMeshOpacity(builder);
  }

  public static void startanimationMeshOpacity(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addOpacity(FlatBufferBuilder builder, float opacity) { builder.addFloat(1, opacity, 0); }
  public static int endanimationMeshOpacity(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

