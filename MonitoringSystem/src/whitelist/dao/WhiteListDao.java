package whitelist.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utils.JdbcUtils;
import whitelist.service.WhiteListService;

public class WhiteListDao implements WhiteListService {
	private JdbcUtils utils = null;

	public WhiteListDao() {
		utils = new JdbcUtils();
	}

	@Override
	public boolean addNumber(List<Object> params) {
		boolean flag = false;

		String sql = "insert into white_list(phone_number,note,pupillus_id) values(?,?,?)";

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
	public boolean updateNumber(List<Object> params) {
		boolean flag = false;

		String sql = "UPDATE white_list SET phone_number=?, note=?, pupillus_id=? where id=?";
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
	public boolean delNumber(List<Object> params) {
		boolean flag = false;

		String sql = "delete from white_list where id=?";
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
	public List<Map<String, Object>> getNumbers(List<Object> params) {
		String sql = "select * from white_list where pupillus_id=?";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
	public boolean findNum(String pupillus_id, String num) {
		boolean flag = false;
		String sql = "select * from white_list where pupillus_id=? and phone_number=?";
		List<Object> params = new ArrayList<Object>();

		params.add(pupillus_id);
		params.add(num);

		try {
			utils.getConnection();
			flag = utils.findSimpleResult(sql, params).isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			utils.releaseConn();
		}
		return flag;
	}

}
