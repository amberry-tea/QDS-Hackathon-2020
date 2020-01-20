package ca.bcitsa.qds2020.team11;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

/**
 * Class that draws a star using JavaFX and the users mouse inputs.
 * <p>
 * Draws a star using lines in JavaFX using the users mouse. When the mouse is
 * clicked and dragged, a star will be drawn and can be resized and rotated by
 * the angle and distance from the initial click point.
 * </p>
 *
 * @author Andrew Martin
 * @version 1.0
 */
public class DrawGrid extends Application {

    // 0: No highlighting, no AI
    // 1: Highlighting, no AI
    // 2: No highlighting, AI
    // 3: Highlighting, AI

    public static final int MODE = 1;

    public static final int CREATE_RATE = 5;

    public static final int HEIGHT = 750;
    public static final int WIDTH = 750;

    public static final int GRID_WIDTH = 16;
    public static final int GRID_HEIGHT = 16;

    public static final int TILE_WIDTH = WIDTH / GRID_WIDTH;
    public static final int TILE_HEIGHT = HEIGHT / GRID_HEIGHT;
    /** Array of the points for the grid. */
    public static Point2D[][] grid;

    public static final int INBOUND_ROAD_X = 7;
    public static final int OUTBOUND_ROAD_X = 8;

    // For timetable sizing
    public static final int CYCLE_LENGTH = 35;

    /** The contents of the application scene. */
    private static Group root;

    private static ArrayList<Node> onScreen;
    private static ArrayList<Truck> trucks;
    private static ArrayList<Shovel> shovels;
    private static ArrayList<Rectangle> highlights;

    private static Timetable shovelTimes;
    private static Timetable intersectTimes;

    private static long FRAMECOUNT = 0;

    // The tick rate of the program
    public static final int TICKRATE = 12;

    public static int sortIndex = 0;

    public static int tractorCounter = 0;

    /**
     * Displays an initially empty scene, waiting for the user to draw lines with
     * the mouse.
     * 
     * @param primaryStage a Stage
     */
    public void start(Stage primaryStage) {
        onScreen = new ArrayList<Node>();
        trucks = new ArrayList<Truck>();
        shovels = new ArrayList<Shovel>();
        highlights = new ArrayList<Rectangle>();
        root = new Group();

        shovelTimes = new Timetable(CYCLE_LENGTH);
        intersectTimes = new Timetable(CYCLE_LENGTH);

        // Instantiate variables here
        Circle[][] circles = new Circle[GRID_WIDTH][GRID_HEIGHT];

        grid = new Point2D[GRID_WIDTH][GRID_HEIGHT];
        for (int c = 1; c < GRID_WIDTH; c++) {
            for (int r = 1; r < GRID_HEIGHT; r++) {
                grid[c][r] = new Point2D(c * TILE_WIDTH, r * TILE_HEIGHT);
                circles[c][r] = new Circle(c * TILE_WIDTH, r * TILE_HEIGHT, 2);
                circles[c][r].setFill(Color.BLACK);
                onScreen.add(circles[c][r]);
            }
        }

//        trucks.add(new Truck(1));
//        trucks.add(new Truck(2));
//        trucks.add(new Truck(3));
//        trucks.add(new Truck(4));
        shovels.add(new Shovel(1, 4, 14));
        shovels.add(new Shovel(2, 11, 14));
        shovels.add(new Shovel(3, 4, 10));
        shovels.add(new Shovel(4, 11, 10));

        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BEIGE);

        primaryStage.setTitle("Distribution of Trucks");
        primaryStage.setScene(scene);
        primaryStage.show();

        Timetable t = new Timetable(10);
        t.setTrue(5, 14);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                refresh();
                FRAMECOUNT++;
                if (FRAMECOUNT % TICKRATE == 0) {
                    truckController();
                }
                if (FRAMECOUNT % (TICKRATE * CREATE_RATE) == 0) {
                    trucks.add(new Truck(getNextShovel()));
                }
            }
        }.start();
    }

    /**
     * Launches the JavaFX application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void refresh() {
        root.getChildren().clear();
        for (Node n : onScreen) {
            root.getChildren().add(n);
        }
        for (Node n : highlights) {
            root.getChildren().add(n);
        }

    }

    public void truckController() {
        for (int i = 0; i < trucks.size(); i++) {
            trucks.get(i).truckController();
            for (int j = 0; j < trucks.size(); j++) {
                if (trucks.get(i).getX() == trucks.get(j).getX() && trucks.get(i).getY() == trucks.get(j).getY()
                        && i != j && (MODE == 1 || MODE == 3)) {
                    trucks.get(i).collide();
                    trucks.get(j).collide();
                }
            }
        }
    }

    public static int addChild(Node n) {
        onScreen.add(n);
        return onScreen.size();
    }

    public static ArrayList<Truck> getTrucks() {
        return trucks;
    }

    public static ArrayList<Shovel> getShovels() {
        return shovels;
    }

    public static long getFrameCount() {
        return FRAMECOUNT;
    }

    public int getNextShovel() {
        if (MODE <= 1) {
            switch (sortIndex) {
            case 0:
                sortIndex = 1;
                return 1;
            case 1:
                sortIndex = 4;
                return 4;
            case 2:
                sortIndex = 3;
                return 3;
            case 3:
                sortIndex = 1;
                return 1;
            case 4:
                sortIndex = 2;
                return 2;
            }
        }
        return 0;
    }
}
