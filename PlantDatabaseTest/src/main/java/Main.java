
import model.Infestation;
import model.MeasuredGrowRequirement;
import model.NativeContinent;
import model.NonMeasuredGrowRequirement;
import model.Pest;
import model.Plant;
import java.util.List;
import database.PlantDAO;
import java.sql.SQLException;
import model.PlantProfile;




//Remember to install the Java extention for VS code and also OPEN JDK for connecting the database with the program
//OPEN JDK: https://adoptium.net/temurin/releases
//The pom.xml file helps with the connection

//1)Open a terminal in the PlantDatabaseTest

//2)Compile:
//  javac -cp "lib\mysql-connector-j-26.10.0.jar" -d out src\main\java\Main.java src\main\java\model\Plant.java src\main\java\model\PlantProfile.java src\main\java\model\MeasuredGrowRequirement.java src\main\java\model\NonMeasuredGrowRequirement.java src\main\java\model\NativeContinent.java src\main\java\model\Pest.java src\main\java\model\Infestation.java src\main\java\database\DatabaseConnection.java src\main\java\database\PlantDAO.java
//3) Run:
//  java -cp "out;lib\mysql-connector-j-26.10.0.jar" Main


//Remember to $env:MYSQL_PASSWORD="root" 
//before the first run
//Now using the actual database
public class Main {

    public static void main(String[] args) {

        PlantDAO plantDAO = new PlantDAO();

        try {

            System.out.println("\n===== COMPLETE PLANT PROFILE =====");

                int plantId = 1001;

                PlantProfile profile =
                        plantDAO.getCompletePlantProfile(plantId);

                if (profile != null) {

                    // Display basic plant information
                    System.out.println(profile.getPlant());

                    // Display measured requirements
                    System.out.println("\nMeasured Growing Requirements:");

                    for (MeasuredGrowRequirement requirement :
                            profile.getMeasuredRequirements()) {

                        System.out.println(requirement);
                    }

                    // Display non-measured requirements
                    System.out.println("\nNon-Measured Growing Requirements:");

                    for (NonMeasuredGrowRequirement requirement :
                            profile.getNonMeasuredRequirements()) {

                        System.out.println(requirement);
                    }

                    //Display native continent
                    System.out.println("\nNative Continents:");

                    for (NativeContinent continent : profile.getNativeContinents()) {
                        System.out.println(continent);
                    }

                    //Display infestations
                    System.out.println("\nInfestations:");

                    for (Infestation infestation : profile.getInfestations()) {
                    System.out.println(infestation);
                    }



                } else {
                    System.out.println("Plant not found.");
                }

            System.out.println("\n===== COMBINED PLANT SEARCH =====");

            List<Plant> searchResults = plantDAO.searchPlants(
                    null,
                    null,
                    null,
                    "Flowering",
                    null,
                    null,
                    "North America"
            );

            for (Plant plant : searchResults) {
                System.out.println(plant);
            }



        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }
}