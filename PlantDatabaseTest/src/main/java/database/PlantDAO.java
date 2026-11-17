
package database;

import model.Plant;
import model.PlantProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.MeasuredGrowRequirement;
import model.NonMeasuredGrowRequirement;
import model.NativeContinent;
import model.Pest;
import model.Infestation;
import java.time.LocalDate;

public class PlantDAO{

    // Shared query for retrieving complete plant information
    private static final String BASE_QUERY =
            "SELECT p.plantID, p.commonName, " +
            "p.scientificName, p.stemColor, " +
            "t.family, t.`order`, t.class, " +
            "fp.petalNo, fp.floweringSeason, fp.flowerColor, " +
            "nfp.reproductionMethod, nfp.foliageType, " +
            "CASE " +
            "WHEN fp.plantID IS NOT NULL THEN 'Flowering' " +
            "WHEN nfp.plantID IS NOT NULL THEN 'Non-Flowering' " +
            "ELSE 'Unclassified' END AS plantType " +
            "FROM Plant p " +
            "JOIN Taxonomy t " +
            "ON p.scientificName = t.scientificName " +
            "LEFT JOIN FloweringPlant fp " +
            "ON p.plantID = fp.plantID " +
            "LEFT JOIN NonFloweringPlant nfp " +
            "ON p.plantID = nfp.plantID ";

