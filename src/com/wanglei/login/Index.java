package com.wanglei.login;

import ministry.CashierManagerEvent;

import com.wanglei.main.LoginMenuEvent;
import com.wanglei.main.PrintCountDateEvent;
import com.wanglei.main.ProductMannagerEvent;
import com.wanglei.tool.P;
import com.wanglei.tool.ScanIn;
import com.wanglei.value.Event;
import com.wanglei.value.EventSet;

public class Index extends Event{
	public Index() {
		// TODO Auto-generated constructor stub
		mEventName = "supermarket ����ϵͳ��ҳ";
	}
	public static void main(String[] args) {
		new Index().action();
		P.rintln("���˳��˸�ϵͳ");
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		EventSet mEventSet = new EventSet();
		mEventSet.addEvent(this);
		P.rintln("1.��Ʒά��\n2.ǰ̨����\n3.�г���������\n4.�ۻ�Ա����");
		int key = ScanIn.scanInRange(1,4);
		switch (key) {
		case 1:
			mEventSet.addEvent(new ProductMannagerEvent());
			break;
		case 2:
			mEventSet.addEvent(new LoginMenuEvent());
			break;
		case 3:
			mEventSet.addEvent(new PrintCountDateEvent());
			break;
		case 4:
			mEventSet.addEvent(new CashierManagerEvent());
		default:
			break;
		}
		mEventSet.EventRun();
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		action();
	}
}
