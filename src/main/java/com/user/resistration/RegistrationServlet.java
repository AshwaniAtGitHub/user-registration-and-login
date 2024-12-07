package com.user.resistration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet
{

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
		{
		
		
		String driverName= "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://@localhost:3306/ashwani";
		String uname ="root";
		String pass = "root";
		String sql = "insert into users(name,password,email,contact) values(?,?,?,?)" ;
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String contact = request.getParameter("contact");
		
		
		RequestDispatcher dispatcher = null;
		Connection connection = null;
		
		try 
		{
			Class.forName(driverName);
			connection = DriverManager.getConnection(url,uname,pass);
			PreparedStatement pst = connection.prepareStatement(sql);
			
			pst.setString(1,name);
			pst.setString(2,password);
			pst.setString(3,email);
			pst.setString(4,contact);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if(rowCount > 0)
			{
				request.setAttribute("status", "success");
			}
			else
			{
				request.setAttribute("status", "failed");
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
