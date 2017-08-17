package ministry;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wanglei.sql.JDBC;
import com.wanglei.tool.P;
import com.wanglei.tool.ScanIn;
import com.wanglei.value.Event;
import com.wanglei.value.EventSet;
import com.wanglei.value.Person;

public class CashierManagerEvent extends Event {

	public CashierManagerEvent() {
		// TODO Auto-generated constructor stub
		mEventName = "售货员管理";
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		EventSet mEventSet = new EventSet();
		P.rintln("1.添加售货员\n2.更改售货员\n3.删除售货员\n4.显示所售货员\n5.搜索售货员");
		int key = ScanIn.scanInRange(1,5);
		switch (key) {
		case 1:
			mEventSet.addEvent(new AddCashierEvent());
			break;
		case 2:
			mEventSet.addEvent(new ChangeCashierEvent());
			break;
		case 3:
			mEventSet.addEvent(new DeleteCashierEvent());
			break;
		case 4:
			mEventSet.addEvent(new PrintAllCashierEvent());
			break;
		case 5:
			mEventSet.addEvent(new SearchCashierEvent());
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
	class AddCashierEvent extends Event{
		private Person cashier = new Person();
		public AddCashierEvent(){
			mEventName="添加售货员";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			P.rintln("添加售货员名:");
			cashier.setmName(ScanIn.scanInString());
			P.rintln("添加登陆密码:");
			cashier.setmPassword(ScanIn.scanInString());
			JDBC.getJDBC().excuteSql("insert into person(pname,password) values('"
					+cashier.getmName()+"',"
					+cashier.getmPassword()+")");
		}

		@Override
		public void restart() {
			// TODO Auto-generated method stub
			cashier.setmName("");
			cashier.setmPassword("");
			action();
		}
	}
	class ChangeCashierEvent extends Event{
		Person cashier = new Person();
		public ChangeCashierEvent() {
			// TODO Auto-generated constructor stub
			mEventName = "更改售货员信息";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			P.rintln("输入更改商品名称");
			cashier = searchCashier(ScanIn.scanInString());
			tradPerson();
		}
		private Person searchCashier(String name){
			//TODO 从数据库中查找商品
			Person cashier = new Person();
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from person where pname='"+name+"'");
				while (result.next()) {
					cashier.setmName(result.getString(1));
					cashier.setmPassword(result.getString(2));
				}
				JDBC.getJDBC().excuteSql("delete  from person where pname='"+name+"'");
				return cashier;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				P.rintln("查找失败！");
				restart();
			}
			return null;
		}
		private void tradPerson(){
			
			try {
				
				P.rintln("选择您要更改的内容\n1.名称\n2.价格\n3.数量\n");
				int key = ScanIn.scanInRange(1,2);
				
				if (key == 1) {
					P.rintln("更改售货员名：");
					cashier.setmName(ScanIn.scanInString());
				}else if(key==2){
					P.rintln("更改密码：");
					cashier.setmPassword(ScanIn.scanInString());
				}
				JDBC.getJDBC().excuteSql("insert into person(pname,password) values('"
						+cashier.getmName()+"',"
						+cashier.getmPassword()+")");
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
	class DeleteCashierEvent extends Event{
		public DeleteCashierEvent() {
			// TODO Auto-generated constructor stub
			mEventName = "删除售货员";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			P.rintln("输入想要删除的姓名：");
			String name = ScanIn.scanInString();
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from person where pname='"+name+"'");
				if (result==null) {
					P.rintln("没有找到该人");
					restart();
				}else{
					P.rintln("是否要删除？（y/n）");
					if (ScanIn.scanInString().equals("y")) {
						JDBC.getJDBC().excuteSql("delete  from person where pname='"+name+"'");
					}else {
						P.rintln("操作取消");
					}
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

		@Override
		public void restart() {
			// TODO Auto-generated method stub
			action();
		}
		
	}
	class PrintAllCashierEvent extends Event{
		public PrintAllCashierEvent() {
			// TODO Auto-generated constructor stub
			mEventName = "显示所有的售货员";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from person");
				P.rintln("\t名称\t密码");
				while (result.next()) {
					P.rintln("\t"+result.getString(1) +"\t"
							+result.getString(2) +"\t"
							);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				P.rintln("打印失败！");
				restart();
			}
		}

		@Override
		public void restart() {
			// TODO Auto-generated method stub
			action();
		}
		
	}
	class SearchCashierEvent extends Event{
		SearchCashierEvent(){
			mEventName = "查找售货员";
		}
		private void SearchPerson(){
			try {
				ResultSet result;
				P.rintln("请输入姓名:");
				String name = ScanIn.scanInString();
				result = JDBC.getJDBC().searchSql("select * from person where pname = '"+name+"' ");
				P.rintln("\t名称\t密码");
				while (result.next()) {
					P.rintln("\t"+result.getString(1) +"\t"
							+result.getString(2) +"\t"
							 );
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				P.rintln("格式输入错误，名称为string,请重新输入");
				restart();//重新开始
			}
	}

		@Override
		public void action() {
			// TODO Auto-generated method stub
			SearchPerson();
		}

		@Override
		public void restart() {
			// TODO Auto-generated method stub
			action();
		}
}
}