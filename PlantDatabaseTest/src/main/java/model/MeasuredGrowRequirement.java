
package model;

public class MeasuredGrowRequirement {

    private int measureID;
    private String unit;
    private String growType;
    private int amount;

    public MeasuredGrowRequirement(
            int measureID,
            String unit,
            String growType,
            int amount) {

        this.measureID = measureID;
        this.unit = unit;
        this.growType = growType;
        this.amount = amount;
    }

    public int getMeasureID() {
        return measureID;
    }

    public String getUnit() {
        return unit;
    }

    public String getGrowType() {
        return growType;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return growType + ": " + amount + " " + unit;
    }
}
