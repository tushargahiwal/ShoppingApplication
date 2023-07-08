<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		Connection con=(Connection)application.getAttribute("jdbccon");
		List<Integer> sps=(List<Integer>) session.getAttribute("cart");
		if(sps == null)
		{ %>
		 
			<h3> No products are selected </h3>
		<%}
		else
		{%> 
	      <%out.println("<table border=1>"); %>
	     <% 
			PreparedStatement ps=null;
			ResultSet rs=null;
			
			
			try
			{%>
			
				ps=con.prepareStatement("select * from product where p_id =?");
				int cnt =0;
				float tprice=0;
				<% 
				for(int n :sps)
				 
				{%>
				<% 
					ps.setInt(1, n);
					rs=ps.executeQuery();
					if(rs.next())
					{%>
					
						<%out.println("<tr>");%>
						<%out.print("<td>"+ (++cnt) + "</td>"); %>
						<%out.print("<td>"+ rs.getString(2) + "</td>"); %>
						<%out.print("<td>"+ rs.getString(4) + "</td>"); %>
						<%out.print("<td> <a href='DeleteServlet?pid="+ rs.getInt(1)+"'> delete </a>  </td>");%>
				        <%out.println("</tr>"); %>
				        <% 
						 tprice+=Float.parseFloat(rs.getString(4));
					}%>
				<% 
				
				}
				%>
				
				session.setAttribute("tprice", tprice);
				<% out.println("<tr>");%>
				<%	out.print("<td colspan='2'> Total price </td>"); %>
				<%out.print("<td>"+ tprice + "</td>"); %>
				<%out.println("</tr>"); %>
				<%out.println("</table>");%>
			    <%out.print("<br/> <br/> <a href='confirmcart'> Confirm Cart <a/>"); %>
			    <%	out.print("<br/> <br/> <a href='home'> Go back to Categories <a/>"); %>	
			<%     
			}
			%>
			<% 
			catch(Exception e)
			{%>
			<% 
			  e.printStackTrace();
			}
			%>
			
			<% 
			finally
			{
				%>
				<% 
				try
				{
					%>
					rs.close();
					ps.close();	
					<% 				
				}
				%>
				<% 
				catch(Exception e)
				{
					%>
					e.printStackTrace();
				}
			}
		<%	
		}
		%>
		
		
	<%
	}
	 %>
	 	
		

</body>
</html>
