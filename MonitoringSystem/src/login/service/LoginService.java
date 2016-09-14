package login.service;

import java.util.List;
import java.util.Map;

public interface LoginService {
	// 用户登录
	public Map<String, Object> userLogin(List<Object> params);

	// 被监护端登录
	public int pupillusLogin(List<Object> params);
	
	// 查找被监护端是否存在
	public int findPupillus(List<Object> params);
	
	// 查找用户名是否存在
	public int findUser(List<Object> params);
	
}
