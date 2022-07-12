package com.example.cropdoc;


import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.jcraft.jsch.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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
    public void sendKml() throws JSchException, SftpException, FileNotFoundException {
        if(session.isConnected()){
            //createKmlsRepo();
            String file = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                    "<Document>\n" +
                    "\t<name>Proyecto sin título</name>\n" +
                    "\t<gx:CascadingStyle kml:id=\"__managed_style_038561E3E121158D50C2\">\n" +
                    "\t\t<Style>\n" +
                    "\t\t\t<IconStyle>\n" +
                    "\t\t\t\t<scale>1.2</scale>\n" +
                    "\t\t\t\t<Icon>\n" +
                    "\t\t\t\t\t<href>https://earth.google.com/earth/rpc/cc/icon?color=f57c00&amp;id=2000&amp;scale=4</href>\n" +
                    "\t\t\t\t</Icon>\n" +
                    "\t\t\t\t<hotSpot x=\"64\" y=\"128\" xunits=\"pixels\" yunits=\"insetPixels\"/>\n" +
                    "\t\t\t</IconStyle>\n" +
                    "\t\t\t<LabelStyle>\n" +
                    "\t\t\t</LabelStyle>\n" +
                    "\t\t\t<LineStyle>\n" +
                    "\t\t\t\t<color>ff2dc0fb</color>\n" +
                    "\t\t\t\t<width>6</width>\n" +
                    "\t\t\t</LineStyle>\n" +
                    "\t\t\t<PolyStyle>\n" +
                    "\t\t\t\t<color>40ffffff</color>\n" +
                    "\t\t\t</PolyStyle>\n" +
                    "\t\t\t<BalloonStyle>\n" +
                    "\t\t\t\t<displayMode>hide</displayMode>\n" +
                    "\t\t\t</BalloonStyle>\n" +
                    "\t\t</Style>\n" +
                    "\t</gx:CascadingStyle>\n" +
                    "\t<gx:CascadingStyle kml:id=\"__managed_style_1FCDE21EB221158D50C1\">\n" +
                    "\t\t<Style>\n" +
                    "\t\t\t<IconStyle>\n" +
                    "\t\t\t\t<Icon>\n" +
                    "\t\t\t\t\t<href>https://earth.google.com/earth/rpc/cc/icon?color=f57c00&amp;id=2000&amp;scale=4</href>\n" +
                    "\t\t\t\t</Icon>\n" +
                    "\t\t\t\t<hotSpot x=\"64\" y=\"128\" xunits=\"pixels\" yunits=\"insetPixels\"/>\n" +
                    "\t\t\t</IconStyle>\n" +
                    "\t\t\t<LabelStyle>\n" +
                    "\t\t\t</LabelStyle>\n" +
                    "\t\t\t<LineStyle>\n" +
                    "\t\t\t\t<color>ff2dc0fb</color>\n" +
                    "\t\t\t\t<width>4</width>\n" +
                    "\t\t\t</LineStyle>\n" +
                    "\t\t\t<PolyStyle>\n" +
                    "\t\t\t\t<color>40ffffff</color>\n" +
                    "\t\t\t</PolyStyle>\n" +
                    "\t\t\t<BalloonStyle>\n" +
                    "\t\t\t\t<displayMode>hide</displayMode>\n" +
                    "\t\t\t</BalloonStyle>\n" +
                    "\t\t</Style>\n" +
                    "\t</gx:CascadingStyle>\n" +
                    "\t<StyleMap id=\"__managed_style_0E92984A7121158D50C1\">\n" +
                    "\t\t<Pair>\n" +
                    "\t\t\t<key>normal</key>\n" +
                    "\t\t\t<styleUrl>#__managed_style_1FCDE21EB221158D50C1</styleUrl>\n" +
                    "\t\t</Pair>\n" +
                    "\t\t<Pair>\n" +
                    "\t\t\t<key>highlight</key>\n" +
                    "\t\t\t<styleUrl>#__managed_style_038561E3E121158D50C2</styleUrl>\n" +
                    "\t\t</Pair>\n" +
                    "\t</StyleMap>\n" +
                    "\t<gx:CascadingStyle kml:id=\"__managed_style_2406BAA73021158CD002\">\n" +
                    "\t\t<Style>\n" +
                    "\t\t\t<IconStyle>\n" +
                    "\t\t\t\t<scale>1.2</scale>\n" +
                    "\t\t\t\t<Icon>\n" +
                    "\t\t\t\t\t<href>https://earth.google.com/earth/rpc/cc/icon?color=d32f2f&amp;id=2000&amp;scale=4</href>\n" +
                    "\t\t\t\t</Icon>\n" +
                    "\t\t\t\t<hotSpot x=\"64\" y=\"128\" xunits=\"pixels\" yunits=\"insetPixels\"/>\n" +
                    "\t\t\t</IconStyle>\n" +
                    "\t\t\t<LabelStyle>\n" +
                    "\t\t\t</LabelStyle>\n" +
                    "\t\t\t<LineStyle>\n" +
                    "\t\t\t\t<color>ff2dc0fb</color>\n" +
                    "\t\t\t\t<width>6</width>\n" +
                    "\t\t\t</LineStyle>\n" +
                    "\t\t\t<PolyStyle>\n" +
                    "\t\t\t\t<color>40ffffff</color>\n" +
                    "\t\t\t</PolyStyle>\n" +
                    "\t\t\t<BalloonStyle>\n" +
                    "\t\t\t\t<displayMode>hide</displayMode>\n" +
                    "\t\t\t</BalloonStyle>\n" +
                    "\t\t</Style>\n" +
                    "\t</gx:CascadingStyle>\n" +
                    "\t<gx:CascadingStyle kml:id=\"__managed_style_13FF62C6F321158CD002\">\n" +
                    "\t\t<Style>\n" +
                    "\t\t\t<IconStyle>\n" +
                    "\t\t\t\t<Icon>\n" +
                    "\t\t\t\t\t<href>https://earth.google.com/earth/rpc/cc/icon?color=d32f2f&amp;id=2000&amp;scale=4</href>\n" +
                    "\t\t\t\t</Icon>\n" +
                    "\t\t\t\t<hotSpot x=\"64\" y=\"128\" xunits=\"pixels\" yunits=\"insetPixels\"/>\n" +
                    "\t\t\t</IconStyle>\n" +
                    "\t\t\t<LabelStyle>\n" +
                    "\t\t\t</LabelStyle>\n" +
                    "\t\t\t<LineStyle>\n" +
                    "\t\t\t\t<color>ff2dc0fb</color>\n" +
                    "\t\t\t\t<width>4</width>\n" +
                    "\t\t\t</LineStyle>\n" +
                    "\t\t\t<PolyStyle>\n" +
                    "\t\t\t\t<color>40ffffff</color>\n" +
                    "\t\t\t</PolyStyle>\n" +
                    "\t\t\t<BalloonStyle>\n" +
                    "\t\t\t\t<displayMode>hide</displayMode>\n" +
                    "\t\t\t</BalloonStyle>\n" +
                    "\t\t</Style>\n" +
                    "\t</gx:CascadingStyle>\n" +
                    "\t<gx:CascadingStyle kml:id=\"__managed_style_2097C6378321158C243B\">\n" +
                    "\t\t<Style>\n" +
                    "\t\t\t<IconStyle>\n" +
                    "\t\t\t\t<scale>1.2</scale>\n" +
                    "\t\t\t\t<Icon>\n" +
                    "\t\t\t\t\t<href>https://earth.google.com/earth/rpc/cc/icon?color=1976d2&amp;id=2000&amp;scale=4</href>\n" +
                    "\t\t\t\t</Icon>\n" +
                    "\t\t\t\t<hotSpot x=\"64\" y=\"128\" xunits=\"pixels\" yunits=\"insetPixels\"/>\n" +
                    "\t\t\t</IconStyle>\n" +
                    "\t\t\t<LabelStyle>\n" +
                    "\t\t\t</LabelStyle>\n" +
                    "\t\t\t<LineStyle>\n" +
                    "\t\t\t\t<color>ff3c8e38</color>\n" +
                    "\t\t\t\t<width>6</width>\n" +
                    "\t\t\t</LineStyle>\n" +
                    "\t\t\t<PolyStyle>\n" +
                    "\t\t\t\t<color>40ffffff</color>\n" +
                    "\t\t\t</PolyStyle>\n" +
                    "\t\t\t<BalloonStyle>\n" +
                    "\t\t\t\t<displayMode>hide</displayMode>\n" +
                    "\t\t\t</BalloonStyle>\n" +
                    "\t\t</Style>\n" +
                    "\t</gx:CascadingStyle>\n" +
                    "\t<gx:CascadingStyle kml:id=\"__managed_style_1A5A807FB721158C243B\">\n" +
                    "\t\t<Style>\n" +
                    "\t\t\t<IconStyle>\n" +
                    "\t\t\t\t<Icon>\n" +
                    "\t\t\t\t\t<href>https://earth.google.com/earth/rpc/cc/icon?color=1976d2&amp;id=2000&amp;scale=4</href>\n" +
                    "\t\t\t\t</Icon>\n" +
                    "\t\t\t\t<hotSpot x=\"64\" y=\"128\" xunits=\"pixels\" yunits=\"insetPixels\"/>\n" +
                    "\t\t\t</IconStyle>\n" +
                    "\t\t\t<LabelStyle>\n" +
                    "\t\t\t</LabelStyle>\n" +
                    "\t\t\t<LineStyle>\n" +
                    "\t\t\t\t<color>ff3c8e38</color>\n" +
                    "\t\t\t\t<width>4</width>\n" +
                    "\t\t\t</LineStyle>\n" +
                    "\t\t\t<PolyStyle>\n" +
                    "\t\t\t\t<color>40ffffff</color>\n" +
                    "\t\t\t</PolyStyle>\n" +
                    "\t\t\t<BalloonStyle>\n" +
                    "\t\t\t\t<displayMode>hide</displayMode>\n" +
                    "\t\t\t</BalloonStyle>\n" +
                    "\t\t</Style>\n" +
                    "\t</gx:CascadingStyle>\n" +
                    "\t<StyleMap id=\"__managed_style_0D06AC1E4421158CD002\">\n" +
                    "\t\t<Pair>\n" +
                    "\t\t\t<key>normal</key>\n" +
                    "\t\t\t<styleUrl>#__managed_style_13FF62C6F321158CD002</styleUrl>\n" +
                    "\t\t</Pair>\n" +
                    "\t\t<Pair>\n" +
                    "\t\t\t<key>highlight</key>\n" +
                    "\t\t\t<styleUrl>#__managed_style_2406BAA73021158CD002</styleUrl>\n" +
                    "\t\t</Pair>\n" +
                    "\t</StyleMap>\n" +
                    "\t<StyleMap id=\"__managed_style_0006867CED21158C243B\">\n" +
                    "\t\t<Pair>\n" +
                    "\t\t\t<key>normal</key>\n" +
                    "\t\t\t<styleUrl>#__managed_style_1A5A807FB721158C243B</styleUrl>\n" +
                    "\t\t</Pair>\n" +
                    "\t\t<Pair>\n" +
                    "\t\t\t<key>highlight</key>\n" +
                    "\t\t\t<styleUrl>#__managed_style_2097C6378321158C243B</styleUrl>\n" +
                    "\t\t</Pair>\n" +
                    "\t</StyleMap>\n" +
                    "\t<Placemark id=\"0C6AE501E1211587FF45\">\n" +
                    "\t\t<name>Terreny</name>\n" +
                    "\t\t<LookAt>\n" +
                    "\t\t\t<longitude>0.6014081453723596</longitude>\n" +
                    "\t\t\t<latitude>41.616115410079</latitude>\n" +
                    "\t\t\t<altitude>166.4212527401993</altitude>\n" +
                    "\t\t\t<heading>0</heading>\n" +
                    "\t\t\t<tilt>0</tilt>\n" +
                    "\t\t\t<gx:fovy>35</gx:fovy>\n" +
                    "\t\t\t<range>395.6314229275449</range>\n" +
                    "\t\t\t<altitudeMode>absolute</altitudeMode>\n" +
                    "\t\t</LookAt>\n" +
                    "\t\t<styleUrl>#__managed_style_0006867CED21158C243B</styleUrl>\n" +
                    "\t\t<Polygon>\n" +
                    "\t\t\t<outerBoundaryIs>\n" +
                    "\t\t\t\t<LinearRing>\n" +
                    "\t\t\t\t\t<coordinates>\n" +
                    "\t\t\t\t\t\t0.6012846530189164,41.61548570487557,166.500530308405 0.6018259724456798,41.61542439651501,166.6397812857483 0.6021394764712795,41.61669292380434,166.5751255389499 0.6015219319000398,41.61673451053046,167.2258174640018 0.6012846530189164,41.61548570487557,166.500530308405\n" +
                    "\t\t\t\t\t</coordinates>\n" +
                    "\t\t\t\t</LinearRing>\n" +
                    "\t\t\t</outerBoundaryIs>\n" +
                    "\t\t</Polygon>\n" +
                    "\t</Placemark>\n" +
                    "\t<Placemark id=\"0F1638BE1621158C67B2\">\n" +
                    "\t\t<name>Infected tree</name>\n" +
                    "\t\t<LookAt>\n" +
                    "\t\t\t<longitude>0.6014081453723596</longitude>\n" +
                    "\t\t\t<latitude>41.616115410079</latitude>\n" +
                    "\t\t\t<altitude>166.4212527401993</altitude>\n" +
                    "\t\t\t<heading>0</heading>\n" +
                    "\t\t\t<tilt>0</tilt>\n" +
                    "\t\t\t<gx:fovy>35</gx:fovy>\n" +
                    "\t\t\t<range>395.6314229275449</range>\n" +
                    "\t\t\t<altitudeMode>absolute</altitudeMode>\n" +
                    "\t\t</LookAt>\n" +
                    "\t\t<styleUrl>#__managed_style_0D06AC1E4421158CD002</styleUrl>\n" +
                    "\t\t<Point>\n" +
                    "\t\t\t<coordinates>0.6016956183941469,41.61648620034892,167.4353637032648</coordinates>\n" +
                    "\t\t</Point>\n" +
                    "\t</Placemark>\n" +
                    "\t<Placemark id=\"037D90EA7621158D00C4\">\n" +
                    "\t\t<name>Infected tree</name>\n" +
                    "\t\t<LookAt>\n" +
                    "\t\t\t<longitude>0.6014081453723596</longitude>\n" +
                    "\t\t\t<latitude>41.616115410079</latitude>\n" +
                    "\t\t\t<altitude>166.4212527401993</altitude>\n" +
                    "\t\t\t<heading>0</heading>\n" +
                    "\t\t\t<tilt>0</tilt>\n" +
                    "\t\t\t<gx:fovy>35</gx:fovy>\n" +
                    "\t\t\t<range>395.6314229275449</range>\n" +
                    "\t\t\t<altitudeMode>absolute</altitudeMode>\n" +
                    "\t\t</LookAt>\n" +
                    "\t\t<styleUrl>#__managed_style_0E92984A7121158D50C1</styleUrl>\n" +
                    "\t\t<Point>\n" +
                    "\t\t\t<coordinates>0.6017395820287597,41.61585346355983,167.7448095566884</coordinates>\n" +
                    "\t\t</Point>\n" +
                    "\t</Placemark>\n" +
                    "</Document>\n" +
                    "</kml>";

            String remoteFile = "/var/www/html/kmls/kmlReader.kml";
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ByteArrayInputStream in = new ByteArrayInputStream(file.getBytes(StandardCharsets.UTF_8));
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.put(in, remoteFile);
        }
    }
    public void createKmlsRepo() throws JSchException {
        if(session.isConnected()){
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("mkdir /var/www/html/kmls");
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
        }
    }

}


