package com.seuic.keycode;
import com.seuic.keycode.OnKeyDownDetectedListener;
  
interface IKeyService {  
        void registListener(in OnKeyDownDetectedListener listener);
        void startRecord();
        void stopRecord();
        boolean isRcord();
        int[] getAllKeyCode();
        int[] getAllKeyNum();
}