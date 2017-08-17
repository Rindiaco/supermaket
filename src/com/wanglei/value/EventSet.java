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
				P.rint(event.mEventName+"》》");
			}
			P.rintln("");
			P.rintln("请输入数字继续当前操作或者按0返回上一级?");
			int key = ScanIn.scanInRange(0, 9);
			if (key == 0) {
				mEventList.remove(i);
				i--;
				continue;
			}
			mEventList.get(i).action();
			P.rintln("是否继续重复上一操作?(y/n)");
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
