package com.example.cropdoc;

import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;
import com.jcraft.jsch.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
            setLogo();
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
    public void sendKml(Terrains terrain) throws JSchException, SftpException {
        if(session.isConnected()){


            createKmlsRepo();
            String finalkml = createKml(terrain);
            String lat = String.valueOf(terrain.trees.get(0).coordinates.latitude);
            String lon = String.valueOf(terrain.trees.get(0).coordinates.longitude);
            String lgdirection = "http://"+host+":81/kmls/kmlReader.kml"+"?id="+ZonedDateTime.now().toString();
            String remoteKml = "/var/www/html/kmls/kmlReader.kml";
            String remoteTxt = "/var/www/html/kmls.txt";
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ByteArrayInputStream in = new ByteArrayInputStream(finalkml.getBytes(StandardCharsets.UTF_8));
            ByteArrayInputStream in2 = new ByteArrayInputStream(lgdirection.getBytes(StandardCharsets.UTF_8));
            ChannelSftp channelSftp = (ChannelSftp) channel;
            sendFylTo(lat,lon,"0","0","5","500","1.2");
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
    
    public void sendFylTo(String lat,String lon,String altitude,String heading,String tilt,String  pRange,String duration) throws JSchException{
        String kml = "<LookAt>" +
                "<longitude>" + lon + "</longitude>" +
                "<latitude>" + lat + "</latitude>" +
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
    public void generateAndSendOrbit(String lat, String lon, String altitude, String heading, String tilt, String  pRange) throws JSchException, SftpException, InterruptedException {
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
            TimeUnit.SECONDS.sleep(2);
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
    public void stopOrbit() throws JSchException, IOException {
        if (session.isConnected()){
            sendCommand("echo 'exittour=true' > /tmp/query.txt");
        }
    }
    public void cleanKmls() throws JSchException, IOException {
        if (session.isConnected()){
            sendCommand("> /var/www/html/kmls.txt");
            sendCommand("echo '' > /var/www/html/kml/slave_5.kml");
        }
    }
    public void cleanAll() throws JSchException, IOException {
        if(session.isConnected()){
            cleanKmls();
            stopOrbit();
            deleteLogo();
        }
    }
    private String createKml(Terrains terrains){
        String points = fromStringToKmlData(terrains);
        String name = terrains.name;

        StringBuilder kml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "<Document>\n" +
                "\t<name>" + name + "</name>\n" +
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
                "\t\t\t\t\t\t" + points + "\n" +
                "\t\t\t\t\t</coordinates>\n" +
                "\t\t\t\t</LinearRing>\n" +
                "\t\t\t</outerBoundaryIs>\n" +
                "\t\t</Polygon>\n" +
                "</Placemark>\n");

        for (Locations tree: terrains.trees){
            kml.append("\t<Placemark id='07DF45709F23E21EAD5D'>\n").append("\t\t<name>"+ tree.prediction +"</name>\n" + "\t\t<LookAt>\n" + "\t\t\t<longitude>").append(tree.coordinates.longitude).append("</longitude>\n").append("\t\t\t<latitude>").append(tree.coordinates.latitude).append("</latitude>\n").append("\t\t\t<altitude>0</altitude>\n").append("\t\t\t<heading>0</heading>\n").append("\t\t\t<tilt>0</tilt>\n").append("\t\t\t<gx:fovy>30</gx:fovy>\n").append("\t\t\t<range>500</range>\n").append("\t\t\t<altitudeMode>absolute</altitudeMode>\n").append("\t\t</LookAt>\n").append("\t\t<styleUrl>#__managed_style_133D9A0E7523C70A932C</styleUrl>\n").append("\t\t<Point>\n").append("\t\t\t<coordinates>").append(tree.coordinates.longitude).append(",").append(tree.coordinates.latitude).append(",0</coordinates>\n").append("\t\t</Point>\n").append("\t</Placemark>\n");
        }
        kml.append("</Document>\n" + "</kml>");
        return kml.toString();
    }
    private String fromStringToKmlData (Terrains data){
        StringBuilder kmlLoc = new StringBuilder();
        for (LatLng elem : data.terrain){
            kmlLoc.append(elem.longitude).append(",").append(elem.latitude).append(",0 ");
        }
        kmlLoc.append(data.terrain.get(0).longitude).append(",").append(data.terrain.get(0).latitude).append(",0 ");
        return kmlLoc.toString();
    }
    private void setLogo() throws JSchException {
        String logo = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "  <kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "    <Document>\n" +
                "      <name>Ras-logos</name>\n" +
                "        <Folder>\n" +
                "        <name>Logos</name>\n" +
                "        <ScreenOverlay>\n" +
                "        <name>Logo</name>\n" +
                "        <Icon>\n" +
                "        <href>https://imgur.com/a/vwEMBFr</href>\n" +
                "        </Icon>\n" +
                "        <overlayXY x=\"0\" y=\"1\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "        <screenXY x=\"0.02\" y=\"0.95\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "        <rotationXY x=\"0\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "        <size x=\"0.4\" y=\"0.2\" xunits=\"fraction\" yunits=\"fraction\"/>\n" +
                "        </ScreenOverlay>\n" +
                "        </Folder>\n" +
                "    </Document>\n" +
                "  </kml>";
        if (session.isConnected()) {
            String command= "echo '"+logo+"' > /var/www/html/kml/slave_1.kml";
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            channel.connect();
        }
    }
    private void deleteLogo() throws JSchException, IOException {
        if (session.isConnected()){
            sendCommand("echo '' > /var/www/html/kml/slave_5.kml");
        }
    }


}


