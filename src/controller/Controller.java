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

/**
 * Controls the relationship between the view and the model.
 * @author Albin Frick
 */
public class Controller {
    private CopyOnWriteArrayList<Channel> channels;
    private CopyOnWriteArrayList<Channel> p1Channels;
    private CopyOnWriteArrayList<Channel> p2Channels;
    private CopyOnWriteArrayList<Channel> p3Channels;
    private CopyOnWriteArrayList<Channel> p4Channels;
    private CopyOnWriteArrayList<Channel> otherChannels;
    private GUI gui;
    private volatile Boolean loading;
    private Boolean episodeFound;
    private APIReader parser;
    private ActionListener channelButtonAL = this::channelButtonDecider;
    private ActionListener menuFilterAL = this::filterMenuButtonDecider;
    private ActionListener menuReload = e -> reloadContent();
    private ActionListener tableSelected = e -> updateEpisodeInformationWindow();

    public Controller(){
        loading = false;
    }

    /**
     * Starts the controller.
     */
    public void startController(){
        SwingUtilities.invokeLater(()-> gui = new GUI(
                menuFilterAL, menuReload, tableSelected));
        startTimer();
    }

    /**
     * Loads or reloads the content, A.I channels and episodes.
     * If the applications is already running every table will
     * be cleared and the loading gif will appear.
     */
    private void reloadContent() {
        if (!loading){
            SwingUtilities.invokeLater(() -> {
                gui.clearAll();
                gui.updateGUI();
            });
        }
        SwingUtilities.invokeLater(()->{
            gui.setVisible(true);
            gui.toggleLoading(true);
            parser = new APIReader();
            updateContent();
        });
    }

    /**
     * Updates all the variables for the channels and it's respective episodes.
     * If there was a problem with the connection an error will be displayed
     * for the user.
     */
    private void updateContent(){
        new Thread(()->{
            loading = false;
            episodeFound = false;
            parser.readChannelAPI();
            parser.readScheduleForThreeDaySpread();
            channels = parser.getChannels();
            parser.sortChannels();
            p1Channels = parser.getP1();
            p2Channels = parser.getP2();
            p3Channels = parser.getP3();
            p4Channels = parser.getP4();
            otherChannels = parser.getOther();
            gui.toggleLoading(false);
            for (Channel c: channels) {
                gui.addChannelButton(c, channelButtonAL);
            }
            if (!parser.getError().equals("")){
                gui.errorMessage(parser.getError());
            }
            gui.updateGUI();
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

    /**
     * Finds which of the channels that were pressed.
     * @param e - ActionEvent
     */
    private void channelButtonDecider(ActionEvent e){
        for (Channel c: channels) {
            if (e.getActionCommand().equals(Integer.toString(c.getChannelID()))){
                whenChannelButtonIsPressed(c);
                break;
            }
        }
    }

    /**
     * Adds the channels and the current episodes information to the user interface.
     * If there is no schedule for the channel an error message be displayed for the
     * user.
     * @param c - Channel
     */
    private void whenChannelButtonIsPressed(Channel c) {
        SwingUtilities.invokeLater(() -> {
            gui.addChannelToDisplay(c);
            ArrayList<Episode> episodes = c.getEpisodes();
            gui.clearTable();
            if (episodes.size() == 0) {
                gui.addErrorMessageForNoSchedule(c);
            } else {
                for (Episode e : episodes) {
                    if (e.isEpisodeIn24HourWindow()) {
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

    /**
     * Figures out which of the menu buttons in the filter
     * menu have been pressed.
     * @param e - ActionEvent
     */
    private void filterMenuButtonDecider(ActionEvent e){
        switch (e.getActionCommand()) {
            case "P1":
                whenFilterMenuButtonIsPressed(p1Channels);
                break;
            case "P2":
                whenFilterMenuButtonIsPressed(p2Channels);
                break;
            case "P3":
                whenFilterMenuButtonIsPressed(p3Channels);
                break;
            case "P4":
                whenFilterMenuButtonIsPressed(p4Channels);
                break;
            case "Other":
                whenFilterMenuButtonIsPressed(otherChannels);
                break;
            case "All":
                whenFilterMenuButtonIsPressed(channels);
                break;
        }
    }

    /**
     * Removes the channels from the channel window and adds
     * only the filtered ones.
     * @param channelList - CopyOnWriteArrayList
     */
    private void whenFilterMenuButtonIsPressed(CopyOnWriteArrayList<Channel> channelList){
        new Thread(()-> SwingUtilities.invokeLater(()-> {
            if (!loading) {
                gui.removeChannels();
                for (Channel c : channelList) {
                    gui.addChannelButton(c, channelButtonAL);
                }
                gui.updateGUI();
            }
        })).start();

    }

    /**
     * Updates the information for the episode pressed in the table.
     * First the channel id from the table are matched with the list.
     * When the right channel are found the channels episodes are searched.
     * When the episode is four the episodes information are displayed for
     * user and the searching stop.
     */
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
}