// Name: Ulises Cantor
// Piece: Bishop
// The bishop moves diagonally and stays on the same colored square throughout the game.
// It has infinite range until the board's edge or is blocked by another piece.

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }

    @Override
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlledSquares = new ArrayList<>();
        int[] directions = {-1, 1};

        for (int dx : directions) {
            for (int dy : directions) {
                int row = start.getRow();
                int col = start.getCol();
                while (true) {
                    row += dx;
                    col += dy;
                    if (row < 0 || row >= 8 || col < 0 || col >= 8) break;
                    controlledSquares.add(board[row][col]);
                    if (board[row][col].isOccupied()) break;
                }
            }
        }
        return controlledSquares;
    }

    @Override
    public ArrayList<Square> getLegalMoves(Board boardObj, Square start) {
        ArrayList<Square> legalMoves = new ArrayList<>();
        Square[][] board = boardObj.getSquareArray();
        int[] directions = {-1, 1};

        for (int dx : directions) {
            for (int dy : directions) {
                int row = start.getRow();
                int col = start.getCol();
                while (true) {
                    row += dx;
                    col += dy;
                    if (row < 0 || row >= 8 || col < 0 || col >= 8) break;

                    if (!board[row][col].isOccupied()) {
                        legalMoves.add(board[row][col]);
                    } else {
                        if (board[row][col].getPiece().getColor() != this.getColor()) {
                            legalMoves.add(board[row][col]);
                        }
                        break;
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        return "A " + super.toString() + " Bishop";
    }
}
