package com.dozengame.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.util.Log;
import com.dozengame.DzpkGameMenuActivity;
import com.dozengame.GameApplication;
import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.event.EventDispatcher;
import com.dozengame.net.pojo.DConfig;
import com.dozengame.net.pojo.DeskInfo;
import com.dozengame.net.pojo.ReadData;
import com.dozengame.playerlocationroom.PlayerLocationData;
import com.dozengame.util.GameUtil;

/**
 *��Ϸ������� ��Ҫ����: ����Ϸ����������, ���շ�����Ӧ
 * 
 * @author hewengao
 * 
 */
public class DNetworkGame extends EventDispatcher implements CallBack {

	final String tag = "DNetworkGame";
	private String CMD_MOBLOGIN="MOBLOGIN";//�ֻ���¼
	private String CMD_MOBPAYOFF="MO_PAYOFF";//�ֻ�֧������
	// ���͵�����
	private String CMD_CHECK_NET="CHECK_NET";//���ټ��
	private String CMD_MO_REFRESH="MO_REFRESH";//ˢ��
	private String CMD_REQUEST_LOGIN = "RQLG"; // �����½

	private String CMD_REQUEST_AUTO_JOIN = "RQAJ"; // �����Զ�������Ϸ
	private String CMD_REQUEST_PAIDUI = "RQDQ"; // �����Ŷ�
	private String CMD_REQUEST_UNQUEUE = "RQCQ"; // ȡ���Ŷ�
	private String CMD_REQUEST_ROOM_DESKLIST = "RQDS"; // ���������б�

	private String CMD_REQUEST_ROOMLIST_PAGE = "RQDP"; // �����ҳ������ϸ��Ϣ
	private String CMD_REQUEST_DESKLIST = "RQFD"; // �����ҳ������ϸ��Ϣ
	private String CMD_REQUEST_STANDUP = "RQSU"; // �û�վ��
	private String CMD_REQUEST_CHANGE_FACE = "RQCF"; // �û�����ı�ͷ��
	private String CMD_REQUEST_ACTIVE_FACE = "RAAF"; // �û����뼤��ͷ��
	private String CMD_REQUEST_TIME_TIME = "RETT"; // ����
	private String CMD_REQUEST_ONLONE_SERVER = "RQOS"; // ��ѯ���з���������������
	private String CMD_REQUEST_GIVE_GOLD = "RQGG"; // Ҫ����Ǯ
	private String CMD_REQUEST_CLIENT_LEAVE_ROOM = "RQCLR"; // �뿪��������

	private String CMD_REQUEST_UPDATE_GOLD = "RQGB"; // Ҫ�������ˢ��Ǯ
	private String CMD_REQUEST_WATCH = "REWT"; // Ҫ���ս
	private String CMD_REQUEST_EXIT_WATCH = "REET"; // Ҫ���˳���ս
	private String CMD_REQUEST_ROOM_SORT_LIST = "RQRSL"; // �ͻ������󷿼��������
	private String CMD_REQUEST_UPDATE_VIPINFO = "RQVIPIF"; // �ͻ�������ˢ��VIP��Ϣ
	private String CMD_NOTIFY_TEX_BBS_URL = "TXNBBS"; // �յ���̳��֤��
	// -------------------------��Ȩ��str--------------------
	private String CMD_NOTIFY_TEX_PRIVILEGE_YANZHENG = "JHMGIFTSN";// ��Ȩ����֤
	// -------------------------��Ȩ��end--------------------
	private String CMD_TEX_UPDATE_CHANNEL_INFO = "TEXUSERCHANNEL"; // �����û��ǳ�
	private String CMD_TEX_CHOOSE_ROLE = "TEXAUTHORBAR"; // �յ���ʾѡ���ɫ���
	private String CMD_TEX_UPDATE_NICK_INFO = "TEXUSERNICK"; // �����û��ǳ�

	// ���ܵ�����(��Ӧ)
	private String CMD_RESPONSE_LOGIN_RESULT = "RELG"; // ��½���

	// /////////////////////////////////////////////////////////////////////////////////////////////////
	// private String CMD_RESPONSE_ROOMLIST_PAGE_START = "REDPS"; //ȡ�÷�ҳ������ϸ��Ϣ��ʼ
	// private String CMD_RESPONSE_ROOMLIST_PAGE = "REDP"; //ȡ�÷�ҳ������ϸ��Ϣ
	// private String CMD_RESPONSE_ROOMLIST_PAGE_END = "REDPE"; //ȡ�÷�ҳ������ϸ��Ϣ����
	// /////////////////////////////////////////////////////////////////////////////////////////////////

	private String CMD_RESPONSE_ROOMLIST_START = "REDSS"; // ȡ�÷�ҳ������ϸ��Ϣ��ʼ
	private String CMD_RESPONSE_ROOMLIST_PAGE = "REDS"; // ȡ�÷�ҳ������ϸ��Ϣ
	private String CMD_RESPONSE_ROOMLIST_END = "REDSE"; // ȡ�÷�ҳ������ϸ��Ϣ����
	private String CMD_RESPONSE_DESK_USERLIST = "SDDU"; // ȡ����������ҵ���ϸ��Ϣ

	private String CMD_RESPONSE_ROOMLIST_DESK_CHANGED = "SDDSS"; // ȡ�÷�ҳ���ӱ仯��Ϣ
	private String CMD_RESPONSE_ONLONE_SERVER = "REOS"; // ȡ�����з���������������
	private String CMD_RESPONSE_REDBAG = "RERB"; // �յ����
	private String CMD_RESPONSE_UPDATE_GOLD = "REGB"; // �յ���Ҹ���

	private String CMD_RESPONSE_HALL_INFO = "REDU"; // �յ�������ϸ��Ϣ[����]
	private String CMD_RESPONSE_GAME_INFO = "NTDU"; // �յ�������ϸ��Ϣ[��Ϸ]

	private String CMD_RESPONSE_WATCH_ERROR = "RESE"; // �յ���Ϸ�����Ϣ
	private String CMD_RESPONSE_FRIEND_CHANGE = "NTAF"; // ������Ϣ�ı�
	private String CMD_RESPONSE_ACTION_FACE = "RAAF"; // ����ͷ����
	private String CMD_RESPONSE_CHANGE_FACE = "CHFCOK"; // ����ͷ��OK
	private String CMD_RESPONSE_SELECT_FACE = "RAHD"; // ѡ��ͷ����
	private String CMD_RESPONSE_SORT_LIST = "RERSL"; // �����������֪ͨ
	private String CMD_RESPONSE_STANDUP_RESULT = "REOT"; // վ����
	// ���ܵ�����(֪ͨ)
	private String CMD_NOTIFY_MY_INFO = "NTMI"; // �յ��Լ���Ϣ
	private String CMD_NOTIFY_GROUP_INFO = "NTGP"; // �������Ϣ
	private String CMD_NOTIFY_CAN_NOT_TOROOM = "RQNI"; // �޷����뷿��
	private String CMD_NOTIFY_RICH_CANNOT_TOROOM = "TEXXST";// ̫�����ˣ����ܽ���
	private String CMD_NOTIFY_SERVER_ERROR = "REMG"; // ������������ʾ��Ϣ
	private String CMD_NOTIFY_RESTRICT_LOGIN = "NTIP"; // �յ��������ܾ���½
	private String CMD_NOTIFY_UPDATAQUEUE = "NTQC"; // ���¶�����Ϣ
	private String CMD_NOTIFY_TIME_TIME = "NTTT"; // ����
	private String CMD_NOTIFY_GM_KICK = "GMSK"; // GM����
	private String CMD_NOTIFY_SITDOWN_RESULT = "REFS"; // �յ����½��
	private String CMD_NOTIFY_GOLD_CHANGE = "NTGC"; // �յ�����Ǯ�ı�
	private String CMD_NOTIFY_LOW_GOLD = "NTPC"; // �յ�Ǯ�����߳�����

