package com.dozengame.net;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import com.dozengame.GameApplication;
import com.dozengame.event.Event;
import com.dozengame.event.EventDispatcher;
import com.dozengame.net.pojo.ReadData;
import com.dozengame.util.ConvertUtil;
 
 

public class SocketBase extends EventDispatcher implements Runnable{

	private String ip;
	private int port;
	private static final int timeOut=6000*50;
	private static final int connectTimeOut=15000;
	private static final int bufferSize=256;
	private Socket socket = null;
	private final byte[] BYTES_HEAD = { 72, 68, 90, 89 }; // ��Ϣͷ�����ƴ���
	private DataOutputStream outputStream = null;	//socket�����
	private DataInputStream inputStream = null;		//socket������

	private ByteArrayOutputStream byteArrayOutStream = null;	//��ʱ�������ڴ洢����д���2��������
	private DataOutputStream dataOutStream = null;				//��ʱ�������ڴ洢����д�������
	private boolean isConnected=false;
	private boolean iscenter =false;
	List recvBuf= new ArrayList();//�����ֽ�����
	/**
	 * ���캯��
	 */
	public SocketBase(String ip,int port) {
		this.ip = ip;
		this.port = port;	
		
	}
	/**
	 * ���캯��
	 */
	public SocketBase(String ip,int port,boolean iscenter) {
		this.ip = ip;
		this.port = port;	
		this.iscenter=iscenter;
		//byteArrayOutStream = new ByteArrayOutputStream();
		//dataOutStream = new DataOutputStream(byteArrayOutStream);
	}

