
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetPointServlet
 */
@WebServlet("/getPoint")
public class GetPointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetPointServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset = utf-8");
		request.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		final String driverClasName = "com.mysql.jdbc.Driver";
		final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai02";
		final String user = "jsonkadai02";
		final String password = "JsonKadai02";

		try {
			Class.forName(driverClasName);
			Connection con = DriverManager.getConnection(url, user, password);
			String point = null;
			
			String tenpoid = request.getParameter("TENPO_ID");
			String userid = request.getParameter("USER_ID");
			
			PreparedStatement at = con.prepareStatement("insert ignore into point_list(tenpo_id, user_id, point) values (?, ?, 500)");
			at.setString(1, tenpoid);
			at.setString(2, userid);
			at.executeUpdate();
			
			PreparedStatement pt = con.prepareStatement("insert ignore into tenpo_list(tenpo_id, tenpo_name) values (?, null)");
			pt.setString(1, tenpoid);
			pt.executeUpdate();
			
			
			PreparedStatement st = con.prepareStatement("Select * from point_list where tenpo_id = ? and user_id = ?");
			st.setString(1, tenpoid);
			st.setString(2, userid);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				point = rs.getString("point");
			}
			
			st.close();
			con.close();
			
			request.setAttribute("POINT", point);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			out.println("<pre>");
			e.printStackTrace(out);
		}

	}

}
