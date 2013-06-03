package com.dozengame.net;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.util.Log;

import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.event.EventDispatcher;
import com.dozengame.net.pojo.DGroupInfoItem;
import com.dozengame.net.pojo.ReadData;
 
/**
 * ���������������
 * @author hewengao
 *
 */
public class DNetworkCenter extends EventDispatcher implements CallBack{
	// ���͵�����
	private final String CMD_REQUEST_LOGIN = "RQLG"; // �����½
	private final String CMD_REQUEST_GROUP_INFO = "RQGI"; // �����ȡ�����б�

	// ���ܵ�����(��Ӧ)
	private final String CMD_RESPONSE_LOGIN_RESULT = "RELG"; // ��½���
	private final String CMD_RESPONSE_GROUP_INFO = "REGI"; // �����б�
	private final String CMD_RESPONSE_GROUP_INFO_END = "REGIEND"; // �����б����
	private final String ON_RECV_DISABLE_ACCOUNT = "DSAC"; // �յ������Ϣ

	// ���ܵ�����(֪ͨ)
	private final String CMD_NOTIFY_MESSAGE = "NTMS"; // ֪ͨ��Ϣ
	private final String CMD_NOTIFT_LINEFAILED = "NTOF"; // ����֪ͨ
	private final String CMD_NOTIFT_GIVE_GOLD = "NTGL"; // ��¼��Ǯ
	private SocketBase dRSocket = null;
	 
	public DNetworkCenter(SocketBase dRSocket) {
		this.dRSocket = dRSocket;
		this.dRSocket.addEventListener(Event.SOCKET_DATA, this, "onDataRecv");
	}

	/**
	 * ��������
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	public void onDataRecv(Event e) throws IOException, Exception {
		ReadData rd =(ReadData)e.getData();
		if(rd ==null)return ;
		  String strCmd = rd.getStrCmd();
		System.out.println("DNetworkCenter: ���մ���ָ��: " + strCmd);
		if (strCmd.equals(CMD_RESPONSE_LOGIN_RESULT)) {
			// ���յ�¼���
			receiveLoginResult(rd);
		} else if (strCmd.equals(ON_RECV_DISABLE_ACCOUNT)) {
			// �����շ����Ϣ
			receiveDisableAccount(rd);
		} else if (strCmd.equals(CMD_RESPONSE_GROUP_INFO)) {
			// ���շ�����Ϣ
			receiveGroupInfo(rd);
		} else if (strCmd.equals(CMD_RESPONSE_GROUP_INFO_END)) {
			// �����б����
			System.out.println("CMD_RESPONSE_GROUP_INFO_END");
			dispatchEvent(new Event(Event.ON_RECV_GROUP_INFO, roomsInfo));
		} else if (strCmd.equals(CMD_NOTIFY_MESSAGE)) {
			// ����֪ͨ��Ϣ
			receiveNotifyMessage(rd);
		} else if (strCmd.equals(CMD_NOTIFT_LINEFAILED)) {
			// ����֪ͨ
			// this.dispatchEvent(new
			// DNetworkCenterEvent(DNetworkCenterEvent.ON_RECONNECT, null));
		} else if (strCmd.equals(CMD_NOTIFT_GIVE_GOLD)) {
			// ��¼��Ǯ
			receiveGiveGlod(rd);
		} else {
			System.out.println("!!!DNetworkCenter: ����δ����ָ��: " + strCmd);
		}

	}

	// ������Ϣ��
	private HashMap roomsInfo = new HashMap();

	// ������
	private List frienddata = null;
	private int friendIdx = 0;
	private boolean friendLoadedFlag = false;

	/**
	 * ���͵�¼��Ϣ��������������������
	 * 
	 * @param strUsername
	 * @param strPassword
	 */
//	public void sendLoginInfo(String strUsername, String strPassword)
//			throws Exception {
//		dRSocket.writeString(CMD_REQUEST_LOGIN);
//		dRSocket.writeString(strUsername);
//		dRSocket.writeString(strPassword);
//		dRSocket.writeByte((byte) 0);
//		dRSocket.writeString("1");
//		dRSocket.writeEnd();
//	}