	/**
	 * ����socket����
	 * 
	 * @throws Exception
	 *             exception
	 */
	public void CreateConnection() throws Exception {
		try {
			byteArrayOutStream = new ByteArrayOutputStream();
			dataOutStream = new DataOutputStream(byteArrayOutStream);
			socket = new Socket();
			socket.connect(new InetSocketAddress(ip,port), connectTimeOut);
		    socket.setSoTimeout(0);	//��ȡ��ʱʱ��
		    socket.setKeepAlive(true);//��������
			if(socket.isConnected()){
				isConnected=true;
				//���ӳɹ�
				//dispatchEvent(new Event(Event.CONNECT));
			}else{
				//����ʧ��
				//dispatchEvent(new Event(Event.CONNECTFAIL));
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (socket != null)
				socket.close();
			byteArrayOutStream.close();
			dataOutStream.close();
			throw e;
		} finally {
		}
	}
	public boolean isConnected() {
		return isConnected;
	}

	/**
	 * �ر�socket����
	 */
	public void shutDownConnection() {
		try {
			//�ر���
			if(byteArrayOutStream != null)
				byteArrayOutStream.close();
			if(dataOutStream != null)
				dataOutStream.close();
			if (outputStream != null)
				outputStream.close();
			if (inputStream != null)
				inputStream.close();
			if (socket != null && socket.isConnected())
				socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����int
	 * @param value
	 * @throws IOException 
	 */
	public void writeInt(int value) throws IOException{
		dataOutStream.write(ConvertUtil.int2byte(value));
	}
	
	/**
	 * ����short
	 * @param value
	 * @throws IOException 
	 */
	public void writeShort(short value) throws IOException{
		dataOutStream.write(ConvertUtil.short2byte(value));
	}
	
	/**
	 * ����String
	 * @param value
	 * @throws IOException 
	 */
	public void writeString(String value) throws IOException{
		byte[] utf8Byte = value.getBytes("UTF-8") ;
		dataOutStream.write(ConvertUtil.short2byte((short)(utf8Byte.length)));
		dataOutStream.write(value.getBytes("UTF-8"));
	}
	
	/**
	 * ����Byte
	 * @param value
	 * @throws IOException
	 */
	public void writeByte(byte value) throws IOException{
		byte[] b = new byte[1];
		b[0] = value;
		dataOutStream.write(b);
	}
	
	/**
	 * �������ݸ�������
	 * @throws IOException
	 * @throws Exception
	 */
	public void writeEnd() throws IOException, Exception{
		try {
			dataOutStream.flush(); // ���������е����ݷŵ�2��������
			// ������bytes���һ�η���ȥ
			byte[] bytes = byteArrayOutStream.toByteArray();
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(byteOut);

			dataOut.write(BYTES_HEAD); // д��ͷ
			dataOut.write(ConvertUtil.int2byte(bytes.length + 4)); // д���ļ�����
			dataOut.write(bytes); // д������
			dataOut.flush(); // �����ݷŵ�byteOut��
			//ͳ�Ʒ��͵��ֽ���
			GameApplication.addSendBytes(byteOut.toByteArray().length);
		 
			// д���ݸ�������
			outputStream = getSendStream();
			outputStream.write(byteOut.toByteArray());

			// �ر���ʱ��
			if (dataOut != null) {
				dataOut.flush();
				dataOut.close();
			}
			if (byteOut != null) {
				byteOut.flush();
				byteOut.close();
			}
			// �ر���
			if (byteArrayOutStream != null) {
				byteArrayOutStream.close();
				byteArrayOutStream = new ByteArrayOutputStream();
			}
			if (dataOutStream != null) {
				dataOutStream.close();
				dataOutStream = new DataOutputStream(byteArrayOutStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("test7", "NetWorkError: "+e.getMessage());
			dispatchEvent(new Event(Event.CLOSE,null));
		}
}
	
	 
	 
	/**
	 * ��ʼ��ȡ����
	 * @throws Exception 
	 * @throws IOException 
	 */
	public boolean readBegin()  {
          int len=0;
		  byte bt[]=new byte[bufferSize];
		  try{
			 InputStream is= getRecvStream();
			  while((len= is.read(bt)) !=-1){
				  receiveByte(bt,len);
				  //ͳ�ƽ��յ��ֽ���
				  GameApplication.receBytes += len;
			  }
			  stop();
		  }catch (Exception e) {
			  Log.e("test7", "NetWorkError: "+e.getMessage());
			  e.printStackTrace();
			  stop();
			  dispatchEvent(new Event(Event.CLOSE,null));
		  }finally{
		     
		  }
		  return true;
	}
	/**
	 * �����ֽڻ���
	 * @param bt
	 * @param len
	 */
	private void receiveByte(byte[] bt, int len) {
		for (int i = 0; i < len; i++) {
			recvBuf.add(bt[i]);
		}
		boolean bl=true;
		while(bl){
			bl=isComplete(recvBuf);
		}
	}
	/**
	 * �Ƿ�����һ��ָ��
	 * @param buf
	 * @return
	 */
	 public boolean isComplete(List buf){
		 int size =buf.size();
		 if(size > 8){
			  byte[] btLen=new byte[4];
			  btLen[0]=(Byte)buf.get(4);
			  btLen[1]=(Byte)buf.get(5);
			  btLen[2]=(Byte)buf.get(6);
			  btLen[3]=(Byte)buf.get(7);
			  int st=ConvertUtil.byte2int(btLen);
			  int pos=4+st;
			  if(pos>0 && size >= pos){
				  ReadData rd= new ReadData(pos);
				  int temp=0;
				  while(temp<pos){
					  rd.addByte(temp++,(Byte)buf.remove(0));
				  }
				  //���յ�һ��ָ��
				  dispatchEvent(new Event(Event.SOCKET_DATA,rd));
				  return true;
			  }
		 }
		 return false;
	 }
	//-------------------------------------------------------------------//
	
	//�õ�socket��ȡbuf
	@SuppressWarnings("unused")
	public DataInputStream getRecvStream() throws IOException {

		if(socket.isConnected()){
			inputStream = new DataInputStream(socket.getInputStream());
		}else{
		  if(socket.isClosed()){
			  //�����ѹر�
			  Log.e("test7", "NetWork Is Close");
			  dispatchEvent(new Event(Event.CLOSE,null));
		  }
		}
		return inputStream;		
	}
 
	
	//�õ�socket�ķ���buf
	public DataOutputStream getSendStream() throws Exception {
		if(socket.isConnected()){
			outputStream = new DataOutputStream(socket.getOutputStream());
			return outputStream;
		}else{
		  if(socket.isClosed()){
			  //�����ѹر�
			  Log.e("test7", "NetWork Is Close");
			  dispatchEvent(new Event(Event.CLOSE,null));
		  }
		}
	    return null;
	}
	
    public void stop(){
    	isConnected=false;
    	shutDownConnection();
    }

	@Override
	public void run() {
		readBegin();
	}
}
