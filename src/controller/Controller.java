package controller;

import model.Channel;
import model.XMLParser;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Channel> channels;
    public Controller(){
        XMLParser parser = new XMLParser();
        parser.readChannelAPI();
        channels = parser.getChannels();


        for (Channel c : channels) {
            System.out.println("Channel ID: " + c.getChannelID());
            System.out.println("Channel Name: " + c.getChannelName());
            System.out.println("Channel tagline: " + c.getTagLine());
            System.out.println("Channel Image URL: " + c.getImageURL());
            System.out.println("Channel scheduleURL: " + c.getScheduleURL());
            System.out.println("Channel siteURL: " + c.getSiteURL());
            System.out.println("Channel live audio: " + c.getLiveAudioURL());
            System.out.println();
            System.out.println();
        }
        System.out.println(channels.size());


    }
}