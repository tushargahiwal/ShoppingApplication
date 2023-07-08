package logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/addtocart")
public class AddToCart extends HttpServlet
{
	private static final long serialVersionUID = 1L;

     //Connection con;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out =response.getWriter();
		int spid=Integer.parseInt(request.getParameter("selectedp"));
		
		HttpSession session=request.getSession();
	List<Integer> products=(List<Integer>)session.getAttribute("cart");
	if(products==null)
	{
		products=new ArrayList<>();
	}
	
	products.add(spid);
	session.setAttribute("cart", products);
	
		out.print("<br/>selected product "+spid+" is added in the cart");
		out.print("<br/>There are "+ products.size()+" item(s) in the cart");
		
		out.print("<br/> <a href='ViewCart.jsp'> View Cart <a/>");
		out.print("<br/> <a href='home'> Go back to Categories <a/>");
 		
	}

}
