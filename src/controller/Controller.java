package controller;

import model.Channel;
import model.APIReader;
import model.Episode;
import view.GUI;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private ActionListener channelButtonAL;
    private APIReader parser;
    private ActionListener menuReload = e -> reloadContent();
    private ActionListener tabelSelected = e -> updateEpisodeInformationWindow();

    public Controller(){
        loading = false;
        episodeFound = false;
        parser = new APIReader();
        parser.readChannelAPI();
        parser.readScheduleForThreeDaySpread();
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
            gui = new GUI(menuFilterAL, menuReload, tabelSelected);
            gui.setVisible(true);
            for (Channel c: channels) {
                gui.addChannelButton(c, channelButtonAL);
            }
        });
    }

    private void whenChannelButtonIsPressed(Channel c) {
       SwingUtilities.invokeLater(() -> {
           gui.addChannelToDisplay(c);
           ArrayList<Episode> episodes = c.getEpisodes();
           //gui.clearTable();
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
            /*try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new URL(c.getLiveAudioURL()));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }*/
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
                                System.out.println("Valda kanalen är: " + c.getChannelName());
                                System.out.println("Valda programmet är: " + e.getTitle());
                                System.out.println();
                                episodeFound = true;
                            }
                        }
                    }
                    if (episodeFound){
                       loading = false;
                       return;
                    }
                }
                loading = false;
            });
        }

    }

    private void reloadContent(){
            parser.readChannelAPI();
            parser.readScheduleForThreeDaySpread();
            channels = parser.getChannels();
            parser.sortChannels();
            p1Channels = parser.getP1();
            p2Channels = parser.getP2();
            p3Channels = parser.getP3();
            p4Channels = parser.getP4();
            otherChannels = parser.getOther();
        SwingUtilities.invokeLater(()-> gui.updateGUI());
    }
}