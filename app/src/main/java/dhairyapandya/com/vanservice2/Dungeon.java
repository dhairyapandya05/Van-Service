package dhairyapandya.com.vanservice2;

import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class Dungeon {
    @PropertyName("Commingstudents")
    private List<String> dungeonGroup;

    public List<String> getDungeonGroup() {
        return dungeonGroup;
    }

    public Dungeon() {
        // Must have a public no-argument constructor
    }

    // Initialize all fields of a dungeon


    @PropertyName("dungeon_group")
    public List<String> getCommingStudents() {
        return dungeonGroup;
    }

    @PropertyName("dungeon_group")
    public void setDungeonGroup(List<String> dungeonGroup) {
        this.dungeonGroup = dungeonGroup;
    }
}
