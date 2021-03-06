/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import application.Main;
import delegate.UserServiceDelegate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.blizzard.iski.entities.User;

/**
 * FXML Controller class
 *
 * @author Joey Badass
 */
public class ProfileController implements Initializable {

	@FXML
	private Label lblName;
	@FXML
	private Button btnEditConfirm;
	@FXML
	private Label lblEmail;
	@FXML
	private Label lblPhone;
	@FXML
	private Label lblBirthdate;
	@FXML
	private Label lblPwd;
	@FXML
	private TabPane tpProfile;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPhonenumber;
	@FXML
	private TextField txtBirthdate;
	@FXML
	private PasswordField pwdPassword;
	@FXML
	private HBox hbEvents;
	@FXML
	private VBox vbInsurance;
	@FXML
	private VBox vbMaterials;
	@FXML
	private Button btnEditProfile;
	@FXML
	private Button btnBecomeOrgnizer;

	/**
	 * Initializes the controller class.
	 */
	public void initialize(URL url, ResourceBundle rb) {

		if (Main.getLoggedUser().getUserType().equals("organizer")) {
			btnBecomeOrgnizer.setDisable(true);
			btnBecomeOrgnizer.setText("Organizer Account");
		}
		txtBirthdate.setDisable(true);

		try {

			this.init(true, false);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void EditOnAction(ActionEvent event) throws NamingException {
		this.init(false, true);
	}

	@FXML
	private void ConfirmEditOnAction(ActionEvent event) throws NamingException

	{

		User u = Main.getLoggedUser();
		u.setEmail(txtEmail.getText());
		u.setNumber(txtPhonenumber.getText());
		u.setPassword(pwdPassword.getText());

		UserServiceDelegate.updateUser(u);
		Notifications notbuilder = Notifications.create();
		notbuilder.title("Updated Successfully ");
		notbuilder.graphic(null);
		notbuilder.hideAfter(Duration.seconds(7));
		notbuilder.position(Pos.BOTTOM_RIGHT);
		notbuilder.showConfirm();
		init(true, false);
	}

	@FXML
	private void BecomeOrganizerOnAction(ActionEvent event) {
		Stage dialog = new Stage();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../views/BecomeOrg.fxml"));

			Scene scene = new Scene(root);

			dialog.setScene(scene);

			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void init(Boolean val1, Boolean val2) throws NamingException {

		User u = Main.getLoggedUser();
		lblName.setText(u.getFirstName() + " " + u.getLastName());
		txtPhonenumber.setText(u.getNumber());
		txtBirthdate.setText(u.getBirthDate());
		pwdPassword.setText(u.getPassword());
		txtEmail.setText(u.getEmail());

		txtPhonenumber.setDisable(val1);
		btnEditConfirm.setVisible(val2);
		pwdPassword.setVisible(val2);
		lblPwd.setVisible(val2);
		txtEmail.setDisable(val1);

	}

}
