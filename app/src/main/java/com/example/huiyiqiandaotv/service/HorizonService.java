package com.example.huiyiqiandaotv.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;


/**
 * Created by Administrator on 2017/11/21.
 */

public class HorizonService extends Service {
    private  AlarmManager manager;
    private  PendingIntent pi;
    private MyReceiver myReceiver=null;

    @Override
    public void onCreate() {
        super.onCreate();
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("guanbi333");
        // 注册广播
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Intent intent1=new Intent("duanxianchonglian");
        sendBroadcast(intent1);
       // Log.d("HorizonService", "监听服务");

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//
//
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();

         manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int five = 20000; // 这是5s
        long triggerAtTime = SystemClock.elapsedRealtime() + five;
        Intent i = new Intent(this, AlarmReceiver.class);
         pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        manager.cancel(pi);
        super.onDestroy();
    }

    private class MyReceiver  extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            //Log.d(TAG, "intent:" + intent.getAction());

            if (intent.getAction().equals("guanbi333")) {
                Log.d("MyReceiver", "收到广告");
                manager.cancel(pi);
                //String time=(System.currentTimeMillis())+"";
                //	xiaoshi.setText(DateUtils.timeMinute(time));
                //	riqi.setText(DateUtils.timesTwo(time));
                //xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));

            }
        }
    }
}