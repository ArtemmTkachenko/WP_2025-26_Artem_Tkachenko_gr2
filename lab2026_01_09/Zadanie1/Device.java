package Gola.lab2026_01_09.Zadanie1;

public class Device {

    public void powerOn(Power5V power) {
        System.out.println("Device powered with " + power.getVoltage5V() + "V");
    }
}
