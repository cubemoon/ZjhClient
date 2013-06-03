package com.dozengame.net;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

import com.dozengame.GameApplication;
import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.event.EventDispatcher;
import com.dozengame.net.pojo.ReadData;

public class DNetworkGameFriend extends EventDispatcher implements CallBack {
	SocketBase m_netptr;
    String tag ="DNetworkGameFriend";
	public DNetworkGameFriend(SocketBase netptr) {
		this.m_netptr = netptr;
		this.m_netptr.addEventListener(Event.SOCKET_DATA, this,"onProcessCommand");
	}

	private static final String CMD_RESPONSE_GAME_INFO_COPY = "REGICP"; // �յ�������Ϸ��Ϣ����
	private static final String CMD_RESPONSE_EXTRAINFO_ACHIEVEINFO = "RQPEXT"; // ������Ϣ�ͳɾ���Ϣ
	private static final String CMD_RESPONSE_EXTRAINFO = "RQMIXT"; // ֻ�Ǹ�����Ϣ

	 

	private static final String CMD_NOTIFY_FRIEND_LIST = "NTFD"; // ������Ϣ
	private static final String CMD_NOTIFY_FRIEND_LIST_END = "NTFDEND"; // ������Ϣ����

	private static final String CMD_NOTIFY_MATCH_CHANGE = "MCNTMS"; // �յ�ĳ����λ�ϵı���״̬�ı�

	private static final String CMD_NOTIFY_FRIEND_ADDED_PLAY = "FDSENDPLAY";
	private static final String CMD_NOTIFY_FRIEND_REQUEST_ADD = "FDSENDCANADD";

	private static final String CMD_NOTIFY_FRIEND_ONLINE_INFO = "FDSENDOL"; // �յ����к��ѵ��������
	private static final String CMD_NOTIFY_CHANNEL_ONLINE_INFO = "CHANNELGCL";
	private static final String CMD_NOTIFY_FRIEND_MGR_INFO = "FDSENDALL"; // �յ��[����x�������б�
	private static final String CMD_NOTIFY_FRIEND_INFO_END = "FDSENDEND"; // �յ����к��ѵ��������

	private static final String CMD_NOTIFY_FRIEND_NEW_ONLINE = "FDSENDNEWOL"; // �յ��º�������
	private static final String CMD_NOTIFY_FRIEND_ONE_ONLINE_CHANGE_INFO = "FDSENDSTATE"; // �յ�һ�����ѵ����߸ı����
	private static final String CMD_NOTIFY_FRIEND_NEW_MGR_INFO = "FDSENDTOMGR"; // �յ��º��ѽ�����Һ��ѹ�����

	private static final String CMD_NOTIFY_FRIEND_SHOW_ADD_BTN = "FDSENDSHOWADD"; // �յ�����ʾ�Ӻ��Ѱ�ť
	private static final String CMD_NOTIFY_FRIEND_INVITED_ME = "FDSENDINVITE"; // �յ�����ĳ��
	private static final String CMD_NOTIFY_FRIEND_CHANGE_RESULT = "FDSENDRESULT"; // �յ��ɹ�ɾ��һλ�x������

	private static final String CMD_NOTIFY_FRIEND_NOT_JOIN_VIP_DESK = "FDJOINFAIL"; // �յ������Լ��������Ϸ����

	public static final String CMD_FROM_CHANNEL_ID = "CHANNELGCI"; // ��ȡ����Ƶ��id
	
