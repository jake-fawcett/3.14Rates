package uk.ac.york.sepr4.object.building;

import lombok.Data;

@Data
public class Department extends Building {
    private Float buildingRange = 500f;
    public Department() {
        // Empty constructor for JSON DAO
    }

}
