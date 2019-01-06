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

    public GUI(ActionListener menuFilterAL){
        setPreferredSize(new Dimension(1500,1000));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLayout(new BorderLayout());
        setJMenuBar(new MenuBar(menuFilterAL).getjMenuBar());
        westPanel = new WestPanel();
        centerPanel = new CenterPanel();
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

    public void updateGUI(){
        centerPanel.update();
        eastPanel.update();
        westPanel.update();
    }

    public void removeChannels(){
        westPanel.removeChannels();
    }
}
