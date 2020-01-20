package ca.bcitsa.qds2020.team11;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Truck {
    private Rectangle r;
    private int x;
    private int y;
    private int objective;
    //Waiting count. Used in left turns.
    int waitCount;
    //Timer for how many ticks the truck should wait
    private int loadingCount;
    //Sets when the timer finishes
    public static final int LOADING_MAX = 10;
    // Represents truck in cycle. Zero for empty. One for spotting. Two for filling.
    // Three for hauling.
    private int state;
    boolean initTurn;
    //index of turn in arraylist for deletion
    private boolean showingHighlight;
    private Rectangle highlight;

    public Truck(int objective) {
        r = new Rectangle(DrawGrid.grid[7][1].getX(), DrawGrid.grid[7][1].getY(), DrawGrid.TILE_WIDTH,
                DrawGrid.TILE_HEIGHT);
        r.setFill(Color.BLACK);
        highlight = new Rectangle(0,0,DrawGrid.TILE_WIDTH*2,DrawGrid.TILE_HEIGHT*2);
        highlight.setFill(new Color(0,0,0,0));
        DrawGrid.addChild(r);
        DrawGrid.addChild(highlight);
        x = 7;
        y = 1;
        state = 0;
        this.objective = objective;
        initTurn = true;
        showingHighlight = false;
    }

    public void moveLeft() {
        r.setX(r.getX() - DrawGrid.TILE_WIDTH);
        x--;
    }

    public void moveRight() {
        r.setX(r.getX() + DrawGrid.TILE_WIDTH);
        x++;
    }

    public void moveUp() {
        r.setY(r.getY() - DrawGrid.TILE_HEIGHT);
        y--;
    }

    public void moveDown() {
        r.setY(r.getY() + DrawGrid.TILE_HEIGHT);
        y++;
    }

    public void truckController() {
        if (state == 0) {
            // EMPTY
            if (objective % 2 == 0) {
                //RIGHT SIDE
                if (y == DrawGrid.getShovels().get(objective - 1).getY()) {
                    leftTurn();
                } else {
                    moveDown();
                }
            } else {
                //LEFT SIDE
                if (y == DrawGrid.getShovels().get(objective - 1).getY()) {
                    state = 1;
                    moveLeft();
                } else {
                    moveDown();
                }
            }

        } else if (state == 1) {
            // SPOTTING
            if (objective % 2 == 0) {
                //objective on right
                if (x == DrawGrid.getShovels().get(objective - 1).getX() - 1) {
                    state = 2;
                } else {
                    moveRight();
                }
            } else {
                //objective on left
                if (x == DrawGrid.getShovels().get(objective - 1).getX() + 1) {
                    state = 2;
                } else {
                    moveLeft();
                }
            }
        }
        else if (state == 2) {
            // LOADING
            
            if(loadingCount >= LOADING_MAX) {
                state = 3;
                
                if(objective % 2 == 0) {
                    moveUp();
                } else {
                    moveDown();
                }
            }
            
            loadingCount++;
            
        } else if (state == 3) {
         // HAULING
            if (objective % 2 == 0) {
                //objective on right
                if (x == DrawGrid.OUTBOUND_ROAD_X) {
                    moveUp();
                } else {
                    moveLeft();
                }
            } else {
                //objective on left
                if(x == DrawGrid.OUTBOUND_ROAD_X) {
                    moveUp();
                } else if (x == DrawGrid.OUTBOUND_ROAD_X - 1) {
                    leftTurn();
                } else {
                    moveRight();
                }
            }
        }
    }

    public boolean isTurningLeft() {
        if ((objective % 2 == 0 && hauling() == false) || (objective % 2 == 1 && hauling() == true)) {
            return true;
        }
        return false;
    }
    
    public void leftTurn() {
        
        showingHighlight = false;
        showHighlight();
        
        if(waitCount >= 3) {
            if(state == 0) {
                moveRight();
                state = 1;
                waitCount = 0;
                initTurn = true;
                showingHighlight = true;
                showHighlight();
            } else if (state == 3) {
                moveRight();
                waitCount = 0;
                showingHighlight = true;
                showHighlight();
            }
        }
        
        if(DrawGrid.getFrameCount() % DrawGrid.TICKRATE == 0) {
            waitCount++;
            //System.out.println("wasd");
        }
    }

    public boolean hauling() {
        return state == 3;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Rectangle getRect() {
        return r;
    }
    
    public void collide() {
        r.setFill(Color.RED);
    }
    
    public void showHighlight() {
        if(!showingHighlight) {
            highlight.setX(x*DrawGrid.TILE_WIDTH);
            highlight.setY((y - 1)*DrawGrid.TILE_HEIGHT);
            highlight.setFill(new Color(1, 0, 0, .25));  
        } else {
            highlight.setFill(new Color(0,0,0,0));
        }
    }

}
