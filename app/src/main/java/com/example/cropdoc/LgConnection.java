package com.example.cropdoc;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import com.jcraft.jsch.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

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
            channel.connect();
        } else {
            System.out.println("Connect first");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendKml(String terrain, String trees) throws JSchException, SftpException {
        if(session.isConnected()){
            //createKmlsRepo();

            createKmlsRepo();
            String finalkml = createKml(terrain);
            String lgdirection = "http://"+host+":81/kmls/kmlReader.kml"+"?id="+ZonedDateTime.now().toString();
            String remoteKml = "/var/www/html/kmls/kmlReader.kml";
            String remoteTxt = "/var/www/html/kmls.txt";
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ByteArrayInputStream in = new ByteArrayInputStream(finalkml.getBytes(StandardCharsets.UTF_8));
            ByteArrayInputStream in2 = new ByteArrayInputStream(lgdirection.getBytes(StandardCharsets.UTF_8));
            ChannelSftp channelSftp = (ChannelSftp) channel;
            sendFylTo("0.6017395820287597","41.61585346355983","167.7448095566884","0","5","200","1.2");
            channelSftp.put(in, remoteKml);
            channelSftp.put(in2,remoteTxt);
        }
    }
    public void createKmlsRepo() throws JSchException {
        if(session.isConnected()){
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("mkdir /var/www/html/kmls");
            ((ChannelExec) channel).setCommand("touch /var/www/html/kmls/kmlReader.kml");
            ((ChannelExec) channel).setCommand("touch /var/www/html/kmls/orbit.kml");
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
        }
    }
    public void deleteKmls() throws JSchException {
        if(session.isConnected()){
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("rm -r /var/www/html/kmls");
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
        }
    }
    
    public void sendFylTo(String lat,String lon,String altitude,String heading,String tilt,String  pRange,String duration) throws JSchException{
        String kml = "<LookAt>" +
                "<longitude>" + lat + "</longitude>" +
                "<latitude>" + lon + "</latitude>" +
                "<altitude>" + altitude + "</altitude>" +
                "<heading>" + heading + "</heading>" +
                "<tilt>" + tilt + "</tilt>" +
                "<range>" + pRange + "</range>" +
                "<gx:fovy>35</gx:fovy>" +
                "<altitudeMode>relativeToGround</altitudeMode>" +
                "<gx:duration>" + duration + "</gx:duration>" +
                "</LookAt>";
        if (session.isConnected()) {
            String command= "echo 'flytoview=" + kml +"'| cat > /tmp/query.txt";
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generateAndSendOrbit(String lat, String lon, String altitude, String heading, String tilt, String  pRange) throws JSchException, SftpException {
        String orbit = "";
        orbit += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        orbit += "<kml xmlns=\"http://www.opengis.net/kml/2.2\"\n";
        orbit += "xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n";
        orbit += "<gx:Tour>\n";
        orbit += "\t<name>Orbit</name>\n";
        orbit += "\t<gx:Playlist>\n";

        int o;

        for (o = 0;o <= 1400;o+=20){
            orbit += "\t\t<gx:FlyTo>\n";
            orbit += "\t\t\t<gx:duration>1.2</gx:duration>\n";
            orbit += "\t\t\t<gx:flyToMode>smooth</gx:flyToMode>\n";
            orbit += "\t\t\t<LookAt>\n";
            orbit += "\t\t\t\t<longitude>"+lon+"</longitude>\n";
            orbit += "\t\t\t\t<latitude>"+lat+"</latitude>\n";
            orbit += "\t\t\t\t<altitude>"+altitude+"</altitude>\n";
            orbit += "\t\t\t\t<heading>"+o+"</heading>\n";
            orbit += "\t\t\t\t<tilt>"+tilt+"</tilt>\n";
            orbit += "\t\t\t\t<gx:fovy>35</gx:fovy>\n";
            orbit += "\t\t\t\t<range>"+pRange+"</range>\n";
            orbit += "\t\t\t\t<gx:altitudeMode>relativeToGround</gx:altitudeMode>\n";
            orbit += "\t\t\t</LookAt>\n";
            orbit += "\t\t</gx:FlyTo>\n";
        }
        orbit += "\t</gx:Playlist>\n";
        orbit += "</gx:Tour>\n";
        orbit += "</kml>\n";
        System.out.println("fora bucle");
        if (session.isConnected()) {
            String lgdirection = "http://"+host+":81/kmls/kmlReader.kml"+"?id="+ZonedDateTime.now().toString()+"\n"+"http://"+host+":81/kmls/orbit.kml"+"?id="+ZonedDateTime.now().toString();

            String remoteKml = "/var/www/html/kmls/orbit.kml";
            String remoteTxt = "/var/www/html/kmls.txt";
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ByteArrayInputStream in = new ByteArrayInputStream(orbit.getBytes(StandardCharsets.UTF_8));
            ByteArrayInputStream in2 = new ByteArrayInputStream(lgdirection.getBytes(StandardCharsets.UTF_8));
            ChannelSftp channelSftp = (ChannelSftp) channel;
            //sendFylTo("0.6017395820287597","41.61585346355983","167.7448095566884","0","5","1000","1.2");
            channelSftp.put(in, remoteKml);
            channelSftp.put(in2,remoteTxt);
            startOrbit();
        }
    }
    private void startOrbit() throws JSchException {
        if (session.isConnected()) {
            String command= "echo 'playtour=Orbit' > /tmp/query.txt";
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
        }
    }

    private String createKml(String points){
        List list = Arrays.asList(points.split(","));

        String kml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "<Document>\n" +
                "\t<name>Building 40</name>\n" +
                "\t<gx:CascadingStyle kml:id=\"__managed_style_2F473FA9AE2350AE102F\">\n" +
                "\t\t<Style>\n" +
                "\t\t\t<IconStyle>\n" +
                "\t\t\t\t<scale>1.44</scale>\n" +
                "\t\t\t\t<Icon>\n" +
                "\t\t\t\t\t<href>https://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>\n" +
                "\t\t\t\t</Icon>\n" +
                "\t\t\t</IconStyle>\n" +
                "\t\t\t<LabelStyle>\n" +
                "\t\t\t</LabelStyle>\n" +
                "\t\t\t<LineStyle>\n" +
                "\t\t\t\t<color>ff2f2fd3</color>\n" +
                "\t\t\t\t<width>1.5</width>\n" +
                "\t\t\t</LineStyle>\n" +
                "\t\t\t<PolyStyle>\n" +
                "\t\t\t\t<color>405053ef</color>\n" +
                "\t\t\t</PolyStyle>\n" +
                "\t\t\t<BalloonStyle>\n" +
                "\t\t\t</BalloonStyle>\n" +
                "\t\t</Style>\n" +
                "\t</gx:CascadingStyle>\n" +
                "\t<gx:CascadingStyle kml:id=\"__managed_style_1612DF88E12350AE102F\">\n" +
                "\t\t<Style>\n" +
                "\t\t\t<IconStyle>\n" +
                "\t\t\t\t<scale>1.2</scale>\n" +
                "\t\t\t\t<Icon>\n" +
                "\t\t\t\t\t<href>https://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>\n" +
                "\t\t\t\t</Icon>\n" +
                "\t\t\t</IconStyle>\n" +
                "\t\t\t<LabelStyle>\n" +
                "\t\t\t</LabelStyle>\n" +
                "\t\t\t<LineStyle>\n" +
                "\t\t\t\t<color>ff2f2fd3</color>\n" +
                "\t\t\t</LineStyle>\n" +
                "\t\t\t<PolyStyle>\n" +
                "\t\t\t\t<color>405053ef</color>\n" +
                "\t\t\t</PolyStyle>\n" +
                "\t\t\t<BalloonStyle>\n" +
                "\t\t\t</BalloonStyle>\n" +
                "\t\t</Style>\n" +
                "\t</gx:CascadingStyle>\n" +
                "\t<StyleMap id=\"__managed_style_0A4E8DF9442350AE102F\">\n" +
                "\t\t<Pair>\n" +
                "\t\t\t<key>normal</key>\n" +
                "\t\t\t<styleUrl>#__managed_style_1612DF88E12350AE102F</styleUrl>\n" +
                "\t\t</Pair>\n" +
                "\t\t<Pair>\n" +
                "\t\t\t<key>highlight</key>\n" +
                "\t\t\t<styleUrl>#__managed_style_2F473FA9AE2350AE102F</styleUrl>\n" +
                "\t\t</Pair>\n" +
                "\t</StyleMap>\n" +
                "\t<Placemark id=\"0DD45D6B5C2350AD6141\">\n" +
                "\t\t<name>Building 40</name>\n" +
                "\t\t<styleUrl>#__managed_style_0A4E8DF9442350AE102F</styleUrl>\n" +
                "\t\t<Polygon>\n" +
                "\t\t\t<outerBoundaryIs>\n" +
                "\t\t\t\t<LinearRing>\n" +
                "\t\t\t\t\t<coordinates>\n" +
                "\t\t\t\t\t\t"+points+"\n" +
                "\t\t\t\t\t</coordinates>\n" +
                "\t\t\t\t</LinearRing>\n" +
                "\t\t\t</outerBoundaryIs>\n" +
                "\t\t</Polygon>\n" +
                "\t</Placemark>\n" +
                "</Document>\n" +
                "</kml>";
        return kml;
    }
}


