
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.xdevapi.PreparableStatement;

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
		final String driverName = "com.mysql.jdbc.Driver";
		final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai02";
		final String id = "jsonkadai02";
		final String password = "JsonKadai02";

		try {
			Class.forName(driverName);
			Connection con = DriverManager.getConnection(url, id, password);
			//PreparedStatement st = con.prepareStatement("select point from POINT where TENPO_ID=? and USER_ID=?");
			String point = null;
			
			String tenpoid = request.getParameter("TENPO_ID");
			String userid = request.getParameter("USER_ID");
			//st.setString(1, tenpoid);
			//st.setString(2, userid);
			//ResultSet result = st.executeQuery();
			
			PreparedStatement at = con.prepareStatement("insert ignore into point_list(tenpo_id, user_id, point) values (?, ?, 500)");
			at.setString(1, tenpoid);
			at.setString(2, userid);
			at.executeUpdate();
			
			PreparedStatement st = con.prepareStatement("select * from point_list where TENPO_ID = ? and USER_ID = ?");
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
			e.printStackTrace();
			
		}

	}

}
