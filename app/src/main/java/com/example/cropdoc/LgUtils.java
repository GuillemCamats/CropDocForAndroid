package com.example.cropdoc;

import android.os.StrictMode;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.util.concurrent.TimeUnit;


public class LgUtils {
    static String user;
    static String password;
    static String host;
    static int port;
    JSch jsch;
    Session session;

    public LgUtils(String user,String password,String host, int port) {
        LgUtils.host = host;
        LgUtils.port = port;
        LgUtils.password = password;
        LgUtils.user = user;
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

    public void setRefresh(){
        String x = "0";
        String search = "<href>##LG_PHPIFACE##kml/slave_"+x+".kml</href>";
        String replace = "<href>##LG_PHPIFACE##kml/slave"+x+".kml</href><refreshMode>onInterval</refreshMode><refreshInterval>2</refreshInterval>";

        String command = "echo "+LgUtils.password+" | sudo -S sed -i \"s/"+search+"/"+replace+"/\" ~/earth/kml/slave/myplaces.kml";
        String clear = "echo "+LgUtils.password+" | sudo -S sed -i \"s/"+replace+"/"+search+"/\" ~/earth/kml/slave/myplaces.kml";

        for (int i = 2; i <= 5; i++) {
            String clearcmd = clear.replace(x,Integer.toString(i));
            String cmd = command.replace(x,Integer.toString(i));
            String query = "sshpass -p "+LgUtils.password+" ssh -t lg"+i+" "+cmd;

            try {
                sendCommand(query.replace(cmd,clearcmd));
                TimeUnit.SECONDS.sleep(2);
                sendCommand(query.replace(cmd,cmd));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        reboot();
    }



    public void resetRefresh(){
        String x = "0";
        String replace = "<href>##LG_PHPIFACE##kml/slave_"+x+".kml</href>";
        String search = "<href>##LG_PHPIFACE##kml/slave"+x+".kml</href><refreshMode>onInterval</refreshMode><refreshInterval>2</refreshInterval>";
        String clear = "echo "+LgUtils.password+" | sudo -S sed -i \"s/"+search+"/"+replace+"/\" ~/earth/kml/slave/myplaces.kml";

        for (int i = 2; i <= 5; i++) {
            String cmd = clear.replace(x,Integer.toString(i));
            String query = "sshpass -p "+LgUtils.password+" ssh -t lg"+i+" "+cmd;

            try {
                sendCommand(query);
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        reboot();
    }

    public void sendCommand(String command) throws JSchException {
        if(session.isConnected()){
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
        } else {
            System.out.println("Connect first");
        }
    }
    public void reboot() {
        for (int i = 5; i >= 1; i--) {
            try{
                sendCommand("sshpass -p "+LgUtils.password+" ssh -t lg"+i+" 'echo "+LgUtils.password+" | sudo -S reboot'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void relaunch(){
        for (int i = 5; i >= 1; i--) {
            try{
                String relaunchCommand = "RELAUNCH_CMD=\"\\ " +
                        "if [ -f /etc/init/lxdm.conf ]; then " +
                        "  export SERVICE=lxdm " +
                        "elif [ -f /etc/init/lightdm.conf ]; then" +
                        "  export SERVICE=lightdm " +
                        "else " +
                        "  exit 1 " +
                        "fi " +
                        "if  [[ \\\\\\$(service \\\\\\$SERVICE status) =~ 'stop' ]]; then " +
                        "  echo "+LgUtils.password+" | sudo -S service \\\\\\${SERVICE} start " +
                        "else " +
                        "  echo "+LgUtils.password+" | sudo -S service \\\\\\${SERVICE} restart " +
                        "fi " +
                        "\" && sshpass -p "+LgUtils.password+" ssh -x -t lg@lg"+i+" \"\\$RELAUNCH_CMD\"";
                sendCommand("'/home/$user/bin/lg-relaunch' > /home/$user/log.txt");
                sendCommand(relaunchCommand);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void shutdow(){
        for (int i = 5; i >= 1; i--) {
            try{
                sendCommand("sshpass -p "+LgUtils.password+" ssh -t lg"+i+" 'echo "+LgUtils.password+" | sudo -S poweroff'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
