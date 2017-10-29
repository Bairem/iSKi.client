/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import delegate.UserServiceDelegate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tn.esprit.blizzard.iski.entities.User;
import tn.esprit.blizzard.services.interfaces.UserServiceRemote;

/**
 * FXML Controller class
 *
 * @author Joey Badass
 */
public class RegisterController implements Initializable {

	@FXML
	private StackPane rootPane;
	@FXML
	private JFXTextField txt_firstname;
	@FXML
	private JFXTextField txt_lastname;
	@FXML
	private JFXCheckBox cbx_male;
	@FXML
	private JFXCheckBox cbx_female;
	@FXML
	private JFXTextField txt_email;
	@FXML
	private JFXPasswordField txt_password;
	@FXML
	private JFXTextField txt_number;
	@FXML
	private JFXTextField txt_cin;
	@FXML
	private JFXDatePicker dp_birthdate;
	@FXML
	private JFXButton btn_register;

	/**
	 * Initializes the controller class.
	 */

	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("Initialize register");
	}

	@FXML
	private void onRegisterBtClick(ActionEvent event) {

		System.out.println("Perform register action from service");

		
			System.out.println("JNDI OK");

			User u = new User();
			u.setEmail(txt_email.getText());
			u.setCin(txt_cin.getText());
			u.setFirstName(txt_firstname.getText());
			u.setLastName(txt_lastname.getText());
			u.setNumber(txt_number.getText());
			u.setBirthDate(dp_birthdate.getValue().toString());
			u.setPassword(txt_password.getText());
			if (cbx_female.isSelected()) {
				u.setGender("female");
			} else if (cbx_male.isSelected()) {
				u.setGender("male");
			}
			u.setUserType("default");

			UserServiceDelegate.addUser(u);
			
			
			Notifications notbuilder = Notifications.create();
			notbuilder.title("Registration Completed ");
			notbuilder.graphic(null);
			notbuilder.hideAfter(Duration.seconds(7));
			notbuilder.position(Pos.BOTTOM_RIGHT);
			notbuilder.showConfirm();
			
			
			
			System.out.println("User added sucessfully " + u.getFirstName() + " with id = " + u.getIdUser());
			this.onGoToLogin(event);

	

	}
	
	
	@FXML
	private void goBack(ActionEvent event){
		this.onGoToLogin(event);
	}
	
	private void onGoToLogin(ActionEvent event) {
	

		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root;
			root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));

			primaryStage.initStyle(StageStyle.DECORATED);

			Scene scene = new Scene(root);
			primaryStage.setTitle("Login");
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("b");

	}

}
