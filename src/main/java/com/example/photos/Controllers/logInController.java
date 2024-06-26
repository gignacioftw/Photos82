package com.example.photos.Controllers;

import com.example.photos.Model.Admin;
import com.example.photos.Model.UserSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import static com.example.photos.Model.UserSystem.*;

/**
 * Handles logIn.fxml
 * @author Gigna
 */
public class logInController {
    UserSystem s;

    @FXML
    Button loginButton;
    @FXML
    Label errorMessage;
    @FXML
    Button helpButton;
    @FXML
    TextField nameTextField;
    public Stage stage;
    public Scene scene;
    public Parent root;

    public void login(ActionEvent event) throws IOException, ClassNotFoundException {
        s = readApp();
        String username = nameTextField.getText();

        if(s.check(username)) {
            if (s.getUser(username) instanceof Admin) {
                nameTextField.setText("");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/adminHome.fxml"));
                root = loader.load();

                adminHomeController adminHomeController = loader.getController();
                adminHomeController.displayName(username);
                adminHomeController.loadSystem(s);
                adminHomeController.setPreScene(loginButton.getScene());

                //root = FXMLLoader.load(getClass().getResource("logIn.xml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else{
                nameTextField.setText("");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/userHome.fxml"));
                root = loader.load();

                userHomeController userHomeController = loader.getController();
                userHomeController.loadSystem(s);
                userHomeController.displayName(username);

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            stage.setOnHidden( e -> {
                try {
                    writeApp(s);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        else{
            errorMessage.setText("Please enter a valid username");
        }
    }
    public void help(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/photos/logInhelp.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}