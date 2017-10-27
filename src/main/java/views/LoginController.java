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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.blizzard.iski.entities.User;
import tn.esprit.blizzard.services.interfaces.UserServiceRemote;

/**
 * FXML Controller class
 *
 * @author Joey Badass
 */
public class LoginController implements Initializable {

	@FXML
	private StackPane rootPane;
	@FXML
	private JFXTextField txt_username;
	@FXML
	private JFXPasswordField txt_password;
	@FXML
	private JFXButton btn_login;
	@FXML
	private Label lbl_welcome;

	@FXML
	private JFXButton btn_Goto_register;

	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("Login initialized");
	}

	@FXML
	private void onActionBT(ActionEvent event) {

		try {
			Context context = new InitialContext();
			UserServiceRemote userService = (UserServiceRemote) context
					.lookup("iski-ear/iski-ejb/UserService!tn.esprit.blizzard.services.interfaces.UserServiceRemote");
			System.out.println("JNDI OK");

			User u = new User();
			u = userService.findByEmail(txt_username.getText());
			if (u == null) {
				System.out.println("bara eb3ed");

			} else {
				System.out.println("User password from DB : " + u.getPassword());
				if (txt_password.getText().equals(u.getPassword())) {
					System.out.println("3okkkeeyy");
				} else {
					System.out.println("tyh ma 9olna rou7");
				}

			}
		} catch (NamingException e) {
			System.out.println("JNDI NOT OK");
			e.printStackTrace();
		}

	}

	@FXML
	private void onGoToRegisterClicked(ActionEvent event) {
		System.out.println("goto register please");

		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root;
			root = FXMLLoader.load(getClass().getResource("../views/Register.fxml"));

			primaryStage.initStyle(StageStyle.DECORATED);

			Scene scene = new Scene(root);
			primaryStage.setTitle("Registration");
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("b");

	}

	private String ControleDeSaisie() {
		if (txt_username.getText().length() == 0) {
			return ("Please type your email ");

		} else if (txt_password.getText().length() == 0) {
			return ("Please type your password");

		} else if (!txt_username.getText().contains("@")) {
			return ("Please type a valid email");

		} else {
			return null;

		}
	}

}
