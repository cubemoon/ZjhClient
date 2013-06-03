package com.dozengame;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dozengame.gameview.GameFaPaiManager;
import com.dozengame.gameview.GameGolbalPokeTypeView;
import com.dozengame.gameview.GameOtherPokeViewManager;
import com.dozengame.gameview.GlobalPokeViewManager;
import com.dozengame.gameview.SitView;

public class FaiPaiDialog extends Dialog {
	DzpkGameActivity context;
	public FrameLayout frameLayout;
	public SitView sitView;//��λ
	 public FaiPaiDialog(final DzpkGameActivity context,int theme,final PlayerDialog playerDialog) {
			super(context,theme);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  
			this.context =context;
			setContentView(R.layout.gamemain1);
			 final ColorDrawable drawable =new ColorDrawable(0);
			 this.getWindow().setBackgroundDrawable(drawable);
			frameLayout=(FrameLayout)findViewById(R.id.mainLayout);
 
			sitView =new SitView(context);
			frameLayout.addView(sitView);
			 //��ӷ���
			 addFaiPaiManager();
			//���������ҵ���
			 addOtherPokeView();
			//��ӹ�����
			 addGlobalManager();
			//���ȫ������
			 addPokeTypeView();
		 
			this.setOnDismissListener(new OnDismissListener(){
				@Override
				public void onDismiss(DialogInterface dialog) {
					  
					  destroy();
					  if(playerDialog != null){
					     playerDialog.dismiss();
					  }
				  
				}
			});
			 
			this.setOnShowListener(new OnShowListener(){
 
				public void onShow(DialogInterface dialog) {
                    
                     DzpkGameActivityDialog   gameDialog = new DzpkGameActivityDialog(context,R.style.dialog,playerDialog,FaiPaiDialog.this);
            	     gameDialog.show();
 
				}
			});
	 }
	 
	 
		public GameFaPaiManager faPaiManager;
		/**
		 * ��ӷ���
		 */
		private void addFaiPaiManager(){
			faPaiManager =new GameFaPaiManager(this);
		}
		
		public GameOtherPokeViewManager otherPokeViewManager;
		/**
		 * ���������ҵĵ���
		 */
		private void addOtherPokeView(){
			otherPokeViewManager =new GameOtherPokeViewManager(this);
		}
		
		public GlobalPokeViewManager globalPokeManager;
		/**
		 * ��ӹ�����
		 */
		private void addGlobalManager(){
			globalPokeManager =new GlobalPokeViewManager(this);
		}
		public GameGolbalPokeTypeView pokeTypeView;
		/**
		 * ���ȫ������
		 */
		private void addPokeTypeView(){
			pokeTypeView = new GameGolbalPokeTypeView(this.getContext());
			frameLayout.addView(pokeTypeView);
		}
		  public void destroy() {
		    	Log.i("test19", "FaiPaiDialog destroy");
			  if(frameLayout != null){
		    		frameLayout.removeAllViews();
		    	}
		        frameLayout =null;
 
		        if(sitView != null){
		        	sitView.destroy();
				}
				 
		        sitView =null;
				if (faPaiManager != null) {
					faPaiManager.destroy();
				}
				faPaiManager = null;
				
				if (globalPokeManager != null) {
					globalPokeManager.destroy();
				}
				globalPokeManager = null;
				if (pokeTypeView != null) {
					pokeTypeView.destroy();
				}
				pokeTypeView = null;
				 
				if(otherPokeViewManager != null){
					otherPokeViewManager.destory();
				}
				 
				otherPokeViewManager =null;
		 }
		  
//			/**
//			 * ���������¼�
//			 */
//			public boolean onKeyDown(int keyCode, KeyEvent event) {
//			       if (keyCode == KeyEvent.KEYCODE_BACK  ) {
//	                     dismiss();
//	                     Intent it =new Intent();
//	        			 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//	        			 it.setClass(this.getContext(), DzpkGameRoomActivity.class);
//	        			 this.getContext().startActivity(it);
//			          return false;
//			       }
//
//			  return super.onKeyDown(keyCode, event);
//			} 
}
