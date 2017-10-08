package com.geekymax.maxschedule;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

public class AppWidgetService extends Service {
    private final String ACTION_UPDATE_DATE = "com.geekymax.maxschedule.UPDATE_DATE";
    private final static String TAG = "FlagMax";
    private Context mContext;
    private UpdateThread mUpdateThread;
    public AppWidgetService() {
    }

    @Override
    public void onCreate() {
        mUpdateThread = new UpdateThread();
        mUpdateThread.start();
        mContext = this.getApplicationContext();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if(mUpdateThread!=null){
            mUpdateThread.interrupt();
        }
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private class UpdateThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                while (true){
                    Log.d(TAG, "run: ");
                    Intent intent = new Intent(ACTION_UPDATE_DATE);
                    mContext.sendBroadcast(intent);
                    Thread.sleep(5000);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
