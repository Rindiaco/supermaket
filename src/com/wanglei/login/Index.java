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
		mEventName = "supermarket 管理系统首页";
	}
	public static void main(String[] args) {
		new Index().action();
		P.rintln("你退出了该系统");
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		EventSet mEventSet = new EventSet();
		mEventSet.addEvent(this);
		P.rintln("1.商品维护\n2.前台收银\n3.列出出售数据\n4.售货员管理");
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
