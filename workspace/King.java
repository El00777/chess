// King.java - Represents the King piece in chess
// The king moves one square in any direction.

import java.util.ArrayList;

public class King extends Piece {
    public King(boolean isWhite, String img_file) {
        super(isWhite, img_file);
    }

    @Override
    public ArrayList<Square> getLegalMoves(Board boardObj, Square currentSquare) {
        ArrayList<Square> legalMoves = new ArrayList<>();
        Square[][] board = boardObj.getSquareArray();
        int row = currentSquare.getRow();
        int col = currentSquare.getCol();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (isInBounds(newRow, newCol, board.length, board[row].length)) {
                    Square target = board[newRow][newCol];
                    if (!target.isOccupied() || target.getPiece().getColor() != this.getColor()) {
                        legalMoves.add(target);
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public ArrayList<Square> getControlledSquares(Square[][] board, Square currentSquare) {
        ArrayList<Square> controlledSquares = new ArrayList<>();
        int row = currentSquare.getRow();
        int col = currentSquare.getCol();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (isInBounds(newRow, newCol, board.length, board[row].length)) {
                    controlledSquares.add(board[newRow][newCol]);
                }
            }
        }
        return controlledSquares;
    }

    private boolean isInBounds(int row, int col, int rowCount, int colCount) {
        return row >= 0 && row < rowCount && col >= 0 && col < colCount;
    }

    @Override
    public String toString() {
        return "A " + super.toString() + " King";
    }
}
