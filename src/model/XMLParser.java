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

public class XMLParser {
    private String apiURL;
    private ArrayList<Channel> channels;
    private String nextPageURL;
    private String previousPageURL;
    private int pageNr;
    private int size;
    private int totalHits;
    private int totalPages;

    public XMLParser(){
        apiURL = "http://api.sr.se/api/v2/channels";
        channels = new ArrayList<>();
    }

    public void readPagination(){
        //pagination class maybe unnecessary
    }

    public void readAPI() {
        try{
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder DB = DBF.newDocumentBuilder();
            Document doc = DB.parse((new URL(apiURL).openStream()));
            doc.getDocumentElement().normalize();

            NodeList paginationNodeList = doc.getElementsByTagName("pagination");
            for (int k = 0; k < paginationNodeList.getLength(); k++){
                Node nNode = paginationNodeList.item(k);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) nNode;
                    NodeList childNodeListPagination = elem.getChildNodes();

                    pageNr = Integer.parseInt(getTextContentFromNode(childNodeListPagination.item(1)));
                }
            }

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
                    //index 11- liveAudio. Don't know if necessary
                    channel.setScheduleURL(getTextContentFromNode(childNodeList.item(13)));
                    channel.setXmlTvID(getTextContentFromNode(childNodeList.item(17)));
                    channels.add(channel);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
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

 /*   public void readSchedulePagination(String scheduleURL){
        try {
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder DB = DBF.newDocumentBuilder();
            Document doc = DB.parse((new URL(scheduleURL).openStream()));
            doc.getDocumentElement().normalize();
            NodeList scheduleNodeList =  doc.getElementsByTagName("pagination");
            for (int i = 0; i < scheduleNodeList.getLength(); i++){
                Node nNode = scheduleNodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element elem = (Element) nNode;
                    NodeList childNodeList = elem.getChildNodes();
                    SP = new  SchedulePage();
                    //index is always odd.
                    SP.setPageNr(Integer.parseInt(getTextContentFromNode(childNodeList.item(1))));
                    SP.setSize(Integer.parseInt(getTextContentFromNode(childNodeList.item(3))));
                    SP.setTotalHits(Integer.parseInt(getTextContentFromNode(childNodeList.item(5))));
                    SP.setTotalPages(Integer.parseInt(getTextContentFromNode(childNodeList.item(7))));
                    if (childNodeList.item(9) != null) {
                        SP.setNextPageURL(getTextContentFromNode(childNodeList.item(9)));
                    }

                    if (childNodeList.item(11) != null)
                        SP.setPreviousPageURL(getTextContentFromNode(childNodeList.item(11)));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }*/

    public SchedulePage getSP() {
        return SP;
    }
}