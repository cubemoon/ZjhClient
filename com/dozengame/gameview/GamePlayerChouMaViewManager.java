package com.dozengame.gameview;

 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

 
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
 
/**
 * �����ע��Ϣ
 * @author hewengao
 *
 */
public class GamePlayerChouMaViewManager{

	DzpkGameActivityDialog context;
	//��ע���뵽���س��붯��
	private static final int[][][] translateAnimPoint = {
		 {{2,-155},{242,-155},{242,-113},{242,-40},{242,-5},{-258,-5},{-258,-40},{-258,-113},{-258,-155}},
		 {{-135,-155},{105,-155},{105,-113},{105,-40},{105,-5},{-395,-5},{-395,-40},{-395,-113},{-395,-155}},
		 {{139,-155},{379,-155},{379,-113},{379,-40},{379,-5},{-121,-5},{-121,-40},{-121,-113},{-121,-155}},
		 
		 {{-250,-80},{-10,-80},{-10,-38},{-10,35},{-10,70},{-510,70},{-510,35},{-510,-38},{-510,-80}},
		
		 {{250,-80},{490,-80},{490,-38},{490,35},{490,70},{-10,70},{-10,35},{-10,-38},{-10,-80}},
		 {{-135,-31},{105,-31},{105,11},{105,84},{105,119},{-395,119},{-395,84},{-395,11},{-395,-31}},
		 {{2,-31},{242,-31},{242,11},{242,84},{242,119},{-258,119},{-258,84},{-258,11},{-258,-31}},
		 {{139,-31},{379,-31},{379,11},{379,84},{379,119},{-121,119},{-121,84},{-121,11},{-121,-31}}
		};
	
	static final GamePlayerChouMaView [] playerChouMaViews= new GamePlayerChouMaView[9];
 
    
  //���ڿ�����ע�����Ƿ���ִ�����
   public final static HashMap<Integer,Boolean> animListIndex = new  HashMap<Integer,Boolean>();
    //��ǰ��������
   public static int currentMainIndex =0;
    
	public GamePlayerChouMaViewManager(DzpkGameActivityDialog context) {
		 
		this.context=context;
		initPlayerChouMaViews();
	}
	
	private void initPlayerChouMaViews(){
		for(int i=0; i<9;i++){
			playerChouMaViews[i]=new GamePlayerChouMaView(context.getContext(),i);
			context.frameLayout.addView(playerChouMaViews[i]);
		}
	}
	
	/**
	 * ���ÿɼ���
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		playerChouMaViews[index].setVisibility(visibility);
	}
	/**
	 * ������ע����
	 * @param index
	 * @param money
	 */
	public void setPlayerXiaZhuChouMa(HashMap data){
		 
		int siteNo = (Byte) data.get("siteno");// ��λ��
		int betGold = (Integer) data.get("betgold");// �ܵ���ע��
		int currbet = (Integer) data.get("currbet");// ������ע��
		byte sex = (Byte) data.get("sex"); // 1=�� 0=Ů
		byte type = (Byte) data.get("type");// 1=��ע 2=��ע 3=��ע 4=��� 5=��ע
		int index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
		playerChouMaViews[index].setMoney(currbet);
		GameApplication.getDzpkGame().playerChouMaViewManager.setOtherViewVisibility(index, View.VISIBLE);		
		//HashMap player =(HashMap)GameApplication.getDzpkGame().gameDataManager.sitDownUsers.get(siteNo);
		//int handgold =(Integer)player.get("handgold");
		//Log.i("test4", "handgold: "+handgold+" currbet:  "+currbet+"  type: "+type);
		if(type ==4){
	    	//ALLIn����
	    	GameApplication.getDzpkGame().allInViewManager.startAnim(index);
	    }
	}
  
   /**
    * ִ����ע���붯��
    */
    public   void execXiaZhuChouMaAnim(){
    	animListIndex.clear();
    	for(int i=0; i<9;i++){
    		if(playerChouMaViews[i].getVisibility() == View.VISIBLE){
    			animListIndex.put(i, false);
    		    translateAnimXiaZhu(playerChouMaViews[i],i);
    		} 
    	}
    	//��ʾû�ж�����Ҫִ��
    	if(animListIndex.size()==0){
    		//�ж���Ϸ�Ƿ��ѽ���
    		if(GameApplication.getDzpkGame().gameDataManager.GameOver){
    			GameApplication.getDzpkGame().gameDataManager.GameOver =false;
				GameApplication.getDzpkGame().otherPokeViewManager.draw();
			}
    	}
    }
    /**
     * ��λ��ע���뵽���������ͼ����
     * @param cmvStart
     * @param index
     * @param currGold
     */
    private   void translateAnimXiaZhu(final GamePlayerChouMaView xiaZhuView,final int index){
		PondView pondView =GameApplication.getDzpkGame().pondViewManager.getPondView(0);
    	int x=translateAnimPoint[currentMainIndex][index][0];
		int y=translateAnimPoint[currentMainIndex][index][1];
		TranslateAnimation mTranslateAnimation =   new TranslateAnimation(0, x, 0, y);
		//����ʱ�����ʱ��Ϊ500 ����=0.5��
		mTranslateAnimation.setDuration(500);
		mTranslateAnimation.setInterpolator(new LinearInterpolator());//����
		mTranslateAnimation.setAnimationListener(new MyAnimationListener(xiaZhuView,pondView,true,index));
		mTranslateAnimation.setStartOffset(100);
		xiaZhuView.startAnimation(mTranslateAnimation);	
    }
    
