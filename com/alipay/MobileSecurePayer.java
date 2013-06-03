/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.alipay;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;

public class MobileSecurePayer
{
	static String TAG = "MobileSecurePayer";

	Integer lock = 0;
	IAlixPay mAlixPay = null;
	boolean mbPaying = false;

	Context mActivity = null;
	
	private ServiceConnection mAlixPayConnection = new ServiceConnection() 
	{
		public void onServiceConnected(ComponentName className, IBinder service)
		{
		    //
		    // wake up the binder to continue.
		    synchronized( lock )
		    {	
		    	mAlixPay 	= IAlixPay.Stub.asInterface(service);
		    	lock.notify();
		    }
		}
	
		public void onServiceDisconnected(ComponentName className)
		{
			mAlixPay	= null;
		}
	};
	
	public boolean pay(final String strOrderInfo, final Handler callback,
			final int myWhat, final Context activity)
	{
		Log.i("test", "1 pay failed");
		if( mbPaying )
			return false;
		mbPaying = true;
		Log.i("test", "2pay failed");
		//
		mActivity = activity;
		
		// bind the service.
		if (mAlixPay == null)
		{
			Log.i("test", "3pay failed");
			mActivity.bindService(new Intent(IAlixPay.class.getName()), mAlixPayConnection, Context.BIND_AUTO_CREATE);
		}
		//else ok.
		
		Log.i("test", "4 pay failed");
		new Thread(new Runnable() {
			public void run()
			{
				try
				{
				 
					// wait for the service bind operation to completely finished.
					// Note: this is important,otherwise the next mAlixPay.Pay() will fail.
					synchronized (lock)
					{
						if (mAlixPay == null)
							lock.wait();
					}
					Log.i("test12", "111prev Pay: ");
					// register a Callback for the service.
					mAlixPay.registerCallback(mCallback);
					Log.i("test12", "prev Pay: ");
					// call the MobileSecurePay service.
					String strRet = mAlixPay.Pay(strOrderInfo);
					Log.i("test12", "After Pay: " + strRet);

					// set the flag to indicate that we have finished.
					// unregister the Callback, and unbind the service.
					mbPaying = false;
					
				
					
					// send the result back to caller.
					Message msg = new Message();
					msg.what = myWhat;
					msg.obj = strRet;
					Log.i("test12","After Pay result::: "+strRet);
					callback.sendMessage(msg);
				}
				catch (Exception e)
				{
					Log.i("test12", "error: "+e.getMessage());
					e.printStackTrace();
					
					// send the result back to caller.
					Message msg = new Message();
					msg.what = myWhat;
					msg.obj = e.toString();
					callback.sendMessage(msg);
				}finally{
					if(mAlixPay !=null){
						 try {
							mAlixPay.unregisterCallback(mCallback);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
					try{
					   mActivity.unbindService(mAlixPayConnection);
					}catch(Exception e){
						
					}
				}
			}
		}).start();
		
		return true;
	}
	
	 /**
	 * This implementation is used to receive callbacks from the remote
	 * service.
	 */
	private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() 
	{
		/**
		 * This is called by the remote service regularly to tell us
		 * about new values. Note that IPC calls are dispatched through
		 * a thread pool running in each process, so the code executing
		 * here will NOT be running in our main thread like most other
		 * things -- so, to update the UI, we need to use a Handler to
		 * hop over there.
		 */
		public void startActivity(String packageName, String className, int iCallingPid, Bundle bundle)
				throws RemoteException
		{
			Intent intent	= new Intent(Intent.ACTION_MAIN, null);
			
			if( bundle == null )
				bundle = new Bundle();
			// else ok.
			
			try
			{
				bundle.putInt("CallingPid", iCallingPid);
				intent.putExtras(bundle);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			intent.setClassName(packageName, className);
			mActivity.startActivity(intent);
		}
	};
}