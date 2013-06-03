package com.dozengame.gameview;

 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.dozengame.FaiPaiDialog;
import com.dozengame.GameApplication;
import com.dozengame.net.pojo.DJieSuan;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;
/**
 * ������ҵ�����Ϣ
 * @author hewengao
 *
 */
public class GameOtherPokeViewManager{
    static final String tag ="com.dozengame.gameview.GameOtherPokeViewManager";
    FaiPaiDialog context;
	static final  GameOtherPokeView [] otherPokeViews= new GameOtherPokeView[9];
	Bitmap backPoke=null;
	public GameOtherPokeViewManager(FaiPaiDialog context) {
		 
		this.context=context;
		initOtherPokeViews();
	}
	
	private void initOtherPokeViews(){
		 backPoke=GameBitMap.getPokeBitMap(52);
		for(int i=0; i<9;i++){
			otherPokeViews[i]=new GameOtherPokeView(context.getContext(),i,backPoke);
			context.frameLayout.addView(otherPokeViews[i]);
			//break;
		}
	}
	/**
	 * �����ƵĿɼ���
	 * @param index
	 * @param visibility
	 */
	public void setOtherViewVisibility(int index,int visibility){
		otherPokeViews[index].setVisibility(visibility);
	}
	 
    /**
     * ������ҵĵ���
     */
	public void draw() {
		count =0;
		// ��;����
		if (DJieSuan.m_iscomplete == 0) {
			drawAllWinPlayerBestPoke();
			return;
		}
		
		HashMap pw = DJieSuan.m_diPoke;
		if (pw != null) {
			Set set = pw.entrySet();
			Iterator it = set.iterator();
			HashMap hm = null;
			int siteNo;
			int pokes[];
			int index;
			int allcount =set.size();//���Ƹ���
			while (it.hasNext()) {
				Entry entry = (Entry) it.next();
				pokes=(int[]) entry.getValue();
				siteNo=(Integer) entry.getKey();
				index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
				if(index ==0 && GameApplication.getDzpkGame().playerViewManager.mySite){
					//endPlayerPokeView.put(siteNo,null);
					fanPaiEnd(allcount);
					continue;
				}
				
				//С���Ʋ��ɼ�
				GameApplication.getDzpkGame().pokeBackViewManager.setOtherViewVisibility(index, View.INVISIBLE);
				//��ʾ������ҵ���
				otherPokeViews[index].setPokes(pokes);
				otherPokeViews[index].startAnim(this,allcount);
				//setOtherViewVisibility(index,View.VISIBLE);
				//GamePokeView[] gpv=drawPoke(siteNo,pokes);
				//endPlayerPokeView.put(siteNo,gpv);
			}
			//����
			//biPai();
		}
	}
	/**
	 * ����
	 */
	private void biPai(){
		Log.i("test15", "biPaibiPaibiPaibiPai");
		//�������м�ֵ�����ҵ�����
		GameApplication.getDzpkGame().playerViewManager.drawPlayerPokeType();
		//���λ�������Ӯ�ҵ������
		drawAllWinPlayerBestPoke();
	}
	int count=0;
	Object lock = new Object();
	public  void fanPaiEnd(int allcount){
		
		synchronized(lock){
			count++;
			if(count == allcount){
				biPai();
			}else{
				Log.i("test15", "biPaibiPaibiPaibiPai count: "+count+" allcount: "+allcount);
			}
		}
	}
	
