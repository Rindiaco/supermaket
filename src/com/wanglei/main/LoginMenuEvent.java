package com.wanglei.main;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wanglei.sql.JDBC;
import com.wanglei.tool.P;
import com.wanglei.tool.ScanIn;
import com.wanglei.value.Event;
import com.wanglei.value.EventSet;
import com.wanglei.value.Person;

public class LoginMenuEvent extends Event{
	public LoginMenuEvent() {
		// TODO Auto-generated constructor stub
		mEventName = "前台登陆菜单";
	}
	public void action(){
		P.rintln("1.登录\n2.退出\n请输入指令");
		int key = ScanIn.scanInRange(1, 2);
		if (key == 1) {
			new LoginEvent().action();
		}else {
			new Queit().action();
		}
	}
	public void restart(){
		action();
	}
	class LoginEvent extends Event{
		Person cashier = new Person();
		int keys;
		public LoginEvent() {
			// TODO Auto-generated constructor stub
			mEventName = "登录";
			keys = 3;
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			P.rintln("输入姓名");
			cashier = searchCashier(ScanIn.scanInString());
			P.rintln("输入密码:");
			String password = ScanIn.scanInString();
			if (cashier.getmPassword().equals(password)) {
				//TODO 跳转到出售商品上
				EventSet mEventSet = new EventSet();
				mEventSet.addEvent(new SaleProductEvent());
				mEventSet.EventRun();
			}else {
				P.rintln("密码输入错误");
				if (keys>0) {
					P.rintln("您还有"+keys+"次机会");
					keys--;
					restart();
				}else {
					System.exit(0);
				}
				
				
			}
		}
		private Person searchCashier(String name){
			//TODO 从数据库中查找商品
			Person cashier = new Person();
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from person where pname='"+name+"'");
				while (result.next()) {
					cashier.setmName(result.getString(1));
					cashier.setmPassword(result.getString(2));
				}
				return cashier;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				P.rintln("查无此人！");
				restart();
			}
			return null;
		}
		@Override
		public void restart() {
			// TODO Auto-generated method stub
			action();
		}
		
	}
	
	class Queit extends Event{
		@Override
		public void action() {
			// TODO Auto-generated method stub
			System.exit(0);
		}

		@Override
		public void restart() {
			// TODO Auto-generated method stub
			
		}
	}
}
