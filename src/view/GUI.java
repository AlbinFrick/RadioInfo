package view;

import model.Channel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel contentPanel;
    private JPanel channelDisplayWindow;
    private JScrollPane channelScrollPane;

    public GUI(){
        setPreferredSize(new Dimension(979,1000));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLayout(new BorderLayout());
        buildChannelWindow();
        buildChannelScrollPane();

        buildRightPanel();
        buildContentPanel();
        buildChannelWindowDisplay();
        add(channelScrollPane, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        setJMenuBar(addMenu());
    }

    private void buildChannelWindow(){
        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.red);
        jPanel.setLayout(new GridLayout(0,3));
        leftPanel = jPanel;
    }

    private void buildChannelScrollPane(){
        channelScrollPane = new JScrollPane(leftPanel);
        channelScrollPane.setPreferredSize(new Dimension(420,1000));
        channelScrollPane.setViewportView(leftPanel);
        channelScrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }

    private void buildRightPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(540, 1000));
        jPanel.setBackground(Color.cyan);
        jPanel.setLayout(new BorderLayout());
        rightPanel = jPanel;
    }

    private void buildContentPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(540, 780));
        jPanel.setBackground(Color.green);
        contentPanel = jPanel;
        rightPanel.add(jPanel, BorderLayout.SOUTH);
    }

    private void buildChannelWindowDisplay(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(540, 150));
        jPanel.setBackground(Color.blue);
        channelDisplayWindow = jPanel;
        rightPanel.add(jPanel, BorderLayout.NORTH);
    }

    public void addChannelButton(Channel c, ActionListener al){
        JButton jb = new JButton();
        jb.addActionListener(al);
        jb.setIcon(c.getImage());
        leftPanel.add(jb);
        updateGUI();
    }

    public void addChannelToDisplay(Channel c){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(540, 150));
        JTextArea nameArea = new JTextArea(c.getChannelName());
        JTextArea taglineArea = new JTextArea(c.getTagLine());
        taglineArea.setPreferredSize(new Dimension(300, 100));
        jPanel.add(nameArea);
        jPanel.add(taglineArea);
        channelDisplayWindow.add(jPanel);
        updateGUI();
    }

    private JMenuBar addMenu(){
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("A menu");
        jmb.add(jm);
        JMenuItem jmi = new JMenuItem("test");
        jm.add(jmi);

        return jmb;
    }

    private void updateGUI(){
        leftPanel.revalidate();
        leftPanel.repaint();
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
