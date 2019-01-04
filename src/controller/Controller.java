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
    private ArrayList<Channel> otherChannels;
    private GUI gui;
    private ActionListener channelButtonAL;

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
        otherChannels = parser.getOther();
    }

    public void startController(){
        channelButtonAL = e -> {
            for (Channel c: channels) {
                if (e.getActionCommand().equals(Integer.toString(c.getChannelID()))){
                    whenChannelButtonIsPressed(c);
                    break;
                }
            }
        };

        ActionListener menuFilterAL = e -> {
            System.out.println(e.getActionCommand());
            switch (e.getActionCommand()){
                case "P1": whenMenuButtonIsPressed(p1Channels);
                    break;
                case "P2": whenMenuButtonIsPressed(p2Channels);
                    break;
                case "P3": whenMenuButtonIsPressed(p3Channels);
                    break;
                case "P4": whenMenuButtonIsPressed(p4Channels);
                    break;
                case "Other": whenMenuButtonIsPressed(otherChannels);
                    break;
            }
        };

        SwingUtilities.invokeLater(()->{
            gui = new GUI(menuFilterAL);
            gui.setVisible(true);
            for (Channel c: channels) {
                gui.addChannelButton(c, channelButtonAL);
            }
        });
    }

    private void whenChannelButtonIsPressed(Channel c) {
        SwingUtilities.invokeLater(() -> gui.addChannelToDisplay(c));
    }

    private void whenMenuButtonIsPressed(ArrayList<Channel> channelList){
        new Thread(()-> {
            gui.removeChannels();
            for (Channel c : channelList) {
                gui.addChannelButton(c, channelButtonAL);
            }
            gui.updateGUI();
        }).start();
    }
}