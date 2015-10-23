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
public final class animationClip extends Table {
  public static animationClip getRootAsanimationClip(ByteBuffer _bb) { return getRootAsanimationClip(_bb, new animationClip()); }
  public static animationClip getRootAsanimationClip(ByteBuffer _bb, animationClip obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public animationClip __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public animationBonesList bones() { return bones(new animationBonesList()); }
  public animationBonesList bones(animationBonesList obj) { int o = __offset(6); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }
  public animationMeshList meshes() { return meshes(new animationMeshList()); }
  public animationMeshList meshes(animationMeshList obj) { int o = __offset(8); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }
  public animationUVSwapList uvSwaps() { return uvSwaps(new animationUVSwapList()); }
  public animationUVSwapList uvSwaps(animationUVSwapList obj) { int o = __offset(10); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }
  public animationMeshOpacityList meshOpacities() { return meshOpacities(new animationMeshOpacityList()); }
  public animationMeshOpacityList meshOpacities(animationMeshOpacityList obj) { int o = __offset(12); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }

  public static int createanimationClip(FlatBufferBuilder builder,
      int name,
      int bones,
      int meshes,
      int uvSwaps,
      int meshOpacities) {
    builder.startObject(5);
    animationClip.addMeshOpacities(builder, meshOpacities);
    animationClip.addUvSwaps(builder, uvSwaps);
    animationClip.addMeshes(builder, meshes);
    animationClip.addBones(builder, bones);
    animationClip.addName(builder, name);
    return animationClip.endanimationClip(builder);
  }

  public static void startanimationClip(FlatBufferBuilder builder) { builder.startObject(5); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addBones(FlatBufferBuilder builder, int bonesOffset) { builder.addOffset(1, bonesOffset, 0); }
  public static void addMeshes(FlatBufferBuilder builder, int meshesOffset) { builder.addOffset(2, meshesOffset, 0); }
  public static void addUvSwaps(FlatBufferBuilder builder, int uvSwapsOffset) { builder.addOffset(3, uvSwapsOffset, 0); }
  public static void addMeshOpacities(FlatBufferBuilder builder, int meshOpacitiesOffset) { builder.addOffset(4, meshOpacitiesOffset, 0); }
  public static int endanimationClip(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

