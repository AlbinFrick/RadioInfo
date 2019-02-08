package view;

import model.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Creates the west panel of the frame.
 * @author Albin Frick
 */
public class WestPanel {
    private JPanel westPanel;
    private JScrollPane channelScrollPane;

    /**
     * The constructor builds the panel.
     */
    public WestPanel(){
        buildChannelWindow();
        buildChannelScrollPane();
    }

    /**
     * Builds a gradient panel and sets a gridlayout with
     * infinite rows and three columns.
     */
    private void buildChannelWindow(){
        GradientPanel jPanel = new GradientPanel(new Color(90, 19, 1),
                                                new Color(14, 13, 90));
        jPanel.setLayout(new GridLayout(0,3));
        westPanel = jPanel;
    }

    /**
     * Creates a scroll-pane with westPanel JPanel.
     * Sets the width and height and increases the scroll speed.
     */
    private void buildChannelScrollPane(){
        channelScrollPane = new JScrollPane(westPanel);
        channelScrollPane.setPreferredSize(new Dimension(420,1000));
        channelScrollPane.setViewportView(westPanel);
        channelScrollPane.getVerticalScrollBar().setUnitIncrement(10);
    }

    /**
     * Creates a button adds a action listener and the given channels
     * picture to it and then adds the button to the westPanel JPanel.
     * @param c - Channel
     * @param al - ActionListener
     */
    public void addChannelButton(Channel c, ActionListener al){
        JButton jb = new JButton();
        jb.setActionCommand(Integer.toString(c.getChannelID()));
        jb.addActionListener(al);
        jb.setIcon(c.getImage());
        westPanel.add(jb);
    }

    /**
     * Depending on the given bool a loader is displayed or removed
     * from the westPanel JPanel.
     * @param bool - boolean
     */
    public void toggleLoadingIcon(boolean bool){
        if (bool) {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass()
                    .getClassLoader().getResource("resources/loading.gif")));
            JLabel label = new JLabel(icon);
            JLabel dummy1 = new JLabel();
            label.setPreferredSize(new Dimension(100,100));
            westPanel.add(dummy1);
            westPanel.add(label);
        }else{
            clearWestPanel();
        }
    }

    /**
     * Removes every element in the westPanel JPanel
     */
    public void clearWestPanel(){
        westPanel.removeAll();
    }

    /**
     * Updates the elements in the westPanel JPanel.
     */
    public void update(){
        westPanel.revalidate();
        westPanel.repaint();
    }

    /**
     * @return - JScrollPane
     */
    public JScrollPane getChannelScrollPane() {
        return channelScrollPane;
    }
}
