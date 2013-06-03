package com.dozengame.gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;

import com.dozengame.music.MediaManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
/**
 * ��Ϸ����Ӯ����ͼ
 * @author hewengao
 *
 */
public class GameMyWinView extends View {
	static final int []  startPoint1={465,100};//�����������
	static final int []  startPoint={237,78};//��������
	Canvas canvas;
	Bitmap cacheBitmap;
	Bitmap cacheBitmapPrev;
	Bitmap MYWINBG;
	Bitmap MYWINTEXT;
	static final float [] res=new float[2];
	boolean bitmapIsInit =false;
	public GameMyWinView(Context context) {
		super(context);
		canvas =  new Canvas();
		res[0] = (float) ((startPoint1[0] / 1.0) / 960);
		res[1] = (float) ((startPoint1[1] / 1.0) / 640);
		this.setVisibility(View.INVISIBLE);
	}
	public void drawCacheBitmap(){
		if(bitmapIsInit == false){
			MYWINBG =GameBitMap.getGameBitMap(GameBitMap.MYWINBG);
			MYWINTEXT =GameBitMap.getGameBitMap(GameBitMap.MYWINTEXT);
			bitmapIsInit =true;
		}
		cacheBitmapPrev = cacheBitmap;
		cacheBitmap = Bitmap.createBitmap(489, 483, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);
		canvas.drawBitmap(MYWINBG, 0,0,null);
		canvas.drawBitmap(MYWINTEXT, 88,182,null);
	     
	}
	boolean drawRun =true;
	public void draw(){
		drawRun =true;
		postInvalidate();
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(View.VISIBLE == this.getVisibility()){
			if(drawRun){
			  drawRun =false;
			  drawCacheBitmap();
			  canvas.drawBitmap(cacheBitmap, startPoint[0],startPoint[1],null);
			  destroyBitmap();
			}else{
			   canvas.drawBitmap(cacheBitmap, startPoint[0],startPoint[1],null);
			}
		}
	}
	MyThread myThread;
	ScaleAnimation  am1=null;
	/**
	 * ����Ӯ�Ķ���
	 */
	public void startWinAnim(){
		if(isStop)return;
    	//�õ���ǰ��λ����ע������ͼ
	    this.setVisibility(View.VISIBLE);
	  
	     am1 =   new ScaleAnimation(0.0f, 1.0f, 0.0f,1.0f,Animation.RELATIVE_TO_SELF,res[0],Animation.RELATIVE_TO_SELF,res[1]);
		//����ʱ�����ʱ��
		 am1.setDuration(200);
		 am1.setInterpolator(new AccelerateInterpolator());//������
		 am1.setAnimationListener(new AnimationListener(){

				@Override
				public void onAnimationEnd(Animation animation) {
					myThread=new MyThread();
				}
				public void onAnimationRepeat(Animation animation) {

				}
				public void onAnimationStart(Animation animation) {
					//���Ŵӵ׳��õ���������
					//MediaManager.getInstance().playSound(MediaManager.winchips);
				}
				
			});
		 
		  this.startAnimation(am1);
		 
    }
	boolean isStop =false;
	/**
	 * ֹͣ�߳�
	 */
	public void stopThread(){
		try{
			isStop =true;
			if(am1 != null){
				if(am1.hasStarted()){
					am1.cancel();
				}
			}
			if(myThread != null){
				if(!myThread.isInterrupted()){
					myThread.interrupt();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			 super.handleMessage(msg);
			 setVisibility(View.INVISIBLE);
		}
	};
	private class MyThread extends Thread{
		public MyThread(){
			this.start();
		}
		@Override
		public void run() {
			 try {
				Thread.sleep(1000);
				handler.sendEmptyMessage(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void destroy() {
		Log.i("test19", "GameMyWinView destroy");
		canvas =null;
		handler =null;
		am1 =null;
		destroyBitmap();
		this.destroyDrawingCache();
		
	}
	private void destroyBitmap(){
		GameUtil.recycle(cacheBitmapPrev); 
		cacheBitmapPrev =null;
		GameUtil.recycle(MYWINTEXT); 
		MYWINTEXT =null;
		GameUtil.recycle(MYWINBG); 
		MYWINBG =null;
		bitmapIsInit =false;
	}
	public void setVisibility(int visibility){
		super.setVisibility(visibility);
		if(View.INVISIBLE ==visibility){
			destroyBitmap();
			GameUtil.recycle(cacheBitmap); 
			cacheBitmap =null;
		}else{
			draw();
		}
	}
}
