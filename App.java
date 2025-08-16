import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {

        int boardWidth = 600;
        int boardHeight = 600;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(600,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakegame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakegame);
        frame.pack();
        snakegame.requestFocus();
        
    }
}
