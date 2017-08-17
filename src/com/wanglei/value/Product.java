package com.wanglei.value;

public class Product {
	private String mName;
	private float mPrice;
	private int mNamber;
	public static final int PRODUCT_MAX_NUMBER=99999;
	public Product(String mName, float mPrice, int mNamber) {
		super();
		this.mName = mName;
		this.mPrice = mPrice;
		this.mNamber = mNamber;
	}
	public Product(){};
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public float getmPrice() {
		return mPrice;
	}
	public void setmPrice(float mPrice) {
		this.mPrice = mPrice;
	}
	public int getmNamber() {
		return mNamber;
	}
	public void setmNamber(int mNamber) {
		this.mNamber = mNamber;
	}
	
	public void printInfor(){
		System.out.println("/t/t"+mName+""+"/t/t"+mPrice+"/t/t"+mNamber);
	}
}
