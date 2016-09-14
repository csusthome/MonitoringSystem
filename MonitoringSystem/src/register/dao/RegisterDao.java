package register.dao;

import java.util.ArrayList;
import java.util.List;

import login.dao.LoginDao;
import login.service.LoginService;

import register.service.RegisterService;
import utils.JdbcUtils;

public class RegisterDao implements RegisterService {

	private JdbcUtils utils = null;
	private LoginService service = null;

	public RegisterDao() {
		utils = new JdbcUtils();
		service = new LoginDao();
	}

	@Override
	/**
	 * 注册用户
	 */
	public int userRegister(List<Object> params) {
		int flag = -1;

		List<Object> temp = new ArrayList<Object>();
		temp.add(params.get(0)); // 获得手机号

		if (service.findUser(temp) > 0) {
			flag = 0;
		} else {
			String sql = "insert into user(phone_number,pwd,name) values(?,?,?)";
			try {
				utils.getConnection();
				flag = utils.updateByPreparedStatement(sql, params) ? 1 : -1;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭数据库连接
				utils.releaseConn();
			}
		}
		return flag;
	}

	@Override
	/**
	 * 注册被监护人
	 */
	public boolean pupillusRegister(List<Object> params) {
		boolean flag = false;
		utils.getConnection();

		List<Object> temp = new ArrayList<Object>();
		temp.addAll(params);
		temp.remove(1); // 去掉验证码
		if (service.findPupillus(temp) > 0) {
			flag = true;
		} else {
			String sql = "insert into pupillus(meid,verification_code) values(?,?)";
			try {
				flag = utils.updateByPreparedStatement(sql, params);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭数据库连接
				utils.releaseConn();
			}
		}
		return flag;
	}

}
