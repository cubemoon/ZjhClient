package com.dozengame.playerlocationroom;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.dozengame.BaseActivity;
import com.dozengame.DzpkGameMenuActivity;
import com.dozengame.GameApplication;
import com.dozengame.HwgCallBack;
import com.dozengame.RoomView;
import com.dozengame.TestNeCunThread;
//import com.dozengame.DzpkGameRoomActivity;
import com.dozengame.R;
//import com.dozengame.RoomView;
import com.dozengame.net.pojo.DConfig;
import com.dozengame.util.GameUtil;

public class DzpkPlayerLocationRoomActivity extends BaseActivity{
	
	ImageView backImageBg;
	ImageView refreshBg;
	LocationRoomView lrv;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	 	getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  
	 	Log.i("test1","�������room onCreate");
	 	setContentView(R.layout.playerlocationroom);
	 	ViewFlipper  flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper);//���ViewFlipperʵ��
       
	 	lrv = new LocationRoomView(this);
        if(flipper != null){
	 	 flipper.addView(lrv);
        }else{
        	Log.i("test1","111111111�������room onCreate");
        }
    	//��ʼ��Bitmap
	 	initBitmap();
	 	//��ʼ������
	 	initData();
	 	//��ʼ��GPS����
	 	if(initLocationGPS() ==1){

		 	loadData();
	 	}
	}
	
	Bitmap hall_back;		//���ذ�ť����
	Bitmap bmp_btnRefresh;	//ˢ�°�ť����
	Bitmap hall_button1;	//��ť����
	Bitmap hall_button2;
	Bitmap hall_listbg;		//���汳��
	Bitmap hall_titlebg;	//���ⱳ��
	
	//��ʼ��Bitmap
	 private void initBitmap(){
	    	try {
	    		hall_listbg = BitmapFactory.decodeStream(getAssets().open("locationroom/gps_bg2.jpg"));
	    		hall_titlebg = BitmapFactory.decodeStream(getAssets().open("hall_titlebg.png"));
	    		hall_button1 = BitmapFactory.decodeStream(getAssets().open("hall_button.png"));
	    		hall_button2 = BitmapFactory.decodeStream(getAssets().open("hall_buttons.png"));
	    		bmp_btnRefresh = BitmapFactory.decodeStream(getAssets().open("locationroom/gps_refurbish.png"));
	    		hall_back = BitmapFactory.decodeStream(getAssets().open("hall_back.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }
	 
	 //��ʼ������
	 private void initData(){
			
		 	findViewById(R.id.playerLocationRoom).setBackgroundDrawable(new BitmapDrawable(hall_listbg));
			findViewById(R.id.locationRoomTop).setBackgroundDrawable(new BitmapDrawable(hall_titlebg));
			
			//���ذ�ť
		     ImageView backImage=(ImageView)findViewById(R.id.locationRoomBack);
		     backImage.setImageBitmap(hall_back);
		     backImageBg=(ImageView)findViewById(R.id.locationRoomBackbg);
			 backImageBg.setImageBitmap(hall_button1);
			 
			 //ˢ�°�ť
			 ImageView btnRefreshImage=(ImageView)findViewById(R.id.locationRoomRefresh);
		     btnRefreshImage.setImageBitmap(bmp_btnRefresh);
			 refreshBg=(ImageView)findViewById(R.id.locationRoomRefreshbg);
			 refreshBg.setImageBitmap(hall_button1);
			 
			 //��Ӱ�ť����
			 addListener();
		}
	 
		 /**
	     * ��Ӱ�ť�¼�
	     */
	    private void addListener(){
	
	    	//���ذ�ť�¼�
	    	backImageBg.setOnTouchListener(new OnTouchListener(){
	
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					final int action =event.getAction();
				    if(action == 0){
				    	backImageBg.setImageBitmap(hall_button2);
				    	return true;
				    }else if(action ==1){
				    	backImageBg.setImageBitmap(hall_button1);
				    	back();   //����
				    	return true;
				    }
					return false;
				}
	    		
	    	});
	    	
	    	//ˢ�°�ť�¼�
	    	refreshBg.setOnTouchListener(new OnTouchListener(){
	
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					final int action =event.getAction();
				    if(action == 0){
				    	refreshBg.setImageBitmap(hall_button2);
				    	return true;
				    }else if(action ==1){
				    	refreshBg.setImageBitmap(hall_button1);
				    	refresh();	//ˢ��
				    	return true;
				    }
					return false;
				}
	    		
	    	});
	    }
		    
	    /**
		 * ����
		 */
		private void back(){
		/*	if(rv != null){
			  rv.setScrollEnable(true);
			}*/
			Intent it = new Intent();
			it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			it.setClass(DzpkPlayerLocationRoomActivity.this,DzpkGameMenuActivity.class);
	    	startActivity(it);
	    	finish();
		}
	 
		 /**
		 * ˢ��
		 */
		private void refresh(){

			if(initLocationGPS() ==1){
			 lrv.loadData();
			}
		}


	 //����
	 @Override
	protected void onDestroy() {
		 
		super.onDestroy();
		
		 Log.i("test1","�������room  onDestroy");
		 
		 GameUtil.recycle(hall_button1);
		 GameUtil.recycle(hall_button2);
		 GameUtil.recycle(hall_back);
		 GameUtil.recycle(hall_titlebg);
		 GameUtil.recycle(hall_listbg);
		 GameUtil.recycle(bmp_btnRefresh);

	//	 rv.dismiss();
		 System.gc();
		   
	}
	 
	 
	Handler handler  = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			   //ѡ��Ĭ�ϳ�
	        loadRoomListView();
		}
	};
	
	/**
	  * ���������б�
	  */
	 private void loadRoomListView(){
		 
		 refresh();
	 }
	 
	 /**
	 * ��ȡ��ǰ�ҵ�λ��
	 */
	private int initLocationGPS()
	{
		Log.i("test2", "initLocationGPS");
	
		int state =GameUtil.initLocationGPS(this);
		switch(state){ 
		case -2: 
			HwgCallBack callback1 = new HwgCallBack() {
				
				@Override
				public void CallBack(Object... obj) {
					 
						Intent it = new Intent();
						it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						it.setClass(DzpkPlayerLocationRoomActivity.this,DzpkGameMenuActivity.class);
				    	startActivity(it);
				    	finish();
 
				}
			};
	    //startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
		//startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);
		//startActivityForResult(new Intent(android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS), 0);
		 GameUtil.openMessageDialog(DzpkPlayerLocationRoomActivity.this, "��ʱ�޷���λ����λ�á�",callback1); 
		break; 
		case -1: 
			HwgCallBack callback = new HwgCallBack() {
				
				@Override
				public void CallBack(Object... obj) {
					if(obj == null || obj.length ==0){
					  startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
					}
				}
			};
		   GameUtil.openMessage1Dialog(this, "û���ҵ���������ң���ȷ�������ڵ������ֻ��û��GPS��", callback,"��������","�ݲ�����");
		break; 
		case 0: 
		break; 
		case 1: 
		break; 

		} 
		return state;

//		
//	 	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//		//�ж�GPS״̬
//		boolean GPS_status = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//	    boolean NETWORK_status = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//	
//	    if(GPS_status){
//	        
//	    	Log.i("test2", "GPS GPS_status����");
//	    }
//	    else if(NETWORK_status){
//	    	Log.i("test2", "GPS NETWORK_status����");
//	    }
//	    else{
//
//	    	Log.i("test2", "GPS GPS_status NETWORK_statusδ����");
//	    	
//	    	GameUtil.openMessageDialog(DzpkPlayerLocationRoomActivity.this, "û���ҵ���������ң���ȷ�������ڵ������ֻ��û��GPS��",callback);
//			return false;
//        }
//				
//		Criteria criteria = new Criteria();  
//		criteria.setAccuracy(Criteria.ACCURACY_FINE);  
//		criteria.setAltitudeRequired(false);  
//		criteria.setBearingRequired(false);  
//		criteria.setCostAllowed(true);
//		criteria.setPowerRequirement(Criteria.POWER_LOW); 
//
//		String provider = lm.getBestProvider(criteria, false);
//		Log.i("test2", "GPS provider: "+provider);
//		//��ȡGPS����
//	//	Location myLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//		Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//	
//	//	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 8, ll);    //���¼����� 
////			lm.requestLocationUpdates(provider, 20000, 10, new LocationListener() {
////				public void onStatusChanged(String provider, int status, Bundle extras) {}
////				public void onProviderEnabled(String provider) {}
////				public void onProviderDisabled(String provider) {}
////				public void onLocationChanged(Location location) {
////					if (location != null) {
////						int lat = (int)(location.getLatitude()*1E6);
////						int longlat = (int)(location.getLongitude()*1E6);
////						Log.i("test2", "lat: "+lat+" longlat: "+longlat);
////					}
////					
////				}
////			});
//		
//		if(myLocation == null) 
//		{
//			 myLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//			//lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 8, ll);    //���¼�����
//		} 
//
//		 if(myLocation == null)
//		 {
//		   	Log.i("test2", "GPS ��ȡʧ��");
//			GameUtil.openMessageDialog(DzpkPlayerLocationRoomActivity.this, "û���ҵ���������ң���ȷ�������ڵ������ֻ��û��GPS��",callback);
//
//			return false;
//		}
//	
//		DConfig.nMyLatitudeX = myLocation.getLatitude();
//		DConfig.nMyLongitudeY = myLocation.getLongitude(); 
//		Log.i("test2", " DConfig.nMyLatitudeX: "+DConfig.nMyLatitudeX+"  DConfig.nMyLongitudeY: "+DConfig.nMyLongitudeY);
//
//		return true;
	}
		LocationListener ll = new LocationListener() {   
	           
	        @Override  
        public void onStatusChanged(String provider, int status, Bundle extras) {   
	        	 Log.i("test2", "onStatusChanged: "+provider);
	       }   
	          
       @Override  
	      public void onProviderEnabled(String provider) {   
    	   Log.i("test2", "onProviderEnabled: "+provider);
	           //���豸����ʱ�����¼�   
	           
	       }   
	           
	       @Override  
	       public void onProviderDisabled(String provider) {   
	    	   Log.i("test2", "onProviderDisabled: "+provider);
	          
	       }   
	           
	        @Override  
	        public void onLocationChanged(Location location) {   
	        	 Log.i("test2", "onLocationChanged: "+location);
	        	 if(location !=null){
		        	 DConfig.nMyLatitudeX = location.getLatitude();
		     		
		 			DConfig.nMyLongitudeY = location.getLongitude(); 
	        	 }
	           
	       }   
	   }; 
		private void loadData(){
			Thread t=new Thread(){
				public void run() {
					// loadRoomListView();
					 handler.sendEmptyMessage(0);
				}
			};
			 
			t.start();
		}
}
