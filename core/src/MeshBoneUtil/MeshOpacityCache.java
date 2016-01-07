package MeshBoneUtil;
import com.badlogic.gdx.math.Vector2;

public class MeshOpacityCache {
	public String key;
	public float opacity;

	public MeshOpacityCache(String key_in)
	{
		key = key_in;
		opacity = 100.0f;
	}
	
	public void setOpacity(float value_in)
	{
		opacity = value_in;
	}
	
	public float getOpacity()
	{
		return opacity;
	}
	
	public String getKey() {
		return key;
	}
}