	public  void onProcessCommand(Event event){
		  ReadData rd= (ReadData)event.getData();
		  String strCmd=rd.getStrCmd();
		  Log.i(tag, "strCmd: "+strCmd);
		 if(CMD_RESPONSE_GAME_INFO_COPY.equals(strCmd)){
			//�յ�������Ϸ��Ϣ����
			 onResponseGameInfoCopy(rd);
		 }else if(CMD_RESPONSE_EXTRAINFO.equals(strCmd)){
			  //ֻ�Ǹ�����Ϣ
			 onResponseExtrainfo(rd);    
			   
		 }else if(CMD_RESPONSE_EXTRAINFO_ACHIEVEINFO.equals(strCmd)){
			 //������Ϣ�ͳɾ�
			 onResponseExtrainfoAchieveInfo(rd);
		 }else if(CMD_NOTIFY_MATCH_CHANGE.equals(strCmd)){
			 // �յ�ĳ����λ�ϵı���״̬�ı�
			 onNotifyMatchChange(rd);
		 }else if("FROK".equals(strCmd)){
			//���Ѽӳɹ���
			 onFrok(rd);
		 }else if("NTFC".equals(strCmd)){
			//������Ӻ���
			 onNtfc(rd);
		 }else if(CMD_NOTIFY_FRIEND_ADDED_PLAY.equals(strCmd)){
				//--------------------- �����˿� �º��ѹ��� --------------------//
			 onNotifyFriendAddedPlay(rd);
		 }else if(CMD_NOTIFY_FRIEND_REQUEST_ADD.equals(strCmd)){
			 
			 onNotifyFriendRequestAdd(rd);
		 }else if(CMD_NOTIFY_CHANNEL_ONLINE_INFO.equals(strCmd)){
			 
			 onNotifyChannelOnLineInfo(rd);
		 }else if(CMD_NOTIFY_FRIEND_ONLINE_INFO.equals(strCmd)){
			 onNotifyFriendOnLineInfo(rd);
		 }else if(CMD_NOTIFY_FRIEND_MGR_INFO.equals(strCmd)){
			 onNotifyFriendMgrInfo(rd);
		 }else if(CMD_NOTIFY_FRIEND_INFO_END.equals(strCmd)){
			 //���ѽ���
			 onNotifyFriendInfoEnd(rd);
		 }else if(CMD_NOTIFY_FRIEND_NEW_ONLINE.equals(strCmd)){
			 onNotifyFriednNewOnLine(rd);
		 }else if(CMD_NOTIFY_FRIEND_ONE_ONLINE_CHANGE_INFO.equals(strCmd)){
			 onNotifyFriendOneLineChangeInfo(rd);
		 }else if(CMD_NOTIFY_FRIEND_NEW_MGR_INFO.equals(strCmd)){
			 onNotifyFriendNewMgrInfo(rd);
		 }else if(CMD_NOTIFY_FRIEND_SHOW_ADD_BTN.equals(strCmd)){
			 //this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_SHOW_ADDED", null));
		 }else if(CMD_NOTIFY_FRIEND_INVITED_ME.equals(strCmd)){
			 onNotifyFriendInvitedMe(rd);
		 }else if(CMD_NOTIFY_FRIEND_CHANGE_RESULT.equals(strCmd)){
			 onNotifyFriendChangeResult(rd);
		 }else if(CMD_NOTIFY_FRIEND_NOT_JOIN_VIP_DESK.equals(strCmd)){
			// this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_NOT_JOIN_VIP_DESK", data));
		 }else if(CMD_FROM_CHANNEL_ID.equals(strCmd)){
			// this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_NOT_JOIN_VIP_DESK", data));
			 onFromChannelId(rd);
		 }
	 
		}
	
