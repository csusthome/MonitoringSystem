package anomalyRecord.service;

import java.util.List;
import java.util.Map;

public interface AnomalyRecordService {
	/**
	 * 添加警报
	 * 
	 * @param params
	 *            (date,pupillus_id,message)
	 * @return
	 */
	public boolean addRecode(List<Object> params);

	/**
	 * 查询历史警报
	 * 
	 * @param date
	 * @param fencing_id
	 * @return
	 */
	public List<Map<String, Object>> getHistory(String date, String pupillus_id);
}
