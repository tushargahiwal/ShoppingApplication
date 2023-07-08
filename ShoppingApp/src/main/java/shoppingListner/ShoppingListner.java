package shoppingListner;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ShoppingListner implements ServletContextListener {

Connection con;
	
    public void contextDestroyed(ServletContextEvent sce)  { 
         try
         {
        	 con.close();
         }
         catch(Exception e)
         {
        	 e.printStackTrace();
         }
    }

    public void contextInitialized(ServletContextEvent sce)  { 
         
    	
    	String driver = sce.getServletContext().getInitParameter("diverclass");
    	String url = sce.getServletContext().getInitParameter("jdbcurl");		
    	String user = sce.getServletContext().getInitParameter("user");
    	String pwd = sce.getServletContext().getInitParameter("password");
    
    	try
    	{
    		Class.forName(driver);
    		con = DriverManager.getConnection(url, user, pwd);
    		sce.getServletContext().setAttribute("jdbccon", con);
    		System.out.println("con is set as ctx level attribute");
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
	
}
