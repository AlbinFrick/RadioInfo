package view;

import model.Episode;

import javax.swing.*;
import java.awt.*;

public class EastPanel {
    private JPanel eastPanel;
    private JPanel informationWindow;
    private JLabel logo;
    private JTextArea description;

    public EastPanel(){
        buildEastPanel();
        buildEpisodeInformationWindow();
    }

    private void buildEastPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(420, 1000));
        jPanel.setBackground(new Color(21, 5, 120));
        jPanel.setLayout(new BorderLayout());
        eastPanel =  jPanel;
    }

    private void buildEpisodeInformationWindow(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(300, 250));
        jPanel.setBackground(new Color(21, 5, 0));
        jPanel.setLayout(new BorderLayout());
        informationWindow =  jPanel;
        eastPanel.add(informationWindow, BorderLayout.NORTH);
    }

    public void addEpisodeInformation(Episode e){
        if (logo != null && description != null){
            informationWindow.removeAll();
        }
        logo = new JLabel(e.getImage());
        description = new JTextArea(e.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        informationWindow.add(logo);
        informationWindow.add(description);
    }

    public void update(){
        eastPanel.revalidate();
        eastPanel.repaint();
        informationWindow.revalidate();
        informationWindow.repaint();
    }

    public JPanel getEastPanel() {
        return eastPanel;
    }
}
