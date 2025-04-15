import java.util.ArrayList;

public class AssassinRook extends Piece {
    public AssassinRook(boolean isWhite, String imagePath) {
        super(isWhite, imagePath);
    }

    @Override
    public ArrayList<Square> getLegalMoves(Board board, Square currentSquare) {
        ArrayList<Square> legalMoves = new ArrayList<>();
        Square[][] squares = board.getSquareArray();

        int row = currentSquare.getRow();
        int col = currentSquare.getCol();

        // Moves like a rook but stops before the second piece in its path
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            boolean firstPieceFound = false;

            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (squares[r][c].isOccupied()) {
                    if (firstPieceFound) break; // Can't move beyond second piece
                    firstPieceFound = true;
                }
                legalMoves.add(squares[r][c]);
                r += dir[0];
                c += dir[1];
            }
        }
        return legalMoves;
    }

    @Override
    public ArrayList<Square> getControlledSquares(Square[][] board, Square currentSquare) {
        ArrayList<Square> controlledSquares = new ArrayList<>();
        int row = currentSquare.getRow();
        int col = currentSquare.getCol();

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] dir : directions) {
            int r = row + dir[0];
            int c = col + dir[1];
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (board[r][c].isOccupied()) break; // Stop at the first piece
                controlledSquares.add(board[r][c]);
                r += dir[0];
                c += dir[1];
            }
        }
        return controlledSquares;
    }
}
