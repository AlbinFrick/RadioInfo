package model;

import com.sun.webkit.dom.NodeListImpl;
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

public class XMLParser {
    private String apiURL;
    private ArrayList<Channel> channels;
    private int pageNr;
    private int size;
    private int totalHits;
    private int totalPages;

    public XMLParser(){
        apiURL = "http://api.sr.se/api/v2/channels?pagination=false";
        channels = new ArrayList<>();
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

                    //index is always odd.
                    channel.setImageURL(getTextContentFromNode(childNodeList.item(1), "image"));
                    channel.setTagLine(getTextContentFromNode(childNodeList.item(7), "tagline"));
                    channel.setSiteURL(getTextContentFromNode(childNodeList.item(9), "siteurl"));
                    channel.setLiveAudioURL(readLiveAudio(doc.getElementsByTagName("liveaudio"), i));
                    channel.setScheduleURL(getTextContentFromNode(childNodeList.item(13), "scheduleurl"));
                    channels.add(channel);
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
            return getTextContentFromNode(childLiveaudio.item(1), "url");
        }else
            return null;
    }

    public String getTextContentFromNode(Node n, String nodeName){

        if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(nodeName)){
            Element elem  = (Element) n;
            return elem.getTextContent();
        }else
            return null;

    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

}