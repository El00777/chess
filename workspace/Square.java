import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Square extends JPanel {
    private Piece piece;
    private final boolean isWhite;  // This variable is now USED
    private final int row;
    private final int col;
    private final Board board;                              
                            
    public Square(Board board, boolean isWhite, int row, int col) {
        this.board = board;
        this.isWhite = isWhite;//  Now used
        this.row = row;
        this.col = col;
        this.piece = null;
    }
    //  Override paintComponent to use `isWhite` for rendering colors
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        // Standard chessboard colors
        Color lightColor = new Color(240, 217, 181);  // Light beige color
        Color darkColor = new Color(181, 136, 99);    // Brown wood color
    
        // Set color based on `isWhite`
        g.setColor(isWhite ? lightColor : darkColor);
    
        // Fill the square
        g.fillRect(0, 0, getWidth(), getHeight());
    
    
    
        if(piece!= null){
            piece.draw(g, this);
        }
    }

    public void setPiece(Piece p) {
        this.piece = p;
        repaint();  // Refresh UI when piece is placed
    }

    public void removePiece() {
        this.piece = null;
        repaint();
    }

    public Piece getPiece() {
        return this.piece;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isWhite() {  //  Provide a getter if needed
        return isWhite;
    }
}
