package org.example;

public interface Bomb {
    void onEnter(Actor actor, Room room);
    boolean isActive();
    String renderSymbol();
    void addObserver(Observer observer);
    void explode(Actor actor);
    void removeObserver(Observer observer) ;
    int getDamage();
    void notifyObservers(String event);
}
