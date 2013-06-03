package com.dozengame;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dozengame.util.GameUtil;
 

/**
 * ��Ϸ����
 * 
 * @author hewengao
 * 
 */
public class GameDataManager {
	
	final String tag = "com.dozengame.GameView";
	// ״̬
	public int state = 0;
	// ���������������Ϣ
	public HashMap deskUsers = null;
	// ��ҵ�״̬
	public ArrayList playerInfoState = null;
	// ���µ����
	public HashMap sitDownUsers = new HashMap();
	// �յ�����
	public HashMap recvFaPai = null;
	// �ҵ���λ
	public int mySiteNo = -1;
	// �ҵ���
	public int[] myPoke = new int[2];
	public ArrayList pokes;// ������

	public DzpkGameActivityDialog dzpkGame = null;

	/**
	 * ���캯��
	 * 
	 * @param dzpkGame
	 */
	public GameDataManager(DzpkGameActivityDialog dzpkGame) {
		this.dzpkGame = dzpkGame;
	}

	/**
	 * �������ӵ������Ϣ
	 * 
	 * @param deskUsers
	 */
	public void setDeskUsers(HashMap deskUsers) {
		
		this.deskUsers = deskUsers;
//		int deskChouMa = (Integer) deskUsers.get("betgold");// �������
//		int usergold = (Integer) deskUsers.get("usergold");// ������ҳ�����
//		int playercount = (Byte) deskUsers.get("playercount"); // ��������
//		int watchercount = (Integer) deskUsers.get("watchercount"); // ��ս����
//		HashMap data=new HashMap();
//		data.put("deskno", rd.readInt());
//		data.put("betgold",rd.readInt());	//���������
//		data.put("usergold",rd.readInt());	//������ҳ�����
//		data.put("playercount",rd.readByte());  //��������
//		data.put("watchercount",rd.readInt());   //��ս����
//		List list =new ArrayList();
//		data.put("userlist",list);
//		int n = rd.readByte();		//��������
//		HashMap temp=null;
//		for (int i = 0; i < n; i++){
//			temp=new HashMap();
//			temp.put("state_value",rd.readByte());		//ÿ����λ��״̬ SITE_UI_VALUE = _S{NULL = 0, NOTREADY = 1, READY = 2, PLAYING = 3}
//			temp.put("userid",rd.readInt());		//ID
//			temp.put("nick",rd.readString());	//�ǳ�
//			temp.put("isvip",rd.readByte());		//�Ƿ�VIP���:0���ǣ�1��
//			temp.put("faceurl",rd.readString());	//ͷ��
//			temp.put("gold",rd.readInt());		//���
//			list.add(temp);
//		}

	}
   public int siteNo=-1;
   private boolean hasPlayerState =false;
	/**
	 * �����������״̬
	 * @param sitDownUsers
	 */
	public void setPlayerInfoState(ArrayList playerInfoState) {
		try{
			this.playerInfoState = playerInfoState;
			hasPlayerState = true;
			// �ж��Ƿ��������Լ���Ӧ��λ��,���δ����Ҫ�ȵ�����������º���ܻ���
			if (!(GameApplication.getDzpkGame().playerViewManager.mySite && GameApplication
					.getDzpkGame().playerViewManager.zhunDongValue != 2)) {
				   executePlayerState();
			}
		}catch(Exception e){
			
		}
		 
	}
	/**
	 * �������״̬
	 */
	public void updatePlayerState(boolean isGameOver){
		 dzpkGame.playerViewManager.setPlayerState(playerInfoState,isGameOver);
	}
	/**
	 * ִ�����״̬
	 */
	private void executePlayerState(){
		if(hasPlayerState){
			if (playerInfoState != null) {
					int size = playerInfoState.size();
					HashMap temp = null;
					int state;
			  for (int i = 0; i < size; i++) {
					temp = (HashMap) playerInfoState.get(i);
					//Log.i(tag, "site : " + (Byte) temp.get("site"));// ��λ��
					//Log.i(tag, "state: " + (Byte) temp.get("state"));// ״̬��
					// 0:Ĭ��״̬ 1:�ȴ� 2:δ׼�� 4:�뿪5:�յ����6:�������
					state = (Byte) temp.get("state");
					if (state == 5) {
						// ʣ��ʱ��
						byte timeOut= (Byte) temp.get("timeout");
						 // ��ʱ��
						byte delay =(Byte)temp.get("delay");
						 
						siteNo = (Byte) temp.get("site");
						// ���Ƽ�ʱ��
						dzpkGame.jsqViewManager.setJsqSiteNo(siteNo,timeOut,delay);
						//Log.i("test13","timeOut: "+timeOut+" delay: "+delay+" siteNo: "+siteNo);
						break;
					}
				}
			   updatePlayerState(false);
			}
		}
		hasPlayerState =false;
		siteNo=-1;
		
	}
 
