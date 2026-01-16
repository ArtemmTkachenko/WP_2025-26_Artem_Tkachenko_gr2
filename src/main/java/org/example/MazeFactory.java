package org.example;

public class MazeFactory {
    public Room makeRoom(int x, int y) { return new Room(x, y); }
    public Door makeDoor(Room a, Room b) { return new Door(a, b); }
    public Bomb makeBomb() { return BombFactory.randomBomb(); }
}
