package com.dozengame.net;

import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.event.EventDispatcher;
import com.dozengame.net.pojo.ReadData;
 
/**
 * ���紦����
 * ��������
 * ��������
 * @author hewengao
 *
 */
public class DNetwork extends EventDispatcher implements CallBack{
	
	protected SocketBase m_netptr = null;	//�ײ�����ӿ�
	
	public  DNetwork(SocketBase net_ptr) {
		this.m_netptr = net_ptr;
		this.m_netptr.addEventListener(Event.SOCKET_DATA,this,"onDataRecv");
	}
	
	public void changeNetPtr(SocketBase netptr)
	{
		this.m_netptr.removeEventListener(Event.SOCKET_DATA, this,"onDataRecv");
		this.m_netptr = netptr;
		this.m_netptr.addEventListener(Event.SOCKET_DATA,this,"onDataRecv");
	}
	
	public void onDataRecv(Event e) {
		ReadData rd=(ReadData)e.getData();
		onProcessCommand(rd);
	}
	
	protected void onProcessCommand(ReadData rd){
		 
	}
}

