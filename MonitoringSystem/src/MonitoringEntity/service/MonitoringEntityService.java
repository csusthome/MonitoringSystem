package MonitoringEntity.service;

import java.util.List;
import java.util.Map;

public interface MonitoringEntityService {
	/**
	 * 绑定被监护端
	 * @param params
	 * @return
	 */
	public int binding(List<Object> params);
	
	/**
	 * 解除被监护端绑定
	 * @param params
	 * @return
	 */
	public boolean removeBinding(List<Object> params);
	
	/**
	 * 获得实体集
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getEntities(String user_id, String pupillus_id);
	
	/**
	 * 查找特定实体
	 * @param params(user_id, pupillus_id)
	 * @return
	 */
	public List<Map<String, Object>> findEntity(List<Object> params);
	
	public Map<String, Object> findById(String entity_id);
}
