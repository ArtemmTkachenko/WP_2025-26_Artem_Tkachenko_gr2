package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class App extends Application {


    private String bombMessage = null;
    private long bombMessageUntil = 0;


    private static final int CELL = 64;
    private static final int WALL = 4;
    private static final int BOMB_COUNT = 20;


    private Maze maze;
    private Player player;

    private Room startRoom;
    private Room finishRoom;


    private Image playerImg;
    private Image bombImg;
    private Image finishImg;

    @Override
    public void start(Stage stage) {


        AppHolder.set(this);


        playerImg = load("/img/player.png");
        bombImg   = load("/img/bomb.png");
        finishImg = load("/img/finish.png");

        restartGame();

        Canvas canvas = new Canvas(
                maze.width() * CELL,
                maze.height() * CELL + 40
        );
        GraphicsContext g = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(e -> {


            if (e.getCode() == KeyCode.R &&
                    (player.isDead() || player.isFinished())) {
                restartGame();
                return;
            }


            if (player.isDead() || player.isStunned() || player.isFinished())
                return;

            if (e.getCode() == KeyCode.UP)    player.tryMove(Direction.NORTH);
            if (e.getCode() == KeyCode.DOWN)  player.tryMove(Direction.SOUTH);
            if (e.getCode() == KeyCode.LEFT)  player.tryMove(Direction.WEST);
            if (e.getCode() == KeyCode.RIGHT) player.tryMove(Direction.EAST);


            if (player.getRoom() == finishRoom) {
                player.setFinished(true);
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                render(g);
            }
        }.start();

        stage.setTitle("Maze – Bombs, Doors & Finish");
        stage.setScene(scene);
        stage.show();
    }


    private void restartGame() {
        MazeFactory factory = new MazeFactory();
        maze = new Maze(15, 11, factory);

        MazeGenerator generator = new MazeGenerator(maze, factory);
        generator.generate(0, 0);

        startRoom  = maze.get(0, 0);
        finishRoom = maze.get(maze.width() - 1, maze.height() - 1);

        placeBombs(factory);

        player = new Player(startRoom);

        bombMessage = null;
    }


    private void render(GraphicsContext g) {
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, 5000, 5000);

        drawMaze(g);
        drawBombs(g);
        drawSpecialRooms(g);
        drawPlayer(g);
        drawHUD(g);
    }

    private void drawMaze(GraphicsContext g) {
        g.setStroke(Color.BLACK);
        g.setLineWidth(WALL);

        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {

                Room r = maze.get(x, y);
                double px = x * CELL;
                double py = y * CELL;

                drawSide(g, r, Direction.NORTH, px, py);
                drawSide(g, r, Direction.SOUTH, px, py);
                drawSide(g, r, Direction.WEST,  px, py);
                drawSide(g, r, Direction.EAST,  px, py);
            }
        }
    }

    private void drawSide(GraphicsContext g, Room r, Direction d, double x, double y) {
        MapSite s = r.getSide(d);

        if (s instanceof Wall) {
            drawWall(g, d, x, y);
        }
        else if (s instanceof Door door) {
            drawDoor(g, d, x, y, door.isOpen());
        }
    }

    private void drawWall(GraphicsContext g, Direction d, double x, double y) {
        switch (d) {
            case NORTH -> g.strokeLine(x, y, x + CELL, y);
            case SOUTH -> g.strokeLine(x, y + CELL, x + CELL, y + CELL);
            case WEST  -> g.strokeLine(x, y, x, y + CELL);
            case EAST  -> g.strokeLine(x + CELL, y, x + CELL, y + CELL);
        }
    }

    private void drawDoor(GraphicsContext g, Direction d, double x, double y, boolean open) {
        g.setStroke(open ? Color.BURLYWOOD : Color.SADDLEBROWN);
        g.setLineWidth(WALL);

        double m = CELL / 2.0;
        double s = 14;

        switch (d) {
            case NORTH -> g.strokeLine(x + m - s, y, x + m + s, y);
            case SOUTH -> g.strokeLine(x + m - s, y + CELL, x + m + s, y + CELL);
            case WEST  -> g.strokeLine(x, y + m - s, x, y + m + s);
            case EAST  -> g.strokeLine(x + CELL, y + m - s, x + CELL, y + m + s);
        }

        g.setStroke(Color.BLACK);
    }

    private void drawBombs(GraphicsContext g) {
        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {

                Room r = maze.get(x, y);
                if (!(r.bomb() instanceof GasBomb gb)) continue;

                double px = x * CELL;
                double py = y * CELL;

                g.drawImage(bombImg, px + 14, py + 14, CELL - 28, CELL - 28);

                if (gb.isActive()) {
                    g.setFill(Color.BLACK);
                    g.fillText(
                            String.valueOf(gb.secondsLeft()),
                            px + CELL / 2.0 - 4,
                            py + CELL / 2.0 + 4
                    );
                }

                if (gb.getExplosion() != null) {
                    gb.getExplosion().render(
                            g,
                            px + CELL / 2.0,
                            py + CELL / 2.0
                    );
                }
            }
        }
    }

    private void drawSpecialRooms(GraphicsContext g) {
        g.drawImage(
                finishImg,
                finishRoom.x() * CELL + 8,
                finishRoom.y() * CELL + 8,
                CELL - 16,
                CELL - 16
        );
    }

    private void drawPlayer(GraphicsContext g) {
        Room r = player.getRoom();
        g.drawImage(
                playerImg,
                r.x() * CELL + 10,
                r.y() * CELL + 10,
                CELL - 20,
                CELL - 20
        );
    }

    private void drawHUD(GraphicsContext g) {

        g.setFill(Color.BLACK);
        g.fillText(
                "HP: " + player.hp(),
                10,
                maze.height() * CELL + 25
        );


        if (bombMessage != null) {
            if (System.currentTimeMillis() < bombMessageUntil) {
                g.setFill(Color.DARKRED);
                g.fillText(
                        bombMessage,
                        maze.width() * CELL / 2.0 - 150,
                        30
                );
            } else {
                bombMessage = null;
            }
        }


        if (player.isDead()) {
            g.setFill(Color.RED);
            g.fillText(
                    "☠ YOU DIED — Press R to restart",
                    maze.width() * CELL / 2.0 - 160,
                    maze.height() * CELL / 2.0
            );
        }


        if (player.isFinished()) {
            g.setFill(Color.DARKGREEN);
            g.fillText(
                    "🏁 YOU WON! Press R to restart",
                    maze.width() * CELL / 2.0 - 170,
                    maze.height() * CELL / 2.0
            );
        }
    }


    public void showBombMessage(String msg, int seconds) {
        bombMessage = msg;
        bombMessageUntil = System.currentTimeMillis() + seconds * 1000L;
    }

    private void placeBombs(MazeFactory factory) {
        Random rng = new Random();
        int placed = 0;

        while (placed < BOMB_COUNT) {
            int x = rng.nextInt(maze.width());
            int y = rng.nextInt(maze.height());

            Room r = maze.get(x, y);

            if (Math.abs(x - startRoom.x()) <= 1 &&
                    Math.abs(y - startRoom.y()) <= 1)
                continue;

            if (r == finishRoom || r.bomb() != null) continue;

            r.placeBomb(factory.makeBomb());
            placed++;
        }
    }

    private Image load(String path) {
        return new Image(getClass().getResourceAsStream(path));
    }

    public static void main(String[] args) {
        launch();
    }
}
