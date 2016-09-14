package GeoFencing.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import GeoFencing.dao.GeoFencingDao;
import GeoFencing.service.GeoFencingService;


public class GeoFencingAction extends HttpServlet {

	private GeoFencingService service;

	/**
	 * Constructor of the object.
	 */
	public GeoFencingAction() {
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
			if (operation.equals("getFencings")) {
				getFencings(request, response);
			} else

			if (operation.equals("addFencings")) {
				addFencings(request, response);
			} else

			if (operation.equals("delFencing")) {
				delFencing(request, response);
			}
		}

	}

	private void delFencing(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		List<Object> params = new ArrayList<Object>();
		String id = request.getParameter("id");
		params.add(id);
		out.print(service.delFencing(params));

		out.flush();
		out.close();
	}

	private void addFencings(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();

		String name, longitude, latitude, radius, entity_id;
		name = request.getParameter("name");
		longitude = request.getParameter("longitude");
		latitude = request.getParameter("latitude");
		radius = request.getParameter("radius");
		entity_id = request.getParameter("entity_id");

		List<Object> params = new ArrayList<Object>();
		params.add(name);
		params.add(longitude);
		params.add(latitude);
		params.add(radius);
		params.add(entity_id);

		out.println(service.addFencing(params));

		out.flush();
		out.close();
	}

	private void getFencings(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		String name = request.getParameter("name").equals("") ? null : request.getParameter("name");
		int entity_id = Integer.parseInt(request.getParameter("entity_id"));

		List<Map<String, Object>> list = service.getFencings(entity_id, name);

		if (list == null || list.size() == 0) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<fencings>");
			out.println("</fencings>");
		} else {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<fencings>");
			for (Map<String, Object> map : list) {
				out.println("<fencing id=\"" + map.get("id") + "\">");
				out.println("<name>" + map.get("name") + "</name>");
				out.println("<longitude>" + map.get("longitude")
						+ "</longitude>");
				out.println("<latitude>" + map.get("latitude") + "</latitude>");
				out.println("<radius>" + map.get("radius") + "</radius>");
				out.println("<entity_id>" + map.get("entity_id") + "</entity_id>");
				out.println("</fencing>");
			}
			out.println("</fencings>");
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
		service = new GeoFencingDao();
	}

}