	//�յ�������Ϸ��Ϣ����
	private void onResponseGameInfoCopy(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("userid",m_netptr.readInt());		//�û�ID
		data.put("nick",m_netptr.readString());	//�ǳ�
		data.put("sex",m_netptr.readByte());		//�Ա�
		data.put("from",m_netptr.readString());	//����
		data.put("gold",m_netptr.readInt());		//���
		data.put("face",m_netptr.readString());	//ͷ��
		data.put("exp",m_netptr.readInt());		//����
		byte active_game_count =m_netptr.readByte();
		data.put("active_game_count",active_game_count);
		HashMap array = new HashMap();
		data.put("gameinfocopy",array);
		for (int i = 0; i < active_game_count; i++){
			String gamename = m_netptr.readString();
			HashMap arrayTemp = new HashMap();
			//data["gameinfocopy"][gamename] = new Array();
			array.put(gamename, arrayTemp);
			//������Ϣ
			int prestige = m_netptr.readInt();
			arrayTemp.put("prestige", prestige);
			arrayTemp.put("ccPoint", m_netptr.readInt());
			 

			int finishedCount = m_netptr.readInt();
			int allCount = m_netptr.readInt();	
			arrayTemp.put("finishedCount",finishedCount);
			arrayTemp.put("allCount",allCount);
			arrayTemp.put("qusetprogress",(finishedCount + "/" + allCount));
		}
		data.put("goldpainum",m_netptr.readInt());
		
		//��Ӯ���ں���Ӯ��,�Լ�������ʱ��
		//DTrace.traceex("user_history")
		byte history_count =m_netptr.readByte();
		data.put("history_count",history_count);
		ArrayList list = new ArrayList();
		data.put("history",list);
		for (int i = 0; i < history_count; i++){
			String date = m_netptr.readString();
			int win= m_netptr.readInt();
			int lose = m_netptr.readInt();
			HashMap temp = new HashMap();
			list.add(temp);//new Array();
			temp.put("date",date);
			temp.put("win",win);
			temp.put("lose",lose);
		}
		data.put("server_time",m_netptr.readString());
		//this.dispatchEvent(new DDataEvent("ON_RECV_USER_GAMEINFO_COPY", data));
	}
	//ֻ�Ǹ�����Ϣ
	private void onResponseExtrainfo( ReadData m_netptr){
	    
		HashMap data = new HashMap();
		
	    data.put("userid",m_netptr.readInt());		//�û�ID
		data.put("nick",m_netptr.readString());	//�ǳ�
		data.put("sex", m_netptr.readByte());		//�Ա�
		data.put("from",m_netptr.readString());	//����
		data.put("gold",m_netptr.readInt());		//���
		data.put("exp",m_netptr.readInt());		//����
		data.put("face",m_netptr.readString());	//ͷ��
		
		data.put("reg_date",m_netptr.readString());//��������
		data.put("max_win",m_netptr.readInt());
		data.put("pokeweight",m_netptr.readString());  //������͵�Ȩֵ
		int pokes5_count=m_netptr.readByte();
		data.put("pokes5_count",pokes5_count);
		int [] pokes5 = new int[pokes5_count];
		data.put("pokes5",pokes5);
		for (int i = 0; i < pokes5_count; i++){
			pokes5[i] = m_netptr.readByte();
		}
		data.put("play_count",m_netptr.readInt());  //�����Ϸ����
		data.put("win_count",m_netptr.readInt());	  //Ӯ��������
		data.put("max_gold",m_netptr.readInt());		//�������ӵ�г���
		data.put("friend_count",m_netptr.readInt());	//���Ѹ���
		data.put("today_winlost",m_netptr.readInt());	//������Ӯ
		data.put("deskmatchwin", m_netptr.readInt());	//Ӯ������̭������
		data.put("vip",m_netptr.readByte());
		data.put("charmlevel",m_netptr.readInt());	  //ũ�������ȼ�
		data.put("charmvalue",m_netptr.readInt());	  //ũ������ֵ
	//	data.put("charmvalue",Number(data["charmvalue"]) / 10; //����ֵ��С����
		data.put("charmgold",m_netptr.readInt());	  //ÿ���콱�������ӵĳ���
		data.put("channelId",m_netptr.readInt());
		//this.dispatchEvent(new DDataEvent("ON_RECV_RESPONSE_EXTRAINFO", data));
	}
	//������Ϣ�ͳɾ�
	private void onResponseExtrainfoAchieveInfo(ReadData m_netptr){
		Log.i("test3", "onResponseExtrainfoAchieveInfoon");
		HashMap data = new HashMap();
		data.put("userid",m_netptr.readInt());		//�û�ID
		data.put("nick",m_netptr.readString());	//�ǳ�
		data.put("sex",m_netptr.readByte());		//�Ա�
		data.put("from",m_netptr.readString());	//����
		data.put("gold",m_netptr.readInt());		//���
		data.put("exp",m_netptr.readInt());		//����
		data.put("face",m_netptr.readString());	//ͷ��
		
		data.put("reg_date", m_netptr.readString());//��������
		data.put("max_win",m_netptr.readInt());
		data.put("pokeweight",m_netptr.readString());  //������͵�Ȩֵ
		int pokes5_count =m_netptr.readByte();
		data.put("pokes5_count",pokes5_count);
		int []pokes5 = new int[pokes5_count];
		data.put("pokes5",pokes5);
		for (int i = 0; i < pokes5_count; i++){
			pokes5[i] = m_netptr.readByte();
		}
		data.put("play_count",m_netptr.readInt());  //�����Ϸ����
		data.put("win_count",m_netptr.readInt());	  //Ӯ��������
		data.put("max_gold",m_netptr.readInt());		//�������ӵ�г���
		data.put("friend_count",m_netptr.readInt());	//���Ѹ���
		data.put("today_winlost", m_netptr.readInt());	//������Ӯ
		data.put("deskmatchwin",m_netptr.readInt());	//Ӯ������̭������
		data.put("servertime",m_netptr.readString());
		int achieve_count =m_netptr.readInt();
		data.put("achieve_count",achieve_count);
		//�ɾ�
	    ArrayList list = new ArrayList();
		data.put("achieve",list);
		for (int i = 0; i < achieve_count; i++){
			HashMap temp = new HashMap();
			list.add(temp);
			temp.put("id", m_netptr.readInt());
			temp.put("time", m_netptr.readString());
		}
		data.put("vip",m_netptr.readByte());
		data.put("charmlevel",m_netptr.readInt());	  //ũ�������ȼ�
		data.put("charmvalue",m_netptr.readInt());	  //ũ������ֵ
		//data.put("charmvalue",Number(data["charmvalue"]) / 10; //����ֵ��С����
		data.put("charmgold",m_netptr.readInt());	  //ÿ���콱�������ӵĳ���
		data.put("channelId",m_netptr.readInt());   //��ȡƵ��id
		data.put("v_task_count",m_netptr.readInt());   //�������ÿ������Ĵ���
	    //this.dispatchEvent(new Event(FriendEventType.ON_RECV_EXTRAINFO_ACHIEVEINFO, data));
		if(GameApplication.getDzpkGame() != null){
			if(GameApplication.getDzpkGame().gameView !=null){
				GameApplication.getDzpkGame().gameView.onResponseExtrainfoAchieveInfo(data);
			}
		}
		
	}
	 // �յ�ĳ����λ�ϵı���״̬�ı�
	private void onNotifyMatchChange(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("site",m_netptr.readByte());		//��λ��
		data.put("matchtypeid",m_netptr.readInt());		//���ڽ��еı������� 0=δ���б���
		data.put("numOfCups",m_netptr.readInt());		//��õĽ�����
		//this.dispatchEvent(new DDataEvent("ON_RECV_MATCH_CHANGE", data));	
	}
	//���Ѽӳɹ���
	private void  onFrok(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("fromuserid",m_netptr.readInt());
		data.put("touserid",m_netptr.readInt());
		//this.dispatchEvent(new DNetWorkEvent("friend_add_ok", data));
	}
	//������Ӻ���
	private void  onNtfc(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("md5", m_netptr.readString());
		data.put("toid",m_netptr.readInt());
		data.put("fromid",m_netptr.readInt());
		data.put("nick",m_netptr.readString());
		//this.dispatchEvent(new DNetWorkEvent("request_add_friend", data));
	}
	private void  onNotifyFriendAddedPlay(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("fromId",m_netptr.readInt());
		data.put("toId",m_netptr.readInt());
		//this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_ADD_PLAY_MC", data));
	}
	private void  onNotifyFriendRequestAdd(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("md5",m_netptr.readString());
		data.put("toid",m_netptr.readInt());
		data.put("fromid", m_netptr.readInt());
		data.put("nick",m_netptr.readString());
		//this.dispatchEvent(new DNetWorkEvent("ON_RECV_FRIEND_REQUEST_ADD", data));
	}
	private void onNotifyChannelOnLineInfo(ReadData m_netptr) {
		int userId = 0;
		ArrayList channelData = new ArrayList();
		int totalpage = m_netptr.readInt();
		while ((userId = m_netptr.readInt()) != 0) {
			HashMap user = new HashMap();

			user.put("id", userId);
			user.put("nick", m_netptr.readString());
			user.put("face", m_netptr.readString());
			user.put("vip", m_netptr.readByte());
			user.put("type", m_netptr.readByte());
			if (m_netptr.readByte() > 0) {

				HashMap deskinfo = new HashMap();
				user.put("deskinfo", deskinfo);
				deskinfo.put("small", m_netptr.readInt());
				deskinfo.put("large", m_netptr.readInt());
				deskinfo.put("id", m_netptr.readInt());
			}
			channelData.add(user);
		}
		// dispatchEvent(new DDataEvent("ON_RECV_CHANNEL_ONLINE_INFO",
		// {info:channelData, totalpage:totalpage}));

	}
	ArrayList frienddata  = new ArrayList();
	private void  onNotifyFriendOnLineInfo(ReadData m_netptr){
		int nUserid = 0;
		while ((nUserid = m_netptr.readInt()) != 0){
			HashMap fi = new HashMap();
			fi.put("id",nUserid);
			fi.put("nick",m_netptr.readString());
			fi.put("face",m_netptr.readString());
			fi.put("vip",m_netptr.readByte());
			fi.put("type", m_netptr.readByte());
			if (m_netptr.readByte() > 0){
				 
				HashMap deskinfo = new HashMap();
				fi.put("deskinfo", deskinfo);
				deskinfo.put("small", m_netptr.readInt());
				deskinfo.put("large", m_netptr.readInt());
				deskinfo.put("id", m_netptr.readInt());
			}
			frienddata.add(fi);
		}
	}
	
