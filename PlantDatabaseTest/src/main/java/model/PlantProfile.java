
package model;

import java.util.List;

public class PlantProfile {

    private Plant plant;

    private List<MeasuredGrowRequirement> measuredRequirements;

    private List<NonMeasuredGrowRequirement> nonMeasuredRequirements;

    private List<NativeContinent> nativeContinents;

    private List<Infestation> infestations;

    // Constructor
    public PlantProfile(
            Plant plant,
            List<MeasuredGrowRequirement> measuredRequirements,
            List<NonMeasuredGrowRequirement> nonMeasuredRequirements,
            List<NativeContinent> nativeContinents,
            List<Infestation> infestations) {

        this.plant = plant;
        this.measuredRequirements = measuredRequirements;
        this.nonMeasuredRequirements = nonMeasuredRequirements;
        this.nativeContinents = nativeContinents;
        this.infestations = infestations;
    }

    // Getters
    public Plant getPlant() {
        return plant;
    }

    public List<MeasuredGrowRequirement> getMeasuredRequirements() {
        return measuredRequirements;
    }

    public List<NonMeasuredGrowRequirement> getNonMeasuredRequirements() {
        return nonMeasuredRequirements;
    }

    public List<NativeContinent> getNativeContinents() {
        return nativeContinents;
    }

    public List<Infestation> getInfestations() {
        return infestations;
    }
}