    // Retrieve all plants
    public List<Plant> getAllPlants() throws SQLException {

        List<Plant> plants = new ArrayList<>();

        String sql = BASE_QUERY + "ORDER BY p.commonName";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                plants.add(mapPlant(result));
            }
        }

        return plants;
    }

    //Searchs
            // Retrieve one plant using its ID
            public Plant getPlantById(int id) throws SQLException {

                String sql = BASE_QUERY + "WHERE p.plantID = ?";

                try (Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setInt(1, id);

                    try (ResultSet result = statement.executeQuery()) {

                        if (result.next()) {
                            return mapPlant(result);
                        }
                    }
                }

                return null;
            }

            //
            // Search plants by common or scientific name
            public List<Plant> searchPlantsByName(String searchTerm)
                    throws SQLException {

                List<Plant> plants = new ArrayList<>();

                String sql = BASE_QUERY +
                        "WHERE LOWER(p.commonName) LIKE LOWER(?) " +
                        "OR LOWER(p.scientificName) LIKE LOWER(?) " +
                        "ORDER BY p.commonName";

                try (Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql)) {

                    String searchPattern = "%" + searchTerm + "%";

                    statement.setString(1, searchPattern);
                    statement.setString(2, searchPattern);

                    try (ResultSet result = statement.executeQuery()) {

                        while (result.next()) {

                            plants.add(mapPlant(result));
                        }
                    }
                }

                return plants;
            }
            // Search plants using taxonomy fields
                public List<Plant> searchPlantsByTaxonomy(
                        String family,
                        String orderName,
                        String plantClass) throws SQLException {

                    List<Plant> plants = new ArrayList<>();
                    List<String> parameters = new ArrayList<>();

                    StringBuilder query = new StringBuilder(BASE_QUERY);
                    query.append("WHERE 1 = 1 ");

                    // Add family filter if provided
                    if (family != null && !family.isBlank()) {
                        query.append("AND LOWER(t.family) LIKE LOWER(?) ");
                        parameters.add("%" + family.trim() + "%");
                    }

                    // Add order filter if provided
                    if (orderName != null && !orderName.isBlank()) {
                        query.append("AND LOWER(t.`order`) LIKE LOWER(?) ");
                        parameters.add("%" + orderName.trim() + "%");
                    }

                    // Add class filter if provided
                    if (plantClass != null && !plantClass.isBlank()) {
                        query.append("AND LOWER(t.class) LIKE LOWER(?) ");
                        parameters.add("%" + plantClass.trim() + "%");
                    }

                    query.append("ORDER BY p.commonName");

                    try (Connection connection = DatabaseConnection.getConnection();
                        PreparedStatement statement =
                                connection.prepareStatement(query.toString())) {

                        // Assign the parameters in the same order they were added
                        for (int i = 0; i < parameters.size(); i++) {
                            statement.setString(i + 1, parameters.get(i));
                        }

                        try (ResultSet result = statement.executeQuery()) {
                            while (result.next()) {
                                plants.add(mapPlant(result));
                            }
                        }
                    }

                    return plants;
                }


             // Search plants by flowering status and flower characteristics
                public List<Plant> searchPlantsByFloweringDetails(
                        String plantType,
                        String flowerColor,
                        String floweringSeason) throws SQLException {

                    List<Plant> plants = new ArrayList<>();
                    List<String> parameters = new ArrayList<>();

                    // Prevent an empty search from returning every plant
                    if ((plantType == null || plantType.isBlank()) &&
                        (flowerColor == null || flowerColor.isBlank()) &&
                        (floweringSeason == null || floweringSeason.isBlank())) {
                        return plants;
                    }

                    StringBuilder query = new StringBuilder(BASE_QUERY);
                    query.append("WHERE 1 = 1 ");

                    // Filter by plant type
                    if (plantType != null && !plantType.isBlank()) {
                        query.append(
                            "AND CASE " +
                            "WHEN fp.plantID IS NOT NULL THEN 'Flowering' " +
                            "WHEN nfp.plantID IS NOT NULL THEN 'Non-Flowering' " +
                            "ELSE 'Unclassified' END = ? "
                        );

                        parameters.add(plantType.trim());
                    }

                    // Filter by flower color
                    if (flowerColor != null && !flowerColor.isBlank()) {
                        query.append("AND LOWER(fp.flowerColor) LIKE LOWER(?) ");
                        parameters.add("%" + flowerColor.trim() + "%");
                    }

                    // Filter by flowering season
                    if (floweringSeason != null && !floweringSeason.isBlank()) {
                        query.append("AND LOWER(fp.floweringSeason) LIKE LOWER(?) ");
                        parameters.add("%" + floweringSeason.trim() + "%");
                    }

                    query.append("ORDER BY p.commonName");

                    try (Connection connection = DatabaseConnection.getConnection();
                        PreparedStatement statement =
                                connection.prepareStatement(query.toString())) {

                        for (int i = 0; i < parameters.size(); i++) {
                            statement.setString(i + 1, parameters.get(i));
                        }

                        try (ResultSet result = statement.executeQuery()) {
                            while (result.next()) {
                                plants.add(mapPlant(result));
                            }
                        }
                    }

                    return plants;
                }  
            // Search plants by native continent
            public List<Plant> searchPlantsByContinent(
                    String continentName) throws SQLException {

                List<Plant> plants = new ArrayList<>();

                // Prevent an empty search
                if (continentName == null || continentName.isBlank()) {
                    return plants;
                }

                String query = BASE_QUERY +
                        "WHERE EXISTS (" +
                        "SELECT 1 " +
                        "FROM GrowsOn g " +
                        "JOIN NativeContinent nc " +
                        "ON g.continentName = nc.continentName " +
                        "WHERE g.plantID = p.plantID " +
                        "AND LOWER(nc.continentName) LIKE LOWER(?)" +
                        ") " +
                        "ORDER BY p.commonName";

                try (Connection connection = DatabaseConnection.getConnection();
                    PreparedStatement statement =
                            connection.prepareStatement(query)) {

                    statement.setString(1, "%" + continentName.trim() + "%");

                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next()) {
                            plants.add(mapPlant(result));
                        }
                    }
                }

                return plants;
            }
        // Search for a plant with multiple filters
                public List<Plant> searchPlants(
                        String family,
                        String orderName,
                        String plantClass,
                        String plantType,
                        String flowerColor,
                        String floweringSeason,
                        String continentName) throws SQLException {

                    List<Plant> plants = new ArrayList<>();
                    List<String> parameters = new ArrayList<>();

                    // Prevent an empty search
                    if ((family == null || family.isBlank()) &&
                        (orderName == null || orderName.isBlank()) &&
                        (plantClass == null || plantClass.isBlank()) &&
                        (plantType == null || plantType.isBlank()) &&
                        (flowerColor == null || flowerColor.isBlank()) &&
                        (floweringSeason == null || floweringSeason.isBlank()) &&
                        (continentName == null || continentName.isBlank())) {

                        return plants;
                    }

                    StringBuilder query = new StringBuilder(BASE_QUERY);
                    query.append("WHERE 1 = 1 ");

                    // Taxonomy filters
                    if (family != null && !family.isBlank()) {
                        query.append("AND LOWER(t.family) LIKE LOWER(?) ");
                        parameters.add("%" + family.trim() + "%");
                    }

                    if (orderName != null && !orderName.isBlank()) {
                        query.append("AND LOWER(t.`order`) LIKE LOWER(?) ");
                        parameters.add("%" + orderName.trim() + "%");
                    }

                    if (plantClass != null && !plantClass.isBlank()) {
                        query.append("AND LOWER(t.class) LIKE LOWER(?) ");
                        parameters.add("%" + plantClass.trim() + "%");
                    }

                    // Plant type filter
                    if (plantType != null && !plantType.isBlank()) {
                        query.append(
                            "AND LOWER(CASE " +
                            "WHEN fp.plantID IS NOT NULL THEN 'Flowering' " +
                            "WHEN nfp.plantID IS NOT NULL THEN 'Non-Flowering' " +
                            "ELSE 'Unclassified' END) = LOWER(?) "
                        );

                        parameters.add(plantType.trim());
                    }

                    // Flower characteristics
                    if (flowerColor != null && !flowerColor.isBlank()) {
                        query.append("AND LOWER(fp.flowerColor) LIKE LOWER(?) ");
                        parameters.add("%" + flowerColor.trim() + "%");
                    }

                    if (floweringSeason != null && !floweringSeason.isBlank()) {
                        query.append("AND LOWER(fp.floweringSeason) LIKE LOWER(?) ");
                        parameters.add("%" + floweringSeason.trim() + "%");
                    }

                    // Geographic filter
                    if (continentName != null && !continentName.isBlank()) {
                        query.append(
                            "AND EXISTS (" +
                            "SELECT 1 " +
                            "FROM GrowsOn g " +
                            "JOIN NativeContinent nc " +
                            "ON g.continentName = nc.continentName " +
                            "WHERE g.plantID = p.plantID " +
                            "AND LOWER(nc.continentName) LIKE LOWER(?)" +
                            ") "
                        );

                        parameters.add("%" + continentName.trim() + "%");
                    }

                    query.append("ORDER BY p.commonName");

                    try (Connection connection = DatabaseConnection.getConnection();
                        PreparedStatement statement =
                                connection.prepareStatement(query.toString())) {

                        // Assign parameters in the same order as the filters
                        for (int i = 0; i < parameters.size(); i++) {
                            statement.setString(i + 1, parameters.get(i));
                        }

                        try (ResultSet result = statement.executeQuery()) {
                            while (result.next()) {
                                plants.add(mapPlant(result));
                            }
                        }
                    }

                    return plants;
                }




    //Fin        


    // Plant
    // Convert a database result into a Plant object
    private Plant mapPlant(ResultSet result) throws SQLException {

        int petalValue = result.getInt("petalNo");

        Integer petalNo = result.wasNull() ? null : petalValue;

        return new Plant(
                result.getInt("plantID"),
                result.getString("commonName"),
                result.getString("scientificName"),
                result.getString("stemColor"),
                result.getString("family"),
                result.getString("order"),
                result.getString("class"),
                petalNo,
                result.getString("floweringSeason"),
                result.getString("flowerColor"),
                result.getString("reproductionMethod"),
                result.getString("foliageType"),
                result.getString("plantType")
        );
    }


