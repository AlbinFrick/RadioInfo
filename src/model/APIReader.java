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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Reads and saves data from Sveriges Radios API.
 * @author Albin Frick
 */
public class APIReader {
    private String apiURL;
    private StringBuilder error;
    private CopyOnWriteArrayList<Channel> channels;
    private CopyOnWriteArrayList<Channel> P1;
    private CopyOnWriteArrayList<Channel> P2;
    private CopyOnWriteArrayList<Channel> P3;
    private CopyOnWriteArrayList<Channel> P4;
    private CopyOnWriteArrayList<Channel> other;

    /**
     * The only constructor of the class.
     * Sets the url to the api and creates arrayLists for all
     * the channels.
     */
    public APIReader(){
        error = new StringBuilder();
        apiURL = "http://api.sr.se/api/v2/channels?pagination=false";
        channels = new CopyOnWriteArrayList<>();
        P1 = new CopyOnWriteArrayList<>();
        P2 = new CopyOnWriteArrayList<>();
        P3 = new CopyOnWriteArrayList<>();
        P4 = new CopyOnWriteArrayList<>();
        other = new CopyOnWriteArrayList<>();
    }

    /**
     * Reads the channel data using XML DOM.
     * Every channel are read using the name of the tag in the xml
     * and are then stored in an arrayList.
     */
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

        } catch (ParserConfigurationException  e) {
            error.append("parser configuration");
        } catch (SAXException e) {
            error.append("Sax exception");
        } catch (MalformedURLException e) {
            error.append("Malformed URL");
        } catch (IOException e) {
            error.append("Problem when opening URL as stream");
        }
    }

    /**
     * Determines what date it was, is and going to be and
     * then calls the readScheduleAPI method for reading the
     * schedules for every episode from yesterday, today and
     * tomorrow.
     */
    public void readScheduleForThreeDaySpread(){
        String yesterday;
        String today;
        String tomorrow;

        Date time = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        today = dateFormat.format(time);
        int todaysdate = time.getDate();

        if(todaysdate > 9){
            yesterday = today.substring(0, today.length() - 2) + "0" + (todaysdate-1);
        }else{
            yesterday = today.substring(0, today.length() - 1) + (todaysdate-1);
        }

        if ((todaysdate+1) > 10){
            tomorrow = today.substring(0, today.length()-2) + (todaysdate+1);
        }else{
            tomorrow = today.substring(0, today.length()-1) + (todaysdate+1);
        }

        readScheduleAPI(yesterday);
        readScheduleAPI(today);
        readScheduleAPI(tomorrow);
    }

    /**
     * Reads every episode for every channel on the given date.
     * Every episode are read using the name of the tag in the xml sheet.
     * @param date - String
     */
    private void readScheduleAPI(String date){
        try{
            for (Channel c: channels) {
                if (c.getScheduleURL() == null){
                    c.setErrorMessage("This channel has no schedule");
                }else {
                    DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
                    DocumentBuilder DB = DBF.newDocumentBuilder();
                    Document doc = DB.parse((new URL(c.getScheduleURL() + "&pagination=false" + "&date=" + date).openStream()));
                    doc.getDocumentElement().normalize();

                    NodeList scheduleList = doc.getElementsByTagName("scheduledepisode");
                    for (int i = 0; i < scheduleList.getLength(); i++) {
                        Node nNode1 = scheduleList.item(i);
                        if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                            Element elem = (Element) nNode1;
                            Episode episode = new Episode();
                            NodeList childNodeList = elem.getChildNodes();
                            episode.setProgramID(Integer.parseInt(getAttributeFromNode(childNodeList, "program", "id")));
                            episode.setChannelID(Integer.parseInt(getAttributeFromNode(childNodeList, "channel", "id")));
                            episode.setTitle(getTextContentFromNode(childNodeList, "title"));
                            episode.setDescription(getTextContentFromNode(childNodeList, "description"));
                            episode.setStartTime(getTextContentFromNode(childNodeList, "starttimeutc"));
                            episode.setEndTime(getTextContentFromNode(childNodeList, "endtimeutc"));
                            episode.setImageURL(getTextContentFromNode(childNodeList, "imageurl"));
                            c.addEpisode(episode);
                        }
                    }
                }
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the live audio tag in the xml sheet.
     * @param liveaudio - NodeList
     * @param i - int
     * @return - String
     */
    private String readLiveAudio(NodeList liveaudio, int i){
        Node node = liveaudio.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE){
            Element elem1 = (Element) node;
            NodeList childLiveaudio = elem1.getChildNodes();
            return getTextContentFromNode(childLiveaudio, "url");
        }else
            return null;
    }

    /**
     * Gets a certain attribute from a certain node name.
     * @param nl - NodeList
     * @param nodeName - String
     * @param attribute - String
     * @return - String
     */
    private String getAttributeFromNode(NodeList nl, String nodeName, String attribute){
        String content = null;
        for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(nodeName)) {
                Element elem = (Element) n;
                content = elem.getAttribute(attribute);
            }
        }
        return content;
    }

    /**
     * Takes a node list and a node name. Finds the corresponding node name in the
     * list and returns its content in the form of a string
     * @param nl - NodeList
     * @param nodeName - String
     * @return - String
     */
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

    /**
     * Sorts the channels into four different lists.
     * The sorting is done with finding which channel group
     * the channel belongs to.
     */
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
            }else{
                other.add(c);
            }
        }
    }

    public CopyOnWriteArrayList<Channel> getChannels() {
        return channels;
    }

    public CopyOnWriteArrayList<Channel> getP1() {
        return P1;
    }

    public CopyOnWriteArrayList<Channel> getP2() {
        return P2;
    }

    public CopyOnWriteArrayList<Channel> getP3() {
        return P3;
    }

    public CopyOnWriteArrayList<Channel> getP4() {
        return P4;
    }

    public CopyOnWriteArrayList<Channel> getOther() {
        return other;
    }

    public String getError() {
        return error.toString();
    }
}