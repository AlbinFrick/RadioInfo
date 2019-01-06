package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBar {
    private JMenuBar jMenuBar;
    public MenuBar(ActionListener menuFilterAL){
        addMenu(menuFilterAL);
    }
    public void addMenu(ActionListener menuFilterAL){
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
        jMenuBar = jmb;
    }

    public JMenuBar getjMenuBar() {
        return jMenuBar;
    }
}
