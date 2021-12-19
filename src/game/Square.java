package game;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Square extends JComponent {
    private final int color;
    private final Board b;
    private final int xpos;
    private final int ypos;
    private Piece piece;
    private boolean dispPiece;

    public Square(Board b, int c, int xpos, int ypos) {
        this.b = b;
        this.color = c;
        this.dispPiece = true;
        this.xpos = xpos;
        this.ypos = ypos;

        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public int getColor() {
        return this.color;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isOccupied() {
        return (this.piece != null);
    }

    public int getXpos() {
        return this.xpos;
    }

    public int getYpos() {
        return this.ypos;
    }

    public void setDisplay(boolean v) {
        this.dispPiece = v;
    }

    public void put(Piece p) {
        this.piece = p;
        p.setPosition(this);
    }

    public Piece removePiece() {
        Piece p = this.piece;
        this.piece = null;
        return p;
    }

    public void capture(Piece p) {
        p.getMovement().add(p.getPosition());
        Piece k = piece;
        if (k.getColor() == 0) b.Bpieces.remove(k);
        if (k.getColor() == 1) b.Wpieces.remove(k);
        p.getMovement().add(this);
        this.piece = p;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.color == 1) {
            g.setColor(new Color(221, 192, 127));
        } else {
            g.setColor(new Color(101, 67, 33));
        }

        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        if (piece != null && dispPiece) {
            piece.draw(g);
        }
    }
}
