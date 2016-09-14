package fencingRecord.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fencingRecord.service.FencingRecordService;

import utils.JdbcUtils;

public class FencingRecordDao implements FencingRecordService {

	private JdbcUtils utils = null;

	public FencingRecordDao() {
		utils = new JdbcUtils();
	}

	@Override
	public boolean addRecode(List<Object> params) {
		boolean flag = false;

		String sql = "insert into fence_record(date,fencing_id,message) values(?,?,?)";
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
	public List<Map<String, Object>> getHistory(String date, String fencing_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from fence_record where fencing_id=?";

		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		params.add(fencing_id);

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
	
	public static void main(String[] args) {
		
	}
}
