/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

/**
 * FXML Controller class
 *
 * @author Joey Badass
 */
public class HomeController implements Initializable {

	@FXML
	private Label lblLoggedUser;
	@FXML
	private JFXButton btnHome;
	@FXML
	private JFXButton btnProfile;
	@FXML
	private JFXButton btnHealthIns;
	@FXML
	private JFXButton btnAccomodation;
	@FXML
	private JFXButton btnShop;
	@FXML
	private JFXButton btnExchange;
	@FXML
	private JFXButton btnEvents;
	@FXML
	private JFXButton btnLogout;
	@FXML
	private ScrollPane spCentral;

	/**
	 * Initializes the controller class.
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.lblLoggedUser.setText(Main.getLoggedUser().getFirstName() + " " + Main.getLoggedUser().getLastName());

	}

	@FXML
	private void profileBtAction(ActionEvent event) {
		System.out.println("Show profile in scroll pane");
		this.loadProfile();
	}

	@FXML
	private void logoutBtAction(ActionEvent event) {
		System.out.println("Logout");
		// exit Application
	}

	private void loadProfile() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../views/Profile.fxml"));
			this.spCentral.setContent(root);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
