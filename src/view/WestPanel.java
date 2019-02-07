package view;

import model.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class WestPanel {
    private JPanel westPanel;
    private JScrollPane channelScrollPane;
    private ImageIcon logo;

    public WestPanel(){
        buildChannelWindow();
        buildChannelScrollPane();
    }

    /**
     * hejsdöflkjoe
     */
    private void buildChannelWindow(){
        GradientPanel jPanel = new GradientPanel(new Color(90, 19, 1),
                                                new Color(14, 13, 90));
        jPanel.setLayout(new GridLayout(0,3));
        westPanel = jPanel;
    }

    private void buildChannelScrollPane(){
        channelScrollPane = new JScrollPane(westPanel);
        channelScrollPane.setPreferredSize(new Dimension(420,1000));
        channelScrollPane.setViewportView(westPanel);
        channelScrollPane.getVerticalScrollBar().setUnitIncrement(10);
    }


    public void addChannelButton(Channel c, ActionListener al){
        JButton jb = new JButton();
        jb.setActionCommand(Integer.toString(c.getChannelID()));
        jb.addActionListener(al);
        jb.setIcon(c.getImage());
        westPanel.add(jb);
    }


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

    public void clearWestPanel(){
        westPanel.removeAll();
    }

    public void update(){
        westPanel.revalidate();
        westPanel.repaint();
    }

    public JScrollPane getChannelScrollPane() {
        return channelScrollPane;
    }


}
