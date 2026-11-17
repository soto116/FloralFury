
package model;

public class NonMeasuredGrowRequirement {

    private int nonmeasureID;
    private String reqType;
    private String description;

    public NonMeasuredGrowRequirement(
            int nonmeasureID,
            String reqType,
            String description) {

        this.nonmeasureID = nonmeasureID;
        this.reqType = reqType;
        this.description = description;
    }

    public int getNonmeasureID() {
        return nonmeasureID;
    }

    public String getReqType() {
        return reqType;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return reqType + ": " + description;
    }
}
