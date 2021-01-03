
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class DuidokuGraphs extends JFrame {
    private ArrayList<Integer> su;
    private JButton button;
    private JLabel label;
    private JPanel panel;
    private JTextField[][] board;
    private LogicDuidoku logicDuidoku;


    public DuidokuGraphs() throws Exception {

        logicDuidoku=new LogicDuidoku();


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button=new JButton("Available numbers:");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setVisible(true);
                button.setEnabled(false);

            }

        });
        label = new JLabel("                  ");
        GridLayout grid = new GridLayout(4, 4, 10, 10);
        FlowLayout layout1 = new FlowLayout();
        layout1.setHgap(20);
        layout1.setVgap(10);
        layout1.setAlignment(FlowLayout.LEADING);

        setLayout(layout1);
        panel = new JPanel();
        panel.setLayout(grid);

        board = new JTextField[4][4];
        su = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = new JTextField();
                board[i][j].setText(Integer.toString(logicDuidoku.getBoard()[i][j]));
                int finalI = i;
                int finalj = j;
                board[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int x = finalI;
                        int y = finalj;
                        label.setText(logicDuidoku.getHelpClassicSudoku(x, y));
                        boolean o = logicDuidoku.addMove(x, y, Integer.parseInt(board[x][y].getText()));
                        if (logicDuidoku.Check()) {

                            new PlayerWinsGraph();
                            dispose();
                        }
                        if (o) {
                            board[x][y].setBackground(Color.DARK_GRAY);
                            board[x][y].setEnabled(false);
                            boolean pc = logicDuidoku.pcMove();
                            board[logicDuidoku.getX()][logicDuidoku.getY()].setBackground(Color.BLUE);
                            board[logicDuidoku.getX()][logicDuidoku.getY()].setText(Integer.toString(logicDuidoku.getBoard()[logicDuidoku.getX()][logicDuidoku.getY()]));
                            board[logicDuidoku.getX()][logicDuidoku.getY()].setEnabled(false);
                            if (logicDuidoku.Check() && pc) {

                                new PcWinsGraph();
                                dispose();
                            }
                        }else if(board[x][y].getText().equals(Integer.toString(logicDuidoku.getBoard()[x][y]))){

                        } else{
                            board[x][y].setText(Integer.toString(logicDuidoku.getBoard()[x][y]));
                            if(logicDuidoku.getBoard()[x][y]!=0){
                                board[x][y].setBackground(Color.GREEN);
                            }
                            else{
                                board[x][y].setBackground(Color.WHITE);
                            }
                        }
                    }
                });
                panel.add(board[i][j]);
            }
        }
        add(panel);
        add(button);
        add(label);
        pack();
        label.setVisible(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}


