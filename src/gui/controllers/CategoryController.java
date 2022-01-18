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

    public void btnCancel(ActionEvent actionEvent) {
        ((Stage) txtName.getScene().getWindow()).close();
    }

    public void setMainSceneModel(MainSceneModel mainSceneModel) {
        this.mainSceneModel = mainSceneModel;
    }

    public void setTxtName(String text){
        txtName.setText(text);
    }

    public void setEdit(){
        edit = true;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
