package ca.bcitsa.qds2020.team11;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shovel {
    private Rectangle r;
    private int x;
    private int y;
    private int id;
    
    
    public Shovel(int id, int x, int y) {
        r = new Rectangle(DrawGrid.grid[x][y].getX(), DrawGrid.grid[x][y].getY(), DrawGrid.TILE_WIDTH, DrawGrid.TILE_HEIGHT);
        r.setFill(Color.ORANGE);
        DrawGrid.addChild(r);
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
    public int getID() {
        return id;
    }

    /**
     * @return the r
     */
    public Rectangle getR() {
        return r;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param r the r to set
     */
    public void setR(Rectangle r) {
        this.r = r;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
}
