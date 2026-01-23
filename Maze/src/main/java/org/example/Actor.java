package org.example;

public interface  Actor {
    void damage(int amount);
    Room getRoom();
    void setRoom(Room room);
    void setStunned(boolean value);
    boolean isStunned();
}
