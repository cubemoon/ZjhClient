package com.dozengame.net;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import com.dozengame.GameApplication;
import com.dozengame.event.Event;
import com.dozengame.net.pojo.ReadData;
import com.dozengame.net.pojo.VipInfo;

public class DNetworkVip extends DNetwork{

	public static final String CMD_NOTIFY_VIP_OVER  		 = "VIPOVR";		//�յ�VIP����
	public static  final String CMD_NOTIFY_MY_VIP_INFO 	 = "VIPMYI";		//�յ��ҵ�VIP��Ϣ
	public static final String CMD_NOTIFY_SITE_VIP_INFO   = "VIPINF";		//�յ�ĳ����λ��VIP��Ϣ

	public DNetworkVip(SocketBase net_ptr) {
		super(net_ptr);
	}
	protected void onProcessCommand(ReadData rd){
		 
 
	  String strCmd = rd.getStrCmd();
     
		if (CMD_NOTIFY_VIP_OVER.equals(strCmd)) {
			//�յ�VIP���� 
			byte success=rd.readByte();// 0=vip���� 1=����vip�ɹ�
			byte vip_level=rd.readByte();	// �ȼ���1ͭ��VIP��2����VIP��3����VIP
			String overtime="";
			if(success ==1 ){
				  overtime= rd.readString();//����ɹ���ȡ����ʱ��
			}
			HashMap data = new HashMap();
			data.put("success", success);
			data.put("viplevel", vip_level);
			data.put("overtime", overtime);
			Log.i("test18","�յ�VIP����: "+vip_level+" success: "+success+" overtime: "+overtime);
			dispatchEvent(new Event(CMD_NOTIFY_VIP_OVER, data));
		} else if (CMD_NOTIFY_MY_VIP_INFO.equals(strCmd)) {
			//�յ��ҵ�VIP��Ϣ
			int vipcount = rd.readInt();
			if(vipcount >0){
				ArrayList vips = new ArrayList();
				for(int i =0; i< vipcount;i++){
					VipInfo vip = new VipInfo();
					vip.setVipLevel(rd.readInt());	//VIP�ȼ���1ͭ��VIP��2����VIP��3����VIP
					vip.setOverTime(rd.readString());//����ʱ�� �ַ���
					vips.add(vip);
				}
				if(GameApplication.userInfo != null){
				  GameApplication.userInfo.put("vip", vips);
				}
			}
			Log.i("test18","�յ��ҵ�VIP��Ϣ����vipcount: "+vipcount);
			// dispatchEvent(new Event(CMD_NOTIFY_MY_VIP_INFO, null));
		} else if (CMD_NOTIFY_SITE_VIP_INFO.equals(strCmd)) {
			//�յ�ĳ����λ��VIP��Ϣ
			
			int userid = rd.readInt();
			int siteno= rd.readByte();
			Log.i("test18","�յ�ĳ����λ��VIP��Ϣ userid: "+userid+" siteno: "+siteno);
			int vipcount= rd.readInt();
			for (int i = 0; i < vipcount; i++){
			     int viplevel= rd.readInt();				//VIP�ȼ���1ͭ��VIP��2����VIP��3����VIP
				 String overtime= rd.readString();			//����ʱ�� �ַ���
				 Log.i("test18","overtime: "+overtime+" viplevel: "+viplevel);
			}
			//data["isrelogin"]	= m_netptr.readByte();				//�ص�½
			//dispatchEvent(new Event(CMD_NOTIFY_SITE_VIP_INFO, null));
		}  
	}
}
