import javax.swing.*;
import Panel.PanelGame;
public class MainGame {

    public static void main(String[] s) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Bomberman");
        PanelGame panel = new PanelGame();
        frame.add(panel);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.startGameThread();
    }
}