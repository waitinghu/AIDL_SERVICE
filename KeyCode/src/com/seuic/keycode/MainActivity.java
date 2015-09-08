package com.seuic.keycode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {

    ListView mList;

    private List<KeyCodeMap> AllDetectedKey;
    private Map<KeyCodeMap, Integer> Times;
    private KeyCodeAdapter adapter;

    private Button mSerivceButton;
    private IKeyService mService;
    private Handler handler;
    
    ServiceConnection conn = new ServiceConnection() {  
        
        @Override  
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity", "service was disconnected");
            handler.sendEmptyMessage(MSG_CONN_DISCONN);
        }  
        @Override  
        public void onServiceConnected(ComponentName name, IBinder service) {  
              mService = IKeyService.Stub.asInterface(service);
              try {
                mService.registListener(new MyListenr());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
           handler.sendEmptyMessage(MSG_CONN_COMPLETE);
        }  
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AllDetectedKey = new ArrayList<KeyCodeMap>();
        Times = new HashMap<KeyCodeMap, Integer>();
        mSerivceButton = (Button) findViewById(R.id.bt_start);
        mSerivceButton.setOnClickListener(this);
        mList = (ListView) findViewById(R.id.list_key);
        adapter = new KeyCodeAdapter();
        mList.setAdapter(adapter);
        handler = new MyHander();
        Intent intent = new Intent("com.seuic.service.KEY_RECORD");
        if(!isBackgroundRunning()){
            startService(intent);
        }
        bindService(intent,conn,BIND_AUTO_CREATE);
        
//        loadingDialog = new ProgressDialog(this);
//        loadingDialog.setTitle("数据加载");
//        loadingDialog.setMessage("正在加载数据请稍等...");
//        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        loadingDialog.setCancelable(false);
//        loadingDialog.show();
    }
    
    @Override
    protected void onDestroy() {
        if(mService != null) {
            try {
              mService.registListener(null);
          } catch (RemoteException e) {
              e.printStackTrace();
          }
        }
        super.onDestroy();
    }  
    
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void updateData() {
        if(mService != null) {
            try {
                int[] code = mService.getAllKeyCode();
                int[] num = mService.getAllKeyNum();
                for (int i = 0; i < num.length; i++) {
                    KeyCodeMap key = KeyCodeMap.findTypeByCode(code[i]);
                    if(!AllDetectedKey.contains(key)){
                        AllDetectedKey.add(key);
                    }
                    if(Times.containsKey(key)) {
                        Times.remove(key);
                    }
                    Times.put(key, num[i]);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (mService.isRcord()) {
                mService.stopRecord();
                updateButtonDesc(false);
            } else {
                mService.startRecord();
                updateButtonDesc(true);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateButtonDesc(boolean isRun) {
        String dec = "开启按键记录";
        if (isRun) {
            dec = "停止按键记录";
        }
        if (mSerivceButton != null) {
            mSerivceButton.setText(dec);
        }
    }
    
    private static final int MSG_UPDATE_LIST = 1;
    private static final int MSG_CONN_DISCONN = 2;
    private static final int MSG_CONN_COMPLETE = 3;
    
    class MyHander extends Handler{
        @Override
        public void handleMessage(Message msg) {
            
            if(msg.what == MSG_UPDATE_LIST) {
                adapter.notifyDataSetChanged();
            } else if(msg.what == MSG_CONN_DISCONN){
                updateButtonDesc(false);
            } else if (msg.what == MSG_CONN_COMPLETE) {
                boolean isRecord = false;
                updateData();
                try {
                    if(mService != null) {
                        isRecord = mService.isRcord();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                updateButtonDesc(isRecord);
                adapter.notifyDataSetChanged();
            }
        }
    }

//    public void onKeyDownDetected(Map<KeyCodeMap, Integer> mTimes,
//            List<KeyCodeMap> allDetectedKey) {
//        this.AllDetectedKey = allDetectedKey;
//        this.Times = mTimes;
//        adapter.notifyDataSetChanged();
//    }
    
    class MyListenr extends OnKeyDownDetectedListener.Stub {
        @Override
        public void onKeyDownDetected(int keyCode, int times)
                throws RemoteException {
            updateData();
            handler.sendEmptyMessage(MSG_UPDATE_LIST);
        }
        
    }
    
    private boolean isBackgroundRunning() {
        String processName = "com.seuic.keyservice";
        
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        if (activityManager == null) {
            return false;
        }
        // get running application processes
        List<ActivityManager.RunningAppProcessInfo> processList = activityManager.getRunningAppProcesses();
        
        for (ActivityManager.RunningAppProcessInfo process : processList) {
            if (process.processName.startsWith(processName)) {
                return true;
            }
        }
        return false;
    }
    
    
    class KeyCodeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return AllDetectedKey.size();
        }

        @Override
        public Object getItem(int position) {
            return AllDetectedKey.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(
                        R.layout.keycode_item, null);
                holder = new ViewHolder();

                TextView key = (TextView) convertView.findViewById(R.id.key_tv);
                TextView keyCode = (TextView) convertView
                        .findViewById(R.id.key_code_tv);
                TextView keyDownTimes = (TextView) convertView
                        .findViewById(R.id.key_down_times);

                holder.key = key;
                holder.keyCode = keyCode;
                holder.keyDownTimes = keyDownTimes;
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            KeyCodeMap key = AllDetectedKey.get(position);
            holder.key.setText(key.getDesc());
            holder.keyCode.setText(key.getCode() + "");
            holder.keyDownTimes.setText(Times.get(key) + "");
            return convertView;
        }

    }

    static class ViewHolder {
        TextView keyCode;
        TextView key;
        TextView keyDownTimes;
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
