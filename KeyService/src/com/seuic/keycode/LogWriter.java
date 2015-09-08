package com.seuic.keycode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

public class LogWriter {

    public static final String DEFUALT_FILE_PATH = Environment
            .getExternalStorageDirectory() + File.separator + "keycode" + File.separator;
    public static final String DEFUALT_FILE_NAME = "log.txt";
    
    private static LogWriter mLogWriter;
    private String mPath;
    private String mName;
    private Writer mWriter;
    private SimpleDateFormat df;

    private LogWriter(String path,String name) throws IOException {
        this.mPath = path;
        this.mName = name;
        File file = new File(path);
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            if(!file.exists()) {
                boolean isok = file.mkdir();
                Log.d("LogWriter", isok+"");
            }
        }
        mWriter = new BufferedWriter(new FileWriter(mPath + File.separator + mName, true), 2048);
        df = new SimpleDateFormat("[yy-MM-dd hh:mm:ss]: ",Locale.getDefault());
    }

    public static synchronized LogWriter open(String path,String name) throws IOException {
        if (mLogWriter == null) {
            mLogWriter = new LogWriter(path,name);
        }
        return mLogWriter;
    }
    
    public static LogWriter open() throws IOException {
        return open(DEFUALT_FILE_PATH,DEFUALT_FILE_NAME);
    }

    public void close() throws IOException {
        mWriter.close();
    }
    
    public void printEnd()throws IOException{
        mWriter.write("--------------------------------------"+new SimpleDateFormat("yy-MM-dd hh:mm:ss",Locale.getDefault()).format(new Date())+"------------------------------------------");
        mWriter.write("\r\n");
        mWriter.flush();
    }

    public void print(String log) throws IOException {
        mWriter.write(df.format(new Date()));
        mWriter.write(log);
        mWriter.write("\r\n");
        mWriter.flush();
    }

}
