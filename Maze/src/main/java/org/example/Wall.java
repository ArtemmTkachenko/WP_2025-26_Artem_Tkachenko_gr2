package org.example;

public class Wall implements MapSite {
    @Override
    public boolean enter(Actor actor) {
        return false;
    }
}
