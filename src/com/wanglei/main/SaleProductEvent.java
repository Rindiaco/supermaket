package com.wanglei.main;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.tools.JavaCompiler;

import com.wanglei.sql.JDBC;
import com.wanglei.tool.P;
import com.wanglei.tool.ScanIn;
import com.wanglei.value.Event;
import com.wanglei.value.Product;

public class SaleProductEvent extends Event{
	public SaleProductEvent() {
		// TODO Auto-generated constructor stub
		mEventName = "�������";
	}
	public Product getProduct(String name){
		Product product = new Product();
		try {
			ResultSet result = JDBC.getJDBC().searchSql("select * from product where name='"+name+"'");
			P.rintln("\t����\t�۸�\t����");
			 
				while (result.next()) {
					product.setmName(result.getString(1));
					product.setmPrice(result.getFloat(2));
					product.setmNamber(result.getInt(3));
					P.rintln("\t"+result.getString(1)+"\t"+result.getString(2)+"\t"+result.getString(3));
				}
			 
			return product;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		SimpleDateFormat time = new SimpleDateFormat("yymmddhhmmss");
		String id = time.format(new java.util.Date());
		float sumPrice = 0;
		int number ;
		float inMenoy;
		float ouMenoy;
		String name;
		do{
			P.rintln("����������Ҫ����Ʒ��");
			name = ScanIn.scanInString();
			Product product = getProduct(name);
			P.rintln("�����빺��������");
			number = ScanIn.scanInRange(0, product.getmNamber());
			sumPrice += number * product.getmPrice();
			int contNumber = product.getmNamber() - number;
			JDBC.getJDBC().excuteSql("update product set number = "+contNumber+" where name = '"+product.getmName()+"'");
			JDBC.getJDBC().excuteSql("insert into productdate(name,id,number,sumprice) value(" 
			+"'"+product.getmName()+"',"
			+"'"+id+"',"
			+number+","
			+(product.getmPrice()*number)+" )");
			P.rintln("�Ƿ���������Ʒ����y/n��");
		}while ("y".equals(ScanIn.scanInString()));
		P.rintln("�ܼƣ�"+sumPrice);
		P.rintln("������ʵ�ʽɷѽ� ");
		inMenoy = ScanIn.scanInFloat();
		ouMenoy = inMenoy - sumPrice;
		JDBC.getJDBC().excuteSql("insert into countdate(id,sumprice,inmoney) value("+
		"'"+id+"',"
		+sumPrice+ ","
		+inMenoy + ")");
		P.rintln("��Ǯ��"+ ouMenoy);
		P.rint("лл����");
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		action();
	}

}
