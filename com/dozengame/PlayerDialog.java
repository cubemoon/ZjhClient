package com.dozengame;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dozengame.gameview.GameMyPokeView;
import com.dozengame.gameview.GamePlayerViewManager;
import com.dozengame.gameview.GameZjViewManager;
import com.dozengame.gameview.PokeBackViewManager;
import com.dozengame.util.GameUtil;
/**
 * �����Ϣ����
 * @author Administrator
 *
 */
public class PlayerDialog extends Dialog {

	DzpkGameActivity context;
	public FrameLayout frameLayout;
	 public PlayerDialog(final DzpkGameActivity context,int theme) {
			super(context,theme);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  
			this.context =context;
			setContentView(R.layout.gamemain1);
			 final ColorDrawable drawable =new ColorDrawable(0);
			 this.getWindow().setBackgroundDrawable(drawable);
			frameLayout=(FrameLayout)findViewById(R.id.mainLayout);
			//���ׯ��
			addZjViewManager();
			//�������С����
			addPokeBackViewManager();
			//��������ͼ
			addPlayerViewManager();
			//����ҵ���
			addMyPoke();
			this.setOnDismissListener(new OnDismissListener(){
				@Override
				public void onDismiss(DialogInterface dialog) {
				    destroy();
				    if(GameUtil.isNetError == false){
				        context.finish();
				    }else{
				    	GameUtil.isNetError=false;
				    }
				}
			});
			 
			this.setOnShowListener(new OnShowListener(){
 
				public void onShow(DialogInterface dialog) {
                    Log.i("test12", "onShowonShowonShow");
                    FaiPaiDialog   gameDialog = new FaiPaiDialog(context,R.style.dialog,PlayerDialog.this);
            	    gameDialog.show();
 
				}
			});
	 }
	 public GameZjViewManager zjViewManager;
	/**
	 * ���ׯ��
	 */
	private void addZjViewManager(){
		  zjViewManager = new GameZjViewManager(this);
	}
	 public GamePlayerViewManager playerViewManager=null;
	/**
	 * �����Ϸ�����ͼ
	 */
	private void addPlayerViewManager(){
	    playerViewManager=new GamePlayerViewManager(this.getContext());
		frameLayout.addView(playerViewManager);
	}
	 public PokeBackViewManager pokeBackViewManager;
		/**
		 * ������ҵ�С��
		 */
		private void addPokeBackViewManager(){
			pokeBackViewManager = new PokeBackViewManager(this);
		}
		public GameMyPokeView myPoke;
		/**
		 * ����Լ�����
		 */
		private void addMyPoke(){
			myPoke =new GameMyPokeView(this.getContext());
			frameLayout.addView(myPoke);
		}
	
		  public void destroy() {
				Log.i("test19", "PlayerDialog destroy");
			  if(frameLayout != null){
		    		frameLayout.removeAllViews();
		    	}
		        frameLayout =null;
				 
				if (pokeBackViewManager != null) {
					pokeBackViewManager.destroy();
				}
				pokeBackViewManager = null;
 
				if (myPoke != null) {
					myPoke.destroy();
				}
				myPoke = null;
		        if(zjViewManager != null){
	        	 zjViewManager.destroy();
		        }
		        zjViewManager =null;
				if (playerViewManager != null) {
					playerViewManager.destroy();
				}
				playerViewManager =null;
				
				pokeBackViewManager = null;
				playerViewManager =null;
				myPoke = null;
				 
		 }
		  GameDataManager gameDataManager;
		  public void sendMsg(Message msg,GameDataManager gameDataManager){
			  this.gameDataManager =gameDataManager;
			  handler2.sendMessage(msg);
		  }
		
		  Handler handler2 = new Handler() {
				public void handleMessage(Message msg) {// ����һ��Handler�����ڴ��������߳���UI��ͨѶ
					if (!Thread.currentThread().isInterrupted()) {
						 
						 if(gameDataManager==null){
							 return;
						 }
						 switch(msg.what){
							case DzpkGameActivityDialog.recvPlayerInfo:
								gameDataManager.setPlayerInfoState((ArrayList)msg.obj); 
								break;
						 }
					}
				}};  
				
//				/**
//				 * ���������¼�
//				 */
//				public boolean onKeyDown(int keyCode, KeyEvent event) {
//				       if (keyCode == KeyEvent.KEYCODE_BACK  ) {
//		                     dismiss();
//		                  
//				          return false;
//				       }
// 
//				  return super.onKeyDown(keyCode, event);
//				} 
				 
}
