package com.example.cropdoc;


import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LgConnection {
    static String user;
    static String password;
    static String host;
    static int port;
    JSch jsch;
    Session session;
    public LgConnection(String user,String password,String host, int port){
        LgConnection.host =host;
        LgConnection.port =port;
        LgConnection.password=password;
        LgConnection.user=user;
    }


    public void connectD(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            System.out.println("Establishing Connection...");
            session.setTimeout(Integer.MAX_VALUE);
            session.connect();
            System.out.println("Connected");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
    public void sendCommand(String command) throws JSchException, IOException {
        if(session.isConnected()){
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();
        } else {
            System.out.println("Connect first");
        }
    }

}


