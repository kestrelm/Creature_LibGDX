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
public final class animationMeshOpacityList extends Table {
  public static animationMeshOpacityList getRootAsanimationMeshOpacityList(ByteBuffer _bb) { return getRootAsanimationMeshOpacityList(_bb, new animationMeshOpacityList()); }
  public static animationMeshOpacityList getRootAsanimationMeshOpacityList(ByteBuffer _bb, animationMeshOpacityList obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationMeshOpacityList __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public animationMeshOpacityTimeSample timeSamples(int j) { return timeSamples(new animationMeshOpacityTimeSample(), j); }
  public animationMeshOpacityTimeSample timeSamples(animationMeshOpacityTimeSample obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int timeSamplesLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }

  public static int createanimationMeshOpacityList(FlatBufferBuilder builder,
      int timeSamples) {
    builder.startObject(1);
    animationMeshOpacityList.addTimeSamples(builder, timeSamples);
    return animationMeshOpacityList.endanimationMeshOpacityList(builder);
  }

  public static void startanimationMeshOpacityList(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addTimeSamples(FlatBufferBuilder builder, int timeSamplesOffset) { builder.addOffset(0, timeSamplesOffset, 0); }
  public static int createTimeSamplesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startTimeSamplesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endanimationMeshOpacityList(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

