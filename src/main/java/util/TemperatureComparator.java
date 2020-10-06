package util;

import static java.lang.Math.abs;

public class TemperatureComparator {

    public double compareTemperature(double tempFromNdtv, double tempFromAPI) {
        return Math.round(abs(tempFromAPI - tempFromNdtv));
    }

    public double compareTemperatureInFahrenheit(double tempFromNdtv, double tempFromAPI) {
        double apiTempInFahrenheit = ((tempFromAPI * 9) / 5) + 32;
        return Math.round(abs(apiTempInFahrenheit - tempFromNdtv));
    }

}
