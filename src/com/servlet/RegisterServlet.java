package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       String name,password,gender,old,img;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		String channel = request.getParameter("users");
		System.out.println(channel);
		JSONObject js=null;
		if(channel!=null)
		js= JSONObject.fromObject(channel);
		name = (String) js.get("name");
		password = (String)js.get("password");
		gender = (String)js.get("gender");
		old = (String)js.get("old");
		img = null;
		if(dataBase()){
			response.getOutputStream().print("true");
		}
		else
			response.getOutputStream().print("false");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private Boolean dataBase(){
	 	 Connection con;
	 	Statement stmt;
	 	try{
	 		Class.forName(Database.JDriver);
	 	}catch(java.lang.ClassNotFoundException e){}
	 	try{
	 		 
	 		 con=DriverManager.getConnection(Database.connectDB,Database.user,Database.password);//连接数据库对象
			 stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);//创建SQL命令对象
			 //获取系统时间
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			 String sql = "INSERT user_inf VALUES ('"+name+"','"+password+"','"+img+"','"+gender+"','"+old+"','null','null','null','"+dateFormat.format(now)+"')";
			 System.out.println("sql==="+sql);
			 int ii = stmt.executeUpdate(sql);
			 System.out.println("change line======"+ii);
			 if(ii!=0)
				 return true;
			 else
				 return false;
	 	}catch(SQLException e)
	 	 {
	 	    return false;
	 	 
	 	 }
	}

}
