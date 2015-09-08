package com.seuic.keycode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.seuic.keycode.KeyCodeReceiver.OnKeyCodeReceiveListener;

public class KeyMappingRecordService extends Service implements
        OnKeyCodeReceiveListener {

    public static final String DEFUALT_FILE_NAME = Environment
            .getExternalStorageDirectory() + File.separator + "keycode/log.txt";
    
    public static final String TAG = "KeyMappingRecordService";
    
    private Map<KeyCodeMap, Integer> mTimes = new HashMap<KeyCodeMap, Integer>();
    private List<KeyCodeMap> mAllDetectedKey = new ArrayList<KeyCodeMap>();
    private static LogWriter mWriter ;
    private KeyCodeReceiver receiver;
    
    private OnKeyDownDetectedListener mListener;
    
    private boolean isRecord;
    
    public class ServiceImpl extends IKeyService.Stub {

        @Override
        public void registListener(OnKeyDownDetectedListener listener)
                throws RemoteException {
                mListener = listener;
        }

        @Override
        public void startRecord() throws RemoteException {
            if(isRecord) {
                return;
            }
            init();
            isRecord = true;
        }

        @Override
        public void stopRecord() throws RemoteException {
            if(!isRecord){
                return;
            }
            mTimes.clear();
            mAllDetectedKey.clear();
            closeWriter();
            unregisterReceiver(receiver);
            isRecord = false;
        }

        @Override
        public boolean isRcord() throws RemoteException {
            return isRecord;
        }

        @Override
        public int[] getAllKeyCode() throws RemoteException {
            int size = mAllDetectedKey.size();
            int[] keyCode= new int[size];
            for (int i = 0; i < size; i++) {
                keyCode[i] = mAllDetectedKey.get(i).getCode();
            }
            return keyCode;
        }

        @Override
        public int[] getAllKeyNum() throws RemoteException {
            int size = mAllDetectedKey.size();
            int[] result = new int[size];
            for (int i = 0; i < size; i++) {
                result[i] = mTimes.get(mAllDetectedKey.get(i));
            }
            return result;
        }
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceImpl();
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
        mListener = null;
        return false;
    }
    
    private void init() {
        receiver = new KeyCodeReceiver();
        receiver.setOnKeyCodeReceiveListener(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(KeyCodeReceiver.ACTION_RECEIVER);
        registerReceiver(receiver, filter);
        try {
            mWriter = LogWriter.open();
        } catch (IOException e) {
            Log.e(TAG, "fail to open :"+ LogWriter.DEFUALT_FILE_PATH+LogWriter.DEFUALT_FILE_NAME);
            e.printStackTrace();
        }
    }
    
    @Override
    public void onKeyCodeReceive(KeyCodeMap key) {
            if (mAllDetectedKey.contains(key)) {
                int time = mTimes.remove(key);
                mTimes.put(key, time + 1);
            } else {
                mAllDetectedKey.add(key);
                mTimes.put(key, 1);
            }
            
            try {
                if(mWriter == null) {
                    mWriter = LogWriter.open();
                }
                mWriter.print(getLogString(key));
            } catch (IOException e) {
              Log.e(TAG, "fail to writer log");
              e.printStackTrace();
            }
            
            if(mListener != null) {
                //callback use to update the UI
                try {
                    mListener.onKeyDownDetected(key.getCode(),mTimes.get(key));
                } catch (RemoteException e) {
                    Log.e(TAG, "fail to pass remote");
                    e.printStackTrace();
                }
            }
    }
    
    
    @Override
    public void onDestroy() {
        closeWriter();
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void closeWriter() {
        if(mWriter != null) {
            try {
                mWriter.printEnd();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                mWriter.close();
                mWriter = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getLogString(KeyCodeMap key) {
        String log = "键值 : " + key.getCode() + "  按键描述: " + key.getDesc() + "  累计按键次数: " +mTimes.get(key);
        return log;
    }

}
