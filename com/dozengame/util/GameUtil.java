package com.dozengame.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.util.Log;

import com.dozengame.DzpkGameActivity;
import com.dozengame.DzpkGameActivityDialog;
import com.dozengame.DzpkGameLoginActivity;
import com.dozengame.DzpkGameRoomActivity;
import com.dozengame.DzpkGameStartActivity;
import com.dozengame.GameApplication;
import com.dozengame.HwgCallBack;
import com.dozengame.Message1Dialog;
import com.dozengame.MessageDialog;
import com.dozengame.R;
import com.dozengame.db.DBManager;
import com.dozengame.net.pojo.DConfig;
import com.dozengame.net.pojo.Gift;
import com.dozengame.net.pojo.PlayerNetPhoto;
import com.dozengame.net.task.TaskExecutorAdapter;
import com.dozengame.net.task.TaskManager;
import com.dozengame.xml.SAXParseXmlService;

/**
 * ��Ϸ������
 * @author hewengao
 *
 */
public class GameUtil {
	public static final String ERRORTAG="Error";
	//��������
	public static final int imgWidth=150;
	//��������
	public static final int imgHeight=150;
	
	//��������
	public static final int imgWidth1=106;
	//��������
	public static final int imgHeight1=106;
	public static final String sure="ȷ  ��";
	public static final String chongZhi="��  ֵ";
	public static final String EXITGAME="��Ҫ�˳���Ϸ��";
	public static final String gamenotfound="��ǰû�к��ʵ���Ϸ�� ";
    public static final String msg1="��¼��Ϸ������ʧ��,�ʺ�����ʹ��";
    public static final String msg2="���Ѿ���һ��������ƣ�������ƾ�";
    public static final String msg31="��½ʧ��,�����˻������Ƿ���ȷ";
    public static final String msg3="��½ʧ�� ,�����˻������Ƿ���ȷ";
    public static final String EXITMSG="ȷ���˳�������";
    public static final String msg4="�ܱ�Ǹ�����ĳ��벻�㡣\r\n������ֵ����ȡ������Ȩ��";
    public static final String msgJiuJi="���벻��,ϵͳ����100�ȼý�.";
    public static final String msg5="�ڲ����δ���Ŵ˹���";
    public static final String  LOGINLINGJIANG="��ϲ������õ�¼����: $";
    public static final String LOGINLINGJIANG1="VIP��¼���⽱��: $";
    public static final String LOGINLINGJIANG2="����������: $";
    public static final String CHOUMABUZU="�ܱ�Ǹ�����ĳ��벻��";
    public static final String ROOMFULL="�Բ��𣬸÷�����������ѡ����������";
    public static final String CHOUMADUO=" �Բ������ĳ��볬����������������";
    public static final String SITHASUSE=" �Բ��𣬸���λ���Ѿ�����!";
	public static final String S = "+$";
	public static final  String msg6="�������û���"; 
	public static final  String msg7="�������¼����"; 
	public static final  String NETERRORMSG="�����ѶϿ�,��������"; 
	public static final String LOGINTIMEOUT="��¼��ʱ������";
	public static final String BEITI=" �Բ���,���ѱ��߳�����.";
	public static final String LOADINGFAILED=" ����ʧ��������.";
	public static final String NOTALK="�Բ����Թ�״ֻ̬����VIP���ԡ�";
	public static final String NOBIAOQING="�ù�����Ҫ���²���ʹ�ã�";
	public static final String BUYVIPSUCCESS="��ϲ��������VIP�ɹ�\r\nVIPʱ�佫��������";
	public static final String VIPOVERTIME="����VIP�ѵ��ڣ�";
	public static final String MOBPAYOFF="��ֵ��������ά�������Ժ�����!";
	/**
	 * ��������ֵ�õ������ַ���
	 * @param pokeWeight
	 * @return
	 */
	public static String getPokeWeight(int pokeWeight){
		String pw="";
		switch(pokeWeight){
    	case 1:
    		pw="����";
    		break;
    	case 2:
    		pw="һ��";
    		break;
    	case 3:
    		pw="����";
    		break;
    	case 4:
    		pw="����";
    		break;
    	case 5:
    		pw="˳��";
    		break;
    	case 6:
    		pw="ͬ��";
    		break;
    	case 7:
    		pw="��«";
    		break;
    	case 8:
    		pw="����";
    		break;
    	case 9:
    		pw="ͬ��˳��";
    		break;
    	case 10:
    		pw="�ʼ�ͬ��˳";
    		break;
    	}
		return pw;
	}
	/**
	 * ��ȡϵͳ�����ڴ��С
	 * @param context
	 * @return
	 */
	public static String getNeCun(Context context){
		 ActivityManager  _ActivityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
	     ActivityManager.MemoryInfo minfo = new ActivityManager.MemoryInfo();     
	     _ActivityManager.getMemoryInfo(minfo);          
	     String result =String.valueOf(minfo.availMem/(1024))+"KB";
	     
	     return result;
	}
	