	private void onNotifyFriendMgrInfo(ReadData m_netptr){
		int nUid = 0;
		while ((nUid = m_netptr.readInt()) != 0){
			HashMap fmi = new HashMap();
			fmi.put("id",nUid);
			fmi.put("nick",m_netptr.readString());
			fmi.put("face", m_netptr.readString());
			fmi.put("level",m_netptr.readInt());
			fmi.put("gold ",m_netptr.readInt());
			fmi.put("isSNS",m_netptr.readByte());
			fmi.put("vip",m_netptr.readByte());
			frienddata.add(fmi);
		}
	}
	/**
	 * ���ѽ���
	 * @param rd
	 */
	private void onNotifyFriendInfoEnd(ReadData m_netptr){
		int len = m_netptr.readInt();
		int isOnline = m_netptr.readByte();
//		if(isOnline == 0)
//			this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_ONLINE_INFO", { info:frienddata, len:len } ));
//		else
//			this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_MGR_INFO", { info:frienddata, len:len } ));
		frienddata.clear();
	}
	
	private void  onNotifyFriednNewOnLine(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("id",m_netptr.readInt());
		data.put("nick",m_netptr.readString());
		data.put("face",m_netptr.readString());
		data.put("vip",m_netptr.readByte());
		data.put("type", m_netptr.readByte());
		if (m_netptr.readByte() > 0){
			HashMap deskInfo = new HashMap();
			data.put("deskInfo", deskInfo);
			deskInfo.put("small",m_netptr.readInt());
			deskInfo.put("large",m_netptr.readInt());
			deskInfo.put("id",m_netptr.readInt());
		}
		//this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_ONLINE_NEW_INFO", data));
	}
	private void onNotifyFriendOneLineChangeInfo(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("id",m_netptr.readInt());
		data.put("type",m_netptr.readByte());
		if (m_netptr.readByte() > 0){
			HashMap deskInfo = new HashMap();
			data.put("deskInfo", deskInfo);
			deskInfo.put("small",m_netptr.readInt());
			deskInfo.put("large",m_netptr.readInt());
			deskInfo.put("id",m_netptr.readInt());
		}
		//this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_ONLINE_ONE_CHANGE_INFO", data));
	}
	private void  onNotifyFriendNewMgrInfo(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("id",m_netptr.readInt());
		data.put("nick",m_netptr.readString());
		data.put("face",m_netptr.readString());
		data.put("level", m_netptr.readInt());
		data.put("gold",m_netptr.readInt());
		data.put("isSNS",m_netptr.readByte());
		data.put("vip",m_netptr.readByte());
		//this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_NEW_MGR_INFO", data));
	}
	private void  onNotifyFriendInvitedMe(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("type",m_netptr.readByte());
		data.put("nick",m_netptr.readString());
		data.put("small",m_netptr.readInt());
		data.put("large",m_netptr.readInt());
		data.put("deskno",m_netptr.readInt());
		//this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_INVITED_ME", data));
	}
	private void  onNotifyFriendChangeResult(ReadData m_netptr){
		HashMap data = new HashMap();
		data.put("type",m_netptr.readByte());
		data.put("success",m_netptr.readByte());
		data.put("userid",m_netptr.readInt());//�޸����ĸ����
		//this.dispatchEvent(new DDataEvent("ON_RECV_FRIEND_CHANGE_RESULT", data));
	}
	private void onFromChannelId(ReadData m_netptr){
		 
		int channelId = m_netptr.readInt();
		//this.dispatchEvent(new DDataEvent(CMD_FROM_CHANNEL_ID, channelId));
	}
	

