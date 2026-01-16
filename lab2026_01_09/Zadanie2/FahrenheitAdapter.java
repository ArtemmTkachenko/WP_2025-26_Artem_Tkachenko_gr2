package Gola.lab2026_01_09.Zadanie2;

public class FahrenheitAdapter implements TemperatureSensor {

    private FahrenheitSensor sensor;

    public FahrenheitAdapter(FahrenheitSensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public double getTemperatureCelsius() {
        return (sensor.getTemperatureFahrenheit() - 32) * 5 / 9;
    }
}