	/**
	 *����Ϣ��ʾ�򲻴�ȡ����ť
	 * @param context
	 * @return
	 */
	public static void openMessageDialog(Context context,String msg){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new MessageDialog(context,R.style.dialog,msg,null);
		GameApplication.msgDialog.show();
	}
	/**
	 *����Ϣ��ʾ�򲻴�ȡ����ť
	 * @param context
	 * @return
	 */
	public static void openMessageDialog(Context context,String msg,HwgCallBack callback){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new MessageDialog(context,R.style.dialog,msg,callback);
		GameApplication.msgDialog.show();
	}
	/**
	 *����Ϣ��ʾ�򲻴�ȡ����ť
	 * @param context
	 * @return
	 */
	public static void openMessageDialog(Context context,String msg,HwgCallBack callback,String sureText){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new MessageDialog(context,R.style.dialog,msg,callback,sureText);
		GameApplication.msgDialog.show();
	}
	
	/**
	 *����Ϣ��ʾ���ȡ����ť
	 * @param context
	 * @return
	 */
	public static void openMessage1Dialog(Context context,String msg,HwgCallBack callback){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new Message1Dialog(context,R.style.dialog,msg,callback);
		GameApplication.msgDialog.show();
	}
	
	/**
	 *����Ϣ��ʾ���ȡ����ť
	 * @param context
	 * @return
	 */
	public static void openMessage1Dialog(Context context,String msg,HwgCallBack callback,String sureText){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new Message1Dialog(context,R.style.dialog,msg,callback,sureText);
		GameApplication.msgDialog.show();
	}
	/**
	 *����Ϣ��ʾ���ȡ����ť
	 * @param context
	 * @return
	 */
	public static void openMessage1Dialog(Context context,String msg,HwgCallBack callback,String sureText,String cancelText){
		GameApplication.dismissLoading();
		GameApplication.dismissMsgDialog();
		GameApplication.msgDialog = new Message1Dialog(context,R.style.dialog,msg,callback,sureText,cancelText);
		GameApplication.msgDialog.show();
	}
	/**
	 * �����˳�
	 */
	public static void backExit(final Activity context){
	 
	 
		if(context.isTaskRoot()){
			HwgCallBack callback = new HwgCallBack(){
	 
				public void CallBack(Object... obj) {
					if(obj == null || obj.length ==0)
					android.os.Process.killProcess(android.os.Process.myPid());//��ȡPID 
				}
			};
			openMessage1Dialog(context,EXITMSG,callback);
		}else{
			context.finish();
		}
	}
	public static void exitGame(final Activity context){
		HwgCallBack callback = new HwgCallBack(){
			 
			public void CallBack(Object... obj) {
				if(obj == null || obj.length ==0)
				android.os.Process.killProcess(android.os.Process.myPid());//��ȡPID 
			}
		};
		openMessage1Dialog(context,EXITGAME,callback);
	}
	public static boolean isNetError =false;
	/**
	 * �����������ʾ��Ϣ
	 */
	public static void openNetErrorMsg(){
		if(isNetError==false && GameApplication.currentActivity.getClass() != DzpkGameStartActivity.class){
		    isNetError =true;
		    GameApplication.getSocketService().shutDownGCenter();
		    GameApplication.getSocketService().shutDownGServer();
			handler.sendEmptyMessage(1);
		}
		
	}
	private static void openNetErrorMessage() {
		GameApplication.dismissLoading();
		HwgCallBack callback =new HwgCallBack(){
			 
			public void CallBack(Object... obj) {
				     GameApplication.dismissLoading();
				     if(GameApplication.currentActivity.getClass() ==DzpkGameActivity.class){
				    	 GameApplication.getDzpkGame().dismiss();
				     }else{
				    	 isNetError =false;
				     }
			 	     Intent it = new Intent();
					 it.setClass(GameApplication.currentActivity, DzpkGameStartActivity.class);
					 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 GameApplication.currentActivity.startActivity(it);
					 GameApplication.currentActivity.finish();
					 
				 
			}
			
		};
		openMessageDialog(GameApplication.currentActivity,NETERRORMSG,callback);
	}
	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what ==1){
			 openNetErrorMessage();
			}else if(msg.what ==0){
				//VIP��Ϣ��ʾ
				openVipDialog(msg.obj);
			}
		}
	};
	//��VIP��ʾ��Ϣ
	public static void openVipDialog(HashMap data){
		Message msg =handler.obtainMessage();
		msg.what = 0;
		msg.obj =data;
		handler.sendMessage(msg);
	}
	//��VIP��ʾ��Ϣ
	private static void openVipDialog(Object  obj){
		HashMap data = (HashMap)obj;
		if(data != null){
			Byte success = (Byte)data.get("success");
			//0=vip���� 1=����vip�ɹ�
			if(GameApplication.currentActivity instanceof DzpkGameStartActivity || GameApplication.currentActivity instanceof DzpkGameLoginActivity){
				GameApplication.showVipInfo =1;
				GameApplication.vipInfoData=data;
				return ;
			}
			if(success ==0){
				openMessageDialog(GameApplication.currentActivity,VIPOVERTIME,null);
			}else if(success ==1){
				String overtime =(String)data.get("overtime");
				openMessageDialog(GameApplication.currentActivity,BUYVIPSUCCESS+overtime,null);
			}
		}
	}
	/**
	 * �ͷ�ͼƬ��Դ
	 * @param bitmap
	 */
	public static void recycle(Bitmap bitmap){
		if(bitmap != null){
			if(!bitmap.isRecycled()){
				bitmap.recycle();
			}
		}
		bitmap =null;
	}
	public static String splitIt(String str,int byteNum) throws Exception {
		if(str ==null)return " ";  
		String temp="";
	      int len = str.length();
	      int tempLen =0;
	      int count =0;
	      int i=0;
	      for(i=0; i< len;i++){
	    	  temp = str.charAt(i)+"";
	    	  tempLen=temp.getBytes().length;
	    	  if(tempLen==1){
	    		  count++;
	    	  }else{
	    		  count+=2;
	    	  }
	    	  if(count > byteNum){
	    		  i--;
	    		  break;
	    	  }else if(count ==byteNum){
	    		  break;
	    	  }
	      }
	      if(i < len-1){
	        temp=  str.substring(0, i+1)+"..";
	      }else{
	    	temp =str;
	      }
	     // Log.i("test8", "result: "+temp);
	      return temp;
	}
	public static String splitItss(String str,int byteNum) throws Exception {
		 
		  if(str ==null)return " ";
	      byte bt[] = str.getBytes("UTF-8");
	      int len =bt.length;
	     
	      if(len <= byteNum){
	    	  return str;
	      }
		if (byteNum >= 1 && len > byteNum) {
			String result;
			int count =0;
			int zfCount=0;
			int i=0;
			for(i=0;i < len;i++){
				if(((count/3)*2 + zfCount)>= byteNum){
					break;
				}
				if(bt[i] < 0){
					count++;
				}else{
					zfCount++;
				}
			}
			Log.i("test8","count: "+count+" i: "+i+"  zfCount: "+zfCount);
			i= i-count%3;
			result = new String(bt, 0,i,"UTF-8"); 
             if(i != len){
			  result =result+"..";
             }
			Log.i("test8", "str: "+str+" byteNum: "+byteNum+" result: "+result);
			 
      return result;
		} else {
			throw new  Exception("��Ҫ��ȡ���ֽ������ϸ�");
		}
	}
	public static String splitIt2(String str,int byteNum) throws Exception {
		 
		  if(str ==null)return " ";
	      byte bt[] = str.getBytes("GBK");
	      int len =bt.length;
	      //Log.i("test4","len: "+len+"  byteNum: "+byteNum);
	      //for(int i=0; i<len;i++){
	    //	  Log.i("test4","i: "+i+"  v: "+bt[i]);
	     // }
	      if(len <= byteNum){
	    	  return str;
	      }
		if (byteNum >= 1 && len > byteNum) {
			String result;
			int count =0;
			for(int i=0;i < byteNum;i++){
				if(bt[i] < 0){
					count++;
				}
			}
			if (count % 2 != 0) {
				result = new String(bt, 0, --byteNum,"GBK"); 
			} else {
				result = new String(bt, 0, byteNum,"GBK");
			}
			result =result+"..";
			Log.i("test8", "str: "+str+" byteNum: "+byteNum+" result: "+result);
			//Log.i("test4",result);
          return result;
		} else {
			throw new  Exception("��Ҫ��ȡ���ֽ������ϸ�");
		}
	}
	public static  void  executeCanNotIntoRoom(Context context,HashMap data ,HwgCallBack callback) {
		GameApplication.dismissLoading();
		if(data ==null)return;
		Byte intoRoomStats =(Byte)data.get("IntoRoomStats");
		switch(intoRoomStats){
		case 1:
			//��������
			break;
		case 0:
			//openMessageDialog(context,CHOUMABUZU,callback);
			TaskManager.getInstance().execute(new TaskExecutorAdapter(){

				@Override
				public int executeTask() throws Exception {
					GameApplication.getSocketService().sendRequestJiuJi();
					return 0;
				}
				
			});
			break;
		case -1:
			openMessageDialog(context,ROOMFULL,callback);
			break;
		case 2:
			openMessageDialog(context,CHOUMADUO,callback);
			break;
		case 3:
			openMessageDialog(context,CHOUMADUO,callback);
			//openMessageDialog(context,CHOUMADUO);
			break;
		case -10:
			//openMessageDialog(context,CHOUMADUO);
			break;
		case -4:
			//openMessageDialog(context,CHOUMADUO);
			break;
		case -8:
			//openMessageDialog(context,CHOUMADUO);
			break;
			
		}
		data.clear();
		data =null;
//		else if (e.data["IntoRoomStats"] == 2)
//		{
//			this.m_ui.resetLastState(true);
//			DTrace.traceex("Ǯ����");
//			m_ui.showMessageBox("    �Բ������ĳ���"+ myinfo.gold +"�Ѿ�������������������"+m_nCurrentGroupInfo.at_most_gold+"����ȷ�����뷿����߱��ķ��������Ϸ", 1, false, null, false, true, gotoHigherRoom);
//		}
//		else if (e.data["IntoRoomStats"] == 3)
//		{
//			this.m_ui.resetLastState(true);
//			m_ui.showMessageBox("    �������Ѿ���������100���ˣ����Ѿ������ʺ���������ս��������Ϸ�������Ƽ���ȥ2000����10000�����䣬ף����Ŀ��ġ�", 1, false, null, true);
//		}
//		else if (e.data["IntoRoomStats"] == -10)
//		{
//			this.m_ui.resetLastState(true);
//			showMessageBox("    �������Ŀ���ʱ��Ϊÿ�� ��9:00 �� �����賿1:00����������ȥ��ͨ������һ�¡�", 1);
//		}
//		else if (e.data["IntoRoomStats"] == -4)
//		{
//			this.m_ui.resetLastState(true);
//			showMessageBox("    ���Ļ��ֲ��㣬����÷���������Ҫ������ "+e.data.gold+" ���ϡ�", 1);
//		}
//		else if(e.data["IntoRoomStats"] == -8)
//		{
//			this.m_ui.resetLastState(true);
//			showMessageBox("    ��Ǹ�����ѱ���������10���Ӻ󷽿��ٴν���.", 1);
//		}
	}
    static HashMap<Integer, Gift> giftHm;
	/**
	 * ��ʼ��������Ϣ
	 * @param context
	 */
	public static void initGift(Context context){
	 
		try {
			 
			 SharedPreferences uiState  =  context.getApplicationContext().getSharedPreferences("gift",Context.MODE_WORLD_WRITEABLE);
			if(uiState.getInt("hasgift",-1) ==-1){
				
				 DBManager.init(context);
				 DBManager.executeSql(Gift.DROP_TABLE);
				 DBManager.executeSql(Gift.CREATE_TABLE);
				 InputStream is = context.getAssets().open("shop.xml");
				 HashMap<Integer, Gift>  giftTemp=SAXParseXmlService.getGiftService(is);
				 if(giftTemp != null){
					Iterator<Entry<Integer, Gift>> it = giftTemp.entrySet().iterator();
					Gift temp =null;
					ContentValues values=null;
					while(it.hasNext()){
					 
					    temp = it.next().getValue();
				        values=new ContentValues();
						values.put(Gift.ID,temp.getId());
						values.put(Gift.NAME,temp.getName());
						values.put(Gift.TAB,temp.getTab());
						values.put(Gift.IMGPATH,temp.getImgPath());
					    DBManager.insert(Gift.TABLE_NAME, null, values);
						 
					}
					it =null;
					giftTemp.clear();
					giftTemp =null;
				 }
				 DBManager.clear();
				 SharedPreferences.Editor editor = uiState.edit();
				 editor.putInt("hasgift", 0);
				 editor.commit(); 
				 uiState =null;
				 editor =null;
			}
			 
		 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ��������ID��ȡ����
	 * @param id
	 */
	public static Gift getGiftById(Context context,Integer giftId){
		//if(giftHm !=null){
		//	return giftHm.get(giftId);
		//}
		 DBManager.init(context);
		 Gift gift= DBManager.findGift(Gift.TABLE_NAME, Gift.ID+"="+giftId, null);
		 DBManager.clear();
		 return gift;
	}
	/**
	 * ����userID��ȡPlayerNetPhoto
	 * @param id
	 */
	public static PlayerNetPhoto getPlayerPhotoById(Context context,Integer userID){
		 try{
			 DBManager.init(context);
			 DBManager.executeSql(PlayerNetPhoto.CREATE_TABLE);
			 PlayerNetPhoto photo= DBManager.findPlayerNetPhoto(PlayerNetPhoto.TABLE_NAME, PlayerNetPhoto.ID+"="+userID, null);
			 DBManager.clear();
			 return photo;
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return null;
	}
	/**
	 *��ӻ��滻PlayerNetPhoto
	 * @param id
	 */
 
	public static void insertPlayerNetPhoto(Context context,int userid,String httpUrl,int state,byte [] photoBytes,boolean exists){
		try{
		    DBManager.init(context);
			if(exists){
				//ɾ��
				DBManager.delete(PlayerNetPhoto.TABLE_NAME, PlayerNetPhoto.ID+"="+userid,null);
			}
			//���
		    ContentValues values=new ContentValues();
			values.put(PlayerNetPhoto.ID,userid);
			values.put(PlayerNetPhoto.HTTP_URL,httpUrl);
			values.put(PlayerNetPhoto.PHOTO_BYTES,photoBytes);
			values.put(PlayerNetPhoto.STATE,state);
		    DBManager.insert(PlayerNetPhoto.TABLE_NAME, null, values);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * �ж��Ƿ�������
	 * @param c
	 * @return
	 */
	public final static boolean isScreenLocked(Context c) {
        android.app.KeyguardManager mKeyguardManager = (KeyguardManager) c.getSystemService(c.KEYGUARD_SERVICE);
        return mKeyguardManager.inKeyguardRestrictedInputMode();
    }
	
	//��սʧ����ʾ
	public final static void onRecvWatchError(final DzpkGameActivityDialog context,HashMap data){
		GameApplication.dismissLoading();
		int errorcode = (Integer) data.get("errorcode");
		String strMsg = "";
		if (errorcode == -1) {
			strMsg = "��������ʧ�ܣ��˷�����쳣��";
		} else if (errorcode == -2) {
			strMsg = "��������ʧ�ܣ��÷��䲻�ڱ���������";
		} else if (errorcode == -3) {
			strMsg = " ��������ʧ�ܣ��÷�����Ϸ�ѿ�ʼ�Ҳ������ս��";
		} else if (errorcode == -4) {
			strMsg = "��������ʧ�ܣ��Թ�����̫�࣬���Ժ���롣";
		} else if (errorcode == -5) {
			strMsg = "��������ʧ�ܣ����ȼ�������Ŷ��";
		} else if (errorcode == -6) {
			strMsg = "��������ʧ�ܣ��÷��������VIP��ҽ��롣";
		} else if (errorcode == -7) {
			// m_ui.showCmNotEnoughWindow();
			return;
		} else if (errorcode == -8) {
			strMsg = "��Ǹ�����ѱ���������10���Ӻ󷽿��ٴν���.";
		} else if (errorcode == -10) {
			strMsg = "��Ǹ,���ѱ��������";
		} else {
			strMsg = "��������ʧ�ܣ��÷��������VIP��ҽ��롣������[" + errorcode + "]";
		}
		HwgCallBack callback = new HwgCallBack(){
			public  void CallBack(Object[] obj) {
		 
				 Intent it =new Intent();
				 it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 it.setClass(context.getContext(), DzpkGameRoomActivity.class);
				 context.getContext().startActivity(it);
				 context.dismiss();
			};
		};
		openMessageDialog(context.getContext(),strMsg,callback);
		//showMessageBox(strMsg, 1, false)
	}
	/**
	 * ���µ�¼
	 */
	public static void reLogin(){
		try{
			if(GameApplication.currentActivity == null)return;
			Intent it = new Intent(GameApplication.currentActivity,DzpkGameLoginActivity.class);
			GameApplication.currentActivity.startActivity(it);
			GameApplication.currentActivity.finish();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * ��������ת�����ĵ�����ģ��
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(InputStream is) throws Exception {
		 
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbfactory.newDocumentBuilder();
		return db.parse(is);

	}
	/**
	 * ��������ת�����ĵ�����ģ��
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(byte[] bt) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(bt);
		return getDocument(bis);
	}
	
	/**
	 * ��ȡ��ǰ�ҵ�λ��
	 * @param context
	 * @return 0:GPS�ѿ��� -1:GPSδ���� -2:δ��ȡ��ΰ�� 1:�ѻ�ȡ����ΰ��
	 */
	public static int initLocationGPS(Context context){
		Log.i("test2", "initLocationGPS");
	 	LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		//�ж�GPS״̬
		boolean GPS_status = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
	   //  boolean NETWORK_status = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	   int state =-1;
	    if(GPS_status){
	    	Log.i("test2", "GPS GPS_status����");
	    	state =0;
	    }
//	    else if(NETWORK_status){
//	    	Log.i("test2", "GPS NETWORK_status����");
//	    }
	    else{

	    	Log.i("test2", "GPS GPS_status NETWORK_statusδ����");
           state =-1;
           return state;
        }	
		Criteria criteria = new Criteria();  
		criteria.setAccuracy(Criteria.ACCURACY_FINE);  
		criteria.setAltitudeRequired(false);  
		criteria.setBearingRequired(false);  
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); 
		String provider = lm.getBestProvider(criteria, false);
		Log.i("test2", "GPS provider: "+provider);
		
		//��ȡGPS����
		Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if(myLocation == null){
			state = -2;
			 return state;
			// myLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		} 
		 
		DConfig.nMyLatitudeX = myLocation.getLatitude();
		DConfig.nMyLongitudeY = myLocation.getLongitude(); 
		//Log.i("test2", " DConfig.nMyLatitudeX: "+DConfig.nMyLatitudeX+"  DConfig.nMyLongitudeY: "+DConfig.nMyLongitudeY);
         state =1;
		return state;
	}
	/**
	 * �ָ���Ϸ��
	 * @param gold
	 * @return
	 */
	public static String getMoneySplit(int gold){
		String result=""+gold;
		int len = result.length()-1;
		StringBuffer sb = new StringBuffer();
		int count =0;
		for(int i= len; i >=0 ; i--){
			count++;
			sb.append(result.charAt(i));
			if(count % 3 == 0 && count != len+1){
				sb.append(",");
			}
		}
		sb=sb.reverse();
		return sb.toString();
	}
}