    /**
     * ���������¼�
     * @author hewengao
     *
     */
    private class MyAnimationListener implements AnimationListener{
    	 GamePlayerChouMaView view1;//��ʼ��ͼ
    	 PondView view2;//������ͼ
    	boolean isXiaZhu=false;//true:��ʾ��ע���� false:��ʼ����
    	int index =-1;//ĳ����λ������
    	public MyAnimationListener(GamePlayerChouMaView view1,PondView view2,boolean isXiaZhu,int index){
    		this.view1=view1;
    		this.view2=view2;
    		this.isXiaZhu=isXiaZhu;
    		this.index=index;
    	}
    	 
		@Override
		public void onAnimationEnd(Animation animation) {
			view1.setVisibility(View.INVISIBLE);
			animListIndex.put(index, true);
			if(validAllAnimIsEnd()){
				//��������ʳ���Ϣ
				executeDeskPollInfo();
				//�ж���Ϸ�Ƿ��ѽ���
				if(GameApplication.getDzpkGame().gameDataManager.GameOver){
					GameApplication.getDzpkGame().otherPokeViewManager.draw();
				}
			}
		 
		}
 
		public void onAnimationRepeat(Animation animation) {
			 
		}
 
		public void onAnimationStart(Animation animation) {
			 
		}
    	
    }
    /**
     * ���ж����Ƿ���ִ�����
     * @return
     */
    public boolean validAllAnimIsEnd(){
    	Set<Entry<Integer, Boolean>>  set=animListIndex.entrySet();
    	Iterator<Entry<Integer, Boolean>>  it=set.iterator();
    	while(it.hasNext()){
    		if(it.next().getValue() == false){
    		     return false;
    		}
    	}
    	animListIndex.clear();
    	return true;
    }
    //����ʳ���Ϣ
    private   ArrayList  deskPolls;
    /**
     * �յ��ʳ���Ϣ
     * @param data
     */
    public  void recvDeskPollInfo(ArrayList  data){
    	Log.i("test18","�յ��ʳ���Ϣ  recvDeskPollInfo");
    	deskPolls=data;
    	//������ע��������ִ�����
    	//if(validAllAnimIsEnd()){
    	//	executeDeskPollInfo();
    	//}
    }
    /**
     * ����ʳ���Ϣ
     */
   private  void   executeDeskPollInfo(){
		if(deskPolls !=null){
			ArrayList polls = new ArrayList();
			polls.addAll(deskPolls);
			deskPolls.clear();
			int size = polls.size();
			PondView pondView=null;
		   
			for (int i = 0; i < size; i++) {
			 
			  pondView=GameApplication.getDzpkGame().pondViewManager.getPondView(i);
			 // pondView.setVisibility(View.VISIBLE);
			  pondView.setMoney((Integer)polls.get(i));
			 
			}
			polls.clear();
            currentMainIndex = size-1;
		}
   }

	public void setChouMaByIndex(int index, int currbet) {
		
		 playerChouMaViews[index].setMoney(currbet);
		// if(currbet >0){
			// playerChouMaViews[index].setVisibility(View.VISIBLE);
		// }
	}


	ArrayList<Integer> currnetVisible =new ArrayList<Integer>();
	/**
	 * ���浱ǰ״̬
	 */
	public void saveCurrentState() {
		currnetVisible.clear();
		for(int i =0; i< 9;i++){
			if(playerChouMaViews[i].getVisibility() == View.VISIBLE){
				currnetVisible.add(i);
				playerChouMaViews[i].setVisibility(View.INVISIBLE);
			}
		}
	}
	/**
	 *
	 * �ָ���ǰ״̬
	 *
	 */
	public void backCurrentState() {
		int size =currnetVisible.size();
		int index =0;
		int money =0;
		for(int i=0; i< size;i++){
			index=currnetVisible.get(i);
			money=playerChouMaViews[index].getMoney();
			index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndexBak(currnetVisible.get(i));
		    playerChouMaViews[index].setMoney(money);
		    playerChouMaViews[index].setVisibility(View.VISIBLE);
		}
		currnetVisible.clear();
	}
	public void destory(){
		Log.i("test19", "GamePlayerChouMaView destroy");
		for(int i=0; i< 9;i++){
			if(playerChouMaViews[i] != null){
			playerChouMaViews[i].destory();
			}
			playerChouMaViews[i]=null;
		}
		if(deskPolls != null){
			deskPolls.clear();
		}
		deskPolls =null;
		context =null; 
	}
	public void reset(){
		for(int i=0; i< 9;i++){
			playerChouMaViews[i].setVisibility(View.INVISIBLE);
			//playerChouMaViews[i]=null;
		}
	}
}