	/**
	 * ���λ�������Ӯ�ҵ������
	 */
	public  void drawAllWinPlayerBestPoke() {
	    
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			public int executeTask() throws Exception {
				Thread.sleep(200);
				handler.sendEmptyMessage(0);
				return 0;
			}
			
		});
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			drawWinPlayerBestPoke();
		}
	};
	
	private void drawWinPlayerBestPoke(){
	  try{
		ArrayList winList = DJieSuan.m_winsiteList;// ����Ӯ��
		if (winList != null && !winList.isEmpty()) {
			int siteNo = (Integer)winList.remove(0);
				// ��;����
				if (DJieSuan.m_iscomplete != 0) {
					//Ӯ�������
					drawWinPlayerBestPoke(siteNo);
				}
				//Ӯ�Ҳʳض���
				drawEndPlayerPondAnim(siteNo);
				
		}else{
			//��������ϵ���Ϣ
			GameApplication.getDzpkGame().reset();
		}
	  }catch(Exception e){
		 
		  e.printStackTrace();
	  }
	}
	
	/**
	 * ���Ƶ�ǰӮ�������
	 * @param siteNo
	 */
	private void drawWinPlayerBestPoke(int siteNo){
	
		//��ǰӮ�ҵ������
		ArrayList pokes = (ArrayList) DJieSuan.m_bestPoke.get(siteNo);
		HashMap pw = DJieSuan.m_diPoke;
		//��ǰӮ�ҵĵ���ֵ
		int[] diPoke=(int[])pw.get(siteNo);
		//GameApplication.getDzpkGame().globalPokeManager.printPoke();
		 
		if(pokes != null ){
			int size =pokes.size();
			int poke=0;
			//��ʼ�����й�����״̬Ϊȫä
			GameApplication.getDzpkGame().globalPokeManager.initState(0);
			
			//GamePokeView[] gpv=(GamePokeView[])endPlayerPokeView.get(siteNo);
			//�õ���ҵ�����
			int index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
			//����������ҵĵ���Ϊȫä
			for(int i = 0; i < 9; i++){
			   if(index == i)continue;
			   if(i==0 &&  GameApplication.getDzpkGame().playerViewManager.mySite){
				   GameApplication.getDzpkGame().myPoke.setState(0, 0);
				   GameApplication.getDzpkGame().myPoke.setState(1, 0);
				   GameApplication.getDzpkGame().myPoke.draw();
			   }else{
				   if(otherPokeViews[i].getVisibility() == View.VISIBLE){
					   otherPokeViews[i].setState(0,0);
					   otherPokeViews[i].setState(1,0);
					   otherPokeViews[i].draw();
			      } 
			   }
			}
			if(index == 0 && GameApplication.getDzpkGame().playerViewManager.mySite){
			    GameApplication.getDzpkGame().myPoke.setState(0,0);
			    GameApplication.getDzpkGame().myPoke.setState(1,0);
			    GameApplication.getDzpkGame().myPoke.draw();
			}else{
				otherPokeViews[index].setState(0, 0);
				otherPokeViews[index].setState(1, 0);
			}
			for(int i = 0; i < size;i++){
				poke=(Byte)pokes.get(i);
				 
				if(diPoke[0]==poke){
					if(index ==0 && GameApplication.getDzpkGame().playerViewManager.mySite){
					    GameApplication.getDzpkGame().myPoke.setState(0,1);
					    GameApplication.getDzpkGame().myPoke.draw();
					}else{
						otherPokeViews[index].setState(0, 1);
					} 
					continue;
				}
				if(diPoke[1]==poke){
					if(index ==0 && GameApplication.getDzpkGame().playerViewManager.mySite){
						  GameApplication.getDzpkGame().myPoke.setState(1,1);
						  GameApplication.getDzpkGame().myPoke.draw();
						}else{
							otherPokeViews[index].setState(1, 1);
						} 
					continue;
				}

				//���ù���������Ѹ��ϵ���Ϣ
				GameApplication.getDzpkGame().globalPokeManager.setBestPoke(poke);
 
			}
 		    //Ӯ�ҵ��ƶ���
			if(index ==0 && GameApplication.getDzpkGame().playerViewManager.mySite){
				//GameApplication.getDzpkGame().myPoke.draw();
			}else{
				otherPokeViews[index].draw();
			}
			//�����ƶ���
			GameApplication.getDzpkGame().globalPokeManager.draw();
			//���ƹ���������
			String pokeWeight=(String)DJieSuan.m_winPokeWeight.get(siteNo);
			 
			if(pokeWeight.length()>0){
				int pokeType=Integer.valueOf(pokeWeight.substring(0,1));
				GameApplication.getDzpkGame().pokeTypeView.setPokeType(pokeType);
				//GameApplication.getDzpkGame().pokeTypeView.setVisibility(View.VISIBLE);
			}
			
//			if(index ==0 && GameApplication.getDzpkGame().playerViewManager.myPos >0){
//				 GameApplication.getDzpkGame().myWinView.startWinAnim();
//			}
		}
	}
	 /**
     * 
     * �������Ӯ�õĲʳض���
     * @param siteNo Ӯ����λ
     */
    public void drawEndPlayerPondAnim(int siteNo){
    	 
    	    
    		HashMap map=DJieSuan.m_pondList;
		    ArrayList poolList =(ArrayList)map.get(siteNo);
			int tempSize=poolList.size();
			//�õ���λ��Ӧ���������
            int playerIndex = GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
            //Ӯ��״̬
            GameApplication.getDzpkGame().playerViewManager.setPlayerState(playerIndex, 15);
            if(playerIndex ==0 && GameApplication.getDzpkGame().playerViewManager.mySite){
            	 //������
            	GameApplication.getDzpkGame().startVibrate(500);
            	
            	 GameApplication.getDzpkGame().myWinView.startWinAnim();
			}
            HashMap temp;
            for(int j=0;j<tempSize;j++){
            	temp=(HashMap)poolList.get(j);
				int poolIndex=(Integer)temp.get("poolindex");//���ػ�ʳ�����
				int poolGold=(Integer)temp.get("poolgold");
			    GameApplication.getDzpkGame().pondViewManager1.translateAnimStart(poolIndex-1,playerIndex,poolGold,tempSize);
			}
    		 
    	 
    }
    /**
     * ����
     */
	public void reset() {
	 
		for(int i =0; i< 9;i++){
			otherPokeViews[i].setVisibility(View.INVISIBLE);
			otherPokeViews[i].setState(0, 1);
			otherPokeViews[i].setState(1, 1);
		}
	}
	/**
	 * ���浱ǰ״̬
	 */
	 ArrayList<Integer> currnetVisible =new ArrayList<Integer>();
	public void saveCurrentState() {
		currnetVisible.clear();
		for(int i =0; i< 9;i++){
			if(otherPokeViews[i].getVisibility() == View.VISIBLE){
				currnetVisible.add(i);
				otherPokeViews[i].setVisibility(View.INVISIBLE);
			}
		}
	}
	/**
	 * 
	 * �ָ���ǰ״̬
	 * @param isStand
	 */
	public void backCurrentState() {
		int size = currnetVisible.size();
		int index = 0;
		for (int i = 0; i < size; i++) {
			index = GameApplication.getDzpkGame().playerViewManager.getPlayerIndexBak(currnetVisible.get(i));
			otherPokeViews[index].setVisibility(View.VISIBLE);
		}
		currnetVisible.clear();
	}
	public void destory(){
		Log.i("test19", "GameOtherPokeViewManager destroy");
		  context =null;
		  for(int i=0; i<9;i++){
			  if(otherPokeViews[i] != null){
			  otherPokeViews[i].destory();
			  }
			  otherPokeViews[i] =null;
		  }
		  currnetVisible=null;
		  GameUtil.recycle(backPoke);
		  backPoke =null;
	}
}
