package game;

import control.ClientCtr;
import model.Match;
import model.ObjectWrapper;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board extends JPanel implements MouseListener, MouseMotionListener {
    private static final String RESOURCES_WBISHOP_PNG = "resources/wbishop.png";
    private static final String RESOURCES_BBISHOP_PNG = "resources/bbishop.png";
    private static final String RESOURCES_WKNIGHT_PNG = "resources/wknight.png";
    private static final String RESOURCES_BKNIGHT_PNG = "resources/bknight.png";
    private static final String RESOURCES_WROOK_PNG = "resources/wrook.png";
    private static final String RESOURCES_BROOK_PNG = "resources/brook.png";
    private static final String RESOURCES_WKING_PNG = "resources/wking.png";
    private static final String RESOURCES_BKING_PNG = "resources/bking.png";
    private static final String RESOURCES_BQUEEN_PNG = "resources/bqueen.png";
    private static final String RESOURCES_WQUEEN_PNG = "resources/wqueen.png";
    private static final String RESOURCES_WPAWN_PNG = "resources/wpawn.png";
    private static final String RESOURCES_BPAWN_PNG = "resources/bpawn.png";

    public final LinkedList<Piece> Bpieces;
    public final LinkedList<Piece> Wpieces;

    private final Square[][] board;
    private final GameWindow g;

    private Piece currPiece;
    private int currX;
    private int currY;

    private final Player player;
    private final ClientCtr myControl;

    public Board(GameWindow g, Player player, ClientCtr myControl) {
        this.g = g;
        this.player = player;
        this.myControl = myControl;

        board = new Square[8][8];
        Bpieces = new LinkedList<Piece>();
        Wpieces = new LinkedList<Piece>();
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if ((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                }
                this.add(board[x][y]);
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REP_MOVE, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REP_O_MOVE, this));
    }

    private void initializePieces() {

        for (int x = 0; x < 8; x++) {
            board[1][x].put(new Pawn(0, board[1][x], RESOURCES_BPAWN_PNG));
            board[6][x].put(new Pawn(1, board[6][x], RESOURCES_WPAWN_PNG));
        }

        board[7][3].put(new Queen(1, board[7][3], RESOURCES_WQUEEN_PNG));
        board[0][3].put(new Queen(0, board[0][3], RESOURCES_BQUEEN_PNG));

        board[0][4].put(new King(0, board[0][4], RESOURCES_BKING_PNG));
        board[7][4].put(new King(1, board[7][4], RESOURCES_WKING_PNG));

        board[0][0].put(new Rook(0, board[0][0], RESOURCES_BROOK_PNG));
        board[0][7].put(new Rook(0, board[0][7], RESOURCES_BROOK_PNG));
        board[7][0].put(new Rook(1, board[7][0], RESOURCES_WROOK_PNG));
        board[7][7].put(new Rook(1, board[7][7], RESOURCES_WROOK_PNG));

        board[0][1].put(new Knight(0, board[0][1], RESOURCES_BKNIGHT_PNG));
        board[0][6].put(new Knight(0, board[0][6], RESOURCES_BKNIGHT_PNG));
        board[7][1].put(new Knight(1, board[7][1], RESOURCES_WKNIGHT_PNG));
        board[7][6].put(new Knight(1, board[7][6], RESOURCES_WKNIGHT_PNG));

        board[0][2].put(new Bishop(0, board[0][2], RESOURCES_BBISHOP_PNG));
        board[0][5].put(new Bishop(0, board[0][5], RESOURCES_BBISHOP_PNG));
        board[7][2].put(new Bishop(1, board[7][2], RESOURCES_WBISHOP_PNG));
        board[7][5].put(new Bishop(1, board[7][5], RESOURCES_WBISHOP_PNG));


        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                Bpieces.add(board[y][x].getPiece());
                Wpieces.add(board[7 - y][x].getPiece());
            }
        }
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    private void opponentMove(ObjectWrapper data) {
        String mm = ((Match) data.getData()).getMovement();
        int x1 = Integer.parseInt(String.valueOf(mm.charAt(0)));
        int y1 = Integer.parseInt(String.valueOf(mm.charAt(1)));
        int x2 = Integer.parseInt(String.valueOf(mm.charAt(3)));
        int y2 = Integer.parseInt(String.valueOf(mm.charAt(4)));

        Square s1 = board[y1][x1];
        currPiece = s1.getPiece();
        Square s2 = board[y2][x2];

        if (currPiece != null) {
            currPiece.move(s2);
            currPiece = null;
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[y][x];
                sq.paintComponent(g);
            }
        }

        if (currPiece != null) {
            final Image i = currPiece.getImage();
            g.drawImage(i, currX, currY, null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX() - 32;
        currY = e.getY() - 32;

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        System.out.println(sq.getXpos() + "" + sq.getYpos());

        if (sq.isOccupied()) {
            currPiece = sq.getPiece();
            sq.setDisplay(false);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (currPiece != null) {

            List<Square> legalMoves = currPiece.getLegalMoves(this);

            if (legalMoves.contains(sq)) {
                sq.setDisplay(true);
                currPiece.move(sq);

                String m = currPiece.getMovement().get(0).getXpos() + "" + currPiece.getMovement().get(0).getYpos() + "-"
                        + currPiece.getMovement().get(1).getXpos() + "" + currPiece.getMovement().get(1).getYpos() + "";
                System.out.println(m);
                ArrayList<Player> players = new ArrayList<>();
                players.add(player);
                players.add(null);

                Match match = new Match(players, m);
                myControl.sendData(new ObjectWrapper(ObjectWrapper.MOVE, match));
            } else {
                currPiece.getPosition().setDisplay(true);
            }
            currPiece = null;
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 32;
        currY = e.getY() - 32;
        repaint();
    }

    // Irrelevant methods, do nothing for these mouse behaviors
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void receivedDataProcessing(ObjectWrapper data) {
        switch (data.getPerformative()) {
            case ObjectWrapper.REP_MOVE:
                if (data.getData().equals("ok")) System.out.println("success");
                else System.out.println("false");
                break;
            case ObjectWrapper.REP_O_MOVE:
                System.out.println("move");
                opponentMove(data);
                break;
        }
    }
}