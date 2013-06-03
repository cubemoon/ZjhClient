package com.dozengame.net.task;
import java.util.LinkedList;

import android.util.Log;

public class ThreadPool extends ThreadGroup {  
    private boolean isClosed = false;  
    private LinkedList<Runnable> workQueue;       
    private static int threadPoolID = 1;  
    public ThreadPool(int poolSize) {   
  
        super(threadPoolID + "");      
        setDaemon(true);               
        workQueue = new LinkedList<Runnable>();  
        for(int i = 0; i < poolSize; i++) {  
            new WorkThread(i).start();   
        }  
    }  
      
   
    /** ���������м���һ��������,�ɹ����߳�ȥִ�и�����*/  
    public synchronized void execute(Runnable task) {  
        if(isClosed) {  
            throw new IllegalStateException();  
        }  
        if(task != null) {  
            workQueue.add(task);//������м���һ������  
            notify();           //����һ������getTask()�����д�����Ĺ����߳�  
        }  
    }  
    private synchronized Runnable getTask(int threadid) throws InterruptedException {  
        while(workQueue.size() == 0) {  
            if(isClosed) return null;  
           
            wait();             
        }  
       
        return (Runnable) workQueue.removeFirst();  
    }  
    public synchronized void clearQueue(){
    	workQueue.clear();
    }
    public synchronized void cancelPool(){
    	isClosed = true;  
    	 
    	workQueue.clear();
    }
    public synchronized void closePool() {  
        if(! isClosed) {  
            waitFinish();        
            isClosed = true;  
            workQueue.clear();   
            interrupt();        
        }  
    }  
    /** �ȴ������̰߳���������ִ�����*/  
    public void waitFinish() {  
        synchronized (this) {  
            isClosed = true;  
            notifyAll();            //�������л���getTask()�����еȴ�����Ĺ����߳�  
        }  
        Thread[] threads = new Thread[activeCount()]; //activeCount() ���ظ��߳����л�̵߳Ĺ���ֵ��  
        int count = enumerate(threads); //enumerate()�����̳���ThreadGroup�࣬���ݻ�̵߳Ĺ���ֵ����߳����е�ǰ���л�Ĺ����߳�  
        for(int i =0; i < count; i++) { //�ȴ����й����߳̽���  
            try {  
                threads[i].join();  //�ȴ������߳̽���  
            }catch(InterruptedException ex) {  
                ex.printStackTrace();  
            }  
        }  
    }  
  
    private class WorkThread extends Thread {  
        private int id;  
        public WorkThread(int id) {  
         
            super(ThreadPool.this,id+"");  
            this.id =id;  
        }  
        public void run() {  
            while(! isInterrupted()) {   
                Runnable task = null;  
                try {  
                    task = getTask(id);      
                }catch(InterruptedException ex) {  
                    ex.printStackTrace();  
                }  
                
                if(task == null) return;  
                  
                try {  
                    task.run();   
                }catch(Throwable t) {  
                    t.printStackTrace();  
                }  
            }//  end while  
        }//  end run  
    }// end workThread  
} 
