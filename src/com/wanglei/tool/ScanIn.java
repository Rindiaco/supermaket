package com.wanglei.tool;

import java.util.Scanner;

public class ScanIn {
	static Scanner in  = new Scanner(System.in);
	
	static public int scanInRange(int a, int b ){
		String key;
		int keyInt = 0;
		key = in.next();
		if (key.matches("\\d+")) {//判断是否是数字
			keyInt = Integer.parseInt(key);
			if (keyInt<a||keyInt>b) {//判断是否属于范围
				System.out.print("输入范围必须是："+a+"~"+b);
				scanInRange( a, b );
			}
		}else {
			System.out.print("输入必须是整型");
			scanInRange( a, b );
		}
		return keyInt;
	}
	static	public Float scanInFloat( ){
		String key;
		float keyFloat = 0;
		key = in.next();
		if (key.matches("^[0-9|.]*$")) {//判断是否是浮点型
			keyFloat = Float.parseFloat(key);
		}else {
			System.out.print("输入必须是浮点型");
			scanInFloat();
		}
		return keyFloat;
	}
	static public String scanInString(){
		String key;
		key = in.next();
		return key;
	}
	 
	public static void main(String[] args)  {
		new ScanIn().scanInFloat( );
	}
}
