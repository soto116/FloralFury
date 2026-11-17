
package model;

public class Pest {

    private String pestName;
    private String pestType;

    public Pest(String pestName, String pestType) {
        this.pestName = pestName;
        this.pestType = pestType;
    }

    public String getPestName() {
        return pestName;
    }

    public String getPestType() {
        return pestType;
    }

    @Override
    public String toString() {
        return pestName + " (" + pestType + ")";
    }
}
