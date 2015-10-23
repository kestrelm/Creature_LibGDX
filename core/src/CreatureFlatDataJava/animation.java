// automatically generated, do not modify

package CreatureFlatDataJava;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class animation extends Table {
  public static animation getRootAsanimation(ByteBuffer _bb) { return getRootAsanimation(_bb, new animation()); }
  public static animation getRootAsanimation(ByteBuffer _bb, animation obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animation __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public animationClip clips(int j) { return clips(new animationClip(), j); }
  public animationClip clips(animationClip obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int clipsLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }

  public static int createanimation(FlatBufferBuilder builder,
      int clips) {
    builder.startObject(1);
    animation.addClips(builder, clips);
    return animation.endanimation(builder);
  }

  public static void startanimation(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addClips(FlatBufferBuilder builder, int clipsOffset) { builder.addOffset(0, clipsOffset, 0); }
  public static int createClipsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startClipsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endanimation(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

