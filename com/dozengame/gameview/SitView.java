package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dozengame.GameApplication;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
import com.dozengame.util.Measure;
/**
 * ��λ��ͼ
 * ��Ҫ�������°�ť����ʾ
 * ��������δ��
 * ֻ�е���Ϸ��δ��ʼ���Լ�δ���µ�״̬�²���Ҫ����
 * ������������λ����Ҫ����
 * @author hewengao
 */
public class SitView extends View {
	
	static final int[][] sitPoint ={{427,440},{213,440},{32,365},{32,120},{260,45},{600,45},{825,125},{825,360},{654,440}};
 
    int clickIndex=-1;//���������
	boolean ispress=false;
	public static int  siteIndex=-1;//���������
    int sitWidth,sitHeight;//����ͼ���
    Bitmap HALL_SITDOWN, HALL_SITDOWNS;
    Bitmap cacheBitmap=null;
    boolean bitmapIsInit =false;
    Canvas c = null;
    boolean isNeedDraw =true;//�Ƿ���Ҫ������λ
	public SitView(Context context) {
		super(context);
	    c = new Canvas();
	    setVisibility(View.INVISIBLE);
	}
	/**
	 * ��ʼ������
	 */
    private void init(){
    	if(bitmapIsInit ==false){
	    	HALL_SITDOWN=GameBitMap.getGameBitMap(GameBitMap.HALL_SITDOWN);
	    	HALL_SITDOWNS=GameBitMap.getGameBitMap(GameBitMap.HALL_SITDOWNS);
	    	
			sitWidth=HALL_SITDOWN.getWidth();
			sitHeight=HALL_SITDOWN.getHeight();
			bitmapIsInit =true;
    	}
    }
    boolean drawRun =true;
	/**
	 * �ػ�
	 */
	private void draw(){
		drawRun =true;
		this.postInvalidate();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		try{
		  if(isNeedDraw && View.VISIBLE ==this.getVisibility()){
			    if(drawRun){
				    drawRun =false;
				    init();
				    GameUtil.recycle(cacheBitmap);
				    cacheBitmap= Bitmap.createBitmap(960,640, Config.ARGB_4444);
				    c.setBitmap(cacheBitmap);
					//��Ϸδ��ʼ��Ҫ������λ
			    	for (int i = 0; i < 9; i++) {
			    		 if(clickIndex==i){
			               drawSit(c, true, i);
			    		 }
			    		 else{
			    		   drawSit(c, false, i);
			    		 }
					}
			     
			    }
			  canvas.drawBitmap(cacheBitmap, 0, 0, null);
		  }
		}catch(Exception e){
			Log.e(GameUtil.ERRORTAG, "msg: "+e.getMessage());
			e.printStackTrace();
		}catch(java.lang.OutOfMemoryError oom){
			Log.e(GameUtil.ERRORTAG, "OutOfMemoryError msg: "+oom.getMessage());
			oom.printStackTrace();
		}
    	 
	}
	
	/**
	 * ������λ
	 * @param canvas
	 * @param isClick �Ƿ�����
	 * @param n �ڼ�����λ
	 */
	private void drawSit(Canvas canvas,boolean isClick, int n) throws Exception{
		//��ǰ��λ������ʱ��Ҫ����
	    if(!GameApplication.getDzpkGame().playerViewManager.isSited(n)){
			int siteX=sitPoint[n][0];//��λ����x
			int siteY=sitPoint[n][1];//��λ����y
			Bitmap bitmap=null;
			if(isClick){
				//�����
				bitmap=HALL_SITDOWNS;
			}else{
				bitmap=HALL_SITDOWN;
			}
			canvas.drawBitmap(bitmap, siteX, siteY, null);
	   }
	}
	
	 @Override
	public boolean onTouchEvent(MotionEvent event) {
		 if(isNeedDraw){
			 
			 return clickSitDown(event.getAction(),event.getX(),event.getY());
		 }else{
			 return false;
		 }
	}

	
	/**
	 * �������
	 * @param xx
	 * @param yy
	 */
	public boolean clickSitDown(int action,float xx ,float yy){
		for(int i = 0; i < 9; i++) {
			 //��ǰλ��û�����ſ��Ե�
			if(GameApplication.getDzpkGame().playerViewManager.isSited(i)){
			        continue;
			}
			if(Measure.isInnerBorder(xx, yy, sitPoint[i][0], sitPoint[i][1], sitPoint[i][0]+sitWidth, sitPoint[i][1]+sitHeight)){
					if(action==0){
						ispress =true;
						//����
						if(clickIndex !=i){
					    	clickIndex=i;
							draw();
						}
					}else if(action ==1){
						//����
						ispress =false;
						clickIndex=-1;
						draw();
						if(clickSit==false && !GameApplication.getDzpkGame().playerViewManager.mySite){
							//��ʾ�Լ�δ����,���͹����������
							siteIndex =i;
							
							sendRequestTXNTBC();
						}
						
					}
					return true;
			}else{
				
				if(action ==1 && ispress ==true){
					ispress=false;
					clickIndex =-1;
					draw();
				}
			}
		}
		return false;
	}
	public boolean clickSit =false;
	/**
	 * ���͹����������
	 */
	private  void sendRequestTXNTBC(){
		clickSit =true;
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			public int executeTask() throws Exception {
				GameApplication.getDzpkGame().gameDataManager.GameOver =false;
				GameApplication.getSocketService().sendRequestTXNTBC();
				return 0;
			}
			
		}); 
	}
	/**
	 * �ͷ���Դ
	 */
	public void destroy(){
		Log.i("test19", "SitView destroy");
		 destroyBitmap();
		 destroyDrawingCache();
		 GameUtil.recycle(cacheBitmap);
		 cacheBitmap =null;
	}
	private void destroyBitmap(){
		 GameUtil.recycle(HALL_SITDOWN);
		 HALL_SITDOWN =null;
		 GameUtil.recycle(HALL_SITDOWNS);
		 HALL_SITDOWNS =null;
		 bitmapIsInit =false;
	}
	/**
	 * ��дView�ɼ���
	 */
	public void setVisibility(int visibility){
		super.setVisibility(visibility);
		if(View.INVISIBLE ==visibility){
			destroyBitmap();
			GameUtil.recycle(cacheBitmap);
			cacheBitmap =null;
			System.gc();
		 
		}else{
			draw();
		}
	}
	public void setNeedDraw(boolean isNeedDraw) {
		this.isNeedDraw = isNeedDraw;
		if(isNeedDraw){
			setVisibility(View.VISIBLE);
		}else{
			setVisibility(View.INVISIBLE);
		}
	}
}
