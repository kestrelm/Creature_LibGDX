package MeshBoneUtil;
import com.badlogic.gdx.math.MathUtils;
import java.util.HashMap;

public class MeshOpacityCacheManager {
	public  java.util.Vector< java.util.Vector<MeshOpacityCache> > opacity_cache_table;
	public  java.util.Vector<Boolean> opacity_cache_data_ready;
	public int start_time, end_time;
	public Boolean is_ready;
	
	public MeshOpacityCacheManager()
	{
		is_ready = false;
		opacity_cache_table = null;
		opacity_cache_data_ready = null;
		opacity_cache_table = new java.util.Vector<java.util.Vector<MeshOpacityCache> > ();
		opacity_cache_data_ready = new java.util.Vector<Boolean> ();
	}
	
	public void init(int start_time_in, int end_time_in)
	{
		start_time = start_time_in;
		end_time = end_time_in;
		
		int num_frames = end_time - start_time + 1;
		opacity_cache_table.clear();
		
		opacity_cache_data_ready.clear();
		for(int i = 0; i < num_frames; i++) {
			opacity_cache_table.add(new java.util.Vector<MeshOpacityCache>());
			opacity_cache_data_ready.add(false);
		}
		
		is_ready = false;
	}
	
	public int getStartTime()
	{
		return start_time;
	}
	
	public int getEndime()
	{
		return end_time;
	}
	
	public int getIndexByTime(int time_in)
	{
		int retval = time_in - start_time;
		retval = (int)MathUtils.clamp((float)retval,
		                              (float)0,
		                              (float)(opacity_cache_table.size()) - 1);
		
		
		return retval;
	}
	
	public void retrieveValuesAtTime(float time_in,
			HashMap<String, MeshRenderRegion> regions_map)
	{
		int base_time = getIndexByTime((int)Math.floor(time_in));
		int end_time = getIndexByTime((int)Math.ceil(time_in));
		
		if(opacity_cache_data_ready.size() == 0) {
			return;
		}
		
		if((opacity_cache_data_ready.get(base_time) == false)
		   || (opacity_cache_data_ready.get(end_time) == false))
		{
			return;
		}
		
		java.util.Vector<MeshOpacityCache> base_cache = opacity_cache_table.get(base_time);
		
		for(int i = 0; i < base_cache.size(); i++) {
			MeshOpacityCache base_data = base_cache.get(i);
			String cur_key = base_data.getKey();
			
			MeshRenderRegion set_region = regions_map.get(cur_key);
			set_region.opacity = base_data.getOpacity();
		}
	}
	
	public Boolean allReady()
	{
		if(is_ready) {
			return true;
		}
		else {
			int num_frames = end_time - start_time + 1;
			int ready_cnt = 0;
			for(int i = 0; i < opacity_cache_data_ready.size(); i++) {
				if(opacity_cache_data_ready.get(i)) {
					ready_cnt++;
				}
			}
			
			if(ready_cnt == num_frames) {
				is_ready = true;
			}
		}
		
		return is_ready;
	}
	
	public void makeAllReady()
	{
		for(int i = 0; i < opacity_cache_data_ready.size(); i++) {
			opacity_cache_data_ready.set(i, true);
		}
	}
	
}