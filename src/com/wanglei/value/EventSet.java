package com.wanglei.value;

import java.util.LinkedList;
import java.util.List;

import com.wanglei.tool.P;
import com.wanglei.tool.ScanIn;

public class EventSet {
	private List<Event> mEventList = new LinkedList<Event>();
	
	public boolean addEvent(Event event){
		return mEventList.add(event);
	}
	public boolean removeEvent(Event event){
		return mEventList.remove(event);
	}
	public void EventRun(){
		
		if (mEventList.isEmpty()) {
			return;
		}
		
		for (int i = mEventList.size() - 1;  i >= 0; ) {
			for (Event event : mEventList) {
				P.rint(event.mEventName+"����");
			}
			P.rintln("");
			P.rintln("���������ּ�����ǰ�������߰�0������һ��?");
			int key = ScanIn.scanInRange(0, 9);
			if (key == 0) {
				mEventList.remove(i);
				i--;
				continue;
			}
			mEventList.get(i).action();
			P.rintln("�Ƿ�����ظ���һ����?(y/n)");
			if("y".equals(ScanIn.scanInString())){
				mEventList.get(i).restart();
			}else {
				mEventList.remove(i);
				i--;
				if (mEventList.isEmpty()) {
					return;
				}
			}
		}
		
	}
	public boolean isEmpty(){
		return mEventList.isEmpty();
	}
}
