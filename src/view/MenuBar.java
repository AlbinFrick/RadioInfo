package view;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Builds the menu of the program.
 * @author Albin Frick
 */
public class MenuBar {
    private JMenuBar jMenuBar;
    private ActionListener menuFilterAL;
    private ActionListener menuReload;

    /**
     * Sets the ActionListener variables
     * @param menuFilterAL - ActionListeners
     * @param menuReload - ActionListeners
     */
    public MenuBar(ActionListener menuFilterAL, ActionListener menuReload){
        this.menuFilterAL = menuFilterAL;
        this.menuReload = menuReload;
        createMenu();
    }

    /**
     * Creates the whole menu.
     * One menu bar and two menus with the concurrent menu items inside them.
     */
    public void createMenu(){
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("Reload");
        jmb.add(jm);
        JMenu jm2 = new JMenu("Filter");
        jmb.add(jm2);
        JMenuItem reload = new JMenuItem("reload");
        reload.addActionListener(menuReload);
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
        jMenuBar = jmb;
    }

    /**
     * @return - JMenuBar
     */
    public JMenuBar getJMenuBar() {
        return jMenuBar;
    }
}
