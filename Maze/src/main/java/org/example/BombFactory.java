package org.example;

import java.util.Random;

public class BombFactory {


    public static Bomb randomBomb() {
        return new GasBomb();
    }
}
