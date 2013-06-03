package com.dozengame.talk;

public class ChatMessage {
	String msg;//˵������
	String talkNick;//����
	boolean isSelf = false;//�Ƿ��Լ�

	public String getTalkNick() {
		return talkNick;
	}

	public void setTalkNick(String talkNick) {
		this.talkNick = talkNick;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		String result ="";
		 if(isSelf){
			 result ="�ң�"+msg;
		 }else{
			 result =talkNick+"��"+msg;
		 }
		return result;
	}
}
