import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private Square[][] squares;
    private GameWindow gameWindow;
    private boolean whiteTurn = true;

    public Board(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        this.setLayout(new GridLayout(8, 8));
        squares = new Square[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col] = new Square(row, col);
                this.add(squares[row][col]);
            }
        }

        // You can place some pieces here for testing if needed
        // Example: setPiece(new Bishop(true), 2, 0);
    }

    public Square[][] getSquareArray() {
        return squares;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void switchTurn() {
        whiteTurn = !whiteTurn;
    }

    public void setPiece(Piece piece, int row, int col) {
        squares[row][col].setPiece(piece);
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }
}
