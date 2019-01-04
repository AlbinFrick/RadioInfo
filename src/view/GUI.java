package view;

import model.Channel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;

public class GUI extends JFrame{
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTable scheduleTable;
    private JPanel channelDisplayWindow;
    private JScrollPane channelScrollPane;
    private JScrollPane scheduleScrollPane;
    private JTextArea nameTaglineArea;
    private JLabel logoLabel;
    private DefaultTableModel dft;

    public GUI(ActionListener menuFilterAL){
        setPreferredSize(new Dimension(979,1000));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        dft = new DefaultTableModel(0,3){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scheduleTable = new JTable(dft);
        setLayout(new BorderLayout());
        //left
        buildChannelWindow();
        buildChannelScrollPane();
        //right
        buildRightPanel();
        buildChannelWindowDisplay();
        buildContentScrollPane();
        buildSchedulePanel();
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
        scheduleScrollPane = new JScrollPane(scheduleTable);
        scheduleScrollPane.setPreferredSize(new Dimension(540,780));
        scheduleScrollPane.setBackground(Color.green);
        scheduleScrollPane.setViewportView(scheduleTable);
        scheduleScrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }

    private void buildSchedulePanel(){
        JTable jTable = new JTable();
        jTable.setPreferredSize(new Dimension(540, 780));
        scheduleTable = jTable;
        rightPanel.add(scheduleScrollPane, BorderLayout.SOUTH);
    }

    private void buildEpisodeTable(){

    }

    private void buildChannelWindowDisplay(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(540, 150));
        jPanel.setBackground(Color.blue);
        channelDisplayWindow = jPanel;
        rightPanel.add(channelDisplayWindow, BorderLayout.NORTH);
    }

    public void addChannelButton(Channel c, ActionListener al){
        JButton jb = new JButton();
        jb.setActionCommand(Integer.toString(c.getChannelID()));
        jb.addActionListener(al);
        jb.setIcon(c.getImage());
        leftPanel.add(jb);
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
        p1.addActionListener(menuFilterAL);
        jm2.add(p1);
        JMenuItem p2 = new JMenuItem("P2");
        p2.addActionListener(menuFilterAL);
        jm2.add(p2);
        JMenuItem p3 = new JMenuItem("P3");
        p3.addActionListener(menuFilterAL);
        jm2.add(p3);
        JMenuItem p4 = new JMenuItem("P4");
        p4.addActionListener(menuFilterAL);
        jm2.add(p4);
        JMenuItem other = new JMenuItem("Other");
        other.addActionListener(menuFilterAL);
        jm2.add(other);
        setJMenuBar(jmb);
    }

    public void updateGUI(){
        leftPanel.revalidate();
        leftPanel.repaint();
        scheduleTable.revalidate();
        scheduleTable.repaint();
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
