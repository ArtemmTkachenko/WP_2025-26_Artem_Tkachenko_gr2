package org.example;

import java.util.Random;

public class BombFactory {
    private static final Random RNG = new Random();

    public static Bomb randomBomb() {
        return new GasBomb();
    }
}
