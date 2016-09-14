package anomalyRecord.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utils.JdbcUtils;

import anomalyRecord.service.AnomalyRecordService;

public class AnomalyRecordDao implements AnomalyRecordService {

	private JdbcUtils utils = null;

	public AnomalyRecordDao() {
		utils = new JdbcUtils();
	}
	
	@Override
	public boolean addRecode(List<Object> params) {
		boolean flag = false;

		String sql = "insert into anomaly_record(date,pupillus_id,message) values(?,?,?)";
		try {
			utils.getConnection();
			flag = utils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			utils.releaseConn();
		}
		return flag;
	}

	@Override
	public List<Map<String, Object>> getHistory(String date, String pupillus_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from anomaly_record where pupillus_id=?";

		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		params.add(pupillus_id);

		if (date != null) {
			buffer.append(" and date like ? ");
			params.add("%" + date + "%");
		}
		try {
			utils.getConnection();
			list = utils.findMoreResult(buffer.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			utils.releaseConn();
		}

		return list;
	}

}
