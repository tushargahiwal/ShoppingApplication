package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;


@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	 
	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
		con=(Connection)config.getServletContext().getAttribute("jdbccon");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out =response.getWriter();
		int spid=Integer.parseInt(request.getParameter("pid"));
		
		HttpSession session=request.getSession();
	List<Integer> products=(List<Integer>)session.getAttribute("cart");
	if(products==null)
	{
		products=new ArrayList<>();
	}
	
	products.remove(spid);
	session.setAttribute("cart", products);
	
//		out.print("<br/>selected product "+spid+" is deleted in the cart");
//		out.print("<br/>There are "+ products.size()+" item(s) in the cart");
//		
//		out.print("<br/> <a href='viewcart'> View Cart <a/>");
//		out.print("<br/> <a href='home'> Go back to Categories <a/>");
	response.sendRedirect("/ShoppingApp/viewcart");
	
	}

}
