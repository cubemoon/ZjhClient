package com.dozengame.net.pojo;

public class DConfig {

	//public static String strUser = "hwg19860119";					//����,������ҳ  �û���			
	//public static String strKey = "hwg19860119hwg";						//����,������ҳ  ���루�Ժ�ĳ����ģ�
	public static String strUser = "222";
	
	public static String strKey = "11";
	public static String strKey2 = "11";						//����,������ҳ  ���루�Ժ�ĳ����ģ�		
    public static int userId=-1;
	public static String strChannelId = "";
	
	public static String strVersion = "";					//GameCenter IP
	
	public static int nRegSit  = 1 	;						//ע��վ��1��ʾ���棬0��ʾ����
	public static String strHost = "duowan.dozengame.com" ;	//yy��
	public static String strHost2 = "duowan.dozengame.com" ;	//yy��
	
	public static int nPort  = 6000;					    	//GameCenter �˿�
	public static int nPort2  = 6000;					    	//GameCenter �˿�
	public static String strChongzhiUrl = "";			 	//��ֵurl
	public static String strDefaultGame = "";			 	//Ĭ����Ϸ��,����swf����
	public static String strLoginType = "1";                 //�����Ƿ�Ƶ����¼��־, Ĭ��Ϊ1
	
	public static final String FROM_CHANNEL = "2";         //����Ƶ�����û�
	
	//������������
	public static final int LOCAL_MUTEX_TYPE_DESKLIST  = 1;	//�ӷ����б���������
	public static final int LOCAL_MUTEX_TYPE_SITDOWN  = 2;	//����յ������¼������(onRecvSitDown)
	public static final int LOCAL_MUTEX_TYPE_TEXT_ENTER  = 3;//���������뷿��ID����
	public static final int LOCAL_MUTEX_TYPE_TEXT_CLICK_DESK_JOINFRIEND  = 4;//���������뷿��ID����

	public static double nMyLatitudeX = 0;					    	//�Լ�GPSγ������X
	public static double nMyLongitudeY = 0;					    	//�Լ�GPS��������Y
}
