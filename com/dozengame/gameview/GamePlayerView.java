package com.dozengame.gameview;

import java.io.IOException;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.alipay.net.JavaHttp;
import com.dozengame.GameApplication;
import com.dozengame.HwgCallBack;
import com.dozengame.net.pojo.PlayerNetPhoto;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.util.GameBitMap;
import com.dozengame.util.GameUtil;

/**
 * ��Ϸ�����ͼ
 * 
 * @author hewengao
 * 
 */
public class GamePlayerView implements HwgCallBack{

	Bitmap cacheBitmap;
	Bitmap cacheBitmapPrev;
	Bitmap cacheBitmapAlpha;
	Canvas canvas;
	boolean isSelf = false; // true:��ʾ�Լ� false:����
	int pos;// ��λ��
	Bitmap liWuBitMapBg;// ���ﱳ��
	Bitmap liWuBitMap;// ����
	Bitmap vipBitMap;// Vip
	Bitmap playerBitMap;//�������
	Bitmap gamephotoframe;//������
	int vipLevel = 5; // vip���� 0���� 1��1�� 2:2�� �Դ�����
	String bottomInfo = "";// �ײ���Ϣ
	String topInfo = "";// ͷ����Ϣ
	static final Paint paint = new Paint();// ����
	static final int[][] liWuPoint = { { 0, 70 }, { 106, 70 } };// ��������
	static final int[][] vipPoint = { { 0, 10 }, { 108, 10 } };// Vip����
	String playerName;
	int handGold = 0;
	boolean isDisplay =false;// �Ƿ���ʾ����ʾ��ʾ�������������ʾ����
	int oldPos = -1;// ԭʼ��λλ��
	HashMap player;
	boolean isGiveUp = false;//�Ƿ�����
	int userid=1;
	String face;
	boolean exists = false;//ԭ���Ƿ���ͼƬ
	GamePlayerViewManager playerManager =null;
	public int getOldPos() {
		return oldPos;
	}

	public void setOldPos(int oldPos) {
		this.oldPos = oldPos;
	}

