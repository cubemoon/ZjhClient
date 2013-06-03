package com.dozengame.gameview;

import java.util.ArrayList;
import java.util.HashMap;

 
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.music.MediaManager;

/**
 * �����ʼ��ע�������
 * 
 * @author hewengao
 * 
 */
public class GameXiaZhuViewManager {

	static final int[][] translateAnimPoint = { { -38, -90 }, { -55, -90 }, { 115, -77 },
			{ 115, 73 }, { -115, 135 }, { 35, 135 }, { -187, 75 },
			{ -187, -75 }, { -12, -90 } };
	static final GameXiaZhuView[] xiaZhuChouMaViews = new GameXiaZhuView[9];

	public DzpkGameActivityDialog context;
	// ��ǰ�Ƿ�������ע
	public boolean currentXiaZhu = false;
	ArrayList<HashMap> list;
	
	public GameXiaZhuViewManager(DzpkGameActivityDialog context) {

		this.context = context;
		initXiaZhuChouMaViews();
	}

	private void initXiaZhuChouMaViews() {
		for (int i = 0; i < 9; i++) {
			xiaZhuChouMaViews[i] = new GameXiaZhuView(context.getContext(), i);
			context.frameLayout.addView(xiaZhuChouMaViews[i]);
		}
	}

	/**
	 * ���ÿɼ���
	 * 
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index, int visibility) {
		xiaZhuChouMaViews[index].setVisibility(visibility);
	}



	/**
	 * ������ע����
	 * 
	 * @param index
	 * @param money
	 */
	public void setPlayerXiaZhuChouMa(ArrayList<HashMap> list) {
		currentXiaZhu = true;
		this.list = list;
		xiaZhuChouMa();
	}

	/**
	 * ��ע����
	 */
	private void xiaZhuChouMa() {
		int size = list.size();
		HashMap data;
		if (list != null && list.size() > 0) {
			data = list.remove(0);
			Byte siteNo = (Byte) data.get("siteno");// ��λ��
			Integer betGold = (Integer) data.get("betgold");// �ܵ���ע��
			Integer currbet = (Integer) data.get("currbet");// ������ע��
			Byte sex = (Byte) data.get("sex"); // 1=�� 0=Ů
			Byte type = (Byte) data.get("type");// 1=��ע 2=��ע 3=��ע 4=��� 5=��ע
			int index = GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
			GameApplication.getDzpkGame().playerViewManager.setPlayerState(index, type);
			xiaZhuChouMaViews[index].setMoney(currbet);
			//xiaZhuChouMaViews[index].setVisibility(View.VISIBLE);
			//ִ�н��ˢ�¶���
			//GameApplication.getDzpkGame().gameDataManager.executeRefreshSiteGoldAction(siteNo);
			translateAnimXiaZhu(xiaZhuChouMaViews[index], index,type);
			 
			if (type == 4) {
				// ALLIn����
				//GameApplication.getDzpkGame().allInViewManager.startAnim(index);
				GameApplication.getDzpkGame().allInViewManager1.startAnim(index);
			}
		} else {
			// ��ע���
			currentXiaZhu = false;
			// ������Ҫִ�з������̶���
			GameApplication.getDzpkGame().gameDataManager
					.executeDeskPokeAction();
			// ������Ҫִ����Ϸ�������̶���
			GameApplication.getDzpkGame().gameDataManager
					.executeGameOverAction();
		}
	}

	/**
	 * ��ҳ��뵽��λ��ע����
	 * 
	 * @param cmvStart
	 * @param index
	 * @param currGold
	 */
	private void translateAnimXiaZhu(final GameXiaZhuView xiaZhuView,
			final int index,final int type) {

		int x = translateAnimPoint[index][0];
		int y = translateAnimPoint[index][1];
		TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, x,
				0, y);
		// ����ʱ�����ʱ��Ϊ300 ����=0.3��
		mTranslateAnimation.setDuration(300);
		mTranslateAnimation.setInterpolator(new LinearInterpolator());// ����
		mTranslateAnimation.setStartOffset(50);
		mTranslateAnimation.setAnimationListener(new MyAnimationListener(xiaZhuView, index,type));
		xiaZhuView.startAnimation(mTranslateAnimation);
	}

	/**
	 * ���������¼�
	 * 
	 * @author hewengao
	 * 
	 */
	private class MyAnimationListener implements AnimationListener {
		GameXiaZhuView view1;// ��ʼ��ͼ
		int index = -1;// ĳ����λ������
        int type =-1;
		public MyAnimationListener(GameXiaZhuView view1, int index,int type) {
			this.view1 = view1;
			this.index = index;
			this.type =type;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			view1.setVisibility(View.INVISIBLE);
			GameApplication.getDzpkGame().playerChouMaViewManager.setChouMaByIndex(index, view1.getMoney());
			// ִ����ע
			xiaZhuChouMa();
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
			if(type ==5){
				 //���Ÿ�ע����
				 MediaManager.getInstance().playSound(MediaManager.call2);
			}else if(type ==2 || type ==4){
				//���ż�ע����
				 MediaManager.getInstance().playSound(MediaManager.raise);
			}
		}

	}
	
	public void destory(){
		Log.i("test19", "GameXizZhuViewManager destroy");
	   for(int i=0; i< 9;i++){
		   if(xiaZhuChouMaViews[i] !=null){
		    xiaZhuChouMaViews[i].destory();
		    xiaZhuChouMaViews[i]=null;
		   }
	   }
	  
		context =null;
		// ��ǰ�Ƿ�������ע
        if(list != null){
        	list.clear();
        	list =null;
        }
		
	}

}
