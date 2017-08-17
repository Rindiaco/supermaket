package com.wanglei.main;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wanglei.sql.JDBC;
import com.wanglei.tool.P;
import com.wanglei.value.Event;

public class PrintCountDateEvent extends Event{
	public PrintCountDateEvent() {
		// TODO Auto-generated constructor stub
		mEventName = "打印销售数据";
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		try {
			ResultSet result = JDBC.getJDBC().searchSql("select * from countdate");
			
			while (result.next()) {
				P.rintln("id\t收入\t收钱\t");
				P.rintln(result.getString(1) +"\t"
						+result.getFloat(2) +"\t"
						+result.getFloat(3));
				P.rintln("\t商品\t销售数量\t总价\t");
				ResultSet resultSet2 = JDBC.getJDBC().searchSql("select * from productdate where id='"+1890+"'");
				while (resultSet2.next()) {
					P.rintln("\t"+resultSet2.getString(1) +"\t"
							+resultSet2.getInt(3) +"\t"
							+resultSet2.getFloat(4));
					
				}
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			P.rintln("查找失败！");
			restart();
		}
		
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}

}
