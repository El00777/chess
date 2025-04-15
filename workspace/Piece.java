import java.awt.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// This class represents a chess piece
public class Piece {
    private final boolean color; // true = white, false = black
    private BufferedImage img;
    private static final int SQUARE_SIZE = 75; // Size in pixels for drawing

    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;

        try {
            if (this.img == null) {
                this.img = ImageIO.read(getClass().getResource(img_file));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean getColor() {
        return color;
    }

    public Image getImage() {
        return img;
    }

    // Fixed draw() method: converts row/col to x/y pixel values
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getCol() * SQUARE_SIZE;
        int y = currentSquare.getRow() * SQUARE_SIZE;

        g.drawImage(this.img, x, y, SQUARE_SIZE, SQUARE_SIZE, null);
    }

    // Returns all squares this piece "controls" (can attack)
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        return getDiagonalMoves(board, start, false);  // Controlled squares = movement squares
    }

    // Returns all legal moves for this piece (bishop logic for now)
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        return getDiagonalMoves(b.getSquareArray(), start, true);
    }

    // Helper function: Diagonal moves (used for bishop-like movement)
    private ArrayList<Square> getDiagonalMoves(Square[][] board, Square start, boolean checkBlocking) {
        ArrayList<Square> moves = new ArrayList<>();
        int[] rowDirections = {-1, -1, 1, 1};
        int[] colDirections = {-1, 1, -1, 1};
        int startRow = start.getRow();
        int startCol = start.getCol();

        for (int d = 0; d < 4; d++) {
            int r = startRow + rowDirections[d];
            int c = startCol + colDirections[d];

            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                Square target = board[r][c];

                if (checkBlocking && target.isOccupied()) {
                    if (target.getPiece().getColor() != this.color) {
                        moves.add(target); // Can capture opponent
                    }
                    break; // Stop if blocked
                }

                moves.add(target);
                r += rowDirections[d];
                c += colDirections[d];
            }
        }

        return moves;
    }
}
