
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class GetPointServlet
 */
@WebServlet("/getTicketList")
public class GetTicketListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetTicketListServlet() {
		super();

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		final String driverName = "com.mysql.jdbc.Driver";
		final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai02";
		final String id = "jsonkadai02";
		final String password = "JsonKadai02";

		try {
			Class.forName(driverName);
			Connection con = DriverManager.getConnection(url, id, password);
			
			PreparedStatement st = con.prepareStatement("select * from point_list");
			String ten = request.getParameter("TENPO_ID");
			String use = request.getParameter("USER_ID");
			
			st.setString(1, ten);
			st.setString(2, ten);
			st.setString(3, use);
			
			ResultSet result = st.executeQuery();
			
			List<String[]> list = new ArrayList<>();
			while (result.next() == true) {
				String[] s = new String[3];
				s[0] = result.getString("ticket_id");
				s[1] = result.getString("ticket_name");
				s[2] = result.getString("point");
				list.add(s);
			}
			
			request.setAttribute("list", list);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getTicketList.jsp");
			rd.forward(request, response);
			
			} catch (ClassNotFoundException e ) {
				e.printStackTrace();
				
		} catch (SQLException e) {
			e.printStackTrace();
			
		}

	}

}
