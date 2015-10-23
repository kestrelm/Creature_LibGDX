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
public final class animationMeshOpacityTimeSample extends Table {
  public static animationMeshOpacityTimeSample getRootAsanimationMeshOpacityTimeSample(ByteBuffer _bb) { return getRootAsanimationMeshOpacityTimeSample(_bb, new animationMeshOpacityTimeSample()); }
  public static animationMeshOpacityTimeSample getRootAsanimationMeshOpacityTimeSample(ByteBuffer _bb, animationMeshOpacityTimeSample obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationMeshOpacityTimeSample __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public animationMeshOpacity meshOpacities(int j) { return meshOpacities(new animationMeshOpacity(), j); }
  public animationMeshOpacity meshOpacities(animationMeshOpacity obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int meshOpacitiesLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public int time() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createanimationMeshOpacityTimeSample(FlatBufferBuilder builder,
      int meshOpacities,
      int time) {
    builder.startObject(2);
    animationMeshOpacityTimeSample.addTime(builder, time);
    animationMeshOpacityTimeSample.addMeshOpacities(builder, meshOpacities);
    return animationMeshOpacityTimeSample.endanimationMeshOpacityTimeSample(builder);
  }

  public static void startanimationMeshOpacityTimeSample(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addMeshOpacities(FlatBufferBuilder builder, int meshOpacitiesOffset) { builder.addOffset(0, meshOpacitiesOffset, 0); }
  public static int createMeshOpacitiesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startMeshOpacitiesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addTime(FlatBufferBuilder builder, int time) { builder.addInt(1, time, 0); }
  public static int endanimationMeshOpacityTimeSample(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

