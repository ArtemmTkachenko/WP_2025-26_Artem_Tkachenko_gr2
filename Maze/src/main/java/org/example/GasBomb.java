package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GasBomb implements Bomb {
    private static List<Observer> observers = new ArrayList<>();

    private static final int DAMAGE = 40;
    private static final int TIMER = 2;

    private boolean active = false;
    private boolean exploded = false;

    private int secondsLeft = TIMER;
    private Room bombRoom;

    private Timeline timer;
    private Explosion explosion;

    @Override
    public void onEnter(Actor actor, Room room) {
        if (active) return;

        active = true;
        bombRoom = room;
        secondsLeft = TIMER;

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsLeft--;
            if (secondsLeft <= 0 && !exploded) {
                explode(actor);
            }
        }));
        timer.setCycleCount(TIMER);
        timer.play();
    }

    public void explode(Actor actor) {
        exploded = true;


        notifyObservers("Bomb exploded");
        explosion = new Explosion();

        App app = AppHolder.get();
        if (actor.getRoom() == bombRoom) {
            actor.damage(DAMAGE);
            if (app != null) {
                app.showBombMessage("YOU GOT HIT!", 2);
            }
            removeBombNow();
        } else {
            if (app != null) {
                app.showBombMessage("Bomb exploded, but you escaped.", 3);
            }

            Timeline delay = new Timeline(
                    new KeyFrame(Duration.seconds(2), e -> removeBombNow())
            );
            delay.play();
        }

        if (timer != null) {
            timer.stop();
        }
    }

    private void removeBombNow() {
        if (bombRoom != null) {
            bombRoom.removeBomb();
        }
    }

    public boolean isActive() {
        return active && !exploded;
    }

    public int secondsLeft() {
        return secondsLeft;
    }

    public Explosion getExplosion() {
        return explosion;
    }

    @Override
    public String renderSymbol() {
        return "";
    }




    public  void addObserver(Observer observer) {

        observers.add(observer);

    }


    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public int getDamage() {
        return DAMAGE;
    }

    public void notifyObservers(String event) {

        for (Observer observer : observers) {
            observer.update(event);
        }
    }


}
