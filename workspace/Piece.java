import java.awt.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.util.*;

// This class represents a chess piece
public class Piece {
    private final boolean color;
    private BufferedImage img;

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

    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.setColor(Color.black);
      
        g.drawImage(this.img, x, y, null);
    }

    // Returns all squares this piece "controls" (can attack)
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        return getDiagonalMoves(board, start, false);  // Controlled squares = movement squares
    }

    //  Returns all legal moves for this piece (bishop logic)
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        return getDiagonalMoves(b.getSquareArray(), start, true); // Movement logic for a bishop
    }

    // 🔹 Helper function: Finds diagonal moves
    private ArrayList<Square> getDiagonalMoves(Square[][] board, Square start, boolean checkBlocking) {
        ArrayList<Square> moves = new ArrayList<>();
        int[] rowDirections = {-1, -1, 1, 1};
        int[] colDirections = {-1, 1, -1, 1};
        int startRow = start.getRow();
        int startCol = start.getCol();

        for (int d = 0; d < 4; d++) { // Four diagonal directions
            int r = startRow + rowDirections[d];
            int c = startCol + colDirections[d];

            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                Square target = board[r][c];

                if (checkBlocking && target.isOccupied()) {
                    if (target.getPiece().getColor() != this.color) {
                        moves.add(target); // Can capture opponent
                    }
                    break; // Stop moving further
                }

                moves.add(target);
                r += rowDirections[d];
                c += colDirections[d];
            }
        }

        return moves;
    }
}
