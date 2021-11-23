package game;

import java.util.LinkedList;

public class Piece {
    private int xp;
    private int yp;
    private boolean isWhite;
    private LinkedList<Piece> ps;
    private String name;
    private String movement;

    public Piece(int xp, int yp, boolean isWhite, String n, LinkedList<Piece> ps) {
        this.xp = xp;
        this.yp = yp;
        this.isWhite = isWhite;
        this.ps = ps;
        name = n;
        movement = "";
        ps.add(this);
    }

    public void premove(){
        movement = "";
        movement += this.getXp() + "" + this.getYp() +"-";
    }

    public void move(int xp, int yp) {
        this.xp = xp;
        this.yp = yp;
        movement += this.getXp() + "" + this.getYp();
    }

    public void kill() {
        ps.remove(this);
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getYp() {
        return yp;
    }

    public void setYp(int yp) {
        this.yp = yp;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public LinkedList<Piece> getPs() {
        return ps;
    }

    public void setPs(LinkedList<Piece> ps) {
        this.ps = ps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }
}
