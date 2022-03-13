package me.sajithdilshan.dep8.Controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import me.sajithdilshan.dep8.util.CustomerTM;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class MainFormController {


    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtImage;
    public TableView<CustomerTM> tblCustomers;
    public Button btnBrowse;


    Path dbPath = Paths.get("backup/customers.dep8");


    public void initialize() {
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<CustomerTM, Button> lastCol =
                (TableColumn<CustomerTM, Button>) tblCustomers.getColumns().get(3);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction((event -> tblCustomers.getItems().remove(param.getValue())));
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        TableColumn<CustomerTM, ImageView> colPicture = (TableColumn<CustomerTM, ImageView>) tblCustomers.getColumns().get(1);

        colPicture.setCellValueFactory(param -> {
            byte[] picture = param.getValue().getPicture();
            ByteArrayInputStream bais = new ByteArrayInputStream(picture);

            ImageView imageView = new ImageView(new Image(bais));
            imageView.setFitHeight(75);
            imageView.setFitWidth(75);
            return new ReadOnlyObjectWrapper<>(imageView);
        });


    }




    public void btnSave_OnAction(ActionEvent actionEvent) throws IOException {

        if(validated()){
            Path path = Paths.get(txtImage.getText());
            byte[] bytes = Files.readAllBytes(path);

            CustomerTM customer = new CustomerTM(txtId.getText(), txtName.getText(), txtAddress.getText(), bytes);
            tblCustomers.getItems().add(customer);


            if(!Files.exists(dbPath)){
                Files.createFile(dbPath);

            }
            OutputStream fos = Files.newOutputStream(dbPath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<CustomerTM>(tblCustomers.getItems()));
            oos.close();

            clearAll();


        }






    }


    public boolean validated(){
        if (!txtId.getText().matches("C\\d{3}") || tblCustomers.getItems().stream().anyMatch(c -> c.getId().equalsIgnoreCase(txtId.getText()))) {
            txtId.requestFocus();
            txtId.selectAll();
            return false;
        } else if (txtName.getText().trim().isEmpty()) {
            txtName.requestFocus();
            txtName.selectAll();
            return false;
        } else if (txtAddress.getText().trim().isEmpty()) {
            txtAddress.requestFocus();
            txtAddress.selectAll();
            return false;
        }
        return true;
    }


    public void clearAll(){
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtImage.clear();
        tblCustomers.getItems().clear();
        txtId.requestFocus();
    }




    public void btnBrowse_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter
                ("Images", "*.jpeg", "*.jpg", "*.gif", "*.png", "*.bmp"));
        fileChooser.setTitle("Select an image");
        File file = fileChooser.showOpenDialog(btnBrowse.getScene().getWindow());
        txtImage.setText(file != null ? file.getAbsolutePath() : "");
    }










}
