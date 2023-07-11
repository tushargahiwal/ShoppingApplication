package ServletDemo;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;



@WebServlet("/logincheck")
public class LoginCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;

	public void init(ServletConfig config) throws ServletException {
		con = (Connection)config.getServletContext().getAttribute("jdbccon");
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		
	
		PreparedStatement ps = null;
		ResultSet rs= null;
		try
		{
			ps = con.prepareStatement("select * from users where u_id=? and password = ?");
			ps.setString(1, uid);
			ps.setString(2, pwd);
			rs = ps.executeQuery();   
			
			if(rs.next())   
			{
				Cookie [] allc = request.getCookies();
				if(allc != null)
				{
					for(Cookie c : allc)
					{
						if(c.getName().equals("loginerror"))
						{
							c.setMaxAge(0);
							response.addCookie(c);
						}							
					}
				}
				
				// creating session id
				User user = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
				//session will be created
				HttpSession session =request.getSession();
				session.setAttribute("loggedinuser", user);
								
				RequestDispatcher rd = request.getRequestDispatcher("/home");
				rd.forward(request, response);
			}
			else
			{
				Cookie c = new Cookie("loginerror","Wrong_UID/PWD");
				response.addCookie(c);
				response.sendRedirect("/ShoppingApp/login.jsp");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
		
	}
	}

