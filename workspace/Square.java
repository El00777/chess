import javax.swing.*;
import java.awt.*;

public class Square extends JPanel {
    private int row, col;
    private Piece piece;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        this.setPreferredSize(new Dimension(80, 80));  // Adjust size as needed
        this.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        repaint();
    }

    public boolean isOccupied() {
        return piece != null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (piece != null) {
            piece.draw(g, this);
        }
    }
}
