package uk.ac.york.sepr4.object.item;

import lombok.Data;

@Data
public class Item {

    private String name;
    private String lore;

    public Item() {}

    public Item(String name, String lore) {
        this.name = name;
        this.lore = lore;
    }
}
