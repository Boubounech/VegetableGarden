package garden.model;

public class TemperatureSource extends Source {

    public TemperatureSource(int temperatureStrength, int temperatureLength) {
        this.setStrength(temperatureStrength);
        this.setLength(temperatureLength);
    }
}
