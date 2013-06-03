package com.dozengame.playerlocationroom;

import java.io.Serializable;

import android.graphics.Bitmap;

public class PlayerLocationData implements Serializable{
	
	int nUserid;    //�û�ID
	String strLatitude;      //γ������x
	String strLongitude;	   //��������y
	String strFaceurl;     // ͷ��
	String strNick;         // �ǳ�
	//int	nIsvip;        // �Ƿ�VIP���:0���ǣ�1��
	int	nGold;          // ���
	int	nStat;           //����״̬��0�����ߣ�1������
	int	nSex;            //�Ա�0��Ů��1����
	String strDate;    	 // ��¼����
	
	double nDistance;	//���ң�
 

	/**
	 * ��¼����
	 * @return
	 */
	public String getDate() 
	{
		return strDate;
	}
	
	public void setDate(String value) 
	{
		this.strDate = value;
	}
	
	
	/**
	 * ���ң�
	 * @return
	 */
	public double getDistance() 
	{
		return nDistance;
	}
	
	public void setDistance(double value) 
	{
		this.nDistance = value;
	}
	
	/**
	 * �û�ID
	 * @return
	 */
	public int getUserID() 
	{
		return nUserid;
	}
	
	public void setUserID(int value) 
	{
		this.nUserid = value;
	}
	
	/**
	 * Latitudeγ��
	 * @return
	 */
	public String getLatitudeX() 
	{
		return strLatitude;
	}
	
	public void setLatitudeX(String x) 
	{
		this.strLatitude = x;
	}
	
	/**
	 * Longitude����
	 * @return
	 */
	public String getLongitudeY() 
	{
		return strLongitude;
	}
	
	public void setLongitudeY(String y) 
	{
		this.strLongitude = y;
	}
	
	/**
	 * ͷ��
	 * @return
	 */
	public String getFaceurl() 
	{
		return strFaceurl;
	}
	
	public void setFaceurl(String url) 
	{
		this.strFaceurl = url;
	}
	
	/**
	 * �ǳ�
	 * @return
	 */
	public String getNick() 
	{
		return strNick;
	}
	
	public void setNick(String nick) 
	{
		this.strNick = nick;
	}

/*	*//**
	 * �Ƿ�VIP���:0���ǣ�1��
	 * @return
	 *//*
	public int getIsvip() 
	{
		return nIsvip;
	}
	
	public void setIsvip(int value) 
	{
		this.nIsvip = value;
	}
	*/
	/**
	 * ���
	 * @return
	 */
	public int getGold() 
	{
		return nGold;
	}
	
	public void setGold(int value) 
	{
		this.nGold = value;
	}
	
	/**
	 * ����״̬��0�����ߣ�1������
	 * @return
	 */
	public int getStat() 
	{
		return nStat;
	}
	
	public void setStat(int value) 
	{
		this.nStat = value;
	}
	
	/**
	 * �Ա�0��Ů��1����
	 * @return
	 */
	public int getSex() 
	{
		return nSex;
	}
	
	public void setSex(int value) 
	{
		this.nSex = value;
	}
	
}