//Measured
// Retrieve measured growing requirements for a specific plant
public List<MeasuredGrowRequirement> getMeasuredRequirements(int plantId)
        throws SQLException {

    List<MeasuredGrowRequirement> requirements = new ArrayList<>();

    String sql =
            "SELECT m.measureID, m.unit, m.growType, gw.amount " +
            "FROM GrowsWith gw " +
            "JOIN MGrowReq m ON gw.measureID = m.measureID " +
            "WHERE gw.plantID = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, plantId);

        try (ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                MeasuredGrowRequirement requirement =
                        new MeasuredGrowRequirement(
                                result.getInt("measureID"),
                                result.getString("unit"),
                                result.getString("growType"),
                                result.getInt("amount")
                        );

                requirements.add(requirement);
            }
        }
    }

    return requirements;
}



//Non-measured
// Retrieve non-measured growing requirements for a specific plant
public List<NonMeasuredGrowRequirement> getNonMeasuredRequirements(int plantId)
        throws SQLException {

    List<NonMeasuredGrowRequirement> requirements = new ArrayList<>();

    String sql =
            "SELECT n.nonmeasureID, n.reqType, n.description " +
            "FROM Needs nd " +
            "JOIN NonMGrowReq n ON nd.nonmeasureID = n.nonmeasureID " +
            "WHERE nd.plantID = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, plantId);

        try (ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                NonMeasuredGrowRequirement requirement =
                        new NonMeasuredGrowRequirement(
                                result.getInt("nonmeasureID"),
                                result.getString("reqType"),
                                result.getString("description")
                        );

                requirements.add(requirement);
            }
        }
    }

    return requirements;
}





