package com.dozengame.gameview;

import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.FaiPaiDialog;
import com.dozengame.GameApplication;
import com.dozengame.music.MediaManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
/**
 * �����ƹ���
 * @author hewengao
 *
 */
public class GlobalPokeViewManager {
	static final GlobalPokeView [] gloBalViews= new GlobalPokeView[5];
	//static final GameGlobalBackPokeView []globalBackViews= new GameGlobalBackPokeView[5];
	FaiPaiDialog context;
    Bitmap bitmapPokeBack=null;//��������
	public GlobalPokeViewManager(FaiPaiDialog context) {
		 
		this.context=context;
		bitmapPokeBack=GameBitMap.getPokeBitMap(52);
		initGlobalViews();
	}
	
	private void initGlobalViews(){
		for(int i=0; i<5;i++){
			gloBalViews[i]=new GlobalPokeView(context.getContext(),i,bitmapPokeBack);
			context.frameLayout.addView(gloBalViews[i]);
			//globalBackViews[i]=new GameGlobalBackPokeView(context,i,bitmapPokeBack);
			//context.frameLayout.addView(globalBackViews[i]);
		}
		
	}
	/**
	 * ���ÿɼ���
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		gloBalViews[index].setVisibility(visibility);
	}
	/**
	 * ������ֵ
	 * @param index
	 * @param visibility
	 */
	public void setPokeByIndex(int index,int poke){
		gloBalViews[index].setPoke(poke);
		gloBalViews[index].setVisibility(View.VISIBLE);
	}
	/**
	 * �����Ƶ�״̬
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewState(int index,int state){
		gloBalViews[index].setState(state);
	}
	int pokeSize =0;
	public void setPokeSize(int pokeSize) {
		this.pokeSize = pokeSize;
	}

	/**
	 * ����������
	 * @param pokes
	 */
	public void setDeskPokes(List pokes){
		 
		if(pokes ==null)return;
		int size  =pokes.size();
		int poke;
		switch(size){
		case 3:
			  MediaManager.getInstance().playSound(MediaManager.flip_a);
			  for(int i=0; i<3;i++){
				  poke= (Byte)pokes.get(i);
				  gloBalViews[i].setPoke(poke);
				 // gloBalViews[i].setVisibility(View.VISIBLE);
				  startAnim(i);
			  }
			  pokeSize=3;
			  
			break;
		case 4:
			 MediaManager.getInstance().playSound(MediaManager.flip_b);
			 if(pokeSize ==0){
				  for(int i=0; i<4;i++){
					  poke= (Byte)pokes.get(i);
					  gloBalViews[i].setPoke(poke);
					 // gloBalViews[i].setVisibility(View.VISIBLE);
					  startAnim(i);
				  }
			  }else if(pokeSize ==3){
				  poke= (Byte)pokes.get(3);
				  gloBalViews[3].setPoke(poke);
				  //gloBalViews[3].setVisibility(View.VISIBLE);
				  startAnim(3); 
			  }
			  pokeSize=4;
			break;
		case 5:
			 MediaManager.getInstance().playSound(MediaManager.flip_c);
			  if(pokeSize ==0){
				  for(int i=0; i<5;i++){
					  poke= (Byte)pokes.get(i);
					  gloBalViews[i].setPoke(poke);
					  // gloBalViews[i].setVisibility(View.VISIBLE);
					  startAnim(i);
				  }
			  }else if(pokeSize ==3){
				  for(int i=3; i<5;i++){
					  poke= (Byte)pokes.get(i);
					  gloBalViews[i].setPoke(poke);
					 // gloBalViews[i].setVisibility(View.VISIBLE);
					  startAnim(i);
				  }
			  }else if(pokeSize ==4){
				  poke= (Byte)pokes.get(4);
				  gloBalViews[4].setPoke(poke);
				  //gloBalViews[4].setVisibility(View.VISIBLE);
				  startAnim(4);
			  }
			  pokeSize=5;
			break;
		}
		 Log.i("test16", "������");
		 GameApplication.getDzpkGame().toastPaiXingView.show();
	}
	 /**
     * �������й����Ƶ�״̬
     */
	public void initState(int state) {
		
		for(int j=0;j<5;j++){
 		   gloBalViews[j].setState(state);
		}
	}
	/**
	 * ��������������Ѹ��ϵ���
	 * @param poke
	 */
	public void setBestPoke(int poke){
 
		for(int j=0;j<5;j++){
 		    if(gloBalViews[j].equalsPoke(poke)){
 		    	break;
 		    } 
		}
	}
    /**
     * �ػ����й�����
     */
	public void draw() {
		
		for(int j=0;j<5;j++){
 		   gloBalViews[j].draw(); 
		}
	}
	
 
	/**
	 * ����
	 */
	public void reset(){
		pokeSize =0;
		for(int i = 0; i < 5;i++){
			gloBalViews[i].setVisibility(View.INVISIBLE);
			gloBalViews[i].setState(1);
		}
	}
	/**
	 * ���÷�ת����
	 * @param index
	 */
	private void startAnim(final int index){
		 
		 gloBalViews[index].startAnim();
	}
	
//	/**
//	 * ���÷�ת����
//	 * @param index
//	 */
//	private void startAnim(final int index){
//		 
//		applyRotation(globalBackViews[index],300,0,90,new AnimationListener(){
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				 gloBalViews[index].setVisibility(View.VISIBLE);
//				 globalBackViews[index].setVisibility(View.INVISIBLE);
//			}
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//			}
//			@Override
//			public void onAnimationStart(Animation animation) {
//			}
//			
//		});
//	}
//	/**
//	 * 
//	 * @param view
//	 * @param durationTime
//	 * @param start
//	 * @param end
//	 * @param animListener
//	 */
//	private void applyRotation(final GameGlobalBackPokeView view,long durationTime,float start, float end,AnimationListener animListener) {
//		view.setVisibility(View.VISIBLE);
//		// �������ĵ�
//		final float centerX = view.getCenterX();
//		final float centerY = view.getCenterY();
//		final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
//				centerX, centerY, 310.0f, true);
//		rotation.setDuration(durationTime);
//		rotation.setFillAfter(true);
//		rotation.setInterpolator(new AccelerateInterpolator());
//		rotation.setAnimationListener(animListener);
//		view.startAnimation(rotation);
//	 
//	}

	public void destroy() {
		// TODO Auto-generated method stub
		Log.i("test19", "GlobalPokeViewManager destroy");
		for(int i=0; i<5;i++){
			if(gloBalViews[i] != null){
			 gloBalViews[i].destroy();
			}
			gloBalViews[i]=null;
//			if(globalBackViews[i] != null){
//			 globalBackViews[i].destroy();
//			}
//			globalBackViews[i] =null;
		}
 
		context =null;
		GameUtil.recycle(bitmapPokeBack);
		bitmapPokeBack =null;
	}
}
