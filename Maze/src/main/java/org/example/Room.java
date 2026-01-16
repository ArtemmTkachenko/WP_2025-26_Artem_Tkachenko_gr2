package org.example;

import java.util.EnumMap;
import java.util.Map;

public class Room {

    private final int x, y;
    private final Map<Direction, MapSite> sides = new EnumMap<>(Direction.class);
    private Bomb bomb;

    public Room(int x, int y) {
        this.x = x;
        this.y = y;
        for (Direction d : Direction.values())
            sides.put(d, new Wall());
    }

    public int x() { return x; }
    public int y() { return y; }

    public void setSide(Direction d, MapSite site) {
        sides.put(d, site);
    }

    public MapSite getSide(Direction d) {
        return sides.get(d);
    }

    public void placeBomb(Bomb b) {
        bomb = b;
    }

    public void removeBomb() {
        bomb = null;
    }

    public void onEnter(Actor actor) {
        if (bomb != null)
            bomb.onEnter(actor, this);
    }

    public Bomb bomb() {
        return bomb;
    }
}
