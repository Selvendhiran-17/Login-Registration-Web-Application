package com.uniquedeveloper.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String pass=request.getParameter("pass");
		String repass=request.getParameter("re_pass");
		String mobile=request.getParameter("contact");
		RequestDispatcher dispatcher=null;
		Connection con=null;
		
		if(name==null||name.equals("")) {
        	request.setAttribute("status","invalidName");
	    	dispatcher=request.getRequestDispatcher("registration.jsp");
	    	  dispatcher.forward(request, response);
        }
		if(email==null||email.equals("")) {
        	request.setAttribute("status","invalidEmail");
	    	dispatcher=request.getRequestDispatcher("registration.jsp");
	    	  dispatcher.forward(request, response);
        }
		if(pass==null||pass.equals("")) {
        	request.setAttribute("status","invalidPass");
	    	dispatcher=request.getRequestDispatcher("registration.jsp");
	    	  dispatcher.forward(request, response);
        }
		else if(!pass.equals(repass)) {
			request.setAttribute("status","invalidConfirmPassword");
	    	dispatcher=request.getRequestDispatcher("registration.jsp");
	    	  dispatcher.forward(request, response);
		}
		if(mobile==null||mobile.equals("")) {
        	request.setAttribute("status","invalidMobile");
	    	dispatcher=request.getRequestDispatcher("registration.jsp");
	    	  dispatcher.forward(request, response);
        }
		else if(mobile.length()>10) {
			request.setAttribute("status","invalidMobileLength");
	    	dispatcher=request.getRequestDispatcher("registration.jsp");
	    	  dispatcher.forward(request, response);
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?useSSL=false","root","Selva17@#");
		    PreparedStatement pst=con.prepareStatement("insert into users(name,password,email,phone) values(?,?,?,?)");
		    pst.setString(1,name);
		    pst.setString(2,pass);
		    pst.setString(3,email);
		    pst.setString(4,mobile);
		    
		    int rowCount=pst.executeUpdate();
		    dispatcher=request.getRequestDispatcher("registration.jsp");
		    if(rowCount>0) {
		    	request.setAttribute("status", "success");
		    }
		    else {
		    	request.setAttribute("status", "failed");
		    }
		    dispatcher.forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
