
package model;

import java.time.LocalDate;

public class Infestation {

    private int plantId;
    private Pest pest;
    private LocalDate startedWhen;
    private String treatmentPlan;
    private boolean activeStatus;

    public Infestation(
            int plantId,
            Pest pest,
            LocalDate startedWhen,
            String treatmentPlan,
            boolean activeStatus) {

        this.plantId = plantId;
        this.pest = pest;
        this.startedWhen = startedWhen;
        this.treatmentPlan = treatmentPlan;
        this.activeStatus = activeStatus;
    }

    public int getPlantId() {
        return plantId;
    }

    public Pest getPest() {
        return pest;
    }

    public LocalDate getStartedWhen() {
        return startedWhen;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    @Override
    public String toString() {
        return "Pest: " + pest +
                "\nStarted: " + startedWhen +
                "\nTreatment: " + treatmentPlan +
                "\nActive: " + activeStatus;
    }
}
