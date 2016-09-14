package whitelist.service;

import java.util.List;
import java.util.Map;

public interface WhiteListService {
	/**
	 * 添加号码
	 * 
	 * @param params (phone_number,note,pupillus_id)
	 * @return
	 */
	public boolean addNumber(List<Object> params);
	
	/**
	 * 修改号码
	 * 
	 * @param params (phone_number,note,pupillus_id) + id
	 * @return
	 */
	public boolean updateNumber(List<Object> params);
	
	/**
	 * 删除号码
	 * @param params (id)
	 * @return
	 */
	public boolean delNumber(List<Object> params);
	
	/**
	 * 获得电话
	 * @param params (pupillus_id)
	 * @return
	 */
	public List<Map<String, Object>> getNumbers(List<Object> params);
	
	/**
	 * 查找号码
	 * @param pupillus_id
	 * @param num
	 * @return
	 */
	public boolean findNum(String pupillus_id, String num);
}
