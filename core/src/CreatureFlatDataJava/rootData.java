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
public final class rootData extends Table {
  public static rootData getRootAsrootData(ByteBuffer _bb) { return getRootAsrootData(_bb, new rootData()); }
  public static rootData getRootAsrootData(ByteBuffer _bb, rootData obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public rootData __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public mesh dataMesh() { return dataMesh(new mesh()); }
  public mesh dataMesh(mesh obj) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }
  public skeleton dataSkeleton() { return dataSkeleton(new skeleton()); }
  public skeleton dataSkeleton(skeleton obj) { int o = __offset(6); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }
  public animation dataAnimation() { return dataAnimation(new animation()); }
  public animation dataAnimation(animation obj) { int o = __offset(8); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }

  public static int createrootData(FlatBufferBuilder builder,
      int dataMesh,
      int dataSkeleton,
      int dataAnimation) {
    builder.startObject(3);
    rootData.addDataAnimation(builder, dataAnimation);
    rootData.addDataSkeleton(builder, dataSkeleton);
    rootData.addDataMesh(builder, dataMesh);
    return rootData.endrootData(builder);
  }

  public static void startrootData(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addDataMesh(FlatBufferBuilder builder, int dataMeshOffset) { builder.addOffset(0, dataMeshOffset, 0); }
  public static void addDataSkeleton(FlatBufferBuilder builder, int dataSkeletonOffset) { builder.addOffset(1, dataSkeletonOffset, 0); }
  public static void addDataAnimation(FlatBufferBuilder builder, int dataAnimationOffset) { builder.addOffset(2, dataAnimationOffset, 0); }
  public static int endrootData(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishrootDataBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
};

