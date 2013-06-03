package com.dozengame.gameview;

import android.util.Log;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;

/**
 * ��Ϸȫ�¶�������
 * @author hewengao
 *
 */
public class GameAllInViewManager {
	DzpkGameActivityDialog context;
	static final GameAllInView [] allInViews= new GameAllInView[9];
	public GameAllInViewManager(DzpkGameActivityDialog context) {
		 
		this.context=context;
		initViews();
	}
	
	private void initViews(){
		for(int i=0; i<9;i++){
			allInViews[i]=new GameAllInView(context.getContext(),i);
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
