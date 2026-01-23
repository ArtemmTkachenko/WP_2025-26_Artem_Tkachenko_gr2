package org.example;

import java.util.*;

public class MazeGenerator {

    private final Maze maze;
    private final MazeFactory factory;
    private final boolean[][] visited;


    public MazeGenerator(Maze maze, MazeFactory factory) {
        this.maze = maze;
        this.factory = factory;
        this.visited = new boolean[maze.height()][maze.width()];
    }

    public void generate(int startX, int startY) {
        dfs(startX, startY);
    }

    private void dfs(int x, int y) {
        visited[y][x] = true;

        List<Direction> dirs = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(dirs);

        for (Direction d : dirs) {
            int nx = x + d.dx;
            int ny = y + d.dy;

            if (nx < 0 || ny < 0 ||
                nx >= maze.width() || ny >= maze.height())
                continue;

            if (visited[ny][nx]) continue;

            maze.connect(
                    maze.get(x, y),
                    maze.get(nx, ny),
                    d,
                    factory
            );

            dfs(nx, ny);
        }
    }
}
