package com.wanglei.tool;

import java.util.Scanner;

public class ScanIn {
	static Scanner in  = new Scanner(System.in);
	
	static public int scanInRange(int a, int b ){
		String key;
		int keyInt = 0;
		key = in.next();
		if (key.matches("\\d+")) {//�ж��Ƿ�������
			keyInt = Integer.parseInt(key);
			if (keyInt<a||keyInt>b) {//�ж��Ƿ����ڷ�Χ
				System.out.print("���뷶Χ�����ǣ�"+a+"~"+b);
				scanInRange( a, b );
			}
		}else {
			System.out.print("�������������");
			scanInRange( a, b );
		}
		return keyInt;
	}
	static	public Float scanInFloat( ){
		String key;
		float keyFloat = 0;
		key = in.next();
		if (key.matches("^[0-9|.]*$")) {//�ж��Ƿ��Ǹ�����
			keyFloat = Float.parseFloat(key);
		}else {
			System.out.print("��������Ǹ�����");
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
