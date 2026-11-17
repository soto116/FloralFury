package model;
//Plant will create an capsule for each plant

// Represents an individual plant
public class Plant {

    // Basic plant attributes
    private int plantId;
    private String commonName;
    private String scientificName;
    private String stemColor;

    // Taxonomy attributes
    private String family;
    private String order;
    private String plantClass;

    // Flowering plant attributes
    private Integer petalNo;
    private String floweringSeason;
    private String flowerColor;

    // Non-flowering plant attributes
    private String reproductionMethod;
    private String foliageType;

    // Plant classification
    private String plantType;

    // Constructor
    public Plant(int plantId, String commonName,
                 String scientificName, String stemColor,
                 String family, String order, String plantClass,
                 Integer petalNo, String floweringSeason,
                 String flowerColor, String reproductionMethod,
                 String foliageType, String plantType) {

        this.plantId = plantId;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.stemColor = stemColor;

        this.family = family;
        this.order = order;
        this.plantClass = plantClass;

        this.petalNo = petalNo;
        this.floweringSeason = floweringSeason;
        this.flowerColor = flowerColor;

        this.reproductionMethod = reproductionMethod;
        this.foliageType = foliageType;

        this.plantType = plantType;
    }

    // Getters
    public int getPlantId() {
        return plantId;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getStemColor() {
        return stemColor;
    }

    public String getFamily() {
        return family;
    }

    public String getOrder() {
        return order;
    }

    public String getPlantClass() {
        return plantClass;
    }

    public Integer getPetalNo() {
        return petalNo;
    }

    public String getFloweringSeason() {
        return floweringSeason;
    }

    public String getFlowerColor() {
        return flowerColor;
    }

    public String getReproductionMethod() {
        return reproductionMethod;
    }

    public String getFoliageType() {
        return foliageType;
    }

    public String getPlantType() {
        return plantType;
    }

    // Setters
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public void setStemColor(String stemColor) {
        this.stemColor = stemColor;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setPlantClass(String plantClass) {
        this.plantClass = plantClass;
    }

    public void setPetalNo(Integer petalNo) {
        this.petalNo = petalNo;
    }

    public void setFloweringSeason(String floweringSeason) {
        this.floweringSeason = floweringSeason;
    }

    public void setFlowerColor(String flowerColor) {
        this.flowerColor = flowerColor;
    }

    public void setReproductionMethod(String reproductionMethod) {
        this.reproductionMethod = reproductionMethod;
    }

    public void setFoliageType(String foliageType) {
        this.foliageType = foliageType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    // Display plant information
    @Override
    public String toString() {

        String information =
                "Plant ID: " + plantId +
                "\nCommon Name: " + commonName +
                "\nScientific Name: " + scientificName +
                "\nStem Color: " + stemColor +
                "\nFamily: " + family +
                "\nOrder: " + order +
                "\nClass: " + plantClass +
                "\nPlant Type: " + plantType;

        if ("Flowering".equals(plantType)) {

            information +=
                    "\nPetal Number: " + petalNo +
                    "\nFlowering Season: " + floweringSeason +
                    "\nFlower Color: " + flowerColor;

        } else if ("Non-Flowering".equals(plantType)) {

            information +=
                    "\nReproduction Method: " + reproductionMethod +
                    "\nFoliage Type: " + foliageType;
        }

        return information;
    }
}
