package view;

import model.Channel;
import model.Episode;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creates the central panel to the frame.
 */
public class CenterPanel{
    private DefaultTableModel defaultTableModel;
    private JScrollPane scheduleScrollPane;
    private JTextArea nameTaglineArea;
    private JPanel channelWindowDisplay;
    private JPanel centerPanel;
    private JTable scheduleTable;
    private JLabel logoLabel;
    private int currentEpisodeID;
    private int currentChannelID;
    private int scheduleColumnSize = 3;
    private CopyOnWriteArrayList<Episode> episodes;

    /**
     * Builds the every element in the central panel and
     * adds the given action listener to the table.
     * @param actionListener - ActionListener
     */
    public CenterPanel(ActionListener actionListener){
        episodes = new CopyOnWriteArrayList<>();
        buildCenterPanel();
        buildChannelWindowDisplay();
        buildScheduleTabel();
        buildScheduleScrollPane();
        setColorOfTable();
        setSelectionListener(actionListener);
    }

    /**
     * Creates a JPanel sets size and layout of the panel.
     */
    private void buildCenterPanel(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(540, 1000));
        jPanel.setLayout(new BorderLayout());
        centerPanel = jPanel;
    }

    /**
     * Creates a gradient panel sets size, border and adds it to
     * the central panel in the center.
     */
    private void buildChannelWindowDisplay(){
        GradientPanel jPanel = new GradientPanel(
                new Color(10, 11, 57),
                new Color(166, 56, 62));
        jPanel.setPreferredSize(new Dimension(540, 130));
        jPanel.setBorder(BorderFactory.createEmptyBorder());
        channelWindowDisplay = jPanel;
        centerPanel.add(jPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a default table model and overrides 'isCellEditable' to return false.
     * Creates a jTable with the default table model and then adds three columns, 'Program',
     * 'Starttid','Sluttid'.
     * Also sets font and the height of the cells.
     */
    private void buildScheduleTabel(){
        defaultTableModel = new DefaultTableModel(0,scheduleColumnSize){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scheduleTable = new JTable(defaultTableModel);
        scheduleTable.getColumnModel()
                .getColumn(0).setHeaderValue("Program");
        scheduleTable.getColumnModel()
                .getColumn(1).setMaxWidth(90);
        scheduleTable.getColumnModel()
                .getColumn(2).setMaxWidth(90);
        scheduleTable.getColumnModel()
                .getColumn(1).setHeaderValue("Starttid");
        scheduleTable.getColumnModel()
                .getColumn(2).setHeaderValue("Sluttid");
        scheduleTable.setFont(new Font("Roboto", Font.PLAIN, 14));
        scheduleTable.setRowHeight(24);
    }

    /**
     * Creates a scroll pane from the schedule table.
     * Sets size, increase the scroll speed and adds it to the
     * central panel.
     */
    private void buildScheduleScrollPane(){
        scheduleScrollPane = new JScrollPane(scheduleTable);
        scheduleScrollPane.setPreferredSize(new Dimension(540,800));
        scheduleScrollPane.setViewportView(scheduleTable);
        scheduleScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        centerPanel.add(scheduleScrollPane, BorderLayout.SOUTH);
    }

    /**
     * Adds one episode to the table.
     * While adding to the table this method also stores the
     * episode in an array list.
     * @param e - Episode
     */
    public void addEpisodesToTable(Episode e){
        if (episodes.size() == 0)
            episodes.clear();
        episodes.add(e);
        String[] row = new String[scheduleColumnSize];
        row[0] = e.getTitle();
        row[1] = new SimpleDateFormat("HH:mm").format(e.getStartTime());
        row[2] = new SimpleDateFormat("HH:mm").format(e.getEndTime());
        defaultTableModel.addRow(row);
    }

    /**
     * Changes the color of the rows in the table.
     * This method sets the default render of the table
     * to a new table cell renderer and overrides the
     * getTableCellRendererComponent method.
     * Then each row is rendered to a certain color dependent if
     * the row is selected or not or if the episode has been broadcast
     * or not.
     */
    public void setColorOfTable() {
        scheduleTable.setDefaultRenderer(
                Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                final Component component = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                Episode e = episodes.get(row);
                if (e.isBroadcasting() && !isSelected){
                    component.setBackground(new Color(0, 200, 200));
                } else if (e.hasAlreadyBroadcasted() && !isSelected){
                    component.setBackground(Color.gray);
                } else if (e.isBroadcastingInFuture() && !isSelected){
                    component.setBackground(Color.white);
                }else if (e.hasAlreadyBroadcasted() && isSelected){
                    component.setBackground(Color.darkGray);
                }else if (e.isBroadcastingInFuture() && isSelected){
                    component.setBackground(new Color(255,223,186));
                }
                return component;
            }
        });
    }

    /**
     * Creates a list selection listener who listens for something
     * to happen in the table. The variables currentEpisodeID and
     * currentChannelID are then set to the right id.
     * When that list is triggered the given action listener are
     * then triggered which affects the controller.
     * This the program to know which episode the user has pressed.
     * @param tableListener - ActionListener
     */
    private void setSelectionListener(ActionListener tableListener){
        scheduleTable.getSelectionModel()
                .addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(listSelectionEvent.getValueIsAdjusting()) {
                    currentEpisodeID = episodes.get(
                            scheduleTable.getSelectedRow()).getProgramID();
                    currentChannelID = episodes.get(
                            scheduleTable.getSelectedRow()).getChannelID();
                    tableListener.actionPerformed(new ActionEvent(
                            this, ActionEvent.ACTION_PERFORMED,
                            null));
                }
            }
        });
    }

    /**
     * Removes the rows from the table and clears the
     * array list of episodes.
     */
    public void clearTable(){
        episodes.clear();
        defaultTableModel.setNumRows(0);
    }

    /**
     * Removes every element in the channel window.
     */
    public void clearChannelWindow(){
        channelWindowDisplay.removeAll();
    }

    /**
     * Adds a channel to the channel display window.
     * Creates a label and ands the image of the channel to it.
     * Then creates a string builder and appends the information
     * of the channel. The image and the information is then added
     * to the channel window display panel.
     * @param c - Channel
     */
    public void addChannelToDisplay(Channel c){
        if (logoLabel != null && nameTaglineArea != null){
            clearChannelWindow();
        }
        logoLabel = new JLabel(c.getImage());
        StringBuilder nameTaglineString =  new StringBuilder();
        nameTaglineString.append(c.getChannelName() + "\n\n");
        nameTaglineString.append(c.getTagLine() + "\n" + c.getSiteURL());
        nameTaglineArea = new JTextArea(nameTaglineString.toString());
        nameTaglineArea.setLineWrap(true);
        nameTaglineArea.setWrapStyleWord(true);
        nameTaglineArea.setPreferredSize(new Dimension(350, 100));
        nameTaglineArea.setLayout(new GridLayout(0,2));
        channelWindowDisplay.add(logoLabel);
        channelWindowDisplay.add(nameTaglineArea);
    }

    /**
     * @return - JPanel
     */
    public JPanel getCenterPanel() {
        return centerPanel;
    }

    /**
     * @return - int
     */
    public int getCurrentEpisodeID() {
        return currentEpisodeID;
    }

    /**
     * @return - int
     */
    public int getCurrentChannelID() {
        return currentChannelID;
    }

    /**
     * Updates everything in the center panel.
     */
    public void update(){
        centerPanel.revalidate();
        centerPanel.repaint();
    }
}
