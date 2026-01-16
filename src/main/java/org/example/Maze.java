package org.example;

public class Maze {

    private final Room[][] rooms;

    public Maze(int w, int h, MazeFactory f) {
        rooms = new Room[h][w];
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                rooms[y][x] = f.makeRoom(x, y);
    }

    public Room get(int x, int y) {
        return rooms[y][x];
    }

    public int width() { return rooms[0].length; }
    public int height() { return rooms.length; }

    public void connect(Room a, Room b, Direction d, MazeFactory f) {
        Door door = f.makeDoor(a, b);
        a.setSide(d, door);
        b.setSide(d.opposite(), door);
    }
}
