package GeoFencing.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import GeoFencing.service.GeoFencingService;

import utils.JdbcUtils;

public class GeoFencingDao implements GeoFencingService {

	private JdbcUtils utils;

	public GeoFencingDao() {
		utils = new JdbcUtils();
	}

	@Override
	public List<Map<String, Object>> getFencings(int entity_id, String name) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from fence where entity_id=?";

		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		params.add(entity_id);

		if (name != null) {
			buffer.append(" and name like ? ");
			params.add("%" + name + "%");
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

	@Override
	public boolean addFencing(List<Object> params) {
		boolean flag = false;

		String sql = "insert into fence(name,longitude,latitude,radius,entity_id) values(?,?,?,?,?)";
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
	public boolean delFencing(List<Object> params) {
		boolean flag = false;

		String sql = "delete from fence where id=?";
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
	public Map<String, Object> findById(String fencing_id) {
		Map<String, Object> map = null;
		String sql = "select * from fence where id=?";

		List<Object> params = new ArrayList<Object>();
		params.add(fencing_id);

		try {
			utils.getConnection();
			map = utils.findSimpleResult(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			utils.releaseConn();
		}
		
		return map;
	}

	public static void main(String[] args) {
//		GeoFencingDao dao = new GeoFencingDao();
//		System.out.println(dao.findById("5"));
	}
}
