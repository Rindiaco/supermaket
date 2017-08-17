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
		mEventName = "ǰ̨��½�˵�";
	}
	public void action(){
		P.rintln("1.��¼\n2.�˳�\n������ָ��");
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
			mEventName = "��¼";
			keys = 3;
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			P.rintln("��������");
			cashier = searchCashier(ScanIn.scanInString());
			P.rintln("��������:");
			String password = ScanIn.scanInString();
			if (cashier.getmPassword().equals(password)) {
				//TODO ��ת��������Ʒ��
				EventSet mEventSet = new EventSet();
				mEventSet.addEvent(new SaleProductEvent());
				mEventSet.EventRun();
			}else {
				P.rintln("�����������");
				if (keys>0) {
					P.rintln("������"+keys+"�λ���");
					keys--;
					restart();
				}else {
					System.exit(0);
				}
				
				
			}
		}
		private Person searchCashier(String name){
			//TODO �����ݿ��в�����Ʒ
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
				P.rintln("���޴��ˣ�");
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
