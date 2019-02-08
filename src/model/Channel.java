package model;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * This class is a data type for the information of a radio channel.
 * @author Albin Frick
 */
public class Channel {
    private int channelID;
    private String channelName;
    private String imageURL;
    private String tagLine;
    private String siteURL;
    private int liveAudioID;
    private String liveAudioURL;
    private String liveAudioStatKey;
    private String scheduleURL;
    private String errorMessage;
    private ArrayList<Episode> episodes;

    /**
     * Sets the given parameters and creates a new instance of
     * and array list.
     * @param channelID - int
     * @param channelName - String
     */
    public Channel(int channelID, String channelName){
        this.channelID = channelID;
        this.channelName = channelName;
        episodes = new ArrayList<>();
    }

    /**
     * Creates an image icon of the image url.
     * Scales the image to a size of 100x100 and then
     * returns the imageIcon.
     * @return - ImageIcon
     */
    public ImageIcon getImage(){
        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageIcon(new URL(getImageURL()));
            Image image = imageIcon.getImage();
            Image newImg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newImg);
        } catch (MalformedURLException e) {
            System.err.println("Malformed ImageURL for channel image");
        }
        return null;
    }

    /**
     * @return - int
     */
    public int getChannelID() {
        return channelID;
    }

    /**
     * @return - String
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * @return - String
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * @param imageURL - String
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * @return - String
     */
    public String getTagLine() {
        return tagLine;
    }

    /**
     * @param tagLine
     */
    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    /**
     * @return - int
     */
    public int getLiveAudioID() {
        return liveAudioID;
    }

    /**
     * @param liveAudioID - int
     */
    public void setLiveAudioID(int liveAudioID) {
        this.liveAudioID = liveAudioID;
    }

    /**
     * @return - String
     */
    public String getLiveAudioURL() {
        return liveAudioURL;
    }

    /**
     * @param liveAudioURL - String
     */
    public void setLiveAudioURL(String liveAudioURL) {
        this.liveAudioURL = liveAudioURL;
    }

    /**
     * @return - String
     */
    public String getLiveAudioStatKey() {
        return liveAudioStatKey;
    }

    /**
     * @param liveAudioStatKey - String
     */
    public void setLiveAudioStatKey(String liveAudioStatKey) {
        this.liveAudioStatKey = liveAudioStatKey;
    }

    /**
     * @return - String
     */
    public String getScheduleURL() {
        return scheduleURL;
    }

    /**
     * @param scheduleURL - String
     */
    public void setScheduleURL(String scheduleURL) {
        this.scheduleURL = scheduleURL;
    }

    /**
     * @return - String
     */
    public String getSiteURL() {
        return siteURL;
    }

    /**
     * @param siteURL - String
     */
    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
    }

    /**
     * @return - ArrayList
     */
    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * @param episode - Episode
     */
    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }

    /**
     * @return - String
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage - String
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
