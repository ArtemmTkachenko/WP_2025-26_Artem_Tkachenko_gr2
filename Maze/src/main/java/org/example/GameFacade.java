package org.example;

import java.util.Random;

public class GameFacade {

    private static final int BOMB_COUNT = 20;

    private Maze maze;
    private Player player;
    private Room startRoom;
    private Room finishRoom;

    public void startNewGame() {
        MazeFactory factory = new MazeFactory();
        maze = new Maze(15, 11, factory);

        MazeGenerator generator = new MazeGenerator(maze, factory);
        generator.generate(0, 0);

        startRoom  = maze.get(0, 0);
        finishRoom = maze.get(maze.width() - 1, maze.height() - 1);

        placeBombs(factory);

        player = new Player(startRoom);

        Bomb bomb = new GasBomb();
        System.out.println("Start new game");


        ((GasBomb) bomb).addObserver(player);
        DamageBombDecorator s =  new DamageBombDecorator(bomb);

//        Player.setDamageDecorator(s);

        placeBombs(factory);
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



    public Maze getMaze() {
        return maze;
    }

    public Player getPlayer() {
        return player;
    }

    public Room getFinishRoom() {
        return finishRoom;
    }
}
