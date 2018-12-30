package model;

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
    private String xmlTvID;

    public Channel(int channelID, String channelName){
        this.channelID = channelID;
        this.channelName = channelName;
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

    public String getXmlTvID() {
        return xmlTvID;
    }

    public void setXmlTvID(String xmlTvID) {
        this.xmlTvID = xmlTvID;
    }

    public String getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
    }
}
