package controller;

import model.Channel;
import model.APIReader;
import view.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Channel> channels;
    private ArrayList<Channel> p1Channels;
    private ArrayList<Channel> p2Channels;
    private ArrayList<Channel> p3Channels;
    private ArrayList<Channel> p4Channels;
    private ArrayList<Channel> srChannels;
    private GUI gui;

    public Controller(){
        APIReader parser = new APIReader();
        parser.readChannelAPI();
        parser.readScheduleAPI();
        channels = parser.getChannels();
        parser.sortChannels();
        p1Channels = parser.getP1();
        p2Channels = parser.getP2();
        p3Channels = parser.getP3();
        p4Channels = parser.getP4();
        srChannels = parser.getSR();
    }

    public void startController(){
        ActionListener channelButtonPress = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                whenChannelButtonIsPressed();
            }
        };

        SwingUtilities.invokeLater(()->{
            gui = new GUI();
            gui.setVisible(true);
            gui.addChannelButton(p1Channels.get(0), channelButtonPress);
            gui.addChannelButton(p2Channels.get(0), channelButtonPress);
            gui.addChannelButton(p3Channels.get(0), channelButtonPress);
            gui.addChannelButton(p4Channels.get(0), channelButtonPress);
            gui.addChannelButton(srChannels.get(0), channelButtonPress);

            System.out.println(channels.size());
        });
    }

    private void whenChannelButtonIsPressed() {
        gui.addChannelToDisplay(p1Channels.get(0));
    }
}