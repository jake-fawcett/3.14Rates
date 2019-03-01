package uk.ac.york.sepr4.object.building;


import lombok.Data;

//Added for Assessment 3: Minigame building required for a map object to exist
@Data
public class MinigameBuilding extends Building{
    private Float buildingRange = 500f;
    public MinigameBuilding(){
        // Empty constructor for JSON DAO
    }
}
