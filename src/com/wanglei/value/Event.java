package com.wanglei.value;

public abstract class Event {
	 protected  String mEventName; 
	 public Event() {
		// TODO Auto-generated constructor stub
	
	}
	public abstract void action();
	public abstract void restart();
}
