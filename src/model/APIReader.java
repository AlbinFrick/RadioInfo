package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class APIReader {
    private String apiURL;
    private ArrayList<Channel> channels;
    private ArrayList<Channel> P1;
    private ArrayList<Channel> P2;
    private ArrayList<Channel> P3;
    private ArrayList<Channel> P4;
    private ArrayList<Channel> SR;

    public APIReader(){
        apiURL = "http://api.sr.se/api/v2/channels?pagination=false";
        channels = new ArrayList<>();
        P1 = new ArrayList<>();
        P2 = new ArrayList<>();
        P3 = new ArrayList<>();
        P4 = new ArrayList<>();
        SR = new ArrayList<>();
    }

    public void readChannelAPI() {
        try{
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder DB = DBF.newDocumentBuilder();
            Document doc = DB.parse((new URL(apiURL).openStream()));
            doc.getDocumentElement().normalize();

            NodeList channelNodeList =  doc.getElementsByTagName("channel");
            for (int i = 0; i < channelNodeList.getLength(); i++){
                Node nNode1 = channelNodeList.item(i);
                if (nNode1.getNodeType() == Node.ELEMENT_NODE){
                    Element elem = (Element) nNode1;
                    Channel channel = new Channel(Integer.parseInt(elem.getAttribute("id")),
                                                                    elem.getAttribute("name"));
                    NodeList childNodeList = elem.getChildNodes();
                    channel.setImageURL(getTextContentFromNode(childNodeList, "image"));
                    channel.setTagLine(getTextContentFromNode(childNodeList, "tagline"));
                    channel.setSiteURL(getTextContentFromNode(childNodeList, "siteurl"));
                    channel.setScheduleURL(getTextContentFromNode(childNodeList, "scheduleurl"));
                    channel.setLiveAudioURL(readLiveAudio(doc.getElementsByTagName("liveaudio"), i));
                    channels.add(channel);
                }
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void readScheduleAPI(){
        try{
            for (Channel c: channels) {
                if (c.getScheduleURL() == null){
                    c.setErrorMessage("This channel has no schedule");

                }else {
                    DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
                    DocumentBuilder DB = DBF.newDocumentBuilder();
                    Document doc = DB.parse((new URL(c.getScheduleURL() + "&pagination=false").openStream()));
                    doc.getDocumentElement().normalize();

                    NodeList scheduleList = doc.getElementsByTagName("scheduledepisode");
                    for (int i = 0; i < scheduleList.getLength(); i++) {
                        Node nNode1 = scheduleList.item(i);
                        if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                            Element elem = (Element) nNode1;
                            Episode episode = new Episode();
                            NodeList childNodeList = elem.getChildNodes();
                            episode.setTitle(getTextContentFromNode(childNodeList, "title"));
                            episode.setDescription(getTextContentFromNode(childNodeList, "description"));
                            episode.setStartTime(getTextContentFromNode(childNodeList, "starttimeutc"));
                            episode.setEndTime(getTextContentFromNode(childNodeList, "endtimeutc"));
                            c.addEpisode(episode);
                        }
                    }
                }
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private String readLiveAudio(NodeList liveaudio, int i){
        Node node = liveaudio.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE){
            Element elem1 = (Element) node;
            NodeList childLiveaudio = elem1.getChildNodes();
            return getTextContentFromNode(childLiveaudio, "url");
        }else
            return null;
    }

    private String getTextContentFromNode(NodeList nl, String nodeName){
        String content = null;
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(nodeName)) {
                Element elem = (Element) n;
                content =  elem.getTextContent().trim();
            }
        }
        return content;
    }

    public void sortChannels(){
        for (Channel c: channels) {
            if (c.getChannelName().startsWith("P1")){
                P1.add(c);
            }else if(c.getChannelName().startsWith("P2")){
                P2.add(c);
            }else if (c.getChannelName().startsWith("P3")) {
                P3.add(c);
            }else if (c.getChannelName().startsWith("P4")){
                P4.add(c);
            }else if(c.getChannelName().startsWith("SR")){
                SR.add(c);
            }
        }
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public ArrayList<Channel> getP1() {
        return P1;
    }

    public ArrayList<Channel> getP2() {
        return P2;
    }

    public ArrayList<Channel> getP3() {
        return P3;
    }

    public ArrayList<Channel> getP4() {
        return P4;
    }

    public ArrayList<Channel> getSR() {
        return SR;
    }
}