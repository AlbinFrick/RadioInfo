package view;

import model.Channel;
import model.Episode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The central class for the user interface.
 * Extends JFrame
 * @author Albin Frick
 */
public class GUI extends JFrame{
    private WestPanel westPanel;
    private CenterPanel centerPanel;
    private EastPanel eastPanel;

    /**
     * The constructor builds the whole frame for the user interface.
     * Takes three action listeners, two for the menus and one for the
     * table. Build the frame with three different panels.
     * @param menuFilterAL - ActionListener
     * @param menuReload - ActionListener
     * @param tableListener - ActionListener
     */
    public GUI(ActionListener menuFilterAL, ActionListener menuReload, ActionListener tableListener){
        setPreferredSize(new Dimension(1500,1000));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLayout(new BorderLayout());
        setJMenuBar(new MenuBar(menuFilterAL, menuReload).getJMenuBar());
        westPanel = new WestPanel();
        centerPanel = new CenterPanel(tableListener);
        eastPanel = new EastPanel();
        add(westPanel.getChannelScrollPane(), BorderLayout.WEST);
        add(centerPanel.getCenterPanel(), BorderLayout.CENTER);
        add(eastPanel.getEastPanel(), BorderLayout.EAST);
    }

    /**
     * Adds one episode to the table.
     * @param e - Episode
     */
    public void addEpisodesToTable(Episode e){
        centerPanel.addEpisodesToTable(e);
    }

    /**
     *  Removes all data from the table.
     */
    public void clearTable(){
        centerPanel.clearTable();
    }

    /**
     * Adds a channel button with to the west panel.
     * @param c - Channel
     * @param al - ActionListener
     */
    public void addChannelButton(Channel c, ActionListener al){
        westPanel.addChannelButton(c,al);
    }

    /**
     * Adds a channel to display on the center panel
     * @param c - Channel
     */
    public void addChannelToDisplay(Channel c){
        centerPanel.addChannelToDisplay(c);
    }

    /**
     * Adds an episode to display on the east panel.
     * @param e - Episode
     */
    public void addEpisodeInformation(Episode e){
        eastPanel.addEpisodeInformation(e);
    }

    /**
     * Adds the error message from a channel to the east panel.
     * @param c - Channel
     */
    public void addErrorMessageForNoSchedule(Channel c) {
        eastPanel.addErrorMessageForNoSchedule(c);
    }

    /**
     * Updates all three panels
     */
    public void updateGUI(){
        centerPanel.update();
        eastPanel.update();
        westPanel.update();
    }

    /**
     * Removes the channel from the west panel.
     */
    public void removeChannels(){
        westPanel.clearWestPanel();
    }

    /**
     * Gets the current episode id.
     * @return - int
     */
    public int getCurrentEpisodeID() {
        return centerPanel.getCurrentEpisodeID();
    }

    /**
     * Gets the current channel id.
     * @return - int
     */
    public int getCurrentChannelID() {
        return centerPanel.getCurrentChannelID();
    }

    /**
     *  Removes all information from every panel.
     */
    public void clearAll(){
        westPanel.clearWestPanel();
        centerPanel.clearChannelWindow();
        centerPanel.clearTable();
        eastPanel.clearInfo();
    }

    /**
     * Toggles the loading icon on the east panel.
     * @param bool - boolean
     */
    public void toggleLoading(Boolean bool){
        westPanel.toggleLoadingIcon(bool);
    }
}