	public GamePlayerView(int pos,GamePlayerViewManager playerManager) {
		this.pos = pos;
		paint.setColor(Color.WHITE);
		this.playerManager =playerManager;
		canvas = new Canvas();
		 
	}

	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
		this.isInit =false;
		isLoad = false;
        draw(true);
	}
    boolean isLoad = false;
	boolean isInit  =false;
	private void init() {
		if(isInit)return;
		isInit =true;
		GameUtil.recycle(vipBitMap);
		vipBitMap =null;
		GameUtil.recycle(playerBitMap);
		playerBitMap =null;
		HashMap sitDownUsers = GameApplication.getDzpkGame().gameDataManager.sitDownUsers;
		if (sitDownUsers != null) {
			player = (HashMap) sitDownUsers.get(oldPos);
			if (player != null) {
				userid = (Integer)player.get("userid");
				playerName = (String) player.get("nick");
				handGold = (Integer) player.get("handgold");// ���еĳ���
				vipLevel = (Integer) player.get("viplevel");// vip�Ǽ�
			    face=(String)player.get("face");
				if(face != null){
					if(face.startsWith("http:") || face.startsWith("https:")){
						if(isLoad == false){
							isLoad = true;
							//�ڱ��ز�ѯ�Ƿ��Ѵ���
							PlayerNetPhoto	photo = GameUtil.getPlayerPhotoById(GameApplication.currentActivity,userid);
							if(photo != null && photo.getHttpUrl().equals(face)){
									//����
								createBitmap(photo.getPhotoBytes());
							}else{
								if(photo != null){
									exists= true;
								}
								//��������Ҫ����ͼƬ
								TaskManager.getInstance().execute(new TaskExecutorAdapter(){
				 
									public int executeTask() throws Exception {
										JavaHttp http= new JavaHttp(face, GamePlayerView.this, "download");
										http.execute();
										return 0;
									}
								});
							}
						}
					}else{
						Bitmap result=GameBitMap.getBitmapByName(GameApplication.getDzpkGame().getContext(), face);
						if(result != null){
						   playerBitMap =GameBitMap.resizeBitmap(result, GameUtil.imgWidth1, GameUtil.imgHeight1);
						   GameUtil.recycle(result);
						} 
					}
				}
				if(playerBitMap ==null || playerBitMap.isRecycled()){
					//ʹ��Ĭ������
					Bitmap result=GameBitMap.getBitmapByName(GameApplication.getDzpkGame().getContext(), "main_dog.jpg");
					if(result != null){
					 playerBitMap =GameBitMap.resizeBitmap(result, GameUtil.imgWidth1, GameUtil.imgHeight1);
					 GameUtil.recycle(result);
					}
				}
				setBottomInfo(handGold + "");
				setTopInfo(playerName);
				switch (vipLevel) {
				case 1:
					vipBitMap = GameBitMap.getGameBitMap(GameBitMap.GAME_VIP1);
					break;
				case 2:
					vipBitMap = GameBitMap.getGameBitMap(GameBitMap.GAME_VIP2);
					break;
				case 3:
					vipBitMap = GameBitMap.getGameBitMap(GameBitMap.GAME_VIP3);
					break;
				case 4:
					vipBitMap = GameBitMap.getGameBitMap(GameBitMap.GAME_VIP4);
					break;
				case 5:
					vipBitMap = GameBitMap.getGameBitMap(GameBitMap.GAME_VIP5);
					break;
				default:
					vipBitMap =null;
					break;
				}

			}

		}
		//draw();
	}
	 
    public void draw(GamePlayerViewManager playerViewManager){
    	 draw(false);
    	 if(playerViewManager != null){
    		 playerViewManager.drawPlayerView(this,pos);
    	 }
    }
	public void draw(boolean bl) {
		// cacheBitmap=null;
		if (isDisplay == false){
			Log.i("test10", "isDisplay false pos: "+pos);
			return;
		}
        init();
        cacheBitmapPrev =cacheBitmap;
		cacheBitmap = Bitmap.createBitmap(136 + 56, 186, Config.ARGB_8888);
		canvas.setBitmap(cacheBitmap);

		if (isSelf) {
			gamephotoframe =GameBitMap.getGameBitMap(GameBitMap.GAME_PHOTO_FRAME_SELF);
		} else {
			gamephotoframe=GameBitMap.getGameBitMap(GameBitMap.GAME_PHOTO_FRAME);
		}
		canvas.drawBitmap(gamephotoframe, 20, 0,null);
		paint.setTextSize(21.0f);
		paint.setAntiAlias(true);
		// ͷ����Ϣ�ĸ߶�
		float topH = paint.descent() - paint.ascent();
		if(isGiveUp){
			topInfo=giveUp;
		}
		// ͷ����Ϣ�Ŀ��
		float topW = paint.measureText(topInfo);
         
		// ����ͷ����Ϣ
		canvas.drawText(topInfo, (136 - topW) / 2 + 20, topH+4, paint);
		 float bottomH =166;
		if(bottomInfo.length()>7){
			 paint.setTextSize(18);
			 bottomH =164;
		}
		
		if(playerBitMap != null &&  !playerBitMap.isRecycled()){
		  canvas.drawBitmap(playerBitMap,35,37,null);
		}
		// �ײ���Ϣ�ĸ߶�
		// float bottomH = (paint.descent() + paint.ascent());
		//  Log.i("test4", "descent: "+paint.descent()+"  ascent: "+paint.ascent());
		// float bottomH = -paint.ascent();
		// �ײ���Ϣ�Ŀ��
		float bottomW = paint.measureText(bottomInfo);
		 
		topW = (136 - bottomW) / 2+28;
//		if(topW < 46){
//			topW =46;
//		}
		// ���Ƶײ���Ϣ
		canvas.drawText(bottomInfo,topW,bottomH, paint);

		// �������ＰVIP����
		if (pos == 0 || pos == 1 || pos == 4 || pos == 5) {
			// �����������
			drawLiWu(0);
			// ����Vip�ұ�
			drawVip(1);
		} else if (pos == 2 || pos == 3) {
			// ���������ұ�
			drawLiWu(1);
			// ����Vip�ұ�
			drawVip(1);
		} else if (pos == 6 || pos == 7) {
			// �����������
			drawLiWu(0);
			// ����Vip���
			drawVip(0);
		} else if (pos == 8) {
			// ���������ұ�
			drawLiWu(1);
			// ����Vip���
			drawVip(0);
		}else{
		
		}
 
		 if(playerManager != null && bl){
			 playerManager.draw();
		 }
		  
	}

	/**
	 * ��������
	 * 
	 * @param state
	 *            1:�ұ� 0:���
	 */
	private void drawLiWu(int state) {
		initLiwuImg();
		if(liWuBitMap != null && !liWuBitMap.isRecycled()){
			
			 canvas.drawBitmap(liWuBitMap, liWuPoint[state][0],liWuPoint[state][1], null);
		}else{
			liWuBitMapBg = GameBitMap.getGameBitMap(GameBitMap.GAME_LIWU_BG);
			if (liWuBitMapBg != null) {
			  canvas.drawBitmap(liWuBitMapBg, liWuPoint[state][0],liWuPoint[state][1], null);
			}
		}
	}

	/**
	 * ����VIP 1:�ұ� 0:���
	 * 
	 * @param state
	 */
	private void drawVip(int state) {
		if (vipBitMap != null && !vipBitMap.isRecycled()) {
			canvas.drawBitmap(vipBitMap, vipPoint[state][0],vipPoint[state][1], null);
		}
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
		//draw();
	}

	public Bitmap getLiWuBitMap() {
		return liWuBitMap;
	}

	public void setLiWuBitMap(Bitmap liWuBitMap) {
		this.liWuBitMap = liWuBitMap;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getBottomInfo() {
		return bottomInfo;
	}

	public void setBottomInfo(String bottomInfo) {
		this.bottomInfo = bottomInfo;
	}

	public String getTopInfo() {
		return topInfo;
	}

	public void setTopInfo(String topInfo) {
		//this.topInfo = topInfo;
		try {
		this.topInfo = GameUtil.splitIt(topInfo,8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Bitmap getCacheBitmap() {
		 
		if(isGiveUp){
			if(cacheBitmapAlpha ==null){
			    cacheBitmapAlpha = GameBitMap.getRgbAlphaBitmap(cacheBitmap);
			} 
			return cacheBitmapAlpha;
		}else{
		  return cacheBitmap;
		}
	}
	public void setCacheBitmap(Bitmap bit) {
		  cacheBitmap=bit;
	}
	public void clearCacheBitmap() {
		 GameUtil.recycle(cacheBitmap);
		 GameUtil.recycle(cacheBitmapAlpha);
		 destroyBitmap();
		 player =null;
		 cacheBitmap=null;
		 cacheBitmapAlpha=null;
	}
	final static String xiaZhu ="��ע"; 
	final static String jiaZhu ="��ע"; 
	final static String diZhu ="��ע"; 
	final static String allIn ="ȫ��"; 
	final static String genZhu ="��ע";
	final static String giveUp ="����";
	final static String kanPai="����";
	final static String bigBet="��äע";
	final static String smallBet="Сäע";
	final static String waitNext ="�ȴ��¾�";
	final static String buyChouMa ="�������";
	final static String operator ="������";
	final static String leave ="�뿪";
	final static String noready ="δ׼��";
	final static String winJia ="Ӯ��";
	int state = -1;
    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	/**
     * �������״̬
     * @param state
     * 1=��ע 2=��ע 3=��ע 4=ȫ�� 5=��ע 6:���� 7:���� 
     * 8:��äע 9:Сäע 10:�ȴ��¾� 11:������� 12:δ׼��
     * 13:�뿪 14�������� 15��Ӯ��
     */
	public void setPlayerState(int state,GamePlayerViewManager playerManager) {
 
		 this.state =state;
		  
		 switch(state){
		 case 1:
			 setTopInfo(xiaZhu);
			 break;
		 case 2:
			 setTopInfo(jiaZhu);
			 break;
		 case 3:
			  setTopInfo(diZhu);
			 break;
		 case 4:
			 setTopInfo(allIn);
			 break;
		 case 5:
			 setTopInfo(genZhu);
			 break;
		 case 6:
			 setTopInfo(giveUp);
			 break;
		 case 7:
			 setTopInfo(kanPai);
			 break;
		 case 8:
			 setTopInfo(bigBet);
			 break;
		 case 9:
			 setTopInfo(smallBet);
			 break;
		 case 10:
			 setTopInfo(waitNext);
			 break;
		 case 11:
			 setTopInfo(buyChouMa);
			 break;
		 case 12:
			 setTopInfo(noready);
			 break;
		 case 13:
			 setTopInfo(leave);
			 break;
		 case 14:
			 setTopInfo(playerName);
			 break;
		 case 15:
			 setTopInfo(winJia);
			 break;
		 }
		 if(state !=6){
		  draw(true);
		 }
		
		 
	}
	/**
	 * �ָ�ͷ����Ϣ
	 */
	public void reSetTopInfo(){
		//if(!(state ==6  || state ==11)){
		if(state != 11){
			setTopInfo(playerName);
			draw(true);
		}
	}
	public void destroy(){
		  GameUtil.recycle(cacheBitmap);
		  cacheBitmap =null;
		  destroyBitmap();
		  bottomInfo =null;
		  topInfo = null;
		  playerName =null;
	}
	public void destroyCacheBitmapPrev(){
		if(cacheBitmapPrev != cacheBitmap){
		  GameUtil.recycle(cacheBitmapPrev);
		  //GameUtil.recycle(cacheBitmapAlpha);
		  cacheBitmapPrev =null;
		  cacheBitmapAlpha=null;
		}else{
			Log.i("test17", "��������");
		}
	}
	private void destroyBitmap(){
		//destroyCacheBitmapPrev();
		GameUtil.recycle(gamephotoframe);
		GameUtil.recycle(liWuBitMapBg);
		GameUtil.recycle(liWuBitMap);
		gamephotoframe =null;
		liWuBitMap =null;
		liWuBitMapBg =null;
//		GameUtil.recycle(vipBitMap);
//		GameUtil.recycle(playerBitMap);
	}
   /**
    * ��������
    * @param imgPath
    */
	public void setLiWuImgPath(String imgPath) {
		if(player != null){
			
			player.put("liwuimg", imgPath);
			isDisplay =true;
			draw(true);
			
		}
	}
	private void initLiwuImg(){
		if(player != null){
			String imgPath = (String)player.get("liwuimg");
			if(imgPath != null && imgPath.trim().length() > 0){
				Bitmap temp=null;
				try {
					temp = BitmapFactory.decodeStream(GameApplication.getDzpkGame().getContext().getAssets().open(imgPath));
					liWuBitMap= GameBitMap.resizeBitmap(temp, 59,59);
				    GameUtil.recycle(temp);
				    temp =null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
	
			liWuBitMap =null;
		}
	}
	
	public void giveUpState(boolean give){
		 
		isGiveUp =give;
		if(isGiveUp == false){
			GameUtil.recycle(cacheBitmapAlpha);
			cacheBitmapAlpha=null;
		}
		draw(true);
	}
    private void createBitmap(byte [] bytes){
    	 Bitmap result= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    	 playerBitMap =GameBitMap.resizeBitmap(result, GameUtil.imgWidth1, GameUtil.imgHeight1);
		 GameUtil.recycle(result);
		 draw(true);
    }
	@Override
	public void CallBack(Object... obj) {
		if(obj == null || obj.length!=1 || !(obj[0] instanceof byte[])) {
			return;
		}
		byte[] response = (byte[])obj[0];
			try {
				createBitmap(response);
				GameUtil.insertPlayerNetPhoto(GameApplication.currentActivity, userid, face, 0, response, exists);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				 
			}
	}
	 
}

