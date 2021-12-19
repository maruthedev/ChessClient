package game;

import control.ClientCtr;
import model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GameWindow {
    public final JFrame mainFrame;
    private final Board board;

    public GameWindow(Player player, ClientCtr myControl) {
        mainFrame = new JFrame("Chess");
        try {
            Image whiteImg = ImageIO.read(new File("resources/wp.png"));
            mainFrame.setIconImage(whiteImg);
        } catch (Exception e) {
            System.out.println("Game file wp.png not found");
        }
        mainFrame.setLocation(100, 100);
        mainFrame.setLayout(new BorderLayout(20, 20));

        this.board = new Board(this, player, myControl);
        mainFrame.add(board, BorderLayout.CENTER);
        mainFrame.add(buttons(), BorderLayout.SOUTH);
        mainFrame.setMinimumSize(mainFrame.getPreferredSize());
        mainFrame.setSize(mainFrame.getPreferredSize());
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
    }

    private JPanel buttons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 1, 10, 0));

        final JButton quit = new JButton("Quit");

        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(
                        mainFrame,
                        "Are you sure you want to quit?",
                        "Confirm quit", JOptionPane.YES_NO_OPTION);

                if (n == JOptionPane.YES_OPTION) {
                    mainFrame.dispose();
                }
            }
        });

        buttons.add(quit);
        buttons.setPreferredSize(buttons.getMinimumSize());
        return buttons;
    }
}
