package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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


//import net.sf.json.JSONObject;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String name;
    private String password;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    /*public JSONObject getJsonObject(HttpServletRequest request) throws IOException{
    	String resultStr = "";
    	        String readLine;
    	        StringBuffer sb = new StringBuffer();
    	        BufferedReader responseReader = null;
    	        OutputStream outputStream = null;
    	        try {
    	            responseReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
    	            while ((readLine = responseReader.readLine()) != null) {
    	                sb.append(readLine).append("\n");
    	            }
    	            responseReader.close();
    	            resultStr = sb.toString();
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        } finally {
    	            if (outputStream != null) {
    	            outputStream.close();
    	            }
    	        } 
    	return JSONObject.fromObject(resultStr);
    	} */
    
    
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
		System.out.println(name+"==="+password);
        System.out.println("result=="+dataBase());
        
		if(dataBase()){
			response.getOutputStream().print("true");
			//修改上次登陆时间
			dataBase2();
		}
		else
			response.getOutputStream().print("false");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doGet(request, response);
	}
	
	public Boolean dataBase(){//查询用户名密码是否正确
	 	 Connection con;
	 	Statement stmt;
	 	try{
	 		Class.forName(Database.JDriver);
	 	}catch(java.lang.ClassNotFoundException e){}
	 	try{
	 		 ResultSet rs = null;
	 		 con=DriverManager.getConnection(Database.connectDB,Database.user,Database.password);//连接数据库对象
			 stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);//创建SQL命令对象
			 rs = stmt.executeQuery("SELECT * FROM User_inf WHERE User_name = '" + name + "'AND User_pass = '" + password + "'");
			 System.out.println(rs.toString());
			 if(rs.next()){
				 return true;
			 }
	 	}catch(SQLException e)
	 	 {
	 	 e.printStackTrace();
	 	 System.exit(0);
	 	 }
	 	return false;
	 	}
	
	  public void dataBase2(){//修改上次登陆时间
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
						String ss = "UPDATE user_inf set User_lastlogin='"+dateFormat.format(now)+"' where User_name = '"+name+"'";
						System.out.println("Update time sql==="+ss);
					 stmt.executeUpdate(ss);

			 	}catch(SQLException e)
			 	 {
			 	 e.printStackTrace();
			 	 System.exit(0);
			 	 }
	  }


}
