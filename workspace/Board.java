import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.*;

// Implements the chessboard, piece movement, and interaction handling
@SuppressWarnings("serial") // Prevents serialization warnings
public class Board extends JPanel implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L; // ✅ Fix for serialization warning

    // Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
    private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
    private static final String RESOURCES_WKING_PNG = "wking.png";
    private static final String RESOURCES_BKING_PNG = "bking.png";

    // Logical and graphical representations of board
    private final Square[][] board;
    private final GameWindow gameWindow; // ✅ Reference to GameWindow

    // True if it's white's turn
    private boolean whiteTurn;

    // Stores the piece currently being dragged
    private Piece currPiece;
    private Square fromMoveSquare;

    // Mouse position tracking
    private int currX;
    private int currY;

    //  Constructor with GameWindow parameter
    public Board(GameWindow g) {
        this.gameWindow = g;  // ✅ Store reference to GameWindow
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // Populate the board with alternating black and white squares
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean isWhite = (row + col) % 2 == 0; // Checkerboard pattern
                board[row][col] = new Square(this, isWhite, row, col);
                this.add(board[row][col]); // Add square to board
            }
        }

        initializePieces(); // Add pieces to the board

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
    }

    // Places initial pieces on the board
    private void initializePieces() {
        // Ensure squares exist before placing pieces
        if (board[0][4] == null || board[7][4] == null) {
            System.out.println("Error: Square not initialized!");
            return;
        }


        // Place Kings
        board[0][4].setPiece(new Piece(false, RESOURCES_BKING_PNG)); // Black King
        board[7][4].setPiece(new Piece(true, RESOURCES_WKING_PNG));  // White King

        // Place Bishops
        board[0][2].setPiece(new Piece(false, RESOURCES_BBISHOP_PNG)); // Black Bishop (left)
        board[0][5].setPiece(new Piece(false, RESOURCES_BBISHOP_PNG)); // Black Bishop (right)
        board[7][2].setPiece(new Piece(true, RESOURCES_WBISHOP_PNG));  // White Bishop (left)
        board[7][5].setPiece(new Piece(true, RESOURCES_WBISHOP_PNG));  // White Bishop (right)
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square sq = board[row][col];
                if (sq == fromMoveSquare)
                    sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                
                //  Use repaint() instead of direct paintComponent(g) call
                sq.repaint();
            }
        }
        if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn) || (!currPiece.getColor() && !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getPiece();
            fromMoveSquare = sq;
            if (!currPiece.getColor() && whiteTurn) return;
            if (currPiece.getColor() && !whiteTurn) return;
        }
        repaint();
    }

    // Handles moving the piece when released
    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        // Remove previous highlights
        for (Square[] row : board) {
            for (Square s : row) {
                s.setBorder(null);
            }
        }

        if (currPiece != null && endSquare != null) {
            ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);

            if (legalMoves.contains(endSquare)) {
                // Move the piece
                endSquare.setPiece(currPiece);
                fromMoveSquare.removePiece();
                whiteTurn = !whiteTurn; // Switch turn
            } else {
                // Invalid move: Reset piece back to original position
                fromMoveSquare.setPiece(currPiece);
            }
        }

        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
