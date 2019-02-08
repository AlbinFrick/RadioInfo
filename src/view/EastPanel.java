package view;

import model.Channel;
import model.Episode;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Creates the east panel of the frame.
 * @author Albin Frick
 */
public class EastPanel {
    private JPanel eastPanel;
    private JPanel informationWindow;
    private JLabel logo;
    private JTextArea description;
    private JButton playButton;

    /**
     * Builds the east panel and the episode information window.
     */
    public EastPanel(){
        buildEastPanel();
        buildEpisodeInformationWindow();
    }

    /**
     * Creates a JPanel sets size, color and layout.
     */
    private void buildEastPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(420, 1000));
        jPanel.setLayout(new BorderLayout());
        eastPanel =  jPanel;
    }

    /**
     * Creates a gradient panel sets size, layout and adds it
     * to the eastPanel JPanel to the north.
     */
    private void buildEpisodeInformationWindow(){
        GradientPanel jPanel = new GradientPanel(new Color(90, 19, 1), new Color(14, 13, 90));
        jPanel.setPreferredSize(new Dimension(300, 940));
        jPanel.setLayout(new BorderLayout());
        informationWindow =  jPanel;
        eastPanel.add(informationWindow, BorderLayout.NORTH);
    }

    /**
     * Takes in an episodes and ads the information of the episode to the
     * episode information window.
     * @param e - Episode
     */
    public void addEpisodeInformation(Episode e){
        if (logo != null && description != null){
            clearInfo();
        }
        if (e.getImage() == null) {
            logo = new JLabel(new ImageIcon(Objects.requireNonNull(getClass()
                    .getClassLoader().getResource("resources/missingPic.png"))));
        } else {
            logo = new JLabel(e.getImage());
        }
        description = new JTextArea(e.getTitle() + "\n" + e.getDescription()  );
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setPreferredSize(new Dimension(300, 150));
        playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(150, 100));
        informationWindow.add(logo, BorderLayout.NORTH);
        informationWindow.add(description, BorderLayout.CENTER);
        informationWindow.add(playButton, BorderLayout.SOUTH);
    }

    /**
     * Clears the episode information window if there is something
     * to clear and adds the error message in the channel to the information
     * window.
     * @param c - Channel
     */
    public void addErrorMessageForNoSchedule(Channel c){
        if (logo != null && description != null){
            clearInfo();
        }
        String pathToImage = "resources/missingPic.png";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(pathToImage)));
        logo = new JLabel(icon);
        String text = "Error for " + c.getChannelName() + ": " + c.getErrorMessage();
        description = new JTextArea(text);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setPreferredSize(new Dimension(300, 150));
        informationWindow.add(logo, BorderLayout.NORTH);
        informationWindow.add(description, BorderLayout.SOUTH);
    }

    /**
     * Updates every element in the east panel
     */
    public void update(){
        eastPanel.revalidate();
        eastPanel.repaint();
        informationWindow.revalidate();
        informationWindow.repaint();
    }

    /**
     * Removes every element in the information window.
     */
    public void clearInfo(){
        informationWindow.removeAll();
    }

    /**
     * @return - JPanel
     */
    public JPanel getEastPanel() {
        return eastPanel;
    }
}
