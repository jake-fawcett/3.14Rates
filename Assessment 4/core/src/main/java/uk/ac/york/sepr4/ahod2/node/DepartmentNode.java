package uk.ac.york.sepr4.ahod2.node;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lombok.Data;
import uk.ac.york.sepr4.ahod2.GameInstance;
import uk.ac.york.sepr4.ahod2.io.FileManager;
import uk.ac.york.sepr4.ahod2.object.building.Department;
import uk.ac.york.sepr4.ahod2.screen.DepartmentScreen;

@Data
public class DepartmentNode extends Node {

    private Department department;

    /***
     * Node type that creates department screen for specified department.
     * @param node
     * @param department specified department
     */
    public DepartmentNode(Node node, Department department) {
        super(node.getId(), node.getRow(), node.getCol());
        this.department = department;

        setConnected(node.getConnected());
        this.setTexture(new TextureRegionDrawable(new TextureRegion(FileManager.departmentNodeIcon)));
    }

    /***
     * Switch current screen to department screen.
     * @param gameInstance
     */
    @Override
    public void action(GameInstance gameInstance) {
        DepartmentScreen departmentScreen = new DepartmentScreen(gameInstance, department);
        gameInstance.fadeSwitchScreen(departmentScreen);
    }
}
