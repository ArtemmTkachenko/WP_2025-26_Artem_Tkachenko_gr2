package Gola.lab2026_01_09.Zadanie2;

public class App {
    public static void main(String[] args) {

        TemperatureSensor t1 =
                new KelvinAdapter(new KelvinSensor());

        TemperatureSensor t2 =
                new FahrenheitAdapter(new FahrenheitSensor());

        System.out.println("Temp 1: " + t1.getTemperatureCelsius() + " °C");
        System.out.println("Temp 2: " + t2.getTemperatureCelsius() + " °C");
    }
}