//Continent
//getNativeContinents(int plantId)
public List<NativeContinent> getNativeContinents(int plantId)
        throws SQLException {

    List<NativeContinent> continents = new ArrayList<>();

    String sql =
            "SELECT nc.continentName, nc.EWhemisphere, nc.NShemisphere " +
            "FROM GrowsOn go " +
            "JOIN NativeContinent nc " +
            "ON go.continentName = nc.continentName " +
            "WHERE go.plantID = ? " +
            "ORDER BY nc.continentName";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, plantId);

        try (ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                NativeContinent continent = new NativeContinent(
                        result.getString("continentName"),
                        result.getString("EWhemisphere"),
                        result.getString("NShemisphere")
                );

                continents.add(continent);
            }
        }
    }

    return continents;
}

//Pest
// Retrieve all pests from the database
public List<Pest> getAllPests() throws SQLException {

    List<Pest> pests = new ArrayList<>();

    String sql = "SELECT pestName, pestType " +
                 "FROM Pest " +
                 "ORDER BY pestName";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {

            Pest pest = new Pest(
                    rs.getString("pestName"),
                    rs.getString("pestType")
            );

            pests.add(pest);
        }
    }

    return pests;
}

//Infestation
// Retrieve all infestations associated with a plant
public List<Infestation> getInfestations(int plantId)
        throws SQLException {

    List<Infestation> infestations = new ArrayList<>();

    String sql =
            "SELECT i.plantID, " +
            "p.pestName, p.pestType, " +
            "i.startedWhen, " +
            "i.treatmentPlan, " +
            "i.activeStatus " +
            "FROM Infestation i " +
            "JOIN Pest p ON i.pestName = p.pestName " +
            "WHERE i.plantID = ? " +
            "ORDER BY i.startedWhen";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, plantId);

        try (ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                // Retrieve pest information
                Pest pest = new Pest(
                        rs.getString("pestName"),
                        rs.getString("pestType")
                );

                // Retrieve the infestation date
                java.sql.Date sqlDate =
                        rs.getDate("startedWhen");

                LocalDate startedWhen =
                        sqlDate == null
                                ? null
                                : sqlDate.toLocalDate();

                // Create infestation object
                Infestation infestation = new Infestation(
                        rs.getInt("plantID"),
                        pest,
                        startedWhen,
                        rs.getString("treatmentPlan"),
                        rs.getBoolean("activeStatus")
                );

                infestations.add(infestation);
            }
        }
    }

    return infestations;
}







//

//Plant profile
//This part retrives the basic plant info, the growing requirements and native continent
public PlantProfile getCompletePlantProfile(int plantId)
        throws SQLException {

    // Retrieve basic plant information
    Plant plant = getPlantById(plantId);

    // Check whether the plant exists
    if (plant == null) {
        return null;
    }

    // Retrieve measured requirements
    List<MeasuredGrowRequirement> measuredRequirements =
            getMeasuredRequirements(plantId);

    // Retrieve non-measured requirements
    List<NonMeasuredGrowRequirement> nonMeasuredRequirements =
            getNonMeasuredRequirements(plantId);

    // Retrieve native continents
    List<NativeContinent> nativeContinents =
            getNativeContinents(plantId);
    
    //Retrive infestatins
    List<Infestation> infestations =
        getInfestations(plantId);

    // Combine everything into one profile
    return new PlantProfile(
        plant,
        measuredRequirements,
        nonMeasuredRequirements,
        nativeContinents,
        infestations
);
}





}

