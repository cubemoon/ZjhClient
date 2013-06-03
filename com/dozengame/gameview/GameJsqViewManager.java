package com.dozengame.gameview;

 
import android.util.Log;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.music.MediaManager;
import com.dozengame.util.GameBitMap;
/**
 * ��ʱ����Ϣ
 * @author hewengao
 *
 */
public class GameJsqViewManager{

	 
	 DzpkGameActivity context;
	 
	final static GameJsqView [] jsqViews= new GameJsqView[9];
	private int currnetIndex=-1;
	public GameJsqViewManager(DzpkGameActivity context) {
		 
		 this.context=context;
		initPokeBackViews();
	}
	 
	
	private void initPokeBackViews(){
		for(int i=0; i<9;i++){
			jsqViews[i]=new GameJsqView(context,i);
			context.frameLayout.addView(jsqViews[i]);
		}
	}
	 /**
	  * ������ʱ��
	  */
	private void start(int timeOut,int delay){
		if(currnetIndex>=0){
		  jsqViews[currnetIndex].start(timeOut,delay);
		}
	}
	/**
	 * ֹͣ��ʱ��
	 */
    public void setStop(){
    	if(currnetIndex>=0){
    	 jsqViews[currnetIndex].stop();
    	}
    	 
    }
    /**
     * �жϼ�ʱ���Ƿ�������
     * @return
     */
    public boolean isStop(){
    	if(currnetIndex>=0){
       	  if(jsqViews[currnetIndex].isRun){
       		  jsqViews[currnetIndex].stop();
       		  return false;
       	  }else{
       		  return true;
       	  }
       	}
    	return true;
    }
    /**
	 * ֹͣ��ʱ��
	 */
    public void setStop(int timeOut,int delay){
    	if(currnetIndex>=0){
    	  jsqViews[currnetIndex].stops(timeOut,delay);
    	}
    	 
    }
    /**
	 * ֹͣ��ʱ��
	 */
    public void setStop(int index){
    	 if(index >=0){
	    	 jsqViews[index].stop();
    	 }
    	 
    }
    //�Ƿ��Ѳ���
    boolean oper = true;
    public void setIsOperation(boolean oper){
    	this.oper =oper;
    }
	 /**
	  * ���Ƽ�ʱ����ʾλ��
	  * @param siteNo
	  * @param timeOut ʣ��ʱ��
	  * @param delay  ��ʱ��
	  */
	public void setJsqSiteNo(int siteNo,int timeOut,int delay) {
		
		 int tempIndex = GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
		 if(GameApplication.getDzpkGame().playerViewManager.getPlayerDisplayByIndex(tempIndex) ==false){
			
			 Log.i("test15", "û����ʾ siteNo: "+siteNo+" timeOut: "+timeOut+" delay: "+delay+" tempIndex: "+tempIndex);
//			 setStop(currnetIndex);
//			 return;
		 }
		// Log.i("test13", "siteNo: "+siteNo+" timeOut: "+timeOut+" delay: "+delay+" tempIndex: "+tempIndex);
		 if(tempIndex == currnetIndex){
			 if(oper == false){
				// Log.i("test13", "jsqsiteno �ظ�.");
				 return;
			 }
//			 else{
//				 Log.i("test13", "jsqsiteno ���ظ�.");
//			 }
			 setStop(timeOut,delay);
		 }else{
			 
			 setStop(currnetIndex);
			 currnetIndex =tempIndex;
			 start(timeOut,delay);
		 }
		 if(tempIndex ==0 && GameApplication.getDzpkGame().playerViewManager.mySite){
			 //�����ֵ��Լ�������
			 MediaManager.getInstance().playSound(MediaManager.turnstart);
			
		 }
		 //��ǰδ����
		 oper = false;
	}

	public void destroy() {
		Log.i("test19", "GameJsqView destroy");
		for(int i=0; i<9;i++){
			if(jsqViews[i] != null){
			jsqViews[i].destroy();
			jsqViews[i]=null;
			}
		}
	 
		context =null;
	 
	}
	
//  /**
//  * ���Ƽ�ʱ����ʾλ��
//  * @param siteNo
//  */
//	public void setJsqSiteNo(int siteNo) {
//		 setStop();
//		 currnetIndex= siteNo-1;
//
//		if(context.playerViewManager.myPos >0){
//			  currnetIndex = currnetIndex-context.playerViewManager.myPos;
//			if(currnetIndex < 0){
//				currnetIndex =currnetIndex+9;
//			}
//		}
//		start(20000,0);
//	}
	
	 /**
	  * ���Ƽ�ʱ����ʾλ��
	  * @param siteNo
	  * @param timeOut ʣ��ʱ��
	  * @param delay  ��ʱ��
	  */
//	public void setJsqSiteNoBak(int siteNo,int timeOut,int delay) {
//		 boolean isSame =false;
//		 int tempIndex =siteNo-1;
//		 if(tempIndex == currnetIndex){
//			
//			 isSame = true;
//			 start(timeOut,delay);
//			 setStop();
//		 }else{
//			 setStop();
//			 currnetIndex= tempIndex;
//				if(context.playerViewManager.myPos >0){
//					  currnetIndex = currnetIndex-context.playerViewManager.myPos;
//					if(currnetIndex < 0){
//						currnetIndex =currnetIndex+9;
//					}
//				}
//			 start(timeOut,delay);
//		 }
//	}
}
