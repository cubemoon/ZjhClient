package com.dozengame.event;

public class Event {

	//���ӳɹ�
	public static final String CONNECT = "CONNECT";
	//����ʧ��
	public static final String CONNECTFAIL = "CONNECTFAIL";
	//�ر�
	public static final String CLOSE = "CLOSE";
	//�׽�������
	public static final String SOCKET_DATA = "SOCKET_DATA";
	 
	public static final String SYNC = "SYNC";
	//���յ������¼�
	public static final String ON_RECV_GROUP_INFO = "ON_RECV_GROUP_INFO";
	//���յ���¼����¼�
	public static final String ON_RECV_LOGIN_RESULT = "ON_RECV_LOGIN_RESULT";
	//���յ���¼����¼�
	public static final String ON_RECV_GAME_LOGIN_RESULT = "ON_RECV_GAME_LOGIN_RESULT";
	//�յ�Ǯ�������߳������¼�
	public static final String ON_RECV_LOW_GOLD = "ON_RECV_LOW_GOLD";
	//�ܷ���뷿���¼�
	public static final String ON_RECV_CAN_NOT_TOROOM = "ON_RECV_CAN_NOT_TOROOM";
	public static final String ON_RECV_RICH_CANNOT_TOROOM = "CMD_NOTIFY_RICH_CANNOT_TOROOM";
	//���յ����½��
	public static final String ON_RECV_SITDOWN_RESULT = "ON_RECV_SITDOWN_RESULT";
	//�յ���Ϸ�����Ϣ
	public static final String ON_RECV_WATCH_ERROR = "ON_RECV_WATCH_ERROR";
	//���յ����ټ��
	public static final String ON_RECV_CHECK_NET = "ON_RECV_CHECK_NET";
	//���յ��Լ�����Ϣ�¼�
	public static String ON_RECV_MY_INFO = "ON_RECV_MY_INFO";
	//����BBS�ִ��¼�
	public static String ON_TEX_RECV_BBS_URL="ON_TEX_RECV_BBS_URL";
	//��¼��Ǯ�¼�
	public static String ON_TEX_LOGIN_SHOW_DAY_GOLD="ON_TEX_LOGIN_SHOW_DAY_GOLD";
	//��¼��Ǯ��Ŀ�¼�
	public static String ON_TEX_RECV_LOGIN_GIVE="ON_TEX_RECV_LOGIN_GIVE";
	//����ѡ���ɫ�¼�
	public static String ON_RECV_CHOOSE_SHOW="ON_RECV_CHOOSE_SHOW";
	//�յ�������ϸ��Ϣ[����]�¼�
	public static String ON_RECV_USER_HALL_GAMEINFO="ON_RECV_USER_HALL_GAMEINFO";
	//�յ�������ϸ��Ϣ[��Ϸ]�¼�
	public static String  ON_RECV_USER_GAME_GAMEINFO="ON_RECV_USER_GAME_GAMEINFO";
	//�յ����������Ƶ�½�¼�
	public static String ON_RECV_RESTRICT_LOGIN="ON_RECV_RESTRICT_LOGIN";
	//ȡ�÷�ҳ������ϸ��Ϣ�����¼�
	public static String ON_RECV_CURPAGEROOM_LIST="ON_RECV_CURPAGEROOM_LIST";
	//ȡ����������ҵ���ϸ��Ϣ�¼�
	public static String ON_RECV_DESK_USERLIST="ON_RECV_DESK_USERLIST";
	//�յ������ұ仯���¼�
	public static String ON_RECV_CHANGE_GOLD="ON_RECV_CHANGE_GOLD";
	//�յ����½�ҵ��¼�
	public static String ON_RECV_UPDATE_GOLD="ON_RECV_UPDATE_GOLD";
	//�յ��û��뿪��ս
	public static String ON_RECV_EXIT_WATCH="ON_RECV_EXIT_WATCH";
	
	//�յ��û�GPS����
	public static String ON_RECV_GPS="ON_RECV_GPS";

	Object data;// ����
	String eventName;// �¼�����

	public Event(String eventName) {
		this.eventName = eventName;
	}

	public Event(String eventName, Object data) {
		this.eventName = eventName;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
}
