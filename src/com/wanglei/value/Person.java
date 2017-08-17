package com.wanglei.value;

public class Person {
	private String mName;
	private String mPassword;
	public Person(String mName, String mPassword) {
		super();
		this.mName = mName;
		this.mPassword = mPassword;
	}
	public Person(){}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmPassword() {
		return mPassword;
	}
	public void setmPassword(String mPassword) {
		this.mPassword = mPassword;
	}
	public void printInfor(){
		System.out.println("–’√˚£∫"+mName+"√‹¬Î:"+mPassword);
	}
}
