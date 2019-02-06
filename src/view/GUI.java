package view;

import model.Channel;
import model.Episode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class GUI extends JFrame{
    private WestPanel westPanel;
    private CenterPanel centerPanel;
    private EastPanel eastPanel;

    public GUI(ActionListener menuFilterAL, ActionListener menuReload, ActionListener tableListener){
        setPreferredSize(new Dimension(1500,1000));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLayout(new BorderLayout());
        setJMenuBar(new MenuBar(menuFilterAL, menuReload).getjMenuBar());
        westPanel = new WestPanel();
        centerPanel = new CenterPanel(tableListener);
        eastPanel = new EastPanel();
        add(westPanel.getChannelScrollPane(), BorderLayout.WEST);
        add(centerPanel.getCenterPanel(), BorderLayout.CENTER);
        add(eastPanel.getEastPanel(), BorderLayout.EAST);
    }

    public void addEpisodesToTable(Episode e){
        centerPanel.addEpisodesToTable(e);
    }

    public void clearTable(){
        centerPanel.clearTable();
    }

    public void addChannelButton(Channel c, ActionListener al){
        westPanel.addChannelButton(c,al);
    }

    public void addChannelToDisplay(Channel c){
        centerPanel.addChannelToDisplay(c);
    }

    public void addEpisodeInformation(Episode e){
        eastPanel.addEpisodeInformation(e);
    }

    public void addErrorMessageForNoSchedule(Channel c){
        eastPanel.addErrorMessageForNoSchedule(c);
    }

    public void updateGUI(){
        centerPanel.update();
        eastPanel.update();
        westPanel.update();
    }

    public void removeChannels(){
        westPanel.removeChannels();
    }

    public int getCurrentEpisodeID() {
        return centerPanel.getCurrentEpisodeID();
    }
    public int getCurrentChannelID() {
        return centerPanel.getCurrentChannelID();
    }

    public void clearAll(){
        westPanel.removeChannels();
        centerPanel.clearChannelWindow();
        centerPanel.clearTable();
        eastPanel.clearInfo();
    }
}
