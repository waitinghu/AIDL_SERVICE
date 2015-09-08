package com.seuic.keycode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class KeyCodeReceiver extends BroadcastReceiver {

    public static final String ACTION_RECEIVER = "com.odm.action.KEY_DOWN";
    
    public OnKeyCodeReceiveListener listener;
    
    public interface OnKeyCodeReceiveListener {
        void onKeyCodeReceive(KeyCodeMap key);
    }
    
    public void setOnKeyCodeReceiveListener (OnKeyCodeReceiveListener listener) {
        if(listener != null) {
            this.listener = listener;
        }
     }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(ACTION_RECEIVER.equals(action)) {
            int keyCode = intent.getIntExtra("key_code", -1);
            KeyCodeMap key = KeyCodeMap.findTypeByCode(keyCode);
            if(listener != null) {
                listener.onKeyCodeReceive(key);
            }
        }
    }
    
}
