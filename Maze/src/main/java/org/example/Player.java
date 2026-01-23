package org.example;

public class Player implements Actor, Observer{

    private static DamageBombDecorator damageDecorator;
    private Room room;
    private int hp = 100;
    private boolean stunned = false;
    private boolean finished = false;

    public Player(Room start) {
        this.room = start;
    }

    public boolean tryMove(Direction d) {

        MapSite side = room.getSide(d);
        if (side == null) return false;
        return side.enter(this);
    }


    public int hp() {
        return hp;
    }

    @Override
    public void damage(int amount) {
        hp -= amount;

        if (damageDecorator != null){
            hp -= damageDecorator.getDamage();
        }
        if (hp < 0) {
            hp = 0;
          stunned = true;


        }
    }

    public boolean isDead() {
        return hp <= 0;
    }


    public boolean isStunned() {
        return stunned;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public void update(String event) {


        if (event.equals("Bomb exploded")) {
            System.out.println("Bomb exploded");
        }

    }

    public static void setDamageDecorator(DamageBombDecorator damageDecorator1) {
        damageDecorator  = damageDecorator1;
    }
}
