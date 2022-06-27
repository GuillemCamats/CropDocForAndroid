package com.example.cropdoc;


import com.jcraft.jsch.*;

public class LgConnection {
    static String user;
    static String password;
    static String host;
    static int port;
    String remoteFile;
    public LgConnection(String user,String password,String host, int port){
        LgConnection.host =host;
        LgConnection.port =port;
        LgConnection.password=password;
        LgConnection.user=user;
    }

    public void connect(){
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            System.out.println("Establishing Connection...");
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

}