	/**
	 * ����ȡ�÷�����Ϣ
	 * 
	 * @param strGameName
	 */
	public void sendGetAreaInfo(String strGameName) throws Exception {
		System.out.println("##����ȡ�÷�����Ϣ");
		dRSocket.writeString(CMD_REQUEST_GROUP_INFO);
		dRSocket.writeString(strGameName);
		dRSocket.writeEnd();
	}

	/**
	 * ���ͷ�����Ϣ
	 * 
	 * @param nToken
	 * @throws Exception
	 */
	public void sendToken(int nToken) throws Exception {
		dRSocket.writeString("ANYC");
		dRSocket.writeInt(nToken);
		dRSocket.writeEnd();
	}

	/**
	 * ���յ�¼���
	 */
	private void receiveLoginResult(ReadData rd) {
		
		HashMap data = new HashMap();
		data.put("code", rd.readShort());
		data.put("lastroomname", rd.readString());
		data.put("lastroomid", rd.readInt());
		data.put("userid", rd.readInt());
		data.put("gamekey", rd.readString());
		data.put("md5", rd.readString());
		data.put("isnewuser", rd.readByte());
		
		 
	    dispatchEvent(new Event(Event.ON_RECV_LOGIN_RESULT, data));

	}

	/**
	 * �յ������Ϣ
	 */
	private void receiveDisableAccount(ReadData rd) {
		// �յ������Ϣ
		Hashtable data = new Hashtable();
		data.put("userid", rd.readInt());
		byte status = rd.readByte();
		data.put("status", status);

		if (status == 1) {
			data.put("endtime", rd.readString());
		}
		// this.dispatchEvent(new
		// DNetworkCenterEvent(DNetworkCenterEvent.ON_RECV_DISABLE_ACCOUNT,
		// data));
	}

	/**
	 * ���շ�����Ϣ
	 */
	private void receiveGroupInfo(ReadData rd) {
		// �����б�
		ArrayList<DGroupInfoItem> roomList = new ArrayList<DGroupInfoItem>();
		String groupid = "";
		String name = rd.readString();
		System.out.println("name: "+name);
		while (!(groupid = rd.readString()).equals("")) {
			 
			DGroupInfoItem item = new DGroupInfoItem();
			item.groupid = Integer.valueOf(groupid);
			item.groupname = rd.readString();
			item.gamepeilv = rd.readInt();
			item.ip = rd.readString();
			item.port = rd.readInt();
			
		
			System.out.println("item.groupname: "+item.groupname );
			System.out.println("item.ip: "+item.ip);
			System.out.println("item.port: "+item.port);
			
			item.curronline = rd.readInt();
			item.maxonline = rd.readInt();
			item.isguildroom = rd.readInt();
			item.istourroom = rd.readInt();
			item.ishighroom = rd.readInt(); // �Ƿ�Ϊ���ֳ� todo cw
			item.ishuanle = rd.readInt(); // �Ƿ�Ϊ���ֳ�
			item.nocheat = rd.readInt(); // �Ƿ�Ϊ������
			item.at_least_gold = rd.readInt();
			item.at_most_gold = rd.readInt();
			roomList.add(item);
			item.toString();
		}
		roomsInfo.put(name, roomList);
	}

	/**
	 * ����֪ͨ��Ϣ
	 */
	private void receiveNotifyMessage(ReadData rd) {
		// ֪ͨ��Ϣ
		Hashtable data = new Hashtable();
		data.put("message", rd.readString());
		// this.dispatchEvent(new
		// DNetworkCenterEvent(DNetworkCenterEvent.ON_RECV_MESSAGE, data));

	}

	/**
	 * ��¼��Ǯ
	 */
	private void receiveGiveGlod(ReadData rd) {
		Hashtable data = new Hashtable();
		data.put("gold", rd.readInt());
		// this.dispatchEvent(new
		// DNetworkCenterEvent(DNetworkCenterEvent.ON_RECV_GIVE_GOLD, data));
	}
  
}
