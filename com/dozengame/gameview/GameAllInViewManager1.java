package com.dozengame.gameview;

import android.util.Log;

import com.dozengame.DzpkGameActivityDialog;

/**
 * ��Ϸȫ�¶�������
 * @author hewengao
 *
 */
public class GameAllInViewManager1 {
	DzpkGameActivityDialog context;
	static final GameAllInView1 [] allInViews= new GameAllInView1[9];
	public GameAllInViewManager1(DzpkGameActivityDialog context) {
		 
		this.context=context;
		initViews();
	}
	
	private void initViews(){
		for(int i=0; i<9;i++){
			allInViews[i]=new GameAllInView1(context.getContext(),i);
			context.frameLayout.addView(allInViews[i]);
		}
	}
	/**
	 * ��ͼ�Ŀɼ���
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		if(allInViews[index] !=null){
			allInViews[index].setVisibility(visibility);
		}
	}
	 
	/**
	 * ���ö���
	 * @param index
	 */
	 public void startAnim(int index){
		 if(allInViews[index] !=null){
			 allInViews[index].startAnim();
		 }
	 }
	   /**
		 * �ͷ���Դ
		 */
		public void destroy(){
			Log.i("test19", "GameAllInViewManager destroy");
			for(int i=0; i<9;i++){
				if(allInViews[i] != null){
				   allInViews[i].destroy();
				   allInViews[i]=null;
				}
			}
			// allInViews =null;
		}
}
