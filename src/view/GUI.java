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
    private JScrollPane contentScrollPane;
    private JTextArea nameTaglineArea;
    private JLabel logoLabel;

    public GUI(ActionListener menuFilterAL){
        setPreferredSize(new Dimension(979,1000));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLayout(new BorderLayout());
        buildChannelWindow();
        buildChannelScrollPane();
        buildRightPanel();
        buildContentPanel();
        buildContentScrollPane();
        buildChannelWindowDisplay();
        add(channelScrollPane, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        addMenu(menuFilterAL);
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

    private void buildContentScrollPane(){
        channelScrollPane = new JScrollPane(contentPanel);
        channelScrollPane.setPreferredSize(new Dimension(540,780));
        channelScrollPane.setViewportView(contentPanel);
        channelScrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }


    private void buildContentPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(540, 780));
        jPanel.setBackground(Color.green);
        JTable table = new JTable();
        contentPanel = jPanel;
        contentPanel.add(table);
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
        jb.setActionCommand(Integer.toString(c.getChannelID()));
        jb.addActionListener(al);
        jb.setIcon(c.getImage());
        leftPanel.add(jb);
        updateGUI();
    }

    public void addChannelToDisplay(Channel c){
        if (logoLabel != null && nameTaglineArea != null){
            removeLogoAndTaglineChannelDisplay();
        }
        StringBuilder nameTaglineString =  new StringBuilder();
        logoLabel = new JLabel(c.getImage());
        nameTaglineString.append(c.getChannelName() + "\n\n");
        nameTaglineString.append(c.getTagLine());
        nameTaglineArea = new JTextArea(nameTaglineString.toString());
        nameTaglineArea.setLineWrap(true);
        nameTaglineArea.setWrapStyleWord(true);
        nameTaglineArea.setPreferredSize(new Dimension(350, 100));
        nameTaglineArea.setLayout(new GridLayout(0,2));
        channelDisplayWindow.add(logoLabel);
        channelDisplayWindow.add(nameTaglineArea);
        updateGUI();
    }

    private void addMenu(ActionListener menuFilterAL){
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("A menu");
        jmb.add(jm);
        JMenu jm2 = new JMenu("Filter");
        jmb.add(jm2);

        JMenuItem reload = new JMenuItem("reload");
        jm.add(reload);

        JMenuItem p1 = new JMenuItem("P1");
        p1.setActionCommand("p1");
        p1.addActionListener(menuFilterAL);
        jm2.add(p1);
        JMenuItem p2 = new JMenuItem("P2");
        p1.setActionCommand("p2");
        p1.addActionListener(menuFilterAL);
        jm2.add(p2);
        JMenuItem p3 = new JMenuItem("P3");
        p1.setActionCommand("p3");
        p1.addActionListener(menuFilterAL);
        jm2.add(p3);
        JMenuItem p4 = new JMenuItem("P4");
        p1.setActionCommand("p4");
        p1.addActionListener(menuFilterAL);
        jm2.add(p4);
        JMenuItem other = new JMenuItem("Other");
        p1.setActionCommand("other");
        p1.addActionListener(menuFilterAL);
        jm2.add(other);

        setJMenuBar(jmb);
    }

    private void updateGUI(){
        leftPanel.revalidate();
        leftPanel.repaint();
        contentPanel.revalidate();
        contentPanel.repaint();
        channelDisplayWindow.revalidate();
        channelDisplayWindow.repaint();
    }

    private void removeLogoAndTaglineChannelDisplay(){
        channelDisplayWindow.removeAll();
    }

    public void removeChannels(){
        leftPanel.removeAll();
    }

}
