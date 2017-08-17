package com.wanglei.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wanglei.tool.P;

public class JDBC {
	static final String JDBC_URL = "jdbc:mysql://localhost:3306/supermarket?"
			+"user=root&password=root&useUnicode=true&characterEncoding=UTF8";
	private static Connection connection;
	private static Statement stmt ;
	private static JDBC jdbc;
	private JDBC(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(JDBC_URL);
			stmt = connection.createStatement();
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			P.rintln("数据库连接失败");
		}
//		P.rintln("数据库连接成功");
	}
	public static JDBC getJDBC(){
		if (jdbc != null) {
			return jdbc;
		}
		return new JDBC();
	}
	public ResultSet searchSql(String sql){
		ResultSet set;
		try {
			set = stmt.executeQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			P.rintln("语句有误！执行失败");
			return null;
		}
		
		return set;
	}
	public void excuteSql(String sql){
		try {
			stmt.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			P.rintln("执行失败");
		}
//		P.rintln("执行成功！");
	}
	public static void main(String args[])throws Exception{
		JDBC j2 = JDBC.getJDBC();
		ResultSet result = JDBC.getJDBC().searchSql("select * from product order by price");
		while (result.next()) {
			P.rintln("\t"+result.getString(1) +"\t"
					+result.getFloat(2) +"\t"
					+result.getInt(3));
		}
	}
}
