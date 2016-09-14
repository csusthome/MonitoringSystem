package point.action;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import point.dao.PointDao;
import point.service.PointService;
import utils.XMLUtil;

public class PointAction extends HttpServlet {

	private PointService service;

	/**
	 * Constructor of the object.
	 */
	public PointAction() {
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
			if (operation.equals("addPoints")) {
				addPoints(request, response);
			} else if (operation.equals("getHistory")) {
				try {
					getHistory(request, response);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (operation.equals("getRealTime")) {
				try {
					getRealTime(request, response);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	private void getRealTime(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		PrintWriter out = response.getWriter();

		int pupillus_id = Integer.parseInt(request.getParameter("pupillus_id"));
		String date = request.getParameter("date");

		List<Map<String, Object>> list = service.getPoints(pupillus_id, date,
				"1");

		if (list == null || list.size() <= 0) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//			out.println("<points>");
//			out.println("<point id=-1>error</point>");
//			out.println("</points>");
		} else {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<points>");
			for (Map<String, Object> map : list) {
				out.println("<point id=\"" + map.get("id") + "\">");
				out.println("<longitude>" + map.get("longitude")
						+ "</longitude>");
				out.println("<latitude>" + map.get("latitude") + "</latitude>");
				out.println("<date>" + map.get("date") + "</date>");
				out.println("</point>");
			}
			out.println("</points>");
		}

		out.flush();
		out.close();
	}

	/**
	 * 添加点
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private void addPoints(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		boolean flag = false;
		// System.out.println(XMLUtil.changeInputStream(request.getInputStream(),
		// "utf-8"));
		List<Map<String, String>> list = XMLUtil.readXML(
				request.getInputStream(), "point");

		if (list == null) {
			out.print("上传坐标失败！");
		} else {
			flag = service.addPoints(list);
		}

		out.print(flag);

		out.flush();
		out.close();
	}

	private void getHistory(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		PrintWriter out = response.getWriter();

		int pupillus_id = Integer.parseInt(request.getParameter("pupillus_id"));
		String date = request.getParameter("date");

		List<Map<String, Object>> list = service.getPoints(pupillus_id, date,
				null);
		if (list == null || list.size() <= 0) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//			out.println("<points>");
//			out.println("<point id=\"" + "-1" + "\">");
//			out.println("</point>");
//			out.println("</points>");
		} else {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<points>");
			for (Map<String, Object> map : list) {
				out.println("<point id=\"" + map.get("id") + "\">");
				out.println("<longitude>" + map.get("longitude")
						+ "</longitude>");
				out.println("<latitude>" + map.get("latitude") + "</latitude>");
				out.println("<date>" + map.get("date") + "</date>");
				out.println("</point>");
			}
			out.println("</points>");
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
		service = new PointDao();
	}

}
