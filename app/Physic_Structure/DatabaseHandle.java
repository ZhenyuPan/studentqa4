package Physic_Structure;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHandle 
{
	static public Connection Conn = null;
	int StudentID;
	//inital the database 
	public DatabaseHandle()
	{
		if( Conn != null )
		{
			return ;
		}
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String Url  = "jdbc:mysql://localhost:3306/CourseQA3";
			Conn = DriverManager.getConnection(Url,"root","UID01394889");
			System.out.println("connection build");
		}catch(Exception E)
		{
			E.printStackTrace();
		}
	}
}
