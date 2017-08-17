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
		mEventName = "�ۻ�Ա����";
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		EventSet mEventSet = new EventSet();
		P.rintln("1.����ۻ�Ա\n2.�����ۻ�Ա\n3.ɾ���ۻ�Ա\n4.��ʾ���ۻ�Ա\n5.�����ۻ�Ա");
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
			mEventName="����ۻ�Ա";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			P.rintln("����ۻ�Ա��:");
			cashier.setmName(ScanIn.scanInString());
			P.rintln("��ӵ�½����:");
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
			mEventName = "�����ۻ�Ա��Ϣ";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			P.rintln("���������Ʒ����");
			cashier = searchCashier(ScanIn.scanInString());
			tradPerson();
		}
		private Person searchCashier(String name){
			//TODO �����ݿ��в�����Ʒ
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
				P.rintln("����ʧ�ܣ�");
				restart();
			}
			return null;
		}
		private void tradPerson(){
			
			try {
				
				P.rintln("ѡ����Ҫ���ĵ�����\n1.����\n2.�۸�\n3.����\n");
				int key = ScanIn.scanInRange(1,2);
				
				if (key == 1) {
					P.rintln("�����ۻ�Ա����");
					cashier.setmName(ScanIn.scanInString());
				}else if(key==2){
					P.rintln("�������룺");
					cashier.setmPassword(ScanIn.scanInString());
				}
				JDBC.getJDBC().excuteSql("insert into person(pname,password) values('"
						+cashier.getmName()+"',"
						+cashier.getmPassword()+")");
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
	class DeleteCashierEvent extends Event{
		public DeleteCashierEvent() {
			// TODO Auto-generated constructor stub
			mEventName = "ɾ���ۻ�Ա";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			P.rintln("������Ҫɾ����������");
			String name = ScanIn.scanInString();
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from person where pname='"+name+"'");
				if (result==null) {
					P.rintln("û���ҵ�����");
					restart();
				}else{
					P.rintln("�Ƿ�Ҫɾ������y/n��");
					if (ScanIn.scanInString().equals("y")) {
						JDBC.getJDBC().excuteSql("delete  from person where pname='"+name+"'");
					}else {
						P.rintln("����ȡ��");
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
			mEventName = "��ʾ���е��ۻ�Ա";
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			try {
				ResultSet result = JDBC.getJDBC().searchSql("select * from person");
				P.rintln("\t����\t����");
				while (result.next()) {
					P.rintln("\t"+result.getString(1) +"\t"
							+result.getString(2) +"\t"
							);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				P.rintln("��ӡʧ�ܣ�");
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
			mEventName = "�����ۻ�Ա";
		}
		private void SearchPerson(){
			try {
				ResultSet result;
				P.rintln("����������:");
				String name = ScanIn.scanInString();
				result = JDBC.getJDBC().searchSql("select * from person where pname = '"+name+"' ");
				P.rintln("\t����\t����");
				while (result.next()) {
					P.rintln("\t"+result.getString(1) +"\t"
							+result.getString(2) +"\t"
							 );
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				P.rintln("��ʽ�����������Ϊstring,����������");
				restart();//���¿�ʼ
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