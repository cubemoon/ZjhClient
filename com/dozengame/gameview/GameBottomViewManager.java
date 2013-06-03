package com.dozengame.gameview;

import java.io.IOException;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.GameApplication;
import com.dozengame.R;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.talk.TalkDialog;
import com.dozengame.util.GameUtil;

/**
 * ��Ϸ����ײ��İ�ť
 * 
 * @author hewengao
 * 
 */
public class GameBottomViewManager {
	
	final String contextDescription0 = "0";
	final String contextDescription1 = "1";
	DzpkGameActivityDialog dzpkGame;
    public boolean quickClick=false;
	Bitmap messageBitmap;
	Bitmap pkshowBitmap;
	Bitmap messageBitmaps;
	Bitmap pkshowBitmaps;
	Bitmap game_button2_chick;
	Bitmap game_button2_unchick;
	Bitmap game_button2;
	Bitmap game_button2s;
	Bitmap game_button2d;
	Bitmap hall_btnBegin1;
	Bitmap hall_btnBegin2;
	
	ImageView messageImg;
	ImageView gamePkShow;
	ImageView kanImg;
	ImageView autoKanImg;
	ImageView genImg;
	ImageView imgQiPai;
	ImageView imgGenZhu;
	ImageView imgJiaZhu;
	TextView genZhu;
	LinearLayout bottomLayout;
	LinearLayout autoButtonLayout;
	LinearLayout buttonLayout;
	int min =0;// ��С
	int max =0;// ���
	boolean bl = true;
   static final  String genZhuText="��ע";
   static final  String kanPaiText="����";
	/**
	 * ���溯��
	 * 
	 * @param dzpkGame
	 */
	public GameBottomViewManager(DzpkGameActivityDialog dzpkGame) {
		this.dzpkGame = dzpkGame;
		initBitmap();
		init();
	}

	/**
	 * ��ʼ���ؼ�
	 */
	public void init() {

		bottomLayout = (LinearLayout) dzpkGame.findViewById(R.id.bottom);
		messageImg = (ImageView) dzpkGame.findViewById(R.id.messageImg);
		messageImg.setImageBitmap(messageBitmap);
		gamePkShow = (ImageView) dzpkGame.findViewById(R.id.gamePkShow);
		gamePkShow.setImageBitmap(pkshowBitmap);
		// ����¼�����
		addButtonListener();
	}

	/**
	 * ��ʼ��ͼƬ
	 */
	private void initBitmap() {
		try {
			game_button2d = BitmapFactory.decodeStream(dzpkGame.getContext().getAssets().open(
			"game_button2d.png"));
			
			game_button2 = BitmapFactory.decodeStream(dzpkGame.getContext().getAssets()
					.open("game_button2.png"));
			game_button2s = BitmapFactory.decodeStream(dzpkGame.getContext().getAssets()
					.open("game_button2s.png"));

			game_button2_chick = BitmapFactory.decodeStream(dzpkGame.getContext()
					.getAssets().open("game_button2_chick.png"));
			game_button2_unchick = BitmapFactory.decodeStream(dzpkGame.getContext()
					.getAssets().open("game_button2_unchick.png"));
			
			
		 

			messageBitmap = BitmapFactory.decodeStream(dzpkGame.getContext().getAssets()
					.open("game_message.png"));
			messageBitmaps = BitmapFactory.decodeStream(dzpkGame.getContext().getAssets()
					.open("game_messages.png"));

			pkshowBitmap = BitmapFactory.decodeStream(dzpkGame.getContext().getAssets()
					.open("game_pkshow.png"));
			pkshowBitmaps = BitmapFactory.decodeStream(dzpkGame.getContext().getAssets()
					.open("game_pkshows.png"));
			
			hall_btnBegin1 = BitmapFactory.decodeStream(dzpkGame.getContext().getAssets().open("hall_btnbegin.png"));
    		hall_btnBegin2 = BitmapFactory.decodeStream(dzpkGame.getContext().getAssets().open("hall_btnbegins.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	RelativeLayout quickStartLayout;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what ==0){
			  addQuickStartButtons();
			}else if(msg.what ==1){
			   removeQuickViews();
			}else if(msg.what ==3){
				//���µȴ��ı�
				updateWaitText();
			}
		};
	};
	public  void addQuickStartButton(){
		 
		handler.sendEmptyMessage(0);
	}
	LinearLayout waitNextLayout =null;
	TextView waitTextView=null;
	Thread waitThread =null;;
	 
	/**
	 * ��ӵȴ�
	 */
	public void addWait(){
		removeAllViews();
		if (waitNextLayout == null) {
			waitNextLayout = (LinearLayout) bottomLayout.inflate(dzpkGame.getContext(),R.layout.waitnext, null);
			bottomLayout.addView(waitNextLayout);
			waitTextView=(TextView)waitNextLayout.findViewById(R.id.waitText);
		}else {
			bottomLayout.addView(waitNextLayout);
		}
		runing=true;
		count=0;
		waitThread=new Thread(waitRunnable);
		waitThread.start();
	}
	String checkSlh="";
	int count =0;
    boolean runing =false;
	Runnable waitRunnable = new Runnable()  {
		@Override
		public void run() {
			  try {
			       while(runing){
			    	   switch(count){
			    	   case 0:
			    		   checkSlh="";
			    		   break;
			    	   case 1:
			    		   checkSlh=".";
			    		   break;
			    	   case 2:
			    		   checkSlh="..";
			    		   break;
			    	   case 3:
			    		   checkSlh="...";
			    		   break;
			    	   }
			    	   count++;
			    	   if(count==4){
			    		   count=0;
			    	   }
			    	   handler.sendEmptyMessage(3);
			    	   Thread.sleep(600);
			       }
			  } catch (InterruptedException e) {
					e.printStackTrace();
			  }
		}
	};
	private void updateWaitText() {
		if(waitTextView != null){
			waitTextView.setText(checkSlh);
		}
	}
	 
	/**
	 * ��ӿ��ٿ�ʼ��ť
	 */
	private void addQuickStartButtons() {
	 		removeAllViews();
		if (quickStartLayout == null) {
			quickStartLayout = (RelativeLayout) bottomLayout.inflate(dzpkGame.getContext(),R.layout.quickstart, null);
			bottomLayout.addView(quickStartLayout);
			final ImageView imgView1 = (ImageView) quickStartLayout.findViewById(R.id.quickStart);
			imgView1.setImageBitmap(hall_btnBegin1);
			imgView1.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == 0) {
						imgView1.setImageBitmap(hall_btnBegin2);
					} else if (event.getAction() == 1) {
						imgView1.setImageBitmap(hall_btnBegin1);
						sendRequestQuickStart();
					}
					return true;
				}

			});
		}else {
			bottomLayout.addView(quickStartLayout);
		}
	}
	/**
	 * ���Ϳ��ٿ�ʼ
	 */
	private void sendRequestQuickStart(){
		GameApplication.getDzpkGame().loadIng();
		TaskManager.getInstance().execute(new TaskExecutorAdapter(){
			public int executeTask() throws Exception {
				Integer default_chouma =(Integer)GameApplication.userInfo.get("default_chouma");
				if(default_chouma != null){
					quickClick=true;
	                GameApplication.getSocketService().sendRequestQuickStart(default_chouma);
				}
				return 0;
			}
		});
	}
	ImageView imgView1,imgView2,imgView3;
	/**
	 * ����Զ���ע����Ϣ
	 */
	public void addAutoButtonLayout(HashMap data) {
		//if (buttonLayout != null) {
		int index =-1;
		if(autoButtonLayout != null){
			index=bottomLayout.indexOfChild(autoButtonLayout);
		}
		if(index < 0 ){
		  removeAllViews();
		}
		//}
		 	byte guo = (Byte) data.get("guo");
			byte guoqi = (Byte) data.get("guoqi");
			byte genrenhe = (Byte) data.get("genrenhe");// ���κ�
			byte gen = (Byte) data.get("gen");// ��
			int gengold = (Integer) data.get("gengold");// ���Ľ��
		    if(guo != 1 && guoqi !=1 && genrenhe !=1 && gen !=1){
		    	if(index >=0){
		    	  removeAllViews();
		    	}
				return;
			}
		if (autoButtonLayout == null) {

			autoButtonLayout = (LinearLayout) bottomLayout.inflate(dzpkGame.getContext(),
					R.layout.autobuttonview, null);
			bottomLayout.addView(autoButtonLayout);
			
			
		    imgView1 = (ImageView) autoButtonLayout.findViewById(R.id.imgbg2);
			imgView1.setImageBitmap(game_button2);
			
			  imgView2 = (ImageView) autoButtonLayout
			.findViewById(R.id.imgbg1);
	        imgView2.setImageBitmap(game_button2);
	
			 imgView3 = (ImageView) autoButtonLayout.findViewById(R.id.imgbg3);
			imgView3.setImageBitmap(game_button2);

			autoKanImg = (ImageView) autoButtonLayout
					.findViewById(R.id.autoKanImg);
			autoKanImg.setContentDescription(contextDescription0);
			autoKanImg.setImageBitmap(game_button2_unchick);

			kanImg = (ImageView) autoButtonLayout.findViewById(R.id.kanImg);
			kanImg.setContentDescription(contextDescription0);
			kanImg.setImageBitmap(game_button2_unchick);

			genImg = (ImageView) autoButtonLayout.findViewById(R.id.genImg);
			genImg.setContentDescription(contextDescription0);
			genImg.setImageBitmap(game_button2_unchick);
			
			imgView1.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					click(1);
				}
			});
			
			imgView2.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					click(2);
				}
			});
			
			
			imgView3.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					click(3);
				}
			});

		} else {
			if(index < 0){
			 bottomLayout.addView(autoButtonLayout);
			 reSetAutoPanelItemState(1);
			 reSetAutoPanelItemState(2);
			 reSetAutoPanelItemState(3);
			 m_autoSelectItem = -1;
			 m_autoSelectGenGold =0;
			} 
		}
		if(guoqi ==1){
			//����
			imgView2.setEnabled(true);
			imgView2.setImageBitmap(game_button2);
		}else{
			//������
			imgView2.setEnabled(false);
			imgView2.setImageBitmap(game_button2d); 
		 
			reSetAutoPanelItemState(2);
		}
		
		if(guo ==1){
			//����
			imgView1.setEnabled(true);	
			imgView1.setImageBitmap(game_button2);
			
		}else{
			//������
			imgView1.setEnabled(false);
			imgView1.setImageBitmap(game_button2d);
			reSetAutoPanelItemState(1);
		}
		if(genrenhe ==1 || gen ==1){
			//���κο���
			imgView3.setEnabled(true);
			imgView3.setImageBitmap(game_button2);
		}else{
			imgView3.setEnabled(false);
			imgView3.setImageBitmap(game_button2d);
			reSetAutoPanelItemState(3);
		}
		 
	}
	/**
	 * ����
	 * @param value
	 */
	private void reSetAutoPanelItemState(int value){
		//�ָ�ԭ״
		switch(value){
		case 1:
			autoKanImg.setImageBitmap(game_button2_unchick);
			autoKanImg.setContentDescription(contextDescription0);
			 if(m_autoSelectItem ==1)m_autoSelectItem=-1;
			break;
		case 2:
			 kanImg.setImageBitmap(game_button2_unchick);
			 kanImg.setContentDescription(contextDescription0);
			 if(m_autoSelectItem ==2)m_autoSelectItem=-1;
			break;
		case 3:
			genImg.setImageBitmap(game_button2_unchick);
			genImg.setContentDescription(contextDescription0);
			 if(m_autoSelectItem ==3)m_autoSelectItem=-1;
			break;
		}
		
	}
	//��ǰ�����ֵ
	int currentValue;
	/**
	 * �Զ�������¼�
	 * @param value 1:��2:������ 3:���κ�
	 */
	private void click(int value){
		if(currentValue != value){
			reSetAutoPanelItemState(currentValue);
		}
		currentValue =value;
		switch(value){
		case 1:
			if (autoKanImg.getContentDescription().equals(
					contextDescription0)) {
				autoKanImg.setImageBitmap(game_button2_chick);
				autoKanImg.setContentDescription(contextDescription1);
				//��
				onClickAutoPanelItem(1);
			} else {
				autoKanImg.setImageBitmap(game_button2_unchick);
				autoKanImg.setContentDescription(contextDescription0);
				onClickAutoPanelItem(-1);
			}
			break;
		case 2:
			if (kanImg.getContentDescription().equals(
					contextDescription0)) {
				kanImg.setImageBitmap(game_button2_chick);
				kanImg.setContentDescription(contextDescription1);
				//������
				onClickAutoPanelItem(2);
			} else {
				kanImg.setImageBitmap(game_button2_unchick);
				kanImg.setContentDescription(contextDescription0);
				onClickAutoPanelItem(-1);
			}
			break;
		case 3:
			if (genImg.getContentDescription().equals(
					contextDescription0)) {
				genImg.setImageBitmap(game_button2_chick);
				genImg.setContentDescription(contextDescription1);
				//���κ�
				onClickAutoPanelItem(3);
			} else {
				genImg.setImageBitmap(game_button2_unchick);
				genImg.setContentDescription(contextDescription0);
				onClickAutoPanelItem(-1);
			}
			break;
		}
	}
	/**
	 * ѡ�����Զ����
	 */
	private void onClickAutoPanelItem(int value){
		  m_autoSelectItem = value;
		  m_autoSelectGenGold =0;
	}
	int m_autoSelectItem;
	int m_autoSelectGenGold;
 
	//�Զ���ע
	private boolean doAutoXiaZhu(HashMap data){
		boolean result = false;
		if(data ==null){
			return result;
		}
		Byte follow = (Byte) data.get("follow");// ��ע
		Byte add = (Byte) data.get("add");// ��ע
		Byte allPut = (Byte) data.get("allPut");// ȫ��
		Byte giveUp = (Byte) data.get("giveUp");// ����
		Byte pass = (Byte) data.get("pass");// ����
		Byte chipIn = (Byte) data.get("chipIn");
		 
		int followGold = (Integer) data.get("followGold");// ��ע���
		//���Թ����Զ����ơ����ƻ����ơ����κΡ������ֶ����Թ�
		if (pass == 1)        {
			if(m_autoSelectItem == 1 || m_autoSelectItem == 2 || m_autoSelectItem == 3 || m_autoSelectItem == 4){
				//����ע
				consoleButtonClick(0,0);
				result = true;
			}				
		}
		//���Է��������ƻ�����
		if (giveUp == 1){
			if(m_autoSelectItem == 2){
				//����
				consoleButtonClick(1,0);
				result = true;
			}
		}
		//���Ը������κΡ���ָ������
		if (follow  == 1){
			//���κ�
			if (m_autoSelectItem == 3) {
				//��ע
				consoleButtonClick(2,0);
				result = true;
			}
			else if (m_autoSelectItem == 4 && m_autoSelectGenGold == followGold) {
				//�Զ���һ�����������
				//ui_onClickFollow();
				result = true;
			}
		}
		reSetAutoPanelItemState(m_autoSelectItem);
		return result;
	}
	/**
	 * �Ƴ��ײ�������ť
	 */
    public void removeAllViews(){
    	runing=false;
		count=0;
		try{
			if(waitThread != null && !waitThread.isInterrupted()){
				waitThread.interrupt();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    	bottomLayout.removeAllViews();
    }
    public void removeQuickView(){
    	handler.sendEmptyMessage(1);
    }
    /**
	 * �Ƴ����ٿ�ʼ��ť
	 */
    private void removeQuickViews(){
    	int index=bottomLayout.indexOfChild(quickStartLayout);
     
        if(index >-1){
    		bottomLayout.removeView(quickStartLayout);
    	}
    }
	/**
	 * �������,��ע,��ע����Ϣ
	 */
	public void addButtonLayout(HashMap data) {

		//if (autoButtonLayout != null) {
			removeAllViews();
		//}
		//ִ���Զ���ע
		if(doAutoXiaZhu(data)){
			if(data != null)data.clear();
			data =null;
			return;
		}
		if (buttonLayout == null) {

			buttonLayout = (LinearLayout) bottomLayout.inflate(dzpkGame.getContext(),
					R.layout.buttonview, null);
			bottomLayout.addView(buttonLayout);
			imgQiPai = (ImageView) buttonLayout.findViewById(R.id.img1);
			imgQiPai.setImageBitmap(game_button2);

			imgGenZhu = (ImageView) buttonLayout.findViewById(R.id.img2);
			imgGenZhu.setImageBitmap(game_button2);
		    //��ע�����ı�
			genZhu= (TextView) buttonLayout.findViewById(R.id.genZhu);
		    
			imgJiaZhu = (ImageView) buttonLayout.findViewById(R.id.img3);
			imgJiaZhu.setImageBitmap(game_button2);

			imgQiPai.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == 0) {
						imgQiPai.setImageBitmap(game_button2s);
					} else if (event.getAction() == 1) {
						imgQiPai.setImageBitmap(game_button2);
						consoleButtonClick(1,0);
					}
					return true;
				}

			});

			imgGenZhu.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == 0) {
						imgGenZhu.setImageBitmap(game_button2s);
					} else if (event.getAction() == 1) {
						imgGenZhu.setImageBitmap(game_button2);
						genZhuOrQiPai();
					}
					return true;
				}

			});

			imgJiaZhu.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == 0) {
						imgJiaZhu.setImageBitmap(game_button2s);
					} else if (event.getAction() == 1) {
						imgJiaZhu.setImageBitmap(game_button2);
						addZhu();
					}
					return true;
				}

			});
		} else {
			bottomLayout.addView(buttonLayout);
		}
		Byte follow = (Byte) data.get("follow");// ��ע
		Byte add = (Byte) data.get("add");// ��ע
		Byte allPut = (Byte) data.get("allPut");// ȫ��
		Byte giveUp = (Byte) data.get("giveUp");// ����
		Byte pass = (Byte) data.get("pass");// ����
		Byte chipIn = (Byte) data.get("chipIn");
		  min = (Integer) data.get("min");// ��С
		  max = (Integer) data.get("max");// ���
		  Integer followGold = (Integer) data.get("followGold");// ��ע���
		 
		if(min ==  max && followGold != 0){
			//�������ע��ť����
			imgQiPai.setEnabled(true);
			genZhu.setText(genZhuText);
			imgJiaZhu.setEnabled(false);
			imgJiaZhu.setImageBitmap(game_button2d);
		}else{
			if(giveUp == 0){
				//���Ʋ�����
				imgQiPai.setEnabled(false);
				imgQiPai.setImageBitmap(game_button2d);
			}else{
				//����
				imgQiPai.setEnabled(true);
				imgQiPai.setImageBitmap(game_button2);
			}
			if(add == 0){
				//��ע������
				imgJiaZhu.setEnabled(false);
				imgJiaZhu.setImageBitmap(game_button2d);
			}else{
				//����
				imgJiaZhu.setEnabled(true);
				imgJiaZhu.setImageBitmap(game_button2);
			}
			if(follow == 1){
				//��ע����
				 genZhu.setText(genZhuText);
			}
			if(pass == 1){
				//���� ����
				genZhu.setText(kanPaiText);
			}
			
		}
		if(data !=null)data.clear();
		data =null;
	}
   public boolean msgDialogIsOpen = false;
	/**
	 * ����¼�����
	 */
	private void addButtonListener() {

		messageImg.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case 0:
					messageImg.setImageBitmap(messageBitmaps);
					break;
				case 1:
					if(msgDialogIsOpen == false){
						msgDialogIsOpen =true;
						messageImg.setImageBitmap(messageBitmap);
						GameApplication.talkDialog = new TalkDialog(dzpkGame.getContext(), R.style.dialog);
						GameApplication.talkDialog.show();
					}
					//�ڲ����Ϸ�ݲ�֧��
					//GameUtil.openMessageDialog(dzpkGame.getContext(),GameUtil.msg5);
					break;
				}
				return true;
			}

		});

		gamePkShow.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case 0:
					gamePkShow.setImageBitmap(pkshowBitmaps);
					break;
				case 1:
					gamePkShow.setImageBitmap(pkshowBitmap);
					GamePkShowDialog pkshow = new GamePkShowDialog(dzpkGame.getContext(),
							R.style.dialog);
					pkshow.show();
					break;
				}
				return true;
			}

		});
	}
	
	/**
	 * ����̨��ť�������¼�
	 * 
	 * @param buttonIndex
	 *            �����ť������
	 */
	public void consoleButtonClick(int value,int gold) {
	 
		if (GameApplication.getDzpkGame().playerViewManager.myPos >= 0) {
			switch (value) {
			case 0:
				GameApplication.getDzpkGame().sendClickBuxia();// ����ע
				break;
			case 1:
				GameApplication.getDzpkGame().sendClickGiveUp();// ����
				break;
			case 2:
				GameApplication.getDzpkGame().sendClickFollow();// ��ע
				break;
			case 3:
				 GameApplication.getDzpkGame().sendClickXiaZhu(gold, 2);// ��ע
				break;
			case 4:// ȫ��
				 GameApplication.getDzpkGame().sendClickXiaZhu(max, 2);// 1=��ע 2=��ע 3=��ע 4=��� 5=��ע
				break;
			}
			removeAllViews();
			//ֹͣ��ʱ��
			GameApplication.getDzpkGame().jsqViewManager.setStop();
		 
		}
	}
	
	private void genZhuOrQiPai(){
		if(genZhu.getText().equals(genZhuText)){
			consoleButtonClick(2,0);
		}else if(genZhu.getText().equals(kanPaiText)){
			consoleButtonClick(0,0);
		}
		
		
	}
	/**
	 * ��ע
	 */
	private void addZhu(){
		 AddChouMaViewDialog addCM =new AddChouMaViewDialog(GameApplication.getDzpkGame().getContext(),R.style.dialog);
		 addCM.show();
	}
	/**
	 * �ͷ�
	 */
	public void destroy(){
		Log.i("test19", "GameBottomView destroy");
		GameUtil.recycle(messageBitmap);
		messageBitmap = null;
		GameUtil.recycle(pkshowBitmap);
		pkshowBitmap = null;
		GameUtil.recycle(messageBitmaps);
		messageBitmaps = null;
		GameUtil.recycle(pkshowBitmaps);
		pkshowBitmaps = null;
		GameUtil.recycle(game_button2_chick);
		game_button2_chick = null;
		GameUtil.recycle(game_button2_unchick);
		game_button2_unchick = null;
		GameUtil.recycle(game_button2);
		game_button2 = null;
		GameUtil.recycle(game_button2s);
		game_button2s = null;
		GameUtil.recycle(game_button2d);
		game_button2d = null;
		GameUtil.recycle(hall_btnBegin1);
		hall_btnBegin1 = null;
		GameUtil.recycle(hall_btnBegin2);
		hall_btnBegin2 = null;
		imgView1 = null;
		imgView2 = null;
		imgView3 = null;
		messageImg = null;
		gamePkShow = null;
		kanImg = null;
		autoKanImg = null;
		genImg = null;
		imgQiPai = null;
		imgGenZhu = null;
		imgJiaZhu = null;
	}
}
