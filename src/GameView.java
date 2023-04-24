import javax.swing.*;
import java.awt.*;

public class GameView {
    private Board board;
    private JFrame frame;
    private JPanel panel;
    private JLabel[][] labels;

    public GameView(Board board) {
        this.board = board;
        this.frame = new JFrame("Chess Game");
        this.panel = new JPanel(new GridLayout(8, 8));
        this.labels = new JLabel[8][8];

        // Initialize the board GUI
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                labels[i][j] = new JLabel();
                labels[i][j].setPreferredSize(new Dimension(60, 60));
                panel.add(labels[i][j]);
            }
        }

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void updateBoard() {
        // Update the board GUI
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Spot spot = board.getSpot(i, j);
                Piece piece = spot.getPiece();

                if (piece == null) {
                    labels[i][j].setIcon(null);
                } else {
                    ImageIcon icon = new ImageIcon(piece.getImagePath());
                    labels[i][j].setIcon(icon);
                }
            }
        }

        panel.revalidate();
        panel.repaint();
    }
}
