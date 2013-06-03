package com.dozengame.gameview;

 
import java.util.ArrayList;

import android.util.Log;
import android.view.View;

import com.dozengame.DzpkGameActivityDialog;
/**
 * �ʳ���Ϣ
 * @author hewengao
 *
 */
public class PondViewManager{

 
	DzpkGameActivityDialog context;
	PondView [] pondViews= new PondView[9];
	public PondViewManager(DzpkGameActivityDialog context) {
		 
		this.context=context;
		initPondViews();
	}
	
	private void initPondViews(){
		for(int i=0; i<8;i++){
			pondViews[i]=new PondView(context.getContext(),i);
			context.frameLayout.addView(pondViews[i]);
		}
	}
	public PondView getPondView(int index){
		return pondViews[index];
	}
	/**
	 * ���ÿɼ���
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		pondViews[index].setVisibility(visibility);
	}
	/**
	 * ����ָ���ʳس���
	 * @param index
	 * @param money
	 */
	public void setPondChouMa(int index,int money){
		pondViews[index].setMoney(money);
	}
	/**
	 * ���浱ǰִ�ж���������
	 */
	public static ArrayList<Integer> animList = new ArrayList<Integer>();
	/**
	 * @param index
	 * @param playerIndex
	 * @param gold
	 * @param pondCount
	 */
	  public void translateAnimStart(int index,int playerIndex,int gold,int pondCount){
		  
		  if(pondViews[index] !=null){
			  animList.add(index);
			  pondViews[index].setPondMoney(gold);
			  pondViews[index].translateAnimStart(playerIndex,pondCount);
		  }
	  }
	  /**
	   * ��֤��ǰִ�еĶ����Ƿ���ִ�����
	   * @return
	   */
	  public boolean validAllAnimEnd(){
		  if(!animList.isEmpty()){
			  int size = animList.size();
			  int i =0;
			  for(i=0; i< size;i++){
				  if(!pondViews[animList.get(i)].animEnd){
					  break;
				  }
			  }
			  if(i == size){
				  //��ʾ��ִ�����
				  animList.clear();
				  return true;
			  }
		  }
		  return false;
	  }
	  
    /**
     * ����
     */
	public void reset() {
		for(int i=0; i<8;i++){
			setOtherViewVisibility(i,View.INVISIBLE);
		}
	}
	 
	public void destory(){
		Log.i("test19", "PondViewManager destroy");
		for(int i=0;i<8;i++){
			if(pondViews[i] != null){
			 pondViews[i].destory();
			}
			pondViews[i] =null;
		}
 
		context=null;
		
	}
}
