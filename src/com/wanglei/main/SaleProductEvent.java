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
		mEventName = "购物结算";
	}
	public Product getProduct(String name){
		Product product = new Product();
		try {
			ResultSet result = JDBC.getJDBC().searchSql("select * from product where name='"+name+"'");
			P.rintln("\t名称\t价格\t数量");
			 
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
			P.rintln("输入您所需要的商品名");
			name = ScanIn.scanInString();
			Product product = getProduct(name);
			P.rintln("请输入购买数量：");
			number = ScanIn.scanInRange(0, product.getmNamber());
			sumPrice += number * product.getmPrice();
			int contNumber = product.getmNamber() - number;
			JDBC.getJDBC().excuteSql("update product set number = "+contNumber+" where name = '"+product.getmName()+"'");
			JDBC.getJDBC().excuteSql("insert into productdate(name,id,number,sumprice) value(" 
			+"'"+product.getmName()+"',"
			+"'"+id+"',"
			+number+","
			+(product.getmPrice()*number)+" )");
			P.rintln("是否继续添加商品？（y/n）");
		}while ("y".equals(ScanIn.scanInString()));
		P.rintln("总计："+sumPrice);
		P.rintln("请输入实际缴费金额： ");
		inMenoy = ScanIn.scanInFloat();
		ouMenoy = inMenoy - sumPrice;
		JDBC.getJDBC().excuteSql("insert into countdate(id,sumprice,inmoney) value("+
		"'"+id+"',"
		+sumPrice+ ","
		+inMenoy + ")");
		P.rintln("找钱："+ ouMenoy);
		P.rint("谢谢光临");
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		action();
	}

}
