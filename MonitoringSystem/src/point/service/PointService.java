package point.service;

import java.util.List;
import java.util.Map;

public interface PointService {
	// 添加点
	public boolean addPoint(List<Object> params);
	boolean addPoints(List<Map<String, String>> list);

	// 显示点
	public List<Map<String, Object>> getPoints(int pupillus_id, String date, String limit);

	
}