	private String CMD_NOTIFY_STUDY_PRIZE = "STOV"; // �յ�ѧϰ�̳̽���

	private String CMD_NOTIFY_USER_DEL = "ULDL"; // �յ�����˳�
	private String CMD_NOTIFY_USER_ADD = "ULAD"; // �յ���ҽ���

	private String CMD_NOTIFY_INTEGRAL_JIESUAN = "REINTE"; // �յ����ֽ���

	private String TOURNAMENT_RECV_ADDPOINT_RESULT = "TRBURS"; // �յ���������������

	// ��½��Ǯ
	private String CMD_NOTIFY_TEX_LOGIN_SHOW_DAY_GOLD = "SHOWDAYGOLD"; // �򿪵�½��Ǯ
	private String CMD_NOTIFY_TEX_RECV_LOGIN_GIVE = "REDAYGOLD"; // �յ���½��Ǯ����Ŀ

	private String CMD_NOTIFY_TEX_RECV_SHOW_FEEDBACK = "SHOWFEEDBK"; // �յ���ʾ��������

	private String CMD_NOTIFY_TEX_SHOW_CHONGZHI = "REHISMPAY"; // �յ���ʷ��߳�ֵ���

	// -------------
	private List m_guildMemberList;
	private ArrayList m_deskList; // ���������б�

	private SocketBase m_netptr;// �ײ�����ӿ�
	
	//���λ��GPS
	private String CMD_REQUEST_GPS = "GPSPOSI"; // GPS����

	/**
	 * ���캯��
	 * 
	 * @param net_ptr
	 */
	public DNetworkGame(SocketBase net_ptr) {
		this.m_netptr = net_ptr;
		this.m_netptr.addEventListener(Event.SOCKET_DATA, this, "onDataRecv");
	}

