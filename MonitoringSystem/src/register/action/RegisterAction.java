package register.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import register.dao.RegisterDao;
import register.service.RegisterService;

public class RegisterAction extends HttpServlet {

	private RegisterService service;

	/**
	 * Constructor of the object.
	 */
	public RegisterAction() {
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
		boolean flag = false;

		if (role != null) {
			if (role.equals("user")) {
				userRegister(request, response);
			} else

			if (role.equals("pupillus")) {
				pupillusRegister(request, response);
			}
		}

	}

	// 被监护端注册
	private void pupillusRegister(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		String meid = request.getParameter("meid");
		String verification_code = request.getParameter("verification_code");

		List<Object> params = new ArrayList<Object>();

		params.add(meid);
		params.add(verification_code);

		out.print(service.pupillusRegister(params));
		out.flush();
		out.close();
	}

	// 用户注册
	private void userRegister(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		
		String phoneNmuber = request.getParameter("phoneNumber");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");

		List<Object> params = new ArrayList<Object>();
		params.add(phoneNmuber);
		params.add(pwd);
		params.add(name);

		out.print(service.userRegister(params));
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
		service = new RegisterDao();
	}

}
