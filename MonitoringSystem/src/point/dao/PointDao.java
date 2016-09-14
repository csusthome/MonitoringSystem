package point.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import point.service.PointService;
import utils.JdbcUtils;

public class PointDao implements PointService {

	private JdbcUtils utils;

	public PointDao() {
		utils = new JdbcUtils();
	}

	@SuppressWarnings("finally")
	@Override
	public boolean addPoint(List<Object> params) {
		boolean flag = false;

		String sql = "insert into point(longitude,latitude,date,pupillus_id) values(?,?,?,?)";
		try {
			utils.getConnection();
			flag = utils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			utils.releaseConn();
			return flag;
		}

	}

	@Override
	public boolean addPoints(List<Map<String, String>> list) {
		List<Object> params = new ArrayList<Object>();
		boolean flag = false;
		for (Map<String, String> map : list) {
			params.add(map.get("longitude"));
			params.add(map.get("latitude"));
			params.add(map.get("date"));
			params.add(map.get("pupillus_id"));
			flag = this.addPoint(params);
			params.clear();
			if (flag == false) {
				break;
			}
		}
		return flag;
	}

	@Override
	public List<Map<String, Object>> getPoints(int pupillus_id, String date,
			String limit) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from point where pupillus_id=?";

		// SELECT * FROM point WHERE DATE_FORMAT(date,'%Y-%m-%d %H') =
		// '2016-05-17 13';
		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		params.add(pupillus_id);
		if (date != null) {
			buffer.append(" and date like ? ");
			params.add(date + "%");
		}

		if (limit != null) {
			buffer.append(" ORDER BY date desc limit ?");
			params.add(Integer.parseInt(limit));
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
