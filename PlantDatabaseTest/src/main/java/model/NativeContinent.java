
package model;

public class NativeContinent {

    private String continentName;
    private String EWhemisphere;
    private String NShemisphere;

    // Constructor
    public NativeContinent(
            String continentName,
            String EWhemisphere,
            String NShemisphere) {

        this.continentName = continentName;
        this.EWhemisphere = EWhemisphere;
        this.NShemisphere = NShemisphere;
    }

    // Getters
    public String getContinentName() {
        return continentName;
    }

    public String getEWhemisphere() {
        return EWhemisphere;
    }

    public String getNShemisphere() {
        return NShemisphere;
    }

    @Override
    public String toString() {
        return continentName +
                " (" + EWhemisphere +
                ", " + NShemisphere + ")";
    }
}

