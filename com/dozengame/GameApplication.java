package com.dozengame;


import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

import com.dozengame.net.SocketService;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.talk.TalkDialog;

import dalvik.system.VMRuntime;

public class GameApplication {
	//�Ƿ������
	public static boolean jieSuanIng =false;
	public static long receBytes=0;//���յ��ֽ���
	public static long sendBytes=0;//���͵��ֽ���
	public static int tab = -1;//ѡ��ķ��䳡
	private final static int CWJ_HEAP_SIZE = 12* 1024* 1024 ; 
	private final static float TARGET_HEAP_UTILIZATION = 0.85f; 
	//�����Ƿ���Ҫ���͵��յ�¼�콱
	public static int showDayGoldResults=-2;
	public static int showVipInfo=-2;
	public static HashMap vipInfoData =null;
	public static byte mobPayOff =0;//0���� 1:��
	static{
//		androidӦ��������������Activity��λ��һ��ջ�У�
//		��������ؼ�ʱ������ͨ��Activity��isTaskRoot()�������жϣ�
//		��ǰactivity�Ƿ�λ�ڵ�ǰջ�����һ�����������������ڷ���ʱ����finish()������
//		����ǰ��acitivityɱ���� ���û���ת��Ӧ�õ���ҳ��ʱ��Ӧ��ת��Intent 
//		����flagΪFLAG_ACTIVITY_CLEAR_TOP�����ԣ�������Ի�������е�֮ǰ��Activity  
		//android.os.Process.killProcess(android.os.Process.myPid());//��ȡPID    
		//������С���ڴ�
		//VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE); 
		//������ǿ������ڴ�Ĵ���Ч��
		//VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
		VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE);
		VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);
		long heapSize= VMRuntime.getRuntime().getMinimumHeapSize();
		long byteAllocated=VMRuntime.getRuntime().getExternalBytesAllocated();
		float heapuitl=VMRuntime.getRuntime().getTargetHeapUtilization();
		Log.i("test1", "heapSize: "+heapSize+" byteAllocated: "+byteAllocated+"  heapuitl: "+heapuitl);
	}
	//public static ArrayList<DGroupInfoItem> list;
	public static HashMap userInfo;
	private static LinkedList<Activity> listViews= new LinkedList<Activity>();
	public static Activity currentActivity;//��ǰ���е�activity
	public static LoadingDialog loading;
	public static  Dialog msgDialog;
 
	public static void dismissMsgDialog(){
		try{
			if(msgDialog != null){
				if(msgDialog.isShowing()){
					msgDialog.dismiss();
				}
			}
		}catch(Exception e){
			
		}
	}
	public static void dismissLoading(){
		try{
			if(loading != null){
				loading.stop();
				if(loading.isShowing()){
				loading.dismiss();
				}
			}
		}catch(Exception e){
			
		}
	}
	public static void showLoading(Context context,HwgCallBack callback,int timeOut){
		 dismissLoading();
		 GameApplication.loading =  new LoadingDialog(context,R.style.dialog,callback,timeOut);
		 loading.show();
		 TaskManager.getInstance().execute(new TaskExecutorAdapter(){
				@Override
				public int executeTask() throws Exception {
					GameApplication.loading.start();
					return 0;
				} 
		 });
	}
	public static void showLoading(Context context){
		showLoading(context,null,0);
	}
	
	
	//�������
	static SocketService service=null;
	public static SocketService getSocketService(){
		  if(service ==null){
			  service = new SocketService(); 
		  }
		  return service;
	}
//	//��Ϸ���ֿ���
//    static DzpkGameActivity dzpkGame=null;
//    public static void setDzpkGame(DzpkGameActivity dzpkGames){
//    	 dzpkGame =dzpkGames;
//	}
  //��Ϸ���ֿ���
    static DzpkGameActivityDialog dzpkGameDialg=null;
    public static void setDzpkGame(DzpkGameActivityDialog dzpkGames){
    	dzpkGameDialg =dzpkGames;
	}
	public static DzpkGameActivityDialog getDzpkGame(){
		  return dzpkGameDialg;
	}
	
	public static void push(Activity activity){
		listViews.addFirst(activity);
	}
	public static Activity poll(){
		if(!listViews.isEmpty()){
		 return listViews.removeFirst();
		}
		return null;
	}
	
	public static boolean joinAagin(){
		getSocketService().shutDownGCenter();
		getSocketService().shutDownGServer();
		return getSocketService().checkNetConnection();
	}
	 
	public int checkNetworkInfo(Context context){    
		ConnectivityManager conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);  
		//mobile 3G Data Network       
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
		//txt3G.setText(mobile.toString()); //��ʾ3G��������״̬       
		//wifi        
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();     
		//txtWifi.setText(wifi.toString()); //��ʾwifi����״̬    } 
		return 0;
	}
	/**
	 * �����������״̬
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context){
		
		ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivity == null){
			return false;
		}else{
			NetworkInfo [] info = connectivity.getAllNetworkInfo();
			if(info != null){
				int len = info.length;
				for(int i=0;i<len;i++){
					if(info[i].getState() == NetworkInfo.State.CONNECTED){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void testNetCun(){
		long heapSize= VMRuntime.getRuntime().getMinimumHeapSize();
		long byteAllocated=VMRuntime.getRuntime().getExternalBytesAllocated();
		float heapuitl=VMRuntime.getRuntime().getTargetHeapUtilization();
		Log.i("test17", "heapSize: "+heapSize+" byteAllocated: "+byteAllocated+"  heapuitl: "+heapuitl);
	
	}
	static Object lock = new Object();
	public static TalkDialog talkDialog;
	public static void addReceBytes(int len){
		receBytes += len;
	}
	public static void addSendBytes(int len){
		synchronized (lock) {
			sendBytes += len;
		}
	}
	
	public static String getSendBytes(){
		synchronized (lock) {
			
			if(sendBytes < 1024){
				return sendBytes+"B";
			}else{
				double x = sendBytes/1024.0;
				if(x < 1024){
					return x+"KB";
				}else{
					x = x/1024;
					return x+"M";
				}
			}
		}
	}
	public static String getReceBytes(){
		synchronized (lock) {
			
			if(receBytes < 1024){
				return receBytes+"B";
			}else{
				double x = receBytes/1024.0;
				if(x < 1024){
					return x+"KB";
				}else{
					x = x/1024;
					return x+"M";
				}
			}
		}
	}
}
