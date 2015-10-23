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
public final class animationUVSwapTimeSample extends Table {
  public static animationUVSwapTimeSample getRootAsanimationUVSwapTimeSample(ByteBuffer _bb) { return getRootAsanimationUVSwapTimeSample(_bb, new animationUVSwapTimeSample()); }
  public static animationUVSwapTimeSample getRootAsanimationUVSwapTimeSample(ByteBuffer _bb, animationUVSwapTimeSample obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationUVSwapTimeSample __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public animationUVSwap uvSwaps(int j) { return uvSwaps(new animationUVSwap(), j); }
  public animationUVSwap uvSwaps(animationUVSwap obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int uvSwapsLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public int time() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createanimationUVSwapTimeSample(FlatBufferBuilder builder,
      int uvSwaps,
      int time) {
    builder.startObject(2);
    animationUVSwapTimeSample.addTime(builder, time);
    animationUVSwapTimeSample.addUvSwaps(builder, uvSwaps);
    return animationUVSwapTimeSample.endanimationUVSwapTimeSample(builder);
  }

  public static void startanimationUVSwapTimeSample(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addUvSwaps(FlatBufferBuilder builder, int uvSwapsOffset) { builder.addOffset(0, uvSwapsOffset, 0); }
  public static int createUvSwapsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startUvSwapsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addTime(FlatBufferBuilder builder, int time) { builder.addInt(1, time, 0); }
  public static int endanimationUVSwapTimeSample(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

