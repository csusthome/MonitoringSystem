package MonitoringEntity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import MonitoringEntity.service.MonitoringEntityService;

import utils.JdbcUtils;

public class MonitoringEntityDao implements MonitoringEntityService {
	private JdbcUtils utils = null;

	public MonitoringEntityDao() {
		utils = new JdbcUtils();
	}

	@Override
	public int binding(List<Object> params) {
		int flag = -1;

		List<Object> temp = new ArrayList<Object>();
		temp.addAll(params);
		temp.remove(0);

		if (findEntity(temp).size() == 0) {
			String sql = "insert into entity(alias,user_id,pupillus_id) values(?,?,?)";
			try {
				utils.getConnection();
				flag = utils.updateByPreparedStatement(sql, params) ? 1 : -1;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭数据库连接
				utils.releaseConn();
			}
		} else {
			flag = 0;
		}

		return flag;
	}

	@Override
	public boolean removeBinding(List<Object> params) {
		boolean flag = false;

		String sql = "delete from entity where id=?";
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
	public List<Map<String, Object>> getEntities(String user_id,
			String pupillus_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = null;
		List<Object> params = new ArrayList<Object>();
		if (pupillus_id != null) {
			params.add(pupillus_id);
			sql = "select * from entity as entity left join user as u on entity.user_id = u.id where pupillus_id=?";
		} else {
			params.add(user_id);
			sql = "select * from entity as entity left join pupillus as p on p.id = entity.pupillus_id where user_id=?";
		}

		try {
			utils.getConnection();
			list = utils.findMoreResult(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			utils.releaseConn();
		}

		return list;
	}

	@Override
	public List<Map<String, Object>> findEntity(List<Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from entity where user_id=? and pupillus_id=?";
		try {
			utils.getConnection();
			list = utils.findMoreResult(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			utils.releaseConn();
		}

		return list;
	}

	@Override
	public Map<String, Object> findById(String entity_id) {
		Map<String, Object> map = null;
		List<Object> params = new ArrayList<Object>();
		params.add(entity_id);
		
		String sql = "select * from entity where id = ?";
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
}
