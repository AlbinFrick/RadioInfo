package model;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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

    public Channel(int channelID, String channelName){
        this.channelID = channelID;
        this.channelName = channelName;
        episodes = new ArrayList<>();
    }

    public ImageIcon getImage(){
        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageIcon(new URL(getImageURL()));
            Image image = imageIcon.getImage();
            Image newImg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newImg);
        } catch (MalformedURLException e) {
            System.err.println("Mal formed ImageURL for channel image");

        }
        return null;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getLiveAudioID() {
        return liveAudioID;
    }

    public void setLiveAudioID(int liveAudioID) {
        this.liveAudioID = liveAudioID;
    }

    public String getLiveAudioURL() {
        return liveAudioURL;
    }

    public void setLiveAudioURL(String liveAudioURL) {
        this.liveAudioURL = liveAudioURL;
    }

    public String getLiveAudioStatKey() {
        return liveAudioStatKey;
    }

    public void setLiveAudioStatKey(String liveAudioStatKey) {
        this.liveAudioStatKey = liveAudioStatKey;
    }

    public String getScheduleURL() {
        return scheduleURL;
    }

    public void setScheduleURL(String scheduleURL) {
        this.scheduleURL = scheduleURL;
    }

    public String getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void addEpisode(Episode episode) {
        episodes.add(episode);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
