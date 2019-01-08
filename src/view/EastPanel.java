package view;

import model.Channel;
import model.Episode;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

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
        jPanel.setPreferredSize(new Dimension(300, 1000));
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
        description.setPreferredSize(new Dimension(300, 150));
        informationWindow.add(logo, BorderLayout.NORTH);
        informationWindow.add(description, BorderLayout.SOUTH);
    }

    public void addErrorMessageForNoSchedule(Channel c){
        if (logo != null && description != null){
            informationWindow.removeAll();
        }
        logo = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("questionMark.jpeg"))));

        String text = "Error for " + c.getChannelName() + ": " + c.getErrorMessage() ;
        description = new JTextArea(text);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setPreferredSize(new Dimension(300, 150));
        informationWindow.add(logo, BorderLayout.NORTH);
        informationWindow.add(description, BorderLayout.SOUTH);
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
