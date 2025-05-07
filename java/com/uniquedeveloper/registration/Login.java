package com.uniquedeveloper.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email=request.getParameter("username");
		String pass=request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher=null;
        if(email==null||email.equals("")) {
        	request.setAttribute("status","invalidEmail");
	    	dispatcher=request.getRequestDispatcher("login.jsp");
	    	  dispatcher.forward(request, response);
        }
        if(pass==null||pass.equals("")) {
        	request.setAttribute("status","invalidPassword");
	    	dispatcher=request.getRequestDispatcher("login.jsp");
	    	  dispatcher.forward(request, response);
        }
        	try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?useSSL=false","root","Selva17@#");
                PreparedStatement pst=con.prepareStatement("select * from users where email=? and password=?");
        	    pst.setString(1,email);
        	    pst.setString(2,pass);
        	    ResultSet rs=pst.executeQuery();
        	    if(rs.next()) {
        	    	session.setAttribute("name",rs.getString("name"));
        	    	dispatcher=request.getRequestDispatcher("index.jsp");
        	    }
        	    else {
        	    	request.setAttribute("status","failed" );
        	    	dispatcher=request.getRequestDispatcher("login.jsp");
        	    }
        	    dispatcher.forward(request, response);
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}

}
}
