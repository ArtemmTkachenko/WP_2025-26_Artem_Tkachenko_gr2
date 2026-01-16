package Gola.lab2026_01_09.Zadanie1;

public class Main {
    public static void main(String[] args) {

        Device device = new Device();

        Power5V power230 = new Adapter230V(new Socket230V());
        Power5V power110 = new Adapter110V(new Socket110V());

        device.powerOn(power230);
        device.powerOn(power110);
    }
}
