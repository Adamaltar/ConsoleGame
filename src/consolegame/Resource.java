/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolegame;

/**
 *
 * @author Utilizator
 */
public abstract class Resource implements BoardPiece{
    protected Personaj p=null;
    protected boolean consumed=false;
    protected int x;
    protected int y;
    
    public abstract void processPersonaj(Personaj p);
    
    
    public boolean isConsumed(){
        return consumed;
    }

    /**
     * @return the p
     */
    public Personaj getP() {
        return p;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
    public boolean isVisible(){
        return !(p==null && this.isConsumed());
    }

    /**
     * @param p the p to set
     */
    public void setP(Personaj p) {
        this.p = p;
    }
                    
}