	/**
	 * ��������
	 * 
	 * @param event
	 */
	public void onDataRecv(Event event) {
		// trace(e.toString());
		ReadData rd = (ReadData) event.getData();
		String strCmd = rd.getStrCmd();
		Log.i(tag, "DNetworkGame receive command: " + strCmd);
        if(CMD_MOBPAYOFF.equals(strCmd)){
        	receiveMobPayOff(rd);
        }
        else if (CMD_NOTIFY_MY_INFO.equals(strCmd)) {
			// �յ��Լ���Ϣ
			receiveMyInfo(rd);
		} else if (CMD_NOTIFY_GROUP_INFO.equals(strCmd)) {
			// �������Ϣ
			receiveGroupInfo(rd);
		} else if (CMD_NOTIFY_SITDOWN_RESULT.equals(strCmd)) {
			// /�յ����½��
			receiveSitDownResult(rd);
		} else if (CMD_RESPONSE_LOGIN_RESULT.equals(strCmd)) {
			// ��½���
			receiveLoginResult(rd);
		} else if (CMD_RESPONSE_ROOMLIST_START.equals(strCmd)) {
			// ȡ�÷�ҳ������ϸ��Ϣ��ʼ
			receiveRoomListStartResult(rd);
		} else if (CMD_RESPONSE_ROOMLIST_PAGE.equals(strCmd)) {
			// ȡ�÷�ҳ������ϸ��Ϣ
			receiveRoomListPageResult(rd);
		} else if (CMD_RESPONSE_ROOMLIST_END.equals(strCmd)) {
			// ȡ�÷�ҳ������ϸ��Ϣ����
			receiveRoomListEndResult(rd);
		} else if (CMD_RESPONSE_DESK_USERLIST.equals(strCmd)) {
			// �յ������������˵���Ϣ
			receiveDeskUserList(rd);
		} else if (CMD_RESPONSE_ROOMLIST_DESK_CHANGED.equals(strCmd)) {
			// ȡ�÷�ҳ���ӱ仯��Ϣ
			receiveDeskChanged(rd);
		} else if (CMD_NOTIFY_UPDATAQUEUE.equals(strCmd)) {
			// ���¶�����Ϣ
			receiveUpdateQueue(rd);
     	}
//      else if (CMD_NOTIFY_SERVER_ERROR.equals(strCmd)) {
//			// ������������ʾ��Ϣ
//			receiveServerError(rd);
//		} 
		else if (CMD_NOTIFY_RESTRICT_LOGIN.equals(strCmd)) {
			// ������IP���Ƶ�½
			receiveRestrictLogin(rd);
		} else if (CMD_NOTIFY_STUDY_PRIZE.equals(strCmd)) {
			// �յ�ѧϰ�̳̽���
			receiveStudyPrize(rd);
		} else if (CMD_NOTIFY_CAN_NOT_TOROOM.equals(strCmd)) {
			// �޷����뷿��
			receiveCanNotToRoom(rd);
		} else if (CMD_NOTIFY_GM_KICK.equals(strCmd)) {
			// GM����
			receiveGmKick(rd);
		} else if (CMD_NOTIFY_TIME_TIME.equals(strCmd)) {
			// ����
			try {
				sendReplyTimeTime();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (CMD_REQUEST_TIME_TIME.equals(strCmd)) {
			// ����
			try {
				sendReplyTimeTime();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (CMD_RESPONSE_ONLONE_SERVER.equals(strCmd)) {
			// ��������
			receiveOnlineServer(rd);
		} else if (CMD_RESPONSE_WATCH_ERROR.equals(strCmd)) {
			// �յ���սʧ��
			receiveWatchError(rd);
		} else if (CMD_RESPONSE_UPDATE_GOLD.equals(strCmd)) {
			// �յ�ˢ�½��
			receiveUpdateGold(rd);
		} else if (CMD_NOTIFY_GOLD_CHANGE.equals(strCmd)) {
			// �յ�����Ǯ�ı�
			receiveGoldChange(rd);
		} else if (CMD_NOTIFY_LOW_GOLD.equals(strCmd)) {
			// �յ�Ǯ�����߳�����
			receiveLowGold(rd);
		} else if (CMD_NOTIFY_USER_ADD.equals(strCmd)) {
			// �յ���ҽ���
			receiveUserAdd(rd);
		} else if (CMD_NOTIFY_USER_DEL.equals(strCmd)) {
			// �յ�����˳�
			receiveUserDel(rd);
		} else if (CMD_NOTIFY_INTEGRAL_JIESUAN.equals(strCmd)) {
			// �յ����ֽ���
			receiveInteGralJieSuan(rd);
		} else if (TOURNAMENT_RECV_ADDPOINT_RESULT.equals(strCmd)) {
			// ��������������
			receiveAddPointResult(rd);
		} else if (CMD_RESPONSE_HALL_INFO.equals(strCmd)) {
			// �յ�������ϸ��Ϣ[����]
			receiveHallInfo(rd);
		} else if (CMD_RESPONSE_GAME_INFO.equals(strCmd)) {
			// �յ�������ϸ��Ϣ[��Ϸ]
			receiveGameInfo(rd);
		} else if (CMD_RESPONSE_ACTION_FACE.equals(strCmd)) {
			// ����ͷ����
			receiveActionFace(rd);
		} else if (CMD_RESPONSE_CHANGE_FACE.equals(strCmd)) {
			// ����ͷ��
			receiveChangeFace(rd);
		} else if (CMD_RESPONSE_SELECT_FACE.equals(strCmd)) {
			// ѡ��ͷ����
			receiveSelectFace(rd);
		} else if (CMD_RESPONSE_SORT_LIST.equals(strCmd)) {
			// �û������б�
			receiveSortList(rd);
		} else if (CMD_RESPONSE_STANDUP_RESULT.equals(strCmd)) {
			// �յ�վ����
			receiveStandUpResult(rd);
		} else if (CMD_NOTIFY_TEX_BBS_URL.equals(strCmd)) {
			// �յ���̳��֤��
			receiveTexBbsUrl(rd);
		} else if (CMD_NOTIFY_TEX_LOGIN_SHOW_DAY_GOLD.equals(strCmd)) {
			// �򿪵�½��Ǯ
			receiveLoginShowDayGold(rd);
		} else if (CMD_NOTIFY_TEX_RECV_LOGIN_GIVE.equals(strCmd)) {
			// �յ���½��Ǯ����Ŀ
			receiveLoginGive(rd);
		} else if (CMD_NOTIFY_TEX_RECV_SHOW_FEEDBACK.equals(strCmd)) {
			// �յ���ʾ����
			receiveShowFeedBack(rd);
		} else if (CMD_NOTIFY_TEX_SHOW_CHONGZHI.equals(strCmd)) {
			// �յ���ʷ��߳�ֵ���
			receiveShowChongZhi(rd);
		} else if (CMD_NOTIFY_TEX_PRIVILEGE_YANZHENG.equals(strCmd)) {
			// ��Ȩ����֤
			receivePrivilegeYanZheng(rd);
		} else if (CMD_TEX_CHOOSE_ROLE.equals(strCmd)) {
			// �յ���ʾѡ���ɫ���
			receiveChooseRole(rd);
		} else if (CMD_NOTIFY_RICH_CANNOT_TOROOM.equals(strCmd)) {
			// ̫�����˲�������
			receiveRichCanNotToRoom(rd);
		}else if (CMD_CHECK_NET.equals(strCmd)) {
			//���յ����ټ��
			receiveCheckNet(rd);
		}else if(CMD_MO_REFRESH.equals(strCmd)){
			//����ˢ��
			receiveRefresh(rd);
		} 
		else if (CMD_REQUEST_GPS.equals(strCmd)) {
			//�������λ�����ݣ�GPS��
			receiveUserGPS(rd);
     	}
		else {
			Log.i(tag, "DNetworkGame not execute command: " + strCmd);
		}

	}
	
	/**�����û�GPS����
	 * 
	 */
	private void receiveUserGPS(ReadData rd) 
	{
		System.out.println("receiveUserGPS");

		ArrayList data = new ArrayList();
		
		int n = rd.readInt();
		PlayerLocationData locationData = null;
		
		System.out.println("LEN: "+n);
		for (int i = 0; i < n; i++) 
		{
			int nUserID = rd.readInt();
		/*	//�ж����Լ�����
			if (nUserID == DConfig.userId)
			{
				System.out.println("GPS,userID���Լ�");
				continue;
			}*/
			locationData = new PlayerLocationData();
			locationData.setUserID(nUserID);
		//	locationData.setUserID(rd.readInt());
			locationData.setLatitudeX(rd.readString());
			locationData.setLongitudeY(rd.readString());
			locationData.setFaceurl(rd.readString());
			locationData.setNick(rd.readString());
			//locationData.setIsvip(rd.readInt());
			locationData.setGold(rd.readInt());
			locationData.setStat(rd.readInt());
			locationData.setSex(rd.readInt());
			locationData.setDate(rd.readString());
			
			data.add(locationData);
		}	
		dispatchEvent(new Event(Event.ON_RECV_GPS, data));
	}
	
	//���յ�ˢ��
	private void receiveRefresh(ReadData rd){
		if(GameApplication.getDzpkGame() != null){
			GameApplication.getDzpkGame().restart=false;
		}
		Byte state = rd.readByte();
		Log.i("test2", "receiveRefresh state: "+state);
		if(state ==1){
			//��ʾ��Ҫ���µ�¼
			GameUtil.reLogin();
			
		}else if(state ==2){
			//��ʾ���߲����κζ���
		}
	}
	//���յ����ټ��
	private void receiveCheckNet(ReadData rd){
		String time =rd.readString();
		Log.i("test4", "receiveCheckNet: time "+time);
		dispatchEvent(new Event(Event.ON_RECV_CHECK_NET,time));
	}
	private void receiveRichCanNotToRoom(ReadData rd) {
		dispatchEvent(new Event(Event.ON_RECV_RICH_CANNOT_TOROOM, null));
	}
	
	private void receiveMobPayOff(ReadData rd){
		Log.i("test18", "�յ��ֻ�֧������");
		GameApplication.mobPayOff=rd.readByte(); //0:�� 1:��
		Log.i("test18", "�յ��ֻ�֧������: "+GameApplication.mobPayOff);
	}
	/**
	 * ���յ��Լ�����Ϣ
	 * 
	 * @param rd
	 */
	private void receiveMyInfo(ReadData rd) {
		System.out.println("start receiveMyInfo �յ��Լ���Ϣ");
		HashMap data = new HashMap();
		data.put("userid", rd.readString());
		data.put("usersex", rd.readByte());
		data.put("usernick", rd.readString());
		data.put("imgurl", rd.readString());
		data.put("gold", rd.readInt());
		data.put("city", rd.readString());
		data.put("channelid", rd.readInt());
		data.put("user_real_id", rd.readInt());
		data.put("md5_userid", rd.readString());
		data.put("gameexp", rd.readInt());
		data.put("cansit", rd.readInt());
		data.put("tour_point", rd.readInt());
		data.put("welcometype", rd.readInt()); // ���̳�:0δ������1����δ�콱��3�����콱��
		data.put("canjiuji", rd.readByte()); // canjiuji
		// System.out.println("userid: "+data.get("userid"));
		// System.out.println("usersex: "+data.get("usersex"));
		// System.out.println("usernick: "+data.get("usernick"));
		// System.out.println("imgurl: "+data.get("imgurl"));
		// System.out.println("gold: "+data.get("gold"));
		// System.out.println("city: "+data.get("city"));
		// System.out.println("channelid: "+data.get("channelid"));
		// System.out.println("user_real_id: "+data.get("user_real_id"));
		// System.out.println("md5_userid: "+data.get("md5_userid"));
		// System.out.println("gameexp: "+data.get("gameexp"));
		// System.out.println("cansit: "+data.get("cansit"));
		System.out.println("end receiveMyInfo �յ��Լ���Ϣ");
		dispatchEvent(new Event(Event.ON_RECV_MY_INFO, data));
	}

	/**
	 * �������Ϣ
	 * 
	 * @param rd
	 */
	private void receiveGroupInfo(ReadData rd) {
		System.out.println("start receiveGroupInfo �������Ϣ");
		HashMap data = new HashMap();
		data.put("groupid", rd.readInt());
		data.put("isguildroom", rd.readInt());
		int len = rd.readInt();
		List list = new ArrayList();
		for (int k = 0; k < len; k++) {
			list.add(rd.readInt());
		}
		java.util.Collections.sort(list);
		data.put("guild_pelv_arr", list);
		data.put("at_least_gold", rd.readInt());
		data.put("at_most_gold", rd.readInt());
		data.put("pay_limit", rd.readInt());
		data.put("nocheat", rd.readByte());
		System.out.println("groupid: " + data.get("groupid"));
		System.out.println("isguildroom: " + data.get("isguildroom"));
		System.out.println("at_least_gold: " + data.get("at_least_gold"));
		System.out.println("at_most_gold: " + data.get("at_most_gold"));
		System.out.println("nocheat: " + data.get("nocheat"));

		System.out.println("end receiveGroupInfo �������Ϣ");
		dispatchEvent(new Event(Event.ON_RECV_GROUP_INFO, data));
	}

	/**
	 * �յ����½��
	 * 
	 * @param rd
	 */
	private void receiveSitDownResult(ReadData rd) {
		int code = rd.readInt(); // ������
	    dispatchEvent(new Event(Event.ON_RECV_SITDOWN_RESULT, code));
	}

	/**
	 * �յ���¼���
	 * 
	 * @param rd
	 */
	private void receiveLoginResult(ReadData rd) {
		System.out.println("start receiveLoginResult �յ���¼���");
		HashMap data = new HashMap();
		data.put("code", rd.readShort());
		data.put("ostime", rd.readString());
		System.out.println("code: " + data.get("code"));
		System.out.println("ostime: " + data.get("ostime"));
		System.out.println("end receiveLoginResult �յ���¼���");
		dispatchEvent(new Event(Event.ON_RECV_GAME_LOGIN_RESULT, data));

	}

	/**
	 * 
	 * ȡ�÷�ҳ������ϸ��Ϣ��ʼ
	 * 
	 * @param rd
	 */
	private void receiveRoomListStartResult(ReadData rd) {
		m_deskList = new ArrayList();
	}

	/**
	 * 
	 * ȡ�÷�ҳ������ϸ��Ϣ
	 * 
	 * @param rd
	 */
	private void receiveRoomListPageResult(ReadData rd) {

		int n = rd.readInt();
		DeskInfo deskinfo = null;
		for (int i = 0; i < n; i++) {

			deskinfo = new DeskInfo();
			deskinfo.setDeskno(rd.readInt());// ����
			deskinfo.setName(rd.readString());// ����
			deskinfo.setDesktype(rd.readByte());// ����:1��ͨ��2������3VIPר��
			deskinfo.setFast(rd.readByte());// �Ƿ����
			deskinfo.setBetgold(rd.readInt());// ���������
			deskinfo.setUsergold(rd.readInt());// ������ҳ�����
			deskinfo.setNeedlevel(rd.readInt());// ���ӽ����ȼ�
			deskinfo.setSmallbet(rd.readInt());// Сä
			deskinfo.setLargebet(rd.readInt());// ��ä
			deskinfo.setAt_least_gold(rd.readInt());// �������
			deskinfo.setAt_most_gold(rd.readInt());// �������
			deskinfo.setSpecal_choushui(rd.readInt());// ��ˮ
			deskinfo.setMin_playercount(rd.readByte());// ���ٿ�������
			deskinfo.setMax_playercount(rd.readByte());// ��࿪������
			deskinfo.setPlayercount(rd.readByte());// ��ǰ��������
			deskinfo.setWatchercount(rd.readInt());// ��ս����
			deskinfo.setStart(rd.readByte());// �Ƿ�ʼ 1=�� 0=��ʼ
			m_deskList.add(deskinfo);

		}
	}

	/**
	 * ȡ�÷�ҳ������ϸ��Ϣ����
	 * 
	 * @param rd
	 */
	private void receiveRoomListEndResult(ReadData rd) {
		ArrayList data = (ArrayList) m_deskList.clone();
		m_deskList.clear();
		dispatchEvent(new Event(Event.ON_RECV_CURPAGEROOM_LIST, data));

	}

	/**
	 *�յ������������˵���Ϣ
	 * 
	 * @param rd
	 */
	private void receiveDeskUserList(ReadData rd) {

		HashMap data = new HashMap();
		data.put("deskno", rd.readInt());
		data.put("betgold", rd.readInt()); // ���������
		data.put("usergold", rd.readInt()); // ������ҳ�����
		data.put("playercount", rd.readByte()); // ��������
		data.put("watchercount", rd.readInt()); // ��ս����
		List list = new ArrayList();
		data.put("userlist", list);
		int n = rd.readByte(); // ��������
		HashMap temp = null;
		for (int i = 0; i < n; i++) {
			temp = new HashMap();
			temp.put("state_value", rd.readByte()); // ÿ����λ��״̬ SITE_UI_VALUE =
													// _S{NULL = 0, NOTREADY =
													// 1, READY = 2, PLAYING =
													// 3}
			temp.put("userid", rd.readInt()); // ID
			temp.put("nick", rd.readString()); // �ǳ�
			temp.put("isvip", rd.readByte()); // �Ƿ�VIP���:0���ǣ�1��
			temp.put("faceurl", rd.readString()); // ͷ��
			temp.put("gold", rd.readInt()); // ���
			list.add(temp);
		}
		dispatchEvent(new Event(Event.ON_RECV_DESK_USERLIST, data));

	}

	/**
	 * ȡ�÷�ҳ���ӱ仯��Ϣ
	 * 
	 * @param rd
	 */
	private void receiveDeskChanged(ReadData rd) {

		int n = rd.readInt();
		HashMap temp = null;
		HashMap siteitem = null;
		List tempList = null;
		List list = new ArrayList();
		int sitecount = 0;
		for (int i = 0; i < n; i++) {

			temp = new HashMap();
			temp.put("deskno", rd.readInt()); // ����
			temp.put("name", rd.readString()); // ����
			temp.put("desktype", rd.readByte()); // ����:1��ͨ��2������3VIPר��
			temp.put("fast", rd.readByte()); // �Ƿ����
			temp.put("betgold", rd.readInt()); // ���������
			temp.put("usergold", rd.readInt()); // ������ҳ�����
			temp.put("needlevel", rd.readInt()); // ���ӽ����ȼ�
			temp.put("smallbet", rd.readInt()); // Сä
			temp.put("largebet", rd.readInt()); // ��ä
			temp.put("at_least_gold", rd.readInt()); // ������� ������
			temp.put("at_most_gold", rd.readInt()); // �������
			temp.put("specal_choushui", rd.readInt()); // ��ˮ
			temp.put("min_playercount", rd.readByte()); // ���ٿ�������
			temp.put("max_playercount", rd.readByte()); // ��࿪������
			temp.put("playercount", rd.readByte()); // ��ǰ��������
			temp.put("watchercount", rd.readInt()); // ��ս����
			temp.put("start", rd.readByte()); // �Ƿ�ʼ 1=�� 0=��ʼ
			sitecount = rd.readByte();
			temp.put("sitecount", sitecount); // ������
			tempList = new ArrayList();
			temp.put("siteinfo", tempList);
			for (int j = 0; j < sitecount; j++) {
				siteitem = new HashMap();
				siteitem.put("state_value", rd.readByte()); // ÿ����λ��״̬
															// SITE_UI_VALUE =
															// _S{NULL = 0,
															// NOTREADY = 1,
															// READY = 2,
															// PLAYING = 3}
				siteitem.put("userid", rd.readInt()); //
				siteitem.put("nick", rd.readString()); //
				siteitem.put("faceurl", rd.readString()); //
				tempList.add(siteitem);
			}
			list.add(temp);
		}
		HashMap data = new HashMap();
		data.put("list", list);
		data.put("page", rd.readShort()); // ��ǰҳ
		data.put("totalpage", rd.readShort());// ������ҳ
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_CURPAGEROOM_CHANGED,
		// data));

	}

	/**
	 * ���¶�����Ϣ
	 * 
	 * @param rd
	 */
	private void receiveUpdateQueue(ReadData rd) {

		int queueplayer = rd.readInt();
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_QUEUE_INFO,queueplayer));

	}

	/**
	 * 
	 * �յ����������ش�����ʾ:0xx�����ģ�1xx��Ϸ���
	 * 
	 * @param rd
	 */
	private void receiveServerError(ReadData rd) {
//		HashMap data = new HashMap();
//		int msgtype = rd.readByte();
//		data.put("msgtype", msgtype);
//		data.put("msg", rd.readString());
//		if (msgtype == 0) {
//			// this.dispatchEvent(new
//			// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_SERVER_ERROR, data));
//		}
	}

	/**
	 * ������IP���Ƶ�½
	 * 
	 * @param rd
	 */
	private void receiveRestrictLogin(ReadData rd) {
		System.out.println("start receiveRestrictLogin �յ����������Ƶ�½");
		HashMap data = new HashMap();
		data.put("retcode", rd.readInt()); // �Ƿ���Ե�¼
		data.put("msg", rd.readString()); // ������Ϣ
		System.out.println("retcode: " + data.get("retcode"));
		System.out.println("msg: " + data.get("msg"));
		System.out.println("end receiveRestrictLogin �յ����������Ƶ�½");
		dispatchEvent(new Event(Event.ON_RECV_RESTRICT_LOGIN, data));

	}

	/**
	 * �յ�ѧϰ�̳̽���
	 * 
	 * @param rd
	 */
	private void receiveStudyPrize(ReadData rd) {

		HashMap data = new HashMap();
		data.put("userid", rd.readInt());
		data.put("addgold", rd.readInt());
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_STUDY_PRIZE, data));

	}

	/**
	 * �޷����뷿��
	 * 
	 * @param rd
	 */
	private void receiveCanNotToRoom(ReadData rd) {
		//Log.i("test4", "receiveCanNotToRoom");
		HashMap data = new HashMap();
		data.put("IntoRoomStats", rd.readByte());
		data.put("gold", rd.readInt());
		this.dispatchEvent(new Event(Event.ON_RECV_CAN_NOT_TOROOM, data));

	}

	/**
	 * GM����
	 * 
	 * @param rd
	 */
	private void receiveGmKick(ReadData rd) {

		int code = rd.readInt();
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_GM_KICK, code));

	}

	/**
	 * ��������
	 * 
	 * @param rd
	 */
	private void receiveOnlineServer(ReadData rd) {

		HashMap data = new HashMap();
		String groupid = "";
		while (!(groupid = rd.readString()).equals("")) {
			data.put(groupid, rd.readInt()); // ��������
		}
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_ONLINE_SERVER, data));

	}

	/**
	 * �յ���սʧ��
	 * 
	 * @param rd
	 */
	private void receiveWatchError(ReadData rd) {

		 
		HashMap data = new HashMap();
		int errorcode = rd.readByte();
		data.put("errorcode", errorcode);

		if (errorcode == -2) {
			data.put("userid", rd.readInt());
			data.put("roomid", rd.readInt());
		}
	    this.dispatchEvent(new Event(Event.ON_RECV_WATCH_ERROR, data));

	}

	/**
	 * �յ�ˢ�½��
	 * 
	 * @param rd
	 */
	private void receiveUpdateGold(ReadData rd) {
		//HashMap data = new HashMap();
		//data.put("gold", rd.readInt());
		//data.put("canjiuji", rd.readByte()); // �Ƿ������ȡ�ȼ�:0���У�1����
		int gold =rd.readInt();
		byte canjiuji = rd.readByte();
		if(GameApplication.userInfo != null){
			GameApplication.userInfo.put("gold", gold);
		   Log.i("test8", "canjiuji gold: "+gold);
			if(GameApplication.currentActivity instanceof DzpkGameMenuActivity){
				((DzpkGameMenuActivity)GameApplication.currentActivity).setPlayerChoumaText(gold,true);
			}
		}
		//dispatchEvent(new Event(Event.ON_RECV_UPDATE_GOLD, data));
	}

	/**
	 * �յ�����Ǯ�ı�
	 * 
	 * @param rd
	 */
	private void receiveGoldChange(ReadData rd) {
		HashMap data = new HashMap();
		data.put("site", rd.readInt()); // ��λ��
		data.put("gold", rd.readInt()); // ��Ǯ
		dispatchEvent(new Event(Event.ON_RECV_CHANGE_GOLD, data));
	}

	/**
	 *�յ�Ǯ�����߳�����
	 * 
	 * @param rd
	 */
	private void receiveLowGold(ReadData rd) {

		HashMap data = new HashMap();
		data.put("retcode", rd.readInt()); // ������
		data.put("gold", rd.readInt()); // Ǯ����
		this.dispatchEvent(new Event(Event.ON_RECV_LOW_GOLD, data));
	}

	/**
	 *�յ���ҽ���
	 * 
	 * @param rd
	 */
	private void receiveUserAdd(ReadData rd) {
		HashMap data = new HashMap();
		data.put("userid", rd.readInt()); // �û������ݿ�ID
		data.put("nick", rd.readString()); // �ǳ�
		data.put("faceurl", rd.readString()); // ͷ��url
		data.put("gold", rd.readInt()); // �����Ŀ
		data.put("exp", rd.readInt()); // ����ֵ
		data.put("prestige", rd.readInt()); // ����
		data.put("integral", rd.readInt()); // ����
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_USER_ADD, data));

	}

	/**
	 * �յ�����˳�
	 * 
	 * @param rd
	 */
	private void receiveUserDel(ReadData rd) {
		int userid = rd.readInt();
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_USER_DEL, userid));

	}

	/**
	 * �յ����ֽ���
	 * 
	 * @param rd
	 */
	private void receiveInteGralJieSuan(ReadData rd) {
		HashMap data = new HashMap();
		data.put("all", rd.readInt());
		data.put("normal", rd.readInt());
		data.put("extra", rd.readInt());
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_INTEGRAL_JIESUAN, data));

	}

	/**
	 * ��������������
	 * 
	 * @param rd
	 */
	private void receiveAddPointResult(ReadData rd) {
		HashMap data = new HashMap();
		data.put("result", rd.readInt());
		data.put("newpoint", rd.readInt());
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_TOUR_ADDPOINT_RESULT,
		// data));
	}

	// �յ�������ϸ��Ϣ[����]
	private void receiveHallInfo(ReadData rd) {
		System.out.println("start receiveHallInfo �յ�������ϸ��Ϣ[����]");
		HashMap data = new HashMap();
		data.put("prestige", rd.readInt());
		data.put("ccPoint", rd.readInt());
		data.put("exp", rd.readInt());
		data.put("level", rd.readInt());
		System.out.println("prestige" + data.get("prestige"));
		System.out.println("ccPoint" + data.get("ccPoint"));
		System.out.println("exp" + data.get("exp"));
		System.out.println("level" + data.get("level"));
		System.out.println("end receiveHallInfo �յ�������ϸ��Ϣ[����]");
		dispatchEvent(new Event(Event.ON_RECV_USER_HALL_GAMEINFO, data));

	}

	/**
	 * �յ�������ϸ��Ϣ[��Ϸ]
	 * 
	 * @param rd
	 */
	private void receiveGameInfo(ReadData rd) {
		HashMap data = new HashMap();
		data.put("siteno", rd.readInt());
		data.put("userid", rd.readInt());
		data.put("prestige", rd.readInt());
		data.put("ccPoint", rd.readInt());
		data.put("experience", rd.readInt());
		data.put("level", rd.readInt());
		data.put("isrelogin", rd.readByte());
		dispatchEvent(new Event(Event.ON_RECV_USER_GAME_GAMEINFO, data));

	}

	// ����ͷ����
	private void receiveActionFace(ReadData rd) {
		HashMap data = new HashMap();
		data.put("success", rd.readInt());
		data.put("faceid", rd.readInt());
		data.put("price", rd.readInt());
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_ACTIVE_FACE, data));

	}

	/**
	 * ����ͷ��
	 * 
	 * @param rd
	 */
	private void receiveChangeFace(ReadData rd) {

		String face = rd.readString();
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_CHANGE_FACE_OK, face));

	}

	/**
	 *ѡ��ͷ����
	 * 
	 * @param rd
	 */
	private void receiveSelectFace(ReadData rd) {

		HashMap data = new HashMap();
		data.put("notshowextra", rd.readInt());
		int extrafacenum = rd.readInt();
		data.put("extrafacenum", extrafacenum);
		List list = new ArrayList();
		data.put("extrafaces", list);
		for (int i = 0; i < extrafacenum; i++) {
			list.add(rd.readInt());
		}
		int vipfacenum = rd.readInt();
		data.put("vipfacenum", vipfacenum);
		List vipList = new ArrayList();
		data.put("vipfaces", vipList);
		for (int i = 0; i < vipfacenum; i++) {
			vipList.add(rd.readInt());
		}
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_SELECT_FACE_OK, data));

	}

	/**
	 * �û������б� �յ����Է������������������
	 * 
	 * @param rd
	 */
	private void receiveSortList(ReadData rd) {

		HashMap data = new HashMap();
		data.put("type", rd.readString());
		List listdata = new ArrayList();
		int len = rd.readShort();
		HashMap item = null;
		for (int i = 0; i < len; i++) {

			item = new HashMap();
			item.put("userid", rd.readInt()); // �û������ݿ�ID
			item.put("nick", rd.readString()); // �ǳ�
			item.put("faceurl", rd.readString()); // ͷ��url
			item.put("gold", rd.readInt()); // �����Ŀ
			item.put("exp", rd.readInt()); // ����ֵ
			item.put("prestige", rd.readInt()); // ����
			item.put("integral", rd.readInt()); // ����
			listdata.add(item);
		}
		data.put("listdata", listdata);
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_RECV_SORT_LIST, data));

	}

	/**
	 * �յ�վ����
	 * 
	 * @param rd
	 */
	private void receiveStandUpResult(ReadData rd) {
		int result = rd.readShort(); // ���
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_STAND_UP_RESULT, result));

	}

	/**
	 * �յ���̳��֤��
	 * 
	 * @param rd
	 */
	private void receiveTexBbsUrl(ReadData rd) {
		String bbsUrl = rd.readString(); // BBS��֤��
		dispatchEvent(new Event(Event.ON_TEX_RECV_BBS_URL, bbsUrl));

	}

	/**
	 *�򿪵�½��Ǯ
	 * 
	 * @param rd
	 */
	private void receiveLoginShowDayGold(ReadData rd) {
		 
		// System.out.println("start receiveLoginShowDayGold �򿪵�½��Ǯ");
		// HashMap data=new HashMap();
         int temp =rd.readInt();
		//GameApplication.showDayGoldResult = temp;
		//Log.i("test3", "receiveLoginShowDayGold: showDayGoldResult: "
		//		+ GameApplication.showDayGoldResult +"  temp: "+temp);
		// data.put("result",result); //-1��IP���ޣ�1�ɹ�������δ֪����
		// data.put("maxviplevel",rd.readInt()); //���VIP�ȼ�
		// data.put("vipadd",rd.readInt()); //vip��Ҽӳ�
		// System.out.println("result: "+data.get("result"));
		// System.out.println("maxviplevel: "+data.get("maxviplevel"));
		// System.out.println("vipadd: "+data.get("vipadd"));
		// System.out.println("end receiveLoginShowDayGold �򿪵�½��Ǯ");
		// 
		rd.clear();
		dispatchEvent(new Event(Event.ON_TEX_LOGIN_SHOW_DAY_GOLD, temp));

	}

	/**
	 * �յ���½��Ǯ����Ŀ
	 * 
	 * @param rd
	 */
	private void receiveLoginGive(ReadData rd) {
		HashMap data = new HashMap();
	
		data.put("success", rd.readByte());
		int gold =rd.readInt();
		data.put("gold", gold); // �����쵽�ĳ���500
		int vipGold = rd.readInt();
		data.put("vipadd", vipGold); // vip�ӳ�888��1888��3888
		data.put("charmlevel", rd.readInt()); // ũ�������ȼ�
		data.put("charmadd", rd.readInt()); // ũ�������ӳ�
		int taskGold = rd.readInt();
		data.put("vtask_add",taskGold); // V�������ͳ���
		Log.i("test3", "gold: "+gold+" vipGold: "+vipGold+" taskGold: "+taskGold);
		gold = gold+vipGold+taskGold;
		Log.i("test3", "gold: "+gold);
		data.put("allGold", gold);
		dispatchEvent(new Event(Event.ON_TEX_RECV_LOGIN_GIVE, data));

	}

	/**
	 * �յ���ʾ����
	 * 
	 * @param rd
	 */
	private void receiveShowFeedBack(ReadData rd) {

		int nFlag = rd.readByte(); // �Ƿ�����0, �����÷�����1�������÷���
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_TEX_RECV_FEEDBACK_CAN_USE,
		// data));

	}

	/**
	 * �յ���ʷ��߳�ֵ���
	 * 
	 * @param rd
	 */
	private void receiveShowChongZhi(ReadData rd) {

		int historyMaxpay = rd.readInt(); // ��ʷ��ߵĵ��γ�ֵ���
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_TEX_RECV_SHOW_CHONGZHI,
		// data));

	}

	/**
	 * ��Ȩ����֤
	 * 
	 * @param rd
	 */
	private void receivePrivilegeYanZheng(ReadData rd) {
		HashMap data = new HashMap();
		data.put("checkRs", rd.readByte());
		data.put("checkMsg", rd.readString());
		// this.dispatchEvent(new
		// DNetworkGameEvent(DNetworkGameEvent.ON_TEX_RECV_PRIVILEGE_CHECK,
		// data));

	}

	/**
	 * �յ�ѡ���ɫ
	 * 
	 * @param rd
	 */
	private void receiveChooseRole(ReadData rd) {
		System.out.println("start receiveChooseRole �յ���ʾѡ���ɫ���");
		HashMap data = new HashMap();
		data.put("isShow", rd.readByte());
		data.put("sex", rd.readByte());
		data.put("nick", rd.readString());
		System.out.println("isShow: " + data.get("isShow"));
		System.out.println("sex: " + data.get("sex"));
		System.out.println("nick: " + data.get("nick"));
		System.out.println("end receiveChooseRole �յ���ʾѡ���ɫ���");
		dispatchEvent(new Event(Event.ON_RECV_CHOOSE_SHOW, data));
	}

	/**
	 * ����������Ϸ��������¼
	 * 
	 * @param strUsername
	 * @param strPassword
	 */
	public void sendRequestLoginGame(int userId, String strPassword)
			throws Exception {
		Log.i("DNetworkGame","DNetworkGame sendRequestLoginGame Command: "+ CMD_REQUEST_LOGIN);
		m_netptr.writeString(CMD_REQUEST_LOGIN);
		m_netptr.writeInt(userId);
		m_netptr.writeString(strPassword);
		m_netptr.writeByte((byte) DConfig.nRegSit); // from YY 1
		m_netptr.writeString(DConfig.strLoginType);
		m_netptr.writeEnd();
	}

	/**
	 * �����Զ�������Ϸ����
	 * 
	 * @param deskno
	 */
	public void sendRequestAutoJoin(int deskno) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestAutoJoin Command: "
				+ CMD_REQUEST_AUTO_JOIN);
		m_netptr.writeString(CMD_REQUEST_AUTO_JOIN);
		m_netptr.writeInt(deskno);
		m_netptr.writeInt(1);
		m_netptr.writeEnd();
	}

	/**
	 * ���������Ŷ�
	 */
	public void sendRequestPaiDui() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestPaiDui Command: "
				+ CMD_REQUEST_PAIDUI);
		m_netptr.writeString(CMD_REQUEST_PAIDUI);
		m_netptr.writeEnd();
	}

	/**
	 * ���������˶�
	 */
	public void sendRequestUnqueue() throws Exception {
		// DTrace.traceex("sendRequestUnqueue");
		Log.i(tag, "DNetworkGame sendRequestUnqueue Command: "
				+ CMD_REQUEST_UNQUEUE);

		m_netptr.writeString(CMD_REQUEST_UNQUEUE);
		m_netptr.writeEnd();
	}

	/**
	 * ���󷿼�������б�
	 * 
	 * @param chang
	 * @param tab
	 * @param hidenull
	 * @param hidefull
	 * @param isfast
	 * @param isStart
	 */
	public void sendRequestDeskList(int chang, int tab, int hidenull,
			int hidefull, int isfast, int isStart) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestDeskList Command: "
				+ CMD_REQUEST_ROOM_DESKLIST);

		m_netptr.writeString(CMD_REQUEST_ROOM_DESKLIST);
		m_netptr.writeShort((short) chang);
		m_netptr.writeShort((short) tab);
		m_netptr.writeByte((byte) hidenull);
		m_netptr.writeByte((byte) hidefull);
		m_netptr.writeByte((byte) isfast);
		m_netptr.writeInt(isStart);
		m_netptr.writeEnd();
	}

	/**
	 * �������󷿼���һҳ
	 * 
	 * @param page
	 * @param pagesize
	 * @param hidenull
	 * @param hidefull
	 */
	public void sendRequestPage(int page, int pagesize, int hidenull,
			int hidefull) throws Exception {

		// ��������Ѿ���������
		m_netptr.writeString(CMD_REQUEST_ROOMLIST_PAGE);
		m_netptr.writeShort((short) page);
		m_netptr.writeShort((short) pagesize);
		m_netptr.writeByte((byte) hidenull);
		m_netptr.writeByte((byte) hidefull);
		m_netptr.writeEnd();
	}

	/**
	 * �������ӵ�����б�
	 */
	public void sendRequestDeskUser(int deskno) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestDeskUser Command: SDDU");
		m_netptr.writeString("SDDU");
		m_netptr.writeInt(deskno);
		m_netptr.writeEnd();
	}

	/**
	 * �����Լ�վ����
	 */
	public void sendRequestStandup() throws Exception {
		// DTrace.traceex("�����Լ�վ����,sendRequestStandup()");
		Log.i(tag, "DNetworkGame sendRequestStandup Command: RQSU");

		m_netptr.writeString(CMD_REQUEST_STANDUP);
		m_netptr.writeEnd();
	}

	/**
	 * ���ͼ���ͷ��
	 * 
	 * @param faceurl
	 * @param sure
	 */
	public void sendRequestActiveExtraFace(String faceurl, int sure)
			throws Exception {
		// DTrace.traceex("���ͼ���ͷ��");
		Log.i(tag, "DNetworkGame sendRequestActiveExtraFace Command: "
				+ CMD_REQUEST_ACTIVE_FACE);
		m_netptr.writeString(CMD_REQUEST_ACTIVE_FACE);
		m_netptr.writeString(faceurl);
		m_netptr.writeInt(sure);
		m_netptr.writeEnd();
	}

	/**
	 * �����뿪��������
	 */
	public void sendClientLeaveRoom() throws Exception {
		Log.i(tag, "DNetworkGame sendClientLeaveRoom Command: "
				+ CMD_REQUEST_CLIENT_LEAVE_ROOM);

		m_netptr.writeString(CMD_REQUEST_CLIENT_LEAVE_ROOM);
		m_netptr.writeEnd();
	}

	/**
	 * ���͸ı�ͷ��
	 * 
	 * @param faceUrl
	 */
	public void sendRequestChangeFace(String faceUrl) throws Exception {
		// DTrace.traceex("���͸ı�ͷ��");
		Log.i(tag, "DNetworkGame sendRequestChangeFace Command: "
				+ CMD_REQUEST_CHANGE_FACE);

		m_netptr.writeString(CMD_REQUEST_CHANGE_FACE);
		m_netptr.writeString(faceUrl);
		m_netptr.writeEnd();
	}

	/**
	 * ����
	 */
	private void sendReplyTimeTime() throws Exception {
		Log.i("GameTime", "DNetworkGame sendReplyTimeTime Command: "+CMD_REQUEST_TIME_TIME);
		m_netptr.writeString(CMD_REQUEST_TIME_TIME);
		m_netptr.writeEnd();
	}

	/**
	 * ��ѡ���з���������������
	 */
	public void sendRequestOnlinePlayerCountAllServer() throws Exception {
		Log.i(tag,
				"DNetworkGame sendRequestOnlinePlayerCountAllServer Command: "
						+ CMD_REQUEST_ONLONE_SERVER);

		m_netptr.writeString(CMD_REQUEST_ONLONE_SERVER);
		m_netptr.writeEnd();
	}

	/**
	 * �����������Ǯ
	 */
	public void sendRequestGiveGold() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestGiveGold Command: "
				+ CMD_REQUEST_GIVE_GOLD);
		m_netptr.writeString(CMD_REQUEST_GIVE_GOLD);
		m_netptr.writeEnd();
	}

	/**
	 * ���������ˢ��Ǯ
	 * 
	 */
	public void sendUpdateGold() throws Exception {
		Log.i(tag, "DNetworkGame sendUpdateGold Command: "
				+ CMD_REQUEST_UPDATE_GOLD);

		m_netptr.writeString(CMD_REQUEST_UPDATE_GOLD);
		m_netptr.writeEnd();
	}

	public void sendUpdateVipInfo() throws Exception {
		Log.i(tag, "DNetworkGame sendUpdateVipInfo Command: "
				+ CMD_REQUEST_UPDATE_VIPINFO);

		m_netptr.writeString(CMD_REQUEST_UPDATE_VIPINFO);
		m_netptr.writeEnd();
	}

	/**
	 * ����������̳�ִ�
	 */
	public void sendRequestURL() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestURL Command: "
				+ CMD_NOTIFY_TEX_BBS_URL);
		m_netptr.writeString(CMD_NOTIFY_TEX_BBS_URL);
		m_netptr.writeEnd();
	}

	/**
	 * �û������ս
	 * 
	 * @param deskno
	 */
	public void sendRequestWatch(int deskno) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestWatch Command: "
				+ CMD_REQUEST_WATCH);
		m_netptr.writeString(CMD_REQUEST_WATCH);
		m_netptr.writeInt(deskno);
		m_netptr.writeEnd();
	}

	/**
	 * �û������˳���ս
	 */
	public void sendRequestExitWatch() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestExitWatch Command: "
				+ CMD_REQUEST_EXIT_WATCH);
		m_netptr.writeString(CMD_REQUEST_EXIT_WATCH);
		m_netptr.writeEnd();
	}

	/**
	 * �ͻ������󷿼��������
	 * 
	 * @param strType
	 */
	public void sendRequestRoomSortList(String strType) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestRoomSortList Command: "
				+ CMD_REQUEST_ROOM_SORT_LIST);
		m_netptr.writeString(CMD_REQUEST_ROOM_SORT_LIST);
		m_netptr.writeString(strType);
		m_netptr.writeEnd();
	}

	/**
	 * �ͻ������󷿼�ȫ������б�
	 */
	public void sendRequestRoomUserList() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestRoomUserList Command: RQASL");
		m_netptr.writeString("RQASL");
		m_netptr.writeEnd();
	}

	/**
	 * �����뿪��Ϸ
	 */
	public void sendExitGame() throws Exception {
		Log.i(tag, "DNetworkGame sendExitGame Command: RQUL");

		m_netptr.writeString("RQUL");
		m_netptr.writeEnd();
	}

	/**
	 * ����ѡ����Ϸ
	 */
	public void sendSelectedGame() throws Exception {
		Log.i(tag, "DNetworkGame sendSelectedGame Command: RQUE");
		m_netptr.writeString("RQUE");
		m_netptr.writeEnd();
	}

	// ����ѡͷ��
	public void sendRequestSelectFace() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestSelectFace Command: RAHD");
		m_netptr.writeString("RAHD");
		m_netptr.writeEnd();
	}

	// ���Ϳ���̳�
	public void sendStudyOver() throws Exception {
		Log.i(tag, "DNetworkGame sendStudyOver Command: STOV");
		m_netptr.writeString("STOV");
		m_netptr.writeEnd();
	}

	// ������ȡ�ȼ�
	public void sendRequestGiveChouMa() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestGiveChouMa Command: RQGIVE");
		m_netptr.writeString("RQGIVE");
		m_netptr.writeEnd();
	}

	/**
	 * ��������
	 * 
	 * @param deskno
	 * @param siteno
	 * @param peilv
	 */
	public void sendSitDown(int deskno, int siteno, int peilv) throws Exception {
		Log.i(tag, "DNetworkGame sendSitDown Command: RQSD");
		m_netptr.writeString("RQSD");
		m_netptr.writeInt(deskno);
		m_netptr.writeInt(siteno);
		m_netptr.writeInt(peilv);
		m_netptr.writeEnd();
	}

	public void tour_sendBuyPoint() throws Exception {
		Log.i(tag, "DNetworkGame tour_sendBuyPoint Command: TRBUGD");
		m_netptr.writeString("TRBUGD");
		m_netptr.writeEnd();
	}

	public void sendRequestLoginLingQu() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestLoginLingQu Command: RQDAYGOLD");
		m_netptr.writeString("RQDAYGOLD");
		m_netptr.writeEnd();
	}

	public void sendRequestFeedBack() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestFeedBack Command: SHOWFEEDBK");
		m_netptr.writeString("SHOWFEEDBK");
		m_netptr.writeEnd();
	}

	public void sendRequestShowChongzhi() throws Exception {
		Log.i(tag, "DNetworkGame sendRequestShowChongzhi Command: "
				+ CMD_NOTIFY_TEX_SHOW_CHONGZHI);
		m_netptr.writeString(CMD_NOTIFY_TEX_SHOW_CHONGZHI);
		m_netptr.writeEnd();
	}

	// -------------------��Ȩ��str------------------------
	public void sendRequestPrivilegeCheck(String code) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestPrivilegeCheck Command: "
				+ CMD_NOTIFY_TEX_PRIVILEGE_YANZHENG);

		m_netptr.writeString(CMD_NOTIFY_TEX_PRIVILEGE_YANZHENG);
		m_netptr.writeString(code);
		m_netptr.writeEnd();
	}

	// -------------------��Ȩ��end------------------------

	/**
	 * ����Ƶ����Ϣ
	 * 
	 * @throws Exception
	 */
	public void sendRequestUpdateChannelInfo(String strChannelId)
			throws Exception {
		Log.i(tag, "DNetworkGame sendRequestUpdateChannelInfo Command: "
				+ CMD_TEX_UPDATE_CHANNEL_INFO);
		m_netptr.writeString(CMD_TEX_UPDATE_CHANNEL_INFO);
		m_netptr.writeString(strChannelId);
		m_netptr.writeEnd();
	}

	public void sendRequestUpdateUserChannelInfo(HashMap userInfo)
			throws Exception {

		Log.i(tag, "DNetworkGame sendRequestUpdateUserChannelInfo Command: "
				+ CMD_TEX_UPDATE_NICK_INFO);
		m_netptr.writeString(CMD_TEX_UPDATE_NICK_INFO);
		m_netptr.writeString((String) userInfo.get("nick"));
		m_netptr.writeInt((Integer) userInfo.get("sex"));
		m_netptr.writeEnd();
	}
	/**
	 * �������ټ��
	 * @throws Exception
	 */
	public void sendCheckNet(String currentTime) throws Exception{
		
		m_netptr.writeString(CMD_CHECK_NET);
		m_netptr.writeString(currentTime);
		m_netptr.writeEnd();
	}
	/**
	 * ��¼�����ֻ���Ϣ
	 * model:�ֻ��ͺ�
	 * screen:��Ļ�ֱ�����640*960
	 * @throws Exception
	 */
	public void sendMobLogin(String model,String screen) throws Exception{
		
		m_netptr.writeString(CMD_MOBLOGIN);
		m_netptr.writeInt(2);
		m_netptr.writeString(model);
		m_netptr.writeString(screen);
		m_netptr.writeEnd();
		Log.i("test18", "model: "+model+"  screen: "+screen);
	}
    /**
     * ����ˢ��
     * @param userId
     * @throws Exception
     */
	public void sendRefresh(int userId)throws Exception {
		Log.i("test5", "sendRefresh userId: "+userId);
		m_netptr.writeString(CMD_MO_REFRESH);
		m_netptr.writeInt(userId);
		m_netptr.writeEnd();
	}
	
	/**
	 * �û������Լ�GPS����
	 * 
	 */
	public void sendGPS(String strMyLatitudeX, String strMyLongitudeY,int type) throws Exception {
		Log.i(tag, "DNetworkGame sendRequestWatch Command: "
				+ CMD_REQUEST_GPS);
		m_netptr.writeString(CMD_REQUEST_GPS);
		m_netptr.writeString(strMyLatitudeX);
		m_netptr.writeString(strMyLongitudeY);
		m_netptr.writeByte((byte)type);
		m_netptr.writeEnd();
	}
}
