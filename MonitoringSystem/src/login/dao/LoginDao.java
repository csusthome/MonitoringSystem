package login.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.JdbcUtils;

import login.service.LoginService;

public class LoginDao implements LoginService {

	private JdbcUtils utils;

	public LoginDao() {
		utils = new JdbcUtils();
	}

	@Override
	public Map<String, Object> userLogin(List<Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select * from user where phone_number=? and pwd=?";
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

	@Override
	public int pupillusLogin(List<Object> params) {
		Object result = -1;

		String sql = "update pupillus set verification_code=? where meid=?";
		String sql1 = "Select * from pupillus where meid=?";
		try {
			utils.getConnection();
			utils.updateByPreparedStatement(sql, params);
			params.remove(0);
			Map<String, Object> map = utils.findSimpleResult(sql1, params);
			result = (map.isEmpty() ? -1 : map.get("id"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			utils.releaseConn();
		}

		return Integer.valueOf(result.toString());
	}

	@Override
	public int findPupillus(List<Object> params) {
		int result = -1;

		String sql = "select id from pupillus where meid=?";
		try {
			utils.getConnection();
			Map<String, Object> map = utils.findSimpleResult(sql, params);
			result = (Integer) (map.isEmpty() ? -1 : map.get("id"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			utils.releaseConn();
		}

		return result;
	}
	
	@Override
	public int findUser(List<Object> params) {
		int result = -1;

		String sql = "select id from user where phone_number=?";
		try {
			utils.getConnection();
			Map<String, Object> map = utils.findSimpleResult(sql, params);
			result = (Integer) (map.isEmpty() ? -1 : map.get("id"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			utils.releaseConn();
		}

		return result;
	}
}
