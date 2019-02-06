package controller;

import model.Channel;
import model.APIReader;
import model.Episode;
import view.GUI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller {
    private CopyOnWriteArrayList<Channel> channels;
    private CopyOnWriteArrayList<Channel> p1Channels;
    private CopyOnWriteArrayList<Channel> p2Channels;
    private CopyOnWriteArrayList<Channel> p3Channels;
    private CopyOnWriteArrayList<Channel> p4Channels;
    private CopyOnWriteArrayList<Channel> otherChannels;
    private GUI gui;
    private Boolean loading;
    private Boolean episodeFound;
    private APIReader parser;
    private ActionListener channelButtonAL = this::channelButtonDecider;
    private ActionListener menuFilterAL = this::menuButtonDecider;
    private ActionListener menuReload = e -> reloadContent();
    private ActionListener tabelSelected = e -> updateEpisodeInformationWindow();

    public Controller(){
        loading = false;
        startTimer();
    }

    public void startController(){
        SwingUtilities.invokeLater(()-> gui = new GUI(
                menuFilterAL, menuReload, tabelSelected));
    }

    private void channelButtonDecider(ActionEvent e){
        for (Channel c: channels) {
            if (e.getActionCommand().equals(Integer.toString(c.getChannelID()))){
                whenChannelButtonIsPressed(c);
                break;
            }
        }
    }

    private void menuButtonDecider(ActionEvent e){
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
    }


    private void whenChannelButtonIsPressed(Channel c) {
       SwingUtilities.invokeLater(() -> {
           gui.addChannelToDisplay(c);
           ArrayList<Episode> episodes = c.getEpisodes();
           gui.clearTable();
           if (episodes.size() == 0) {
               gui.addErrorMessageForNoSchedule(c);
           } else {
               for (Episode e : episodes) {
                   if (e.isEpisodeIn12HourWindow()) {
                       gui.addEpisodesToTable(e);
                       if (e.isBroadcasting()) {
                           gui.addEpisodeInformation(e);
                       }
                   }
               }
           }
           gui.updateGUI();
        });
    }

    private void whenMenuButtonIsPressed(CopyOnWriteArrayList<Channel> channelList){
        SwingUtilities.invokeLater(()-> {
            gui.removeChannels();
            for (Channel c : channelList) {
                gui.addChannelButton(c, channelButtonAL);
            }
            gui.updateGUI();
        });
    }

    private void updateEpisodeInformationWindow(){
        if (!loading) {
            SwingUtilities.invokeLater(()->{
                loading = true;
                for (Channel c : channels) {
                    if (c.getChannelID() == gui.getCurrentChannelID()){
                        for (Episode e : c.getEpisodes()) {
                            if (e.getProgramID() == gui.getCurrentEpisodeID()){
                                gui.addEpisodeInformation(e);
                                gui.updateGUI();
                                episodeFound = true;
                                break;
                            }
                        }
                    }
                    if (episodeFound){
                       loading = false;
                       episodeFound = false;
                       break;
                    }
                }
                loading = false;
            });
        }

    }

    /**
     * Loads or reloads the content, A.I channels and episodes.
     * The GUI is reset and
     */
    private void reloadContent() {
        if (!loading){
            SwingUtilities.invokeLater(() -> {
                gui.clearTable();
                gui.clearAll();
                gui.updateGUI();
            });
        }
        SwingUtilities.invokeLater(()->{
            gui.setVisible(true);
            System.out.println("Start updating");
            updateContent();

            gui.updateGUI();
        });
    }

    private void updateContent(){
        new Thread(()->{
            loading = false;
            episodeFound = false;
            parser = new APIReader();
            System.out.println("read Channels");
            parser.readChannelAPI();
            System.out.println("Channels done \nread episodes");
            parser.readScheduleForThreeDaySpread();
            System.out.println("episodes done");
            channels = parser.getChannels();
            parser.sortChannels();
            p1Channels = parser.getP1();
            p2Channels = parser.getP2();
            p3Channels = parser.getP3();
            p4Channels = parser.getP4();
            otherChannels = parser.getOther();
            for (Channel c: channels) {
                gui.addChannelButton(c, channelButtonAL);
            }
            gui.updateGUI();
            System.out.println("content loaded");
        }).start();
    }

    /**
     * Starts a time to automatically update the channels and episodes
     * every hour.
     */
    private void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(()->reloadContent());
            }
        }, 100, 3600000);
    }
}