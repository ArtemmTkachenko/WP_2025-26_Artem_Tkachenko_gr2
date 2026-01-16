package Gola.lab2026_01_09.Zadanie2;

public class KelvinAdapter implements TemperatureSensor {

    private KelvinSensor sensor;

    public KelvinAdapter(KelvinSensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public double getTemperatureCelsius() {
        return sensor.getTemperatureKelvin() - 273.15;
    }
}
