package fencingRecord.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.AppPush;
import GeoFencing.dao.GeoFencingDao;
import GeoFencing.service.GeoFencingService;
import MonitoringEntity.dao.MonitoringEntityDao;
import MonitoringEntity.service.MonitoringEntityService;
import fencingRecord.dao.FencingRecordDao;
import fencingRecord.service.FencingRecordService;

public class FencingRecordAction extends HttpServlet {

	FencingRecordService recordService;
	GeoFencingService fencingService;
	MonitoringEntityService entityService;

	/**
	 * Constructor of the object.
	 */
	public FencingRecordAction() {
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

	private void getHistory(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		String date, fencing_id;
		date = request.getParameter("date");
		fencing_id = request.getParameter("fencing_id");

		List<Map<String, Object>> list = recordService.getHistory(date,
				fencing_id);
		if (list == null || list.size() <= 0) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<fencingRecords>");
			out.println("</fencingRecords>");
		} else {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<fencingRecords>");
			for (Map<String, Object> map : list) {
				out.println("<record id=\"" + map.get("id") + "\">");
				out.println("<message>" + map.get("message") + "</message>");
				out.println("<fencing_id>" + map.get("fencing_id")
						+ "</fencing_id>");
				
				out.println("<date>" + ((Date)map.get("date")).getTime() + "</date>");
				out.println("</record>");
			}
			out.println("</fencingRecords>");
		}

		out.flush();
		out.close();

	}

	private void addRecord(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		String date, fencing_id, message;
		date = request.getParameter("date");
		fencing_id = request.getParameter("fencing_id");
		message = request.getParameter("message");

		List<Object> params = new ArrayList<Object>();
		params.add(date);
		params.add(fencing_id);
		params.add(message);

		boolean flag = recordService.addRecode(params);
		out.println(flag);

		if (flag == true) {
			Map<String, Object> fence = fencingService.findById(fencing_id);
			Map<String, Object> entity = entityService.findById(fence.get(
					"entity_id").toString());

			AppPush push = new AppPush();
			// 设置推送消息
			String text = entity.get("alias") + " " + message + ":"
					+ fence.get("name");
			
			push.push("围栏警报", text);
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
		recordService = new FencingRecordDao();
		fencingService = new GeoFencingDao();
		entityService = new MonitoringEntityDao();
	}

}
