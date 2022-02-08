
import java.io.IOException;
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

		response.setCharacterEncoding("utf-8");
		String driverClasName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai02";
		String user = "jsonkadai02";
		String password = "JsonKadai02";

		int point = 0;

		try {
			Class.forName(driverClasName);
			Connection con = DriverManager.getConnection(url, user, password);
			PreparedStatement st = con
					.prepareStatement("Select point from point_mst where shop_id = ? and user_id = ?");

			PreparedStatement st2 = con.prepareStatement("insert into point_mst values(?,?,500)");

			String shop_id = request.getParameter("shop_id");
			String user_id = request.getParameter("user_id");

			st.setString(1, shop_id);
			st.setString(2, user_id);
			ResultSet rs = st.executeQuery();

			if (rs.next() == true) {
				point = rs.getInt("point");

			} else {
				st2.setString(1, "shop_id");
				st2.setString(2, "user_id");

				st2.executeUpdate();

			}

			rs.close();
			st.close();
			con.close();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		request.setAttribute("point", point);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
		rd.forward(request, response);

	}

}
