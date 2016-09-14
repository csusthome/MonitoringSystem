package GeoFencing.service;

import java.util.List;
import java.util.Map;

public interface GeoFencingService {
	/**
	 * 获得围栏
	 * 
	 * @param entity_id
	 * @param date
	 * @param name
	 * @return
	 */
	public List<Map<String, Object>> getFencings(int entity_id, String name);

	/**
	 * 增加围栏
	 * 
	 * @param params
	 *            (name,longitude,latitude,radius,entity_id)
	 * @return
	 */
	public boolean addFencing(List<Object> params);

	/**
	 * 删除围栏
	 * 
	 * @param params
	 * @return
	 */
	public boolean delFencing(List<Object> params);
	
	/**
	 * 通过ID查找围栏
	 * @param fencing_id
	 * @return
	 */
	public Map<String, Object> findById(String fencing_id);
}
