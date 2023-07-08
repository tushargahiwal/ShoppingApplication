package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/getproduct")
public class GetProduct extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Connection con;
 
	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
		con=(Connection)config.getServletContext().getAttribute("jdbccon");
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out=response.getWriter();
		int cid=Integer.parseInt(request.getParameter("cid"));
		
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try
		{
			ps=con.prepareStatement("select * from product where cat_id=?");
			ps.setInt(1, cid);
			rs=ps.executeQuery();
			out.print("<form action='addtocart' method='get'>");
			out.print("select product : ");
			out.print("<select name='selectedp'>");
			
			while(rs.next())
			{
				out.print("<option value='"+rs.getInt(1)+"'>"+ rs.getString(2)+"</option>");
			}
			
			out.print("</select>");
			out.print("<br/> <input type='submit' value='Add to cart'/>");
			out.print("</form>");
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				rs.close();
				ps.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		doGet(request, response);
	}

}
