package com.user.resistration;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String driverName= "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://@localhost:3306/ashwani";
		String uname ="root";
		String pass = "root";
		String sql = " select * from users where email = ? and password = ? " ;
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");		
		HttpSession session = request.getSession();
		
		
		RequestDispatcher dispatcher = null;
		Connection connection = null;
		
		try 
		{
			Class.forName(driverName);
			connection = DriverManager.getConnection(url,uname,pass);
			PreparedStatement pst = connection.prepareStatement(sql);
			
			pst.setString(2,password);
			pst.setString(1,email);
			
			ResultSet rs = pst.executeQuery();
			
			
			if(rs.next())
			{
				session.setAttribute("name", rs.getString("name"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			}
			else
			{
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				connection.close();			
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		
	}

}
