package org.example;
public abstract class BombDecorator implements Bomb {
    protected Bomb decoratedBomb;

    public BombDecorator(Bomb bomb) {
        this.decoratedBomb = bomb;
    }

    @Override
    public void onEnter(Actor actor, Room room) {
        decoratedBomb.onEnter(actor, room);
    }

    @Override
    public boolean isActive() {
        return decoratedBomb.isActive();
    }

    @Override
    public String renderSymbol() {
        return decoratedBomb.renderSymbol();
    }


    @Override
    public void addObserver(Observer observer) {
        decoratedBomb.addObserver(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        decoratedBomb.removeObserver(observer);
    }

    @Override
    public void notifyObservers(String event) {
        decoratedBomb.notifyObservers(event);
    }


    @Override
    public void explode(Actor actor) {
        decoratedBomb.explode(actor);
    }

    @Override
    public int getDamage() {
        return decoratedBomb.getDamage();
    }
}