	// ����ˢ�º���״̬ ע��,�˴��õ�writeBeginToGameCenter(),��Ϣ����GameCenter���յ�.
	public void sendReloadFriend() throws Exception {
		//m_netptr.writeBeginToGameCenter();
		m_netptr.writeString("RQFDL");
		m_netptr.writeEnd();
	}

	// �����Ϊ����
	public void sendRequestAddFriend(int nUserid)throws Exception {
		
		m_netptr.writeString("RQAF");
		m_netptr.writeInt(nUserid);
		m_netptr.writeEnd();
	}

	public void sendAddFriend(int nUserid)throws Exception {

		m_netptr.writeString("FDRQWTAD");
		m_netptr.writeInt(nUserid);
		m_netptr.writeEnd();
	}

	// ������ϸ��Ϸ����Ϣ
	public void sendRequestGameInfoPackage(int nUserid)throws Exception {

		m_netptr.writeString(CMD_RESPONSE_GAME_INFO_COPY);
		m_netptr.writeInt(nUserid);
		m_netptr.writeEnd();
	}

	// ������Ҹ�����Ϣ
	public void sendRequestUserExtraInfo(int nUserid)throws Exception {
		// DTrace.traceex("������Ҹ�����Ϣ:" + nUserid);

		m_netptr.writeString(CMD_RESPONSE_EXTRAINFO);
		m_netptr.writeInt(nUserid);
		m_netptr.writeEnd();
	}

