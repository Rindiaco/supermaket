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
		mEventName = "��Ʒ����";
	}
	

 class SupposedProductEvent extends Event{
		private Product good = new Product();
		public SupposedProductEvent() {
			// TODO Auto-generated constructor stub
			mEventName="�����Ʒ";
		}
		public void action(){
			//�����Ʒ
			addProduct();
		
		}
		private void addProduct(){
			
			try {
				P.rintln("�����Ʒ��:");
				good.setmName(ScanIn.scanInString());
				P.rintln("�����Ʒ�۸�:");
				good.setmPrice(ScanIn.scanInFloat());
				P.rintln("�����Ʒ����:");
				good.setmNamber(ScanIn.scanInRange(0, good.PRODUCT_MAX_NUMBER));
				JDBC.getJDBC().excuteSql("insert into product(name,price,number) values('"
						+good.getmName()+"',"
						+good.getmPrice()+","
						+good.getmNamber()+")");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				P.rintln("��ʽ�����������Ϊstring,�۸�Ϊfloat������Ϊint������������");
				restart();//���¿�ʼ
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
			mEventName = "������Ʒ";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
		
			P.rintln("���������Ʒ����");
			good = searchProduct(ScanIn.scanInString());
			tradProduct();
		}
		private Product searchProduct(String name){
			//TODO �����ݿ��в�����Ʒ
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
				P.rintln("����ʧ�ܣ�");
				restart();
			}
			return null;
			
			
		}
		private void tradProduct(){
			
			try {
				
				P.rintln("ѡ����Ҫ���ĵ�����\n1.����\n2.�۸�\n3.����\n");
				int key = ScanIn.scanInRange(1,3);
				
				if (key == 1) {
					P.rintln("������Ʒ����");
					good.setmName(ScanIn.scanInString());
				}else if(key==2){
					P.rintln("������Ʒ�۸�");
					good.setmPrice(ScanIn.scanInFloat());
				}if(key == 3){
					P.rintln("�����Ʒ����");
					good.setmNamber(ScanIn.scanInRange(0,good.PRODUCT_MAX_NUMBER));
				}
				JDBC.getJDBC().excuteSql("insert into product(name,price,number) values('"
						+good.getmName()+"',"
						+good.getmPrice()+","
						+good.getmNamber()+")");
				//TODO �Ƿ��������y/n������ѡ�����ִ���������
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				P.rintln("��ʽ�����������Ϊstring,�۸�Ϊfloat������Ϊint������������");
				restart();//���¿�ʼ
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
			mEventName = "ɾ����Ʒ";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			
			P.rintln("����ɾ����Ʒ������:");
			
			deleteProduct(ScanIn.scanInString());
		}
		private boolean deleteProduct(String name){
			//TODO ������Ʒ�����ҵ���Ʒ
			//TODO ɾ�������Ʒ
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from product where name='"+name+"'");
				if (result==null) {
					P.rintln("û���ҵ�����Ʒ");
					restart();
				}else{
					P.rintln("�Ƿ�Ҫɾ������y/n��");
					if (ScanIn.scanInString().equals("y")) {
						JDBC.getJDBC().excuteSql("delete  from product where name='"+name+"'");
					}else {
						P.rintln("����ȡ��");
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
			mEventName = "��ʾ������Ʒ";
		}
		@Override
		public void action() {
			// TODO ��ʾ���е�����
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from product");
				P.rintln("\t����\t�۸�\t����");
				while (result.next()) {
					P.rintln("\t"+result.getString(1) +"\t"
							+result.getFloat(2) +"\t"
							+result.getInt(3));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				P.rintln("����ʧ�ܣ�");
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
			mEventName = "������Ʒ";
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
				P.rintln("ѡ����Ҫ���ҵ�����");
				P.rintln("1,����Ʒ�����������");
				P.rintln("2,����Ʒ�۸��������");
				P.rintln("3,����ؼ��ֲ���");
				int key = ScanIn.scanInRange(1,3);
				if (key == 1) {
						result = JDBC.getJDBC().searchSql("select * from product order by number ");
				}else if(key == 2){
						result = JDBC.getJDBC().searchSql("select * from product order by price  ");
				}else if(key == 3){
						P.rintln("������ؼ���:");
						String name = ScanIn.scanInString();
						result = JDBC.getJDBC().searchSql("select * from product where name = '"+name+"' ");
				}else {
					result = JDBC.getJDBC().searchSql("select * from product");
				}
				
				P.rintln("\t����\t�۸�\t����");
				while (result.next()) {
					P.rintln("\t"+result.getString(1) +"\t"
							+result.getFloat(2) +"\t"
							+result.getInt(3));
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				P.rintln("��ʽ�����������Ϊstring,�۸�Ϊfloat������Ϊint������������");
				restart();//���¿�ʼ
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
		P.rintln("1.�����Ʒ\n2.������Ʒ\n3.ɾ����Ʒ\n4.��ʾ������Ʒ\n5.������Ʒ");
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
