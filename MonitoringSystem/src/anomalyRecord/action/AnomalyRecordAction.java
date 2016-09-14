package anomalyRecord.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.AppPush;

import anomalyRecord.dao.AnomalyRecordDao;
import anomalyRecord.service.AnomalyRecordService;

public class AnomalyRecordAction extends HttpServlet {

	private AnomalyRecordService service;

	/**
	 * Constructor of the object.
	 */
	public AnomalyRecordAction() {
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
			if (operation.equals("addRecord")) {
				addRecord(request, response);
			} else if (operation.equals("getHistory")) {
				getHistory(request, response);
			}
		}
	}

	private void addRecord(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		String date, pupillus_id, message;
		date = request.getParameter("date");
		pupillus_id = request.getParameter("pupillus_id");
		message = request.getParameter("message");

		List<Object> params = new ArrayList<Object>();
		params.add(date);
		params.add(pupillus_id);
		params.add(message);

		boolean flag = service.addRecode(params);
		out.println(flag);
		if (flag == true) {
			AppPush appPush = new AppPush();
			appPush.push("异常警报", message);
		}

		out.flush();
		out.close();
	}

	private void getHistory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		String date, pupillus_id;
		date = request.getParameter("date");
		pupillus_id = request.getParameter("pupillus_id");

		List<Map<String, Object>> list = service.getHistory(date, pupillus_id);

		if (list == null || list.size() <= 0) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<anomalyRecords>");
			out.println("</anomalyRecords>");
		} else {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<anomalyRecords>");
			for (Map<String, Object> map : list) {
				out.println("<record id=\"" + map.get("id") + "\">");
				out.println("<message>" + map.get("message") + "</message>");
				out.println("<pupillus_id>" + map.get("pupillus_id")
						+ "</pupillus_id>");
				out.println("<date>" + map.get("date") + "</date>");
				out.println("</record>");
			}
			out.println("</anomalyRecords>");
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
		service = new AnomalyRecordDao();
	}

}
