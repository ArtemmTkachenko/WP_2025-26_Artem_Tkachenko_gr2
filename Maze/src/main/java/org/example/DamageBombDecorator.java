package org.example;

public class DamageBombDecorator extends BombDecorator{

    public DamageBombDecorator(Bomb bomb) {
        super(bomb);
    }
    public int getDamage() {

      int i  =  super.getDamage() -20;
      System.out.println(i);
      return i;
    }
}
