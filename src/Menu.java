

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame implements ActionListener {
    JMenu menu, submenu;

    JMenuItem i1, i2, i8;

    public Menu() {
        JFrame f = new JFrame("Let's play Sudoku!");
        JMenuBar menubar = new JMenuBar();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menu = new JMenu("Menu");
        submenu = new JMenu("Play");
        i1 = new JMenuItem("Classic Sudoku (9x9)");
        i2 = new JMenuItem("Duidoku");
        i8 = new JMenuItem("Exit");

        i1.addActionListener(this);
        i2.addActionListener(this);
        i8.addActionListener(this);

        menubar.add(menu);
        menu.add(submenu);

        submenu.add(i1);
        submenu.add(i2);
        menu.add(i8);


        f.setJMenuBar(menubar);
        f.setSize(500, 200);
        f.setLayout(null);
        f.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {

        MySudoku start= new MySudoku();
        if (e.getSource() == i1) {
            try {
                start.ShowGUI();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == i2) {
            try {
                new DuidokuGraphs();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == i8) {
            System.exit(0);
        }
    }


}
