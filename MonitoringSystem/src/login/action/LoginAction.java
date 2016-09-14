package login.action;

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

public class LoginAction extends HttpServlet {

	private LoginService service;

	/**
	 * Constructor of the object.
	 */
	public LoginAction() {
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

		response.setContentType("text/html;charset=utf-8");

		String role = request.getParameter("role");
		int id = -1; // 主键值

		if (role != null) {
			if (role.equals("user")) {
				userLogin(request, response);
			} else

			if (role.equals("pupillus")) {
				pupillusLogin(request, response);
			}
		}

	}

	private void pupillusLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		String meid = request.getParameter("meid");
		String verification_code = request.getParameter("verification_code");
		List<Object> params = new ArrayList<Object>();
		params.add(verification_code);
		params.add(meid);

		out.print(service.pupillusLogin(params));

		out.flush();
		out.close();
	}

	private void userLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String phoneNmuber = request.getParameter("phoneNumber");
		String pwd = request.getParameter("pwd");

		List<Object> params = new ArrayList<Object>();
		params.add(phoneNmuber);
		params.add(pwd);

		Map<String, Object> map = service.userLogin(params);
		if (map.size() == 0 || map == null) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<user id=\"" + "-1" + "\">");
			out.println("<name>" + "用户名或密码不正确" + "</name>");
			out.println("</user>");
		} else {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<user id=\"" + map.get("id") + "\">");
			out.println("<name>" + map.get("name") + "</name>");
			out.println("</user>");
		}

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
		service = new LoginDao();
	}

}
