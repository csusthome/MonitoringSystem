package whitelist.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import whitelist.dao.WhiteListDao;
import whitelist.service.WhiteListService;

public class WhiteListAction extends HttpServlet {

	private WhiteListService service;

	/**
	 * Constructor of the object.
	 */
	public WhiteListAction() {
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
		PrintWriter out = response.getWriter();

		String operation = request.getParameter("operation");
		if (operation != null) {
			if (operation.equals("getNums")) {
				getNums(request, response);
			} else

			if (operation.equals("addNum")) {
				addNum(request, response);
			} else

			if (operation.equals("delNum")) {
				delNum(request, response);
			}
		}

		out.flush();
		out.close();
	}

	private void delNum(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();

		List<Object> params = new ArrayList<Object>();
		String id = request.getParameter("id");
		params.add(id);
		out.println(service.delNumber(params));

		out.flush();
		out.close();
	}

	private void addNum(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();

		String phone_number, note, pupillus_id;
		phone_number = request.getParameter("phone_number");
		note = request.getParameter("note");
		pupillus_id = request.getParameter("pupillus_id");

		if (service.findNum(pupillus_id, phone_number) == true) {
			List<Object> params = new ArrayList<Object>();
			params.add(phone_number);
			params.add(note);
			params.add(pupillus_id);
			out.print(service.addNumber(params));
		} else {
			out.print("existed");
		}

		out.flush();
		out.close();
	}

	private void getNums(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		List<Object> params = new ArrayList<Object>();
		String pupillus_id = request.getParameter("pupillus_id");
		params.add(pupillus_id);

		List<Map<String, Object>> list = service.getNumbers(params);

		if (list == null) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<nums>");
			out.println("</nums>");
		} else {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<nums>");
			for (Map<String, Object> map : list) {
				out.println("<num id=\"" + map.get("id") + "\">");
				out.println("<phone_number>" + map.get("phone_number")
						+ "</phone_number>");
				out.println("<note>" + map.get("note") + "</note>");
				out.println("<pupillus_id>" + map.get("pupillus_id")
						+ "</pupillus_id>");
				out.println("</num>");
			}
			out.println("</nums>");
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
		service = new WhiteListDao();
	}

}
