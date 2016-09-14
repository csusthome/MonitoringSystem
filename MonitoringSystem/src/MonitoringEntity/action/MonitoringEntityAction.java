package MonitoringEntity.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.dao.LoginDao;
import login.service.LoginService;

import MonitoringEntity.dao.MonitoringEntityDao;
import MonitoringEntity.service.MonitoringEntityService;

public class MonitoringEntityAction extends HttpServlet {

	private MonitoringEntityService service;
	private LoginService loginService;

	/**
	 * Constructor of the object.
	 */
	public MonitoringEntityAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/xml;charset=utf-8");

		String operation = request.getParameter("operation");
		if (operation != null) {
			if (operation.endsWith("banding")) {
				banding(request, response);
			} else

			if (operation.endsWith("removeBanding")) {
				removeBanding(request, response);
			} else

			if (operation.endsWith("getEntities")) {
				getEntities(request, response);
			}
		}

	}

	private void removeBanding(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String id = request.getParameter("entity_id");
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		out.print(service.removeBinding(params));
	}

	private void banding(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		int flag = -1; //-1插入失败，0实体已存在，1成功
		String alias = request.getParameter("alias");
		String user_id = request.getParameter("user_id");
		String meid = request.getParameter("meid");
		String verification_code = request.getParameter("verification_code");

		int pupillus_id = -1;

		List<Object> params = new ArrayList<Object>();
		params.add(meid);
		pupillus_id = loginService.findPupillus(params);
		params.add(verification_code);

		if (pupillus_id > 0) {
			params.clear();
			params.add(alias);
			params.add(user_id);
			params.add(pupillus_id);

			flag = service.binding(params);
		} 
			
		out.print(flag);


		out.flush();
		out.close();
	}

	private void getEntities(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		boolean flag = false;
		String user_id = request.getParameter("user_id");
		String pupillus_id = request.getParameter("pupillus_id");

		List<Map<String, Object>> list = service.getEntities(user_id, pupillus_id);

		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		out.println("<entities>");
		for (Map<String, Object> map : list) {
			out.println("<entity id=\"" + map.get("id") + "\">");
			out.println("<alias>" + map.get("alias") + "</alias>");
			out.println("<user_id>" + map.get("user_id") + "</user_id>");
			out.println("<phone_number>" + map.get("phone_number") + "</phone_number>");
			out.println("<pupillus_id>" + map.get("pupillus_id") + "</pupillus_id>");
			out.println("<meid>" + map.get("meid") + "</meid>");
			out.println("</entity>");
		}
		out.println("</entities>");

		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		service = new MonitoringEntityDao();
		loginService = new LoginDao();
	}

}
