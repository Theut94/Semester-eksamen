package gui.controllers;

import be.Category;
import gui.AlertHandler;
import gui.MainSceneModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryController {
    @FXML
    private TextField txtName;
    private MainSceneModel mainSceneModel;
    private boolean edit;
    private Category category;

    public CategoryController() {
        edit = false;
    }

    /**
     * Creates a new category or updates the name of the category being edited
     */
    public void btnSave(ActionEvent actionEvent) {
        if(!txtName.getText().trim().equals("")){
            if(!edit)
                mainSceneModel.createCategory(txtName.getText());
            else {
                category.setName(txtName.getText());
                mainSceneModel.updateCategory(category);
            }
            ((Stage) txtName.getScene().getWindow()).close();
        }
        else
            AlertHandler.informationAlert("Name cannot be empty");
    }

    /**
     * Closes the window without making any changes
     */
    public void btnCancel(ActionEvent actionEvent) {
        ((Stage) txtName.getScene().getWindow()).close();
    }

    /**
     * Sets the reference to MainSceneModel
     */
    public void setMainSceneModel(MainSceneModel mainSceneModel) {
        this.mainSceneModel = mainSceneModel;
    }

    /**
     * Sets the reference to the category being edited, fills its name into the text field
     * and sets the edit variable to true
     * @param category - the category being edited
     */
    public void setEditCategory(Category category){
        this.category = category;
        txtName.setText(category.getName());
        edit = true;
    }
}
