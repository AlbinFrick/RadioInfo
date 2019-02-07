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

/**
 * Data type for an episode.
 * Stores all data for an episode.
 * @author Albin Frick
 */
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

    /**
     * The only constructor of the class.
     * Specifies the format for the date.
     */
    public Episode() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    /**
     * Returns a ImageIcon from the image url from the episode.
     * The method creates an image icon and scales it to a size
     * of 200x200 and the returns it.
     * @return - ImageIcon
     */
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

    /**
     * Determines whether the episode is in a 24 hour window.
     * @return - Boolean
     */
    public Boolean isEpisodeIn24HourWindow() {
        int twelveHours = 43200000;
        long now = new Date().getTime();
        return ((endTime.getTime() > (now - twelveHours))&&(startTime.getTime() < (now + twelveHours)));
    }

    /**
     * Determines if the episode is currently broadcasting.
     * @return - Boolean
     */
    public Boolean isBroadcasting() {
        long now = new Date().getTime();
        return ((now > startTime.getTime()) && (now < endTime.getTime()));
    }

    /**
     * Determines if the episode is to be broadcast in the future.
     * @return - Boolean
     */
    public Boolean isBroadcastingInFuture(){
        long now = new Date().getTime();
        return (now < startTime.getTime());
    }

    /**
     * Determines if the episode has already been broadcast.
     * @return - Boolean
     */
    public Boolean hasAlreadyBroadcasted(){
        long now = new Date().getTime();
        return (endTime.getTime() < now);
    }

    /**
     * @return - String
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title - String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return - String
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description - String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return - Date
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Converts a string time to a date using the format
     * specified in the constructor.
     * @param startTime - String
     */
    public void setStartTime(String startTime) {
        try {
            this.startTime = dateFormat.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return - Date
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Converts a string time to a date using the format
     * specified in the constructor.
     * @param endTime - String
     */
    public void setEndTime(String endTime) {
        try {
            this.endTime = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return - int
     */
    public int getProgramID() {
        return programID;
    }

    /**
     * @param programID - int
     */
    public void setProgramID(int programID) {
        this.programID = programID;
    }

    /**
     * @return - int
     */
    public int getChannelID() {
        return channelID;
    }

    /**
     * @param channelID - int
     */
    public void setChannelID(int channelID) {
        this.channelID = channelID;
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
}
