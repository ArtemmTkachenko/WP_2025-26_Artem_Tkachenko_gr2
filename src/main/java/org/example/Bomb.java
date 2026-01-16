package org.example;

public interface Bomb {
    void onEnter(Actor actor, Room room);
    boolean isActive();
    String renderSymbol();
}
