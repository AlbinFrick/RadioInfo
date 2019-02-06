package view;

import model.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class WestPanel {
    private JPanel westPanel;
    private JScrollPane channelScrollPane;
    private JLabel loadingImage;

    public WestPanel(){
        buildChannelWindow();
        buildChannelScrollPane();

        String pathToImage = "resources/loading.gif";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull
                (getClass().getClassLoader().getResource(pathToImage)));
        loadingImage = new JLabel(icon);
        JPanel bajspanel = new JPanel();
        bajspanel.setLayout(new BorderLayout());
        westPanel.add(loadingImage);
    }

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

    public void removeChannels(){
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
