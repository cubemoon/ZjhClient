package com.dozengame.playerlocationroom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dozengame.GameApplication;
import com.dozengame.LoadingDialog;
import com.dozengame.R;
import com.dozengame.TestNeCunThread;
import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.event.FriendEventType;
import com.dozengame.gameview.PlayerInfoView;
import com.dozengame.net.pojo.DConfig;
import com.dozengame.net.pojo.DeskInfo;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;

public class LocationRoomView extends LinearLayout implements CallBack{

	DzpkPlayerLocationRoomActivity context;
	ListView locationRoomlistView;
	LocationRoomListViewAdapter adapter;
	List<PlayerLocationData> listViewData;
	
	 private static double EARTH_RADIUS = 6378.137;
	 
//	 DetailInfoView pv;
//	 FrameLayout frameLayout;
	
	private List<PlayerLocationData> listItem= new ArrayList<PlayerLocationData>();
	private List<PlayerLocationData> listItemClone= new ArrayList<PlayerLocationData>();
	
	public LocationRoomView(DzpkPlayerLocationRoomActivity context) {
		super(context);
		this.context=context;
		View view=this.inflate(context, R.layout.locationroomlistview, this);
		locationRoomlistView=(ListView)findViewById(R.id.locationRoomListView);
        initData();
	}
	
	//���ù�����
	public void setScrollEnable(boolean enable){
		this.locationRoomlistView.setScrollbarFadingEnabled(enable);
	}
	
	/**
	 * ��ʼ������
	 */
	public void initData(){
		
		//loadStart();
		
		listViewData = new ArrayList<PlayerLocationData>();
		adapter = new LocationRoomListViewAdapter(context, listViewData);
		locationRoomlistView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
	}
	
