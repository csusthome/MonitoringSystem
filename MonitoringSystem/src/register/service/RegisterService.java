package register.service;

import java.util.List;

public interface RegisterService {
	/**
	 * 完成用户注册
	 * @param params (phone_number,pwd)
	 * @return
	 */
	public int userRegister(List<Object> params);

	/**
	 * 完成被监护人的注册
	 * @param params (meid,verification_code)
	 * @return
	 */
	public boolean pupillusRegister(List<Object> params);
}
