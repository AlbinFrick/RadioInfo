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
        apiURL = "http://api.sr.se/api/v2/channels";
        channels = new ArrayList<>();
    }


    public void readChannelAPI() {
        try{
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder DB = DBF.newDocumentBuilder();
            Document doc = DB.parse((new URL(apiURL).openStream()));
            doc.getDocumentElement().normalize();
            readPagination(doc.getElementsByTagName("pagination"));

            NodeList channelNodeList =  doc.getElementsByTagName("channel");
            for (int i = 0; i < channelNodeList.getLength(); i++){
                Node nNode1 = channelNodeList.item(i);
                if (nNode1.getNodeType() == Node.ELEMENT_NODE){
                    Element elem = (Element) nNode1;
                    Channel channel = new Channel(Integer.parseInt(elem.getAttribute("id")),
                                                                    elem.getAttribute("name"));
                    NodeList childNodeList = elem.getChildNodes();

                    //index is always odd.
                    channel.setImageURL(getTextContentFromNode(childNodeList.item(1)));
                    channel.setTagLine(getTextContentFromNode(childNodeList.item(7)));
                    channel.setSiteURL(getTextContentFromNode(childNodeList.item(9)));
                    channel.setLiveAudioURL(readLiveAudio(doc.getElementsByTagName("liveaudio")));
                    channel.setScheduleURL(getTextContentFromNode(childNodeList.item(13)));
                    channels.add(channel);
                }
            }
            if (pageNr != totalPages)
                readChannelAPI();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void readPagination(NodeList paginationNodeList){
        for (int k = 0; k < paginationNodeList.getLength(); k++){
            Node nNode = paginationNodeList.item(k);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;
                NodeList childNodeListPagination = elem.getChildNodes();
                this.pageNr = Integer.parseInt(getTextContentFromNode(childNodeListPagination.item(1)));
                this.size = Integer.parseInt(getTextContentFromNode(childNodeListPagination.item(3)));
                this.totalHits = Integer.parseInt(getTextContentFromNode(childNodeListPagination.item(5)));
                this.totalPages = Integer.parseInt(getTextContentFromNode(childNodeListPagination.item(7)));
                this.apiURL = getTextContentFromNode(childNodeListPagination.item(9));
            }
        }
    }

    private String readLiveAudio(NodeList liveaudio){
        Node node = liveaudio.item(1);
        if (node.getNodeType() == Node.ELEMENT_NODE){
            Element elem1 = (Element) node;
            NodeList childLiveaudio = elem1.getChildNodes();
            return getTextContentFromNode(childLiveaudio.item(1));
        }else
            return null;
    }

    public String getTextContentFromNode(Node n){
        if (n.getNodeType() == Node.ELEMENT_NODE){
            Element elem  = (Element) n;
            return elem.getTextContent();
        }else
            return null;

    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

}