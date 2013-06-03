package com.dozengame.gameview;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.dozengame.GameApplication;
import com.dozengame.event.CallBack;
import com.dozengame.event.Event;
import com.dozengame.net.SocketService;

/**
 * ��������߳�
 * @author hewengao
 *
 */
public class CheckNetThread extends Thread implements CallBack{

	private boolean run = true;
	private int sleepTime = 2500;
	private boolean send=false;
	private  String currnetTime="";
	private CheckNetView cnv;
	int count =0;
	public CheckNetThread(CheckNetView cnv){
		this.cnv= cnv;
		this.start();
	}
 
	public void run() {
		addListener();
		try {
			while(run){
				count++;
				if(send ==  false){
					handler.sendEmptyMessage(2);//���ֵ�ǰ
					SocketService service = GameApplication.getSocketService();
			    	if(service != null){
			    		try {
			    			currnetTime =System.currentTimeMillis()+"";
							service.sendCheckNet(currnetTime);
							send =true;
						} catch (Exception e) {
							e.printStackTrace();
							send =false;
						}
			    	}
			    	 
				}else{
					//��Ҫ��ʾ������
					send = false;
				    handler.sendEmptyMessage(1);//������
			    	 
				}
				sleep(sleepTime);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			removeListener();
		}
	}
	public void onRecvCheckNet(Event e){
		Log.i("test4", "onRecvCheckNet");
		String time = (String)e.getData();
		if(time != null && time.equals(currnetTime)){
			send = false;
			handler.sendEmptyMessage(0);//��ʾ��Ϣ�ȶ�
		}else{
			
		}
	}
    private void addListener(){
    	SocketService service = GameApplication.getSocketService();
    	if(service != null){
    	    service.addEventListener(Event.ON_RECV_CHECK_NET, this, "onRecvCheckNet");
    	}
    }
    private void removeListener(){
    	SocketService service = GameApplication.getSocketService();
    	if(service != null){
    	    service.removeEventListener(Event.ON_RECV_CHECK_NET, this, "onRecvCheckNet");
    	}
    }
    int currentState =2;
    Handler handler = new Handler(){
    	@Override
    	public void handleMessage(Message msg) {
    	    
    	    	 if(cnv !=null){
    	    		 boolean timeUpdate = false;
    	    		 if(count % 4 ==0){
    	    			 //count =0;
    	    			 timeUpdate = true;
    	    		 }
    	    		  
    	    		 if(msg.what ==0){
    	    			 currentState=3;
    	    			 //�ź��ȶ�
    	    			 cnv.setSignalState(currentState, timeUpdate);
    				   //cnv.setVisibility(View.INVISIBLE);
    			     }else if(msg.what == 1){
    			    	 currentState=1;
    			    	 //�źŲ��ȶ�
    			    	 cnv.setSignalState(currentState, timeUpdate);
    			       //cnv.setVisibility(View.VISIBLE); 
    			     }else{
    			    	 
    			    	 //���ֵ�ǰ
    			    	 if(timeUpdate){
    			    		 cnv.setSignalState(currentState, timeUpdate);
    			    	 }
    			     }
    	        }
    	}
    };
}
