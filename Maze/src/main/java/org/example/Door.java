package org.example;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Door implements MapSite {

    private final Room r1;
    private final Room r2;

    private boolean open = false;
    private boolean opening = false;

    public Door(Room r1, Room r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    public boolean isOpen() {
        return open;
    }

    private Room other(Room from) {
        return from == r1 ? r2 : r1;
    }

    @Override
    public boolean enter(Actor actor) {



        if (open) {
            Room next = other(actor.getRoom());
            actor.setRoom(next);

            if (next.bomb() != null) {
                next.bomb().onEnter(actor, next);
            }
            return true;
        }



        if (opening) return false;


        opening = true;
        actor.setStunned(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
        delay.setOnFinished(e -> {
            open = true;
            opening = false;

            Room next = other(actor.getRoom());
            actor.setRoom(next);
            actor.setStunned(false);

            if (next.bomb() != null) {
                next.bomb().onEnter(actor, next);
            }
        });

        delay.play();
        return true;
    }
}
