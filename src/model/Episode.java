package model;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Episode {
    private String title;
    private String subTitle;
    private String description;
    private String imageURL;
    private String channelName;
    private Date startTime;
    private Date endTime;
    private int programID;
    private String programName;
    private int channelID;
    private DateFormat dateFormat;

    public Episode() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public ImageIcon getImage() {
        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageIcon(new URL(getImageURL()));
            Image image = imageIcon.getImage();
            Image newImg = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newImg);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public Boolean isEpisodeIn12HourWindow() {
        int twelveHours = 43200000;
        long now = new Date().getTime();
        return ((endTime.getTime() > (now - twelveHours))&&(startTime.getTime() < (now + twelveHours)));
    }

    public Boolean isBroadcasting() {
        long now = new Date().getTime();
        return ((now > startTime.getTime()) && (now < endTime.getTime()));
    }

    public Boolean isBroadcastingInFuture(){
        long now = new Date().getTime();
        return (now < startTime.getTime());
    }

    public Boolean hasAlreadyBroadcast(){
        long now = new Date().getTime();
        return (endTime.getTime() < now);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        try {
            this.startTime = dateFormat.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        try {
            this.endTime = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getProgramID() {
        return programID;
    }

    public void setProgramID(int programID) {
        this.programID = programID;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