	// ������Ϸ�߸�����Ϣ�ͳɾ���Ϣ
	public void sendRequestUserExtraInfoAchieveInfo(int nUserid)throws Exception {

		m_netptr.writeString(CMD_RESPONSE_EXTRAINFO_ACHIEVEINFO);
		m_netptr.writeInt(nUserid);
		m_netptr.writeEnd();
	}

	// ͬ�����Ϊ����
	public void sendAcceptAddFriend(String szMd5, int nFromUserId) throws Exception{

		m_netptr.writeString("ACAF");
		m_netptr.writeString(szMd5);
		m_netptr.writeInt(nFromUserId);
		m_netptr.writeEnd();
	}

	public void sendDelMgrFriend(int id) throws Exception{

		m_netptr.writeString("");
		m_netptr.writeInt(id);
		m_netptr.writeEnd();
	}

	public void sendInviteFriend(int id)throws Exception {

		m_netptr.writeString("FDRQYQHY");
		m_netptr.writeInt(id);
		m_netptr.writeEnd();
	}

	public void sendShowAddFriend(int userid)throws Exception {

		m_netptr.writeString("FDRQTJHY");
		m_netptr.writeInt(userid);
		m_netptr.writeEnd();
	}

	public void sendChangeFriendInfo(int type, int heId, String md5) throws Exception{

		m_netptr.writeString("FDRQZJHY");
		m_netptr.writeByte((byte)type);
		m_netptr.writeInt(heId);
		m_netptr.writeString(md5);
		m_netptr.writeEnd();
	}

	public void sendRequestAllFriendInfo() throws Exception{

		m_netptr.writeString("FDRQAFIF");
		m_netptr.writeEnd();
	}

	public void sendRequestJoinFriend(int deskId)throws Exception {

		m_netptr.writeString("FDRQJOIN");
		m_netptr.writeInt(deskId);
		m_netptr.writeEnd();
	}

	public void sendRequestChannelInfo(int page) throws Exception{

		m_netptr.writeString(CMD_NOTIFY_CHANNEL_ONLINE_INFO);
		m_netptr.writeInt(page);
		m_netptr.writeEnd();
	}

	/**
	 * ����Э����
	 * 
	 * @param cmdFromChannelId
	 */
	public void sendDefault(String strCmd) throws Exception{

		m_netptr.writeString(strCmd);
		m_netptr.writeEnd();

	}

}