	/**
	 * ��������
	 * @throws Exception 
	 */
	public void loadData(){
		setScrollEnable(true);   //���ù�����
		
		loadingStart();
		
		//����¼�����
		GameApplication.getSocketService().addEventListener(Event.ON_RECV_GPS, this, "onRecvPlayerLocationData");
		
		//����¼�����
	//	GameApplication.getSocketService().addEventListener(FriendEventType.ON_RECV_EXTRAINFO_ACHIEVEINFO, this, "onResponseExtrainfoAchieveInfo");	
		
		//����GPS�����������,����������
		try {
			GameApplication.getSocketService().sendGPS
				(Double.toString(DConfig.nMyLatitudeX), Double.toString(DConfig.nMyLongitudeY), 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ��ʼ����
	 */
	private void loadingStart(){
		loadingStop();
		GameApplication.loading =  new LoadingDialog(context,R.style.dialog,null,30000);
		GameApplication.loading.show();
        TaskManager.getInstance().execute(new TaskExecutorAdapter(){
		@Override
		public int executeTask() throws Exception {
			GameApplication.loading.start();
			return 0;
		} 
       });
      
	}
	
	public  void loadingStop(){
		 
		GameApplication.dismissLoading();
	}
	
	/**
 	 * �������λ������
 	 * 
 	 * @param e
 	 */
 	public void onRecvPlayerLocationData(Event e) 
 	{
 		//loadingStop();
 		
		//�Ƴ��¼�����
		GameApplication.getSocketService().removeEventListener(Event.ON_RECV_GPS, this, "onRecvPlayerLocationData");
		
 		ArrayList tempData = (ArrayList)e.getData();
 		
 		//���½�������
	    sendMsg(tempData);
 	}
 	
 /*	
 	*//**
 	 * ������Ҹ�����Ϣ�ͳɾ�
 	 * 
 	 * @param e
 	 *//*
 	public void onResponseExtrainfoAchieveInfo(Event e) 
 	{
 		Log.i("test17", "������Ҹ�����Ϣ�ͳɾ�");
 		Message msg=handler1.obtainMessage();
		msg.what =0;
		msg.obj = (HashMap)e.getData();
		handler1.sendMessage(msg);
 
 	}
 
	Handler handler1 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			executeResponseExtrainfoAchieveInfo((HashMap)msg.obj);
			//executeResponseExtrainfoAchieveInfo(msg.obj);
		}
	};
	*//**
	 * �����յ��ɾ���Ϣ
	 * ���µĵ���������
	 * @param data
	 *//*
	private void executeResponseExtrainfoAchieveInfo(HashMap data){
	//private void executeResponseExtrainfoAchieveInfo(Object data){

		Log.i("test2", "���µĵ�������");
		// ����
		pv =new DetailInfoView(context,3, data);
	  //  frameLayout.addView(pv);
		//pv.setGv(context);
	//	context.
	}*/
 	/////
 	
 	
 	Handler handler = new Handler() {
		public void handleMessage(Message msg) {// ����һ��Handler�����ڴ��������߳���UI��ͨѶ
			if (!Thread.currentThread().isInterrupted()) {

				    listItemClone.clear();
				    listItem.clear();
					if (msg.obj != null) {
						ArrayList<PlayerLocationData> list = (ArrayList<PlayerLocationData>) msg.obj;
		
						listViewData.clear();
						listViewData.addAll(list);     
						
						//�������
						totalDistance();
						
						//����
						sort(0,(short)0);
						
						adapter.notifyDataSetChanged();
						list.clear();
						list = null;
					
					}else{
					   adapter.notifyDataSetChanged();
					}
			 	Log.i("test17", "next");
				 
				GameApplication.testNetCun();
			        if(listItemClone != null && listItemClone.size() > 0){
			        	 setScrollEnable(false);
			        }else{
			        	 setScrollEnable(true);
			        }
					
			}
			super.handleMessage(msg);
		}

	};
 	
 	private void sendMsg(List<PlayerLocationData> list) {
		Message msg = new Message();
		msg.obj = list;
		handler.sendMessage(msg);
	}
 	
 	/**
	 * �����������
	 * @param titleIndex 0:����
	 * @param s 0:���� 1:����
	 */
	public void sort(final int titleIndex,final short s) {
		java.util.Collections.sort(listViewData,new Comparator<PlayerLocationData>(){
			@Override
			public int compare(PlayerLocationData obj1, PlayerLocationData obj2) {
				int result =0;
				switch(titleIndex){
				
				case 0:
					 if(obj1.getDistance() > obj2.getDistance()){
						 result =1;
					 }else if(obj1.getDistance()< obj2.getDistance()){
						 result = -1;
					 } 
					break;
				}
				if(s == 1){
					result = -result;
				}
				return result;
			}
		});
	}
	
	
	/**
	 * ��������㷨
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	 public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
     {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
                        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
       
        distance = distance * EARTH_RADIUS;
     //   distance.setMaximumFractionDigits(3);
       // distance = Math.round(distance * 10000)/ 10000; 
        
        BigDecimal b1 = new BigDecimal(distance);  
        double f1  = b1.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();  
        distance = f1;
        
        return distance;
     }
     
	 /**
	  * ������븨��
	  * @param d
	  * @return
	  */
     private static double rad(double d)
     {
        return d * Math.PI / 180.0000;
     }
	
     
     /**
      * ������Ҿ���
      */
     private void totalDistance()
     {
    	 int nLen = listViewData.size();
    	 
    	 for(int i = 0; i < nLen; i++)
    	 {
    		 double a = DConfig.nMyLatitudeX;
        	 double b = DConfig.nMyLongitudeY;
        	 double c = Double.valueOf(listViewData.get(i).strLatitude).doubleValue();
        	 double d = Double.valueOf(listViewData.get(i).strLongitude).doubleValue();
        	 double nDistance = GetDistance(a, b, c, d);
        	 
        	 listViewData.get(i).setDistance(nDistance);
    	 }
 
     }
     
}