	//�Ƿ���������
	private boolean hasPlayerSit =false;
	//��ǰ���µ����
	ArrayList<HashMap> currentSitPlayer = new ArrayList<HashMap>();
	private ArrayList<Boolean> listHashPalerSit = new ArrayList<Boolean>();
	private boolean isSelfSit=false;
	/**
	 * ��ӻ��滻�����û�
	 * �˴�ÿ����һ�������½������μ������µ�������
	 * @param sitDownUser
	 */
	public void addOrReplaceSitDownUser(HashMap sitDownUser) {
		//����ǰ��ִ����Ҫվ��Ĵ���
		executeStandUpAction2();
		executeStandUpAction();
		if (sitDownUser != null) {
			Integer siteNo = (Integer) sitDownUser.get("siteno");
			//Log.i("test11", "sitDown siteNo: "+siteNo);
			if (siteNo != null) {
				HashMap  user = (HashMap)sitDownUsers.get(siteNo);
				if(user != null){
					int userid1=(Integer)user.get("userid");
					int userid2=(Integer)sitDownUser.get("userid");
					if(userid1 == userid2){
						Log.i("test15", "������ͬһ����: userid1: "+userid1+" userid2: "+userid2);
						sitDownUsers.put(siteNo, sitDownUser);
						return;
					}else{
						Log.i("test15", "���²���ͬһ����: userid1: "+userid1+" userid2: "+userid2);
					}
				}
				 {
					currentSitPlayer.add(sitDownUser);
					sitDownUsers.put(siteNo, sitDownUser);
					listHashPalerSit.add(true);
					Object obj = GameApplication.userInfo.get("user_real_id");
					if(sitDownUser.get("userid").equals(obj)){
						isSelfSit=true;//�Լ�����
					}
					//hasPlayerSit=true;
					 
					if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){
						
						  executeSitDownAction();
						
					}
				} 
			}
		}
	}
	 
	 
	/**
	 * ִ�����¶���
	 */
	public void executeSitDownAction(){
		 //�Լ�����ʱδ�ڽ����п���ִ������,�ڽ���������Ҫ�ȴ��������
		 if(isSelfSit && (GameApplication.jieSuanIng || GameApplication.getDzpkGame().faPaiManager.faiPaiIng))return;
		  isSelfSit =false;
		 if(!listHashPalerSit.isEmpty()){
			   if(listHashPalerSit.remove(0)){
			     // hasPlayerSit =false;
				   if(dzpkGame != null && dzpkGame.playerViewManager !=null){
					  Log.i("test15", "currentSitPlayer: "+currentSitPlayer.size());
			          dzpkGame.playerViewManager.sitDown(currentSitPlayer);
				   }
			   }
			}else{
			   
			}
		 
		 
	 
	}
	

   boolean hasPalerUp =false;
    //��ǰ�Ƴ����
	ArrayList<Integer> currentRemovePlayer = new ArrayList<Integer>();
	public boolean hasPalerUp2 =false;
	ArrayList<Integer> currentRemovePlayer2 = new ArrayList<Integer>();
    
	/**
	 * �յ��û�վ��
	 * 
	 * @param obj
	 */
	public void setRecvStandUp(HashMap data) {

		String nick = (String) data.get("nick");
		String currentuser=  (String) data.get("currentuser");
		//Log.i(tag, "setRecvStandUp " + nick);
		int removeSiteNo = (Integer) data.get("siteno");
		///Log.i("test11", "setRecvStandUp siteNo: "+removeSiteNo);
		int recode = (Integer)data.get("recode");
		//Log.i("test","recode: "+recode);
		HashMap users=(HashMap)sitDownUsers.get(removeSiteNo);
		if(users == null || nick== null || !nick.equals(users.get("nick"))){
			Log.i("test15", "standUp nick: "+nick+" siteNo: "+removeSiteNo+"������");
			return;
		}
		if(recode != 0){
			//����վ��
			hasPalerUp =true;
			currentRemovePlayer.add(removeSiteNo);
		}else{
			hasPalerUp2 =true;
			currentRemovePlayer2.add(removeSiteNo);
		}
		Log.i("test15", "standUp nick: "+nick+" siteNo: "+removeSiteNo+"  currentuser: "+currentuser);
		sitDownUsers.remove(removeSiteNo);
		
		//hasRecvStandUp=true;
		if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
			executeStandUpAction2();
			executeStandUpAction();
		}
	 
	}
	/**
	 * �����վ��
	 * ִ���û�վ����
	 */
	private void executeStandUpAction2(){
			if(hasPalerUp2){
				//Log.i("test", "executeStandUpAction2executeStandUpAction2executeStandUpAction2 size : "+currentRemovePlayer2.size());
				hasPalerUp2 =false;
			    GameApplication.getDzpkGame().playerViewManager.removeSiteNoPlayer(currentRemovePlayer2);
			}
		 
	}
	/**
	 * ����վ��
	 * ִ���û�վ����
	 */
	private void executeStandUpAction(){
			if(hasPalerUp){
				hasPalerUp =false;
			    GameApplication.getDzpkGame().playerViewManager.removeSiteNoPlayer(currentRemovePlayer);
			}
		 
	}
    //���ӵ���λ
	public ArrayList siteList = null;
	private boolean hasFaiPai =false;
	/**
	 * ���÷���
	 * 
	 * @param recvFaPai
	 */
	public void setRecvFaPai(HashMap recvFaPai) {
		this.recvFaPai = recvFaPai;
		 
		if (recvFaPai != null) {
			if(siteList != null)siteList.clear();
		    siteList = (ArrayList) recvFaPai.get("siteList");
			ArrayList pokes = (ArrayList) recvFaPai.get("pokes");			 
			if (pokes != null) {
				int size  = pokes.size();
				for (int i = 0; i < size; i++) {
					//Log.i(tag, "poke: " + pokes.get(i));
					if (i == 2) {
						break;
					}
					myPoke[i] = (Byte) pokes.get(i);
				}
				if(size != 0){
					dzpkGame.myPoke.setPokes(myPoke);
				}
			}
			hasFaiPai =true;
			//�ж��Ƿ��������Լ���Ӧ��λ��,���δ����Ҫ�ȵ�����������º���ܻ���,��������������º�֪ͨ���� 
			if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){
				executeFaiPaiAction();
			}
		}
	}
	public boolean faiPai =false;//�Ƿ�����
	/**
	 * ִ�з��ƶ���
	 */
	private void executeFaiPaiAction(){
		
		if(hasFaiPai){
		    
		   hasFaiPai =false;
		   faiPai =true;
		   dzpkGame.faPaiManager.startFaPaiAnim();
		}
		
	}
	/**
	 * �õ�ׯ����λ�� 
	 * @param index ������ 0
	 * @return
	 */
	public int getSiteNo(int index){
		if(siteList != null ){
			int size =siteList.size();
			if(size>index){
				return (Byte)siteList.get(index);
			}
		}
		return -1;
	}

	/**
	 * �������
	 * 
	 * @param type
	 *            1:��ͨ��� 2���Զ����
	 * @param recvPanel
	 */
	public void setRecvPanel(int type, HashMap data) {
		if (type == 2) {
			byte guo = (Byte) data.get("guo");
			byte guoqi = (Byte) data.get("guoqi");
			byte genrenhe = (Byte) data.get("genrenhe");// ���κ�
			byte gen = (Byte) data.get("gen");// ��
			int gengold = (Integer) data.get("gengold");// ���Ľ��
			GameApplication.getDzpkGame().gameBottomView.addAutoButtonLayout(data);
		} else {
			GameApplication.getDzpkGame().gameBottomView.addButtonLayout(data);
			//state = 1;
			// ������ͨ����ֵ
			//GameApplication.getDzpkGame().consoleView.setPanelData(data);
		}
	}

	/**
	 * ������
	 */
	public void setRecvKickMe() {
		if (mySiteNo != -1) {
			sitDownUsers.remove(mySiteNo);
		}
		mySiteNo = -1;
		cleareMyPoke();

	}
	
    //�Ƿ�������ע
	private boolean hasXiaZhu=false;
	private ArrayList<HashMap> xiaZhuData = new ArrayList<HashMap>();
	/**
	 * ĳ��λ��ע�ɹ�
	 * 
	 * @param obj
	 */
	public void setRecvXiaZhuSucc(HashMap data) {
		if (data != null) {
			hasXiaZhu =true;
		    xiaZhuData.add(data);
		    dzpkGame.jsqViewManager.setIsOperation(true);//�Ѳ���
//			byte siteNo = (Byte) data.get("siteno");// ��λ��
//			int betGold = (Integer) data.get("betgold");// �ܵ���ע��
//			int currbet = (Integer) data.get("currbet");// ������ע��
//			byte sex = (Byte) data.get("sex"); // 1=�� 0=Ů
//			byte type = (Byte) data.get("type");// 1=��ע 2=��ע 3=��ע 4=��� 5=��ע
			if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
				executeXiaZhuAction();
			}
		}
	}
	/**
	 * ִ����ע����
	 */
	public void executeXiaZhuAction(){
		if(GameApplication.getDzpkGame().faPaiManager.faiPaiIng == false){
		 if(hasXiaZhu && !xiaZhuData.isEmpty()){
			 GameApplication.getDzpkGame().xiaZhuViewManager.setPlayerXiaZhuChouMa(xiaZhuData);
			
		 }
		 hasXiaZhu =false;
		}
	 
	}
	/**
	 * �Լ��ĵ��ݶ�
	 * 
	 * @param myTotalGold
	 */
	public void setRecvShowMyTotalBean(Integer myTotalGold) {
		//Log.i(tag, "setRecvShowMyTotalBean myTotalGold : " + myTotalGold);
		if (mySiteNo != -1) {
			HashMap user = (HashMap) sitDownUsers.get(mySiteNo);
			if (user != null) {
				// �������Ͻ��
				user.put("total_gold", myTotalGold);
			}
		}
	}
	private boolean hasRecvDeskPoke=false;
	/**
	 * �����ʾ�������
	 * 
	 * @param obj
	 */
	public void addDeskPoke(ArrayList pokes) {
		this.pokes = pokes;
		hasRecvDeskPoke =true;
		if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
			  //��ǰû����ת��������
			if(!GameApplication.getDzpkGame().xiaZhuViewManager.currentXiaZhu){
			   //��ǰû��������ע
				executeDeskPokeAction();
		   }
			
		}
	}
	/**
	 * ִ���յ������ƵĶ���
	 */
	public void executeDeskPokeAction(){
		if(hasRecvDeskPoke){
		  hasRecvDeskPoke =false;
		  //ִ����ע����
		  GameApplication.getDzpkGame().playerChouMaViewManager.execXiaZhuChouMaAnim();
		  //���ƶ���
		  GameApplication.getDzpkGame().globalPokeManager.setDeskPokes(pokes);
		}
	}

	 
	private boolean hasRecvSiteGold=false;
	int refreshSiteGold;//ˢ�µĽ��
	int refreshSite;//ˢ�µ���λ
	HashMap<Integer,Integer> refreshGold = new HashMap<Integer,Integer>();
	/**
	 * �յ�ĳ����λ�Ľ��ˢ��
	 * 
	 * @param obj
	 */
	public void setRecvRefreshGold(HashMap data) {
         if(data == null)return;
		 refreshSite= (Byte) data.get("siteno");//ˢ�µ���λ
		 refreshSiteGold = (Integer) data.get("gold");//ˢ�µĽ��
		data.clear();
		HashMap user = (HashMap) sitDownUsers.get(refreshSite);
		if (user != null) {
			// �������Ͻ��
			 
			user.put("handgold", refreshSiteGold);
			if(GameOver){
			    refreshGold.put(refreshSite, refreshSiteGold);
			}else{
				hasRecvSiteGold =true;
				if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
					 
					executeRefreshSiteGoldAction();
				}else{
					 
				}
			}
		}else{
			 
		} 
	}
	/**
	 * ����ӮǮ��������
	 * ִ��ˢн��λ��Ҷ���
	 */
	public void executeRefreshSiteGoldAction(int siteNo){
		 Integer gold =refreshGold.remove(siteNo);
		 if(gold != null){
		  GameApplication.getDzpkGame().playerViewManager.setRecvRefreshGold(siteNo,gold);
		 }else{
			 
		 }
	}
	/**
	 * ִ��ˢн��λ��Ҷ���
	 */
	private void executeRefreshSiteGoldAction(){
		  if(hasRecvSiteGold){
		    hasRecvSiteGold =false;
		    GameApplication.getDzpkGame().playerViewManager.setRecvRefreshGold(refreshSite,refreshSiteGold);
	      }
	}
    //������Ϸ�Ƿ����
	public boolean GameOver =false;
	private boolean hasGameOver=false;
	 
	/**
	 * �յ���Ϸ����
	 * 
	 * @param obj
	 */
	public void setRecvGameOver(HashMap data) {
		
	    faiPai =false;
		GameOver =true;
		hasGameOver =true;
	   
		if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
		       //��ǰû����ת��������
			if(!GameApplication.getDzpkGame().xiaZhuViewManager.currentXiaZhu){
			   //��ǰû��������ע
			 executeGameOverAction();
		   }
		} 
		
	 
	 
	
	}
	/**
	 * ִ����Ϸ��������
	 */
	public void executeGameOverAction(){
	
		if(hasGameOver){
			hasGameOver =false;
			if(GameApplication.getDzpkGame().playerViewManager.mySite){
			  GameApplication.getDzpkGame().gameBottomView.addWait();
			}else{
			  GameApplication.getDzpkGame().gameBottomView.removeAllViews();
			}
			//ֹͣ��ʱ��
			dzpkGame.jsqViewManager.setStop();
			//������ע���붯��
			dzpkGame.playerChouMaViewManager.execXiaZhuChouMaAnim();
		}
	}
	boolean hasGiftIcon =false;
	 ArrayList<HashMap> giftIconDataList=new ArrayList<HashMap>();
    public void setGiftIcon(HashMap data) {
		//this.giftIconData =data;
		giftIconDataList.add(data);
    	hasGiftIcon =true;
		DzpkGameActivityDialog dzpkGame = GameApplication.getDzpkGame();
		if(!(dzpkGame.playerViewManager.mySite  && dzpkGame.playerViewManager.zhunDongValue !=2)){	
		       //��ǰû����ת��������
			executeGiftIconAction();
		} 
	}
    /**
	 * ִ�����ﶯ��
	 */
	private void executeGiftIconAction(){
		if(hasGiftIcon){
			hasGiftIcon =false;
			 
			GameApplication.getDzpkGame().playerViewManager.setGiftIcon(giftIconDataList);
		}
	}
    boolean hasPlayGiftIcon =false;
    ArrayList<HashMap> playGiftIconDataList = new ArrayList<HashMap>();
	//HashMap playGiftIconData=null;
    public void setPlayGiftIcon(HashMap data) {
		// TODO Auto-generated method stub
    	 
    	//this.playGiftIconData =data;
    	playGiftIconDataList.add(data);
    	hasPlayGiftIcon =true;
		DzpkGameActivityDialog dzpkGame = GameApplication.getDzpkGame();
		if(!(dzpkGame.playerViewManager.mySite  && dzpkGame.playerViewManager.zhunDongValue !=2)){	
		       //��ǰû����ת��������
			executePlayGiftIconAction();
		} 
	}
    /**
	 * ִ�в������ﶯ��
	 */
	private void executePlayGiftIconAction(){
		if(hasPlayGiftIcon){
			hasPlayGiftIcon =false;
			GameApplication.getDzpkGame().playerViewManager.setPlayGiftIcon(playGiftIconDataList);
		}
	}
	/**
	 * �յ���Ϸ��ʼ
	 */
	public void setRecvGameStart() {
		GameOver =false;
		GameApplication.getDzpkGame().gameBottomView.removeAllViews();
		GameApplication.getDzpkGame().reset();
		GameApplication.getDzpkGame().playerChouMaViewManager.currentMainIndex=0;
		//GameApplication.getDzpkGame().playerViewManager.resetPlayerState();
		cleareMyPoke();
	}
	/**
	 * ����Լ�����
	 */
	private void cleareMyPoke(){
		myPoke[0] = 0;
		myPoke[1] = 0;
	}
	 
	

	/**
	 *��λ��Ҳ���ע
	 * 
	 * @param siteNo
	 */
	public void setRecvBuXiaZhu(byte siteNo) {
		dzpkGame.jsqViewManager.setIsOperation(true);//�Ѳ���
		GameApplication.getDzpkGame().playerViewManager.setPlayerStateBySiteNo(siteNo, 7);
		 
	}
    /**
     * �յ�����ʳ���Ϣ
     * @param obj
     */
	public void setRecvDeskPollInfo(ArrayList data) {
		 
		GameApplication.getDzpkGame().playerChouMaViewManager.recvDeskPollInfo(data);
	}
	 
	/**
	 * �յ��Լ����������
	 * 
	 * @param data
	 */
	public void setRecvBestPokess(HashMap data) {
		String weight=(String)data.get("weight");//����
		ArrayList pokes =  (ArrayList)data.get("pokes");//��ϵ���
	 
	}
	private boolean hasRecvGiveUp =false;
	private int giveUpSiteNo;
	ArrayList<Integer> giveUpList = new ArrayList<Integer>();
	 /**
     * ����
     * @param siteNo ��λ��
     */
	public void setRecvGiveUp(Byte siteNo) {
		hasRecvGiveUp =true;
		giveUpSiteNo=siteNo;
		giveUpList.add(giveUpSiteNo);
		dzpkGame.jsqViewManager.setIsOperation(true);//�Ѳ���
		if(!(GameApplication.getDzpkGame().playerViewManager.mySite  && GameApplication.getDzpkGame().playerViewManager.zhunDongValue !=2)){	
			
			executeGiveUpAction();
			 
		}
	}
	/**
	 * ִ��������ƶ���
	 */
	public void executeGiveUpAction(){
		//�Ƿ��ڷ�����
		if(GameApplication.getDzpkGame().faPaiManager.faiPaiIng ==false){
			if(hasRecvGiveUp){
				while(giveUpList.size()>0){
					dzpkGame.playerViewManager.setPlayerStateBySiteNo(giveUpList.remove(0), 6);
					
				}
				
			}
			hasRecvGiveUp =false;
			giveUpSiteNo =-1;
		}
		
	}
	/**
	 * ���͹������
	 */
	public void sendBuyChouma() {
		// ���͹������ָ��
		//dzpkGame.sendBuyChouma((Integer) deskInfo.get("at_least_gold"),(Integer) deskInfo.get("deskno"), mySiteNo);
		state = 3;
	}
	//�Ƿ���Ҫ�ָ���ʾ
	public boolean isNeedResetDisplay =false;
	/**
	 * �յ��ָ���ʾ
	 * @param obj
	 */
	public void setRecvResetDisplay(HashMap data) {
	
		if(!GameApplication.getDzpkGame().restart){
			if(!GameApplication.getDzpkGame().isFirstIn ){
				//�ǵ�һ�ν�����Ϸ���治��Ҫ���ָ���ʾ
				return;
			}
		}else{
			//Log.i("test2", "setRecvResetDisplaysetRecvResetDisplay restart true");
			GameApplication.getDzpkGame().restart =false;
			//GameApplication.getDzpkGame().reset();
		    
		}
	
		GameApplication.getDzpkGame().isFirstIn=false;
	 
		isNeedResetDisplay =true;
		 
		//ׯ��λ��
		int zjIndex=(Byte)data.get("zhuangsite");
		zjIndex=GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(zjIndex);
		GameApplication.getDzpkGame().zjViewManager.setOtherViewVisibility(zjIndex, View.VISIBLE);
		//������
		ArrayList deskPokes=(ArrayList)data.get("deskPokes");
		if(deskPokes != null){
			int size = deskPokes.size();
			int poke;
			for(int i =0 ;i < size; i++){
				poke=(Byte)deskPokes.get(i);
				//�����������ֵ
				GameApplication.getDzpkGame().globalPokeManager.setPokeByIndex(i, poke);
			}
			//���������Ƹ���
			GameApplication.getDzpkGame().globalPokeManager.setPokeSize(size);
		}
		//�����Ϣ
		ArrayList playerInfo=(ArrayList)data.get("playerInfo");
		if(playerInfo != null){
			int size =playerInfo.size();
			HashMap tempMap;
			int siteNo;
			int index;
			int currbet;
			for (int i = 0; i < size; i++) {
				tempMap = (HashMap)playerInfo.get(i);
//				//�ܵ���ע���
//              int betGold=(Integer)tempMap.get("betgold");
// 				tempMap.get("islose");
// 				tempMap.get("isallin");
				//�����λ
				siteNo=(Byte)tempMap.get("siteno");
				//�õ���λ��Ӧ���������
				index=GameApplication.getDzpkGame().playerViewManager.getPlayerIndex(siteNo);
				 //������ע���
				currbet=(Integer)tempMap.get("currbet");
				GameApplication.getDzpkGame().playerChouMaViewManager.setChouMaByIndex(index, currbet);
				HashMap player=(HashMap)sitDownUsers.get(siteNo);
            	if(player == null)continue;
                if(index ==0){
                	Object obj1=player.get("userid");
                	Object obj2 = GameApplication.userInfo.get("user_real_id");
                	if(obj1 !=null && obj1.equals(obj2)){
                	    //��ʾ��ǰλ�����Լ�
                		//�˴�����Ҫ֪���Լ�����ֵ
                		GameApplication.getDzpkGame().myPoke.setVisibility(View.VISIBLE);
                	}else{
                		GameApplication.getDzpkGame().pokeBackViewManager.setOtherViewVisibility(index, View.VISIBLE);
                	}
                }else{
                	GameApplication.getDzpkGame().pokeBackViewManager.setOtherViewVisibility(index, View.VISIBLE);
                }
 			   
			}
		}
		isNeedResetDisplay =false;
		Log.i("test15","1111 ResetDisplay ResetDisplay ResetDisplay: "+currentSitPlayer.size());
	 
		//ִ�����¶���
	    executeSitDownAction();
		if(GameApplication.getDzpkGame().isquick == false){
			Log.i("test15", "ResetDisplay ResetDisplay ResetDisplay");
		    GameApplication.dismissLoading();
		}
	}

	public void executeAction(){
		 
		 //ִ�з��ƶ���
		executeFaiPaiAction();
		//ִ���������ƶ���
		executeDeskPokeAction();
		//ִ�����״̬����
		executePlayerState();
		//ִ�н��ˢ�¶���
		executeRefreshSiteGoldAction();
		
		//ִ��վ����
		executeStandUpAction2();
		executeStandUpAction();
		//ִ�����¶���
	    executeSitDownAction();
		//ִ��������ƶ���
		executeGiveUpAction();
		//ִ����ע����
		executeXiaZhuAction();
		//ִ����Ϸ��������
		executeGameOverAction();
		//ִ�����ﶯ��
		executeGiftIconAction();
		//ִ�в������ﶯ��
		executePlayGiftIconAction();
	}

	

	
}
