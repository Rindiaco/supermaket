package com.wanglei.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.wanglei.sql.JDBC;
import com.wanglei.tool.P;
import com.wanglei.tool.ScanIn;
import com.wanglei.value.Event;
import com.wanglei.value.EventSet;
import com.wanglei.value.Product;

public class ProductMannagerEvent extends Event{
	
	public ProductMannagerEvent() {
		// TODO Auto-generated constructor stub
		mEventName = "商品管理";
	}
	

 class SupposedProductEvent extends Event{
		private Product good = new Product();
		public SupposedProductEvent() {
			// TODO Auto-generated constructor stub
			mEventName="添加商品";
		}
		public void action(){
			//添加商品
			addProduct();
		
		}
		private void addProduct(){
			
			try {
				P.rintln("添加商品名:");
				good.setmName(ScanIn.scanInString());
				P.rintln("添加商品价格:");
				good.setmPrice(ScanIn.scanInFloat());
				P.rintln("添加商品数量:");
				good.setmNamber(ScanIn.scanInRange(0, good.PRODUCT_MAX_NUMBER));
				JDBC.getJDBC().excuteSql("insert into product(name,price,number) values('"
						+good.getmName()+"',"
						+good.getmPrice()+","
						+good.getmNamber()+")");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				P.rintln("格式输入错误，名称为string,价格为float，数量为int，请重新输入");
				restart();//重新开始
			}
			return;
		}
		
		public void restart(){
			good.setmNamber(0);
			good.setmName("");
			good.setmPrice(0);
			action();
		}
		
	}
 
 	class TradeProductEvent extends Event{
		private Product good ;
		public TradeProductEvent() {
			// TODO Auto-generated constructor stub
			mEventName = "更改商品";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
		
			P.rintln("输入更改商品名称");
			good = searchProduct(ScanIn.scanInString());
			tradProduct();
		}
		private Product searchProduct(String name){
			//TODO 从数据库中查找商品
			Product product = new Product();
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from product where name='"+name+"'");
				while (result.next()) {
					product.setmName(result.getString(1));
					product.setmPrice(result.getFloat(2));
					product.setmNamber(result.getInt(3));
				}
				JDBC.getJDBC().excuteSql("delete  from product where name='"+name+"'");
				return product;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				P.rintln("查找失败！");
				restart();
			}
			return null;
			
			
		}
		private void tradProduct(){
			
			try {
				
				P.rintln("选择您要更改的内容\n1.名称\n2.价格\n3.数量\n");
				int key = ScanIn.scanInRange(1,3);
				
				if (key == 1) {
					P.rintln("更改商品名称");
					good.setmName(ScanIn.scanInString());
				}else if(key==2){
					P.rintln("更改商品价格");
					good.setmPrice(ScanIn.scanInFloat());
				}if(key == 3){
					P.rintln("添加商品数量");
					good.setmNamber(ScanIn.scanInRange(0,good.PRODUCT_MAX_NUMBER));
				}
				JDBC.getJDBC().excuteSql("insert into product(name,price,number) values('"
						+good.getmName()+"',"
						+good.getmPrice()+","
						+good.getmNamber()+")");
				//TODO 是否继续？（y/n）让他选择继续执行这个方法
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				P.rintln("格式输入错误，名称为string,价格为float，数量为int，请重新输入");
				restart();//重新开始
			}
			
			return;
		}
		@Override
		public void restart() {
			// TODO Auto-generated method stub
			action();
		}
		
	}

 class RemoveProductEvent extends Event{
	
		public RemoveProductEvent() {
			// TODO Auto-generated constructor stub
			mEventName = "删除商品";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			
			P.rintln("输入删除商品的名称:");
			
			deleteProduct(ScanIn.scanInString());
		}
		private boolean deleteProduct(String name){
			//TODO 根据商品名称找到商品
			//TODO 删除这个商品
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from product where name='"+name+"'");
				if (result==null) {
					P.rintln("没有找到该商品");
					restart();
				}else{
					P.rintln("是否要删除？（y/n）");
					if (ScanIn.scanInString().equals("y")) {
						JDBC.getJDBC().excuteSql("delete  from product where name='"+name+"'");
					}else {
						P.rintln("操作取消");
					}
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return true;
		}
		@Override
		public void restart() {
			// TODO Auto-generated method stub
			action();
		}
		
	}


	class PrintAllProductEvent extends Event{
		
		public PrintAllProductEvent() {
			// TODO Auto-generated constructor stub
			mEventName = "显示所有商品";
		}
		@Override
		public void action() {
			// TODO 显示所有的内容
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from product");
				P.rintln("\t名称\t价格\t数量");
				while (result.next()) {
					P.rintln("\t"+result.getString(1) +"\t"
							+result.getFloat(2) +"\t"
							+result.getInt(3));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				P.rintln("查找失败！");
				restart();
			}
			
		}

		@Override
		public void restart() {
			 action();
			
		}
		
	}

//public static void main(String[] args){
//	
//}


 class SearchProductEvent extends Event{

		public SearchProductEvent() {
			// TODO Auto-generated constructor stub
			mEventName = "查找商品";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			SearchProduct();
		}
		private void SearchProduct(){
			try {
				ResultSet result;
				Scanner in = new Scanner(System.in);
				P.rintln("选择您要查找的内容");
				P.rintln("1,按商品数量升序查找");
				P.rintln("2,按商品价格升序查找");
				P.rintln("3,输入关键字查找");
				int key = ScanIn.scanInRange(1,3);
				if (key == 1) {
						result = JDBC.getJDBC().searchSql("select * from product order by number ");
				}else if(key == 2){
						result = JDBC.getJDBC().searchSql("select * from product order by price  ");
				}else if(key == 3){
						P.rintln("请输入关键字:");
						String name = ScanIn.scanInString();
						result = JDBC.getJDBC().searchSql("select * from product where name = '"+name+"' ");
				}else {
					result = JDBC.getJDBC().searchSql("select * from product");
				}
				
				P.rintln("\t名称\t价格\t数量");
				while (result.next()) {
					P.rintln("\t"+result.getString(1) +"\t"
							+result.getFloat(2) +"\t"
							+result.getInt(3));
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				P.rintln("格式输入错误，名称为string,价格为float，数量为int，请重新输入");
				restart();//重新开始
			}
		}
		@Override
		public void restart() {
			// TODO Auto-generated method stub
			action();
		}
		
	}
	

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
		EventSet mEventSet = new EventSet();
		mEventSet.addEvent(new ProductMannagerEvent());
		P.rintln("1.添加商品\n2.更改商品\n3.删除商品\n4.显示所有商品\n5.搜索商品");
		int key = ScanIn.scanInRange(1,5);
		switch (key) {
		case 1:
			mEventSet.addEvent(new SupposedProductEvent());
			break;
		case 2:
			mEventSet.addEvent(new TradeProductEvent());
			break;
		case 3:
			mEventSet.addEvent(new RemoveProductEvent());
			break;
		case 4:
			mEventSet.addEvent(new PrintAllProductEvent());
			break;
		case 5:
			mEventSet.addEvent(new SearchProductEvent());
			break;
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
