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
public final class meshRegionBone extends Table {
  public static meshRegionBone getRootAsmeshRegionBone(ByteBuffer _bb) { return getRootAsmeshRegionBone(_bb, new meshRegionBone()); }
  public static meshRegionBone getRootAsmeshRegionBone(ByteBuffer _bb, meshRegionBone obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public meshRegionBone __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public float weights(int j) { int o = __offset(6); return o != 0 ? bb.getFloat(__vector(o) + j * 4) : 0; }
  public int weightsLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }
  public ByteBuffer weightsAsByteBuffer() { return __vector_as_bytebuffer(6, 4); }
  
  public float[] ReadWeights()
  {
	  float[] retData = new float[weightsLength()];
	  for(int i = 0; i < retData.length; i++)
	  {
		  retData[i] = weights(i);
	  }
	  
	  return retData;
  }

  public static int createmeshRegionBone(FlatBufferBuilder builder,
      int name,
      int weights) {
    builder.startObject(2);
    meshRegionBone.addWeights(builder, weights);
    meshRegionBone.addName(builder, name);
    return meshRegionBone.endmeshRegionBone(builder);
  }

  public static void startmeshRegionBone(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addWeights(FlatBufferBuilder builder, int weightsOffset) { builder.addOffset(1, weightsOffset, 0); }
  public static int createWeightsVector(FlatBufferBuilder builder, float[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addFloat(data[i]); return builder.endVector(); }
  public static void startWeightsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endmeshRegionBone(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

