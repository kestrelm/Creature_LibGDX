// automatically generated, do not modify

package CreatureFlatDataJava;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class animationBonesTimeSample extends Table {
  public static animationBonesTimeSample getRootAsanimationBonesTimeSample(ByteBuffer _bb) { return getRootAsanimationBonesTimeSample(_bb, new animationBonesTimeSample()); }
  public static animationBonesTimeSample getRootAsanimationBonesTimeSample(ByteBuffer _bb, animationBonesTimeSample obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationBonesTimeSample __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public animationBone bones(int j) { return bones(new animationBone(), j); }
  public animationBone bones(animationBone obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int bonesLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public int time() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createanimationBonesTimeSample(FlatBufferBuilder builder,
      int bones,
      int time) {
    builder.startObject(2);
    animationBonesTimeSample.addTime(builder, time);
    animationBonesTimeSample.addBones(builder, bones);
    return animationBonesTimeSample.endanimationBonesTimeSample(builder);
  }

  public static void startanimationBonesTimeSample(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addBones(FlatBufferBuilder builder, int bonesOffset) { builder.addOffset(0, bonesOffset, 0); }
  public static int createBonesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startBonesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addTime(FlatBufferBuilder builder, int time) { builder.addInt(1, time, 0); }
  public static int endanimationBonesTimeSample(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

