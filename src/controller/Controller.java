package controller;

import model.Channel;
import model.SchedulePage;
import model.XMLParser;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Channel> channels;
    public Controller(){
        XMLParser parser = new XMLParser();
        parser.readAPI();
        channels = parser.getChannels();

        for (Channel c : channels) {
            System.out.println("Channel ID: " + c.getChannelID());
            System.out.println("Channel Name: " + c.getChannelName());
            System.out.println("Channel Image URL: " + c.getImageURL());
            System.out.println("Channel scheduleURL: " + c.getScheduleURL());
            System.out.println("Channel siteURL: " + c.getSiteURL());

        }

        /*parser.readSchedulePagination(channels.get(8).getScheduleURL());
        SchedulePage sp = parser.getSP();
        System.out.println(sp.getPageNr());
        System.out.println(sp.getNextPageURL());
        System.out.println(sp.getPreviousPageURL());*/
    }
}