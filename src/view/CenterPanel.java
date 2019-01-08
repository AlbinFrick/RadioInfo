package view;

import model.Channel;
import model.Episode;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class CenterPanel {
    private DefaultTableModel defaultTableModel;
    private JScrollPane scheduleScrollPane;
    private JTextArea nameTaglineArea;
    private JPanel channelWindowDisplay;
    private JPanel centerPanel;
    private JTable scheduleTable;
    private JLabel logoLabel;
    private int currentEpisodeID;
    private int scheduleColumnSize = 3;
    private ArrayList<Episode> episodes;

    public CenterPanel(ActionListener actionListener){
        episodes = new ArrayList<>();
        buildCenterPanel();
        buildChannelWindowDisplay();
        buildScheduleTabel();
        buildScheduleScrollPane();
        setSelectionListener(actionListener);
    }

    private void buildCenterPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(540, 1000));
        jPanel.setBackground(Color.cyan);
        jPanel.setLayout(new BorderLayout());
        centerPanel = jPanel;
    }

    private void buildChannelWindowDisplay(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(540, 130));
        jPanel.setBackground(new Color(41, 106, 166));
        jPanel.setBorder(BorderFactory.createEmptyBorder());
        channelWindowDisplay = jPanel;
        centerPanel.add(jPanel, BorderLayout.CENTER);
    }

    private void buildScheduleTabel(){
        defaultTableModel = new DefaultTableModel(0,scheduleColumnSize){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scheduleTable = new JTable(defaultTableModel);
        scheduleTable.getColumnModel().getColumn(0).setHeaderValue("Program");
        scheduleTable.getColumnModel().getColumn(1).setHeaderValue("Starttid");
        scheduleTable.getColumnModel().getColumn(2).setHeaderValue("Sluttid");
    }

    private void buildScheduleScrollPane(){
        scheduleScrollPane = new JScrollPane(scheduleTable);
        scheduleScrollPane.setPreferredSize(new Dimension(540,800));
        scheduleScrollPane.setBackground(Color.green);
        scheduleScrollPane.setViewportView(scheduleTable);
        scheduleScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        centerPanel.add(scheduleScrollPane, BorderLayout.SOUTH);
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public void addEpisodesToTable(Episode e){
        episodes.add(e);
        String[] row = new String[scheduleColumnSize];
        row[0] = e.getTitle();
        row[1] = new SimpleDateFormat("YYYY-MM-DD HH:mm").format(e.getStartTime());
        row[2] = new SimpleDateFormat("YYYY-MM-DD HH:mm").format(e.getEndTime());
        defaultTableModel.addRow(row);
    }

    public void clearTable(){
        defaultTableModel.setNumRows(0);
    }

    public void addChannelToDisplay(Channel c){
        if (logoLabel != null && nameTaglineArea != null){
            channelWindowDisplay.removeAll();
        }
        logoLabel = new JLabel(c.getImage());
        StringBuilder nameTaglineString =  new StringBuilder();
        nameTaglineString.append(c.getChannelName() + "\n\n");
        nameTaglineString.append(c.getTagLine());
        nameTaglineArea = new JTextArea(nameTaglineString.toString());
        nameTaglineArea.setLineWrap(true);
        nameTaglineArea.setWrapStyleWord(true);
        nameTaglineArea.setPreferredSize(new Dimension(350, 100));
        nameTaglineArea.setLayout(new GridLayout(0,2));
        channelWindowDisplay.add(logoLabel);
        channelWindowDisplay.add(nameTaglineArea);
    }

    private void setSelectionListener(ActionListener tableListener){
        scheduleTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                currentEpisodeID = episodes.get(scheduleTable.getSelectedRow()).getProgramID();
                tableListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });
    }

    public int getCurrentEpisodeID() {
        return currentEpisodeID;
    }

    public void update(){
        centerPanel.revalidate();;
        centerPanel.repaint();
    }
}