package com.dozengame.event;

public interface IEventDispatcher {
	
	//ʹ�� EventDispatcher ����ע���¼�������������ʹ�������ܹ������¼�֪ͨ�� IEventDispatcher 
	void addEventListener(String type, CallBack callback,String methodName, boolean useCapture,int priority,boolean useWeakReference);
	  
	void addEventListener(String type, CallBack callback,String methodName);
	
	// ���¼����ȵ��¼����С� IEventDispatcher    
	boolean dispatchEvent(Event event);
	
	//��� EventDispatcher �����Ƿ�Ϊ�ض��¼�����ע�����κ��������� IEventDispatcher 
	boolean hasEventListener(String type);
	
	 //�� EventDispatcher ������ɾ���������� IEventDispatcher 
	void  removeEventListener(String type, CallBack callback,String methodName, boolean useCapture);
	
	void  removeEventListener(String type, CallBack callback,String methodName);
	
	//����Ƿ��ô� EventDispatcher ��������κ�ʼ��Ϊָ���¼�����ע�����¼���������
    boolean  willTrigger(String type);
	  

}
