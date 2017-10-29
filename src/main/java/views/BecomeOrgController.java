/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import tn.esprit.blizzard.iski.entities.OrganizerRequest;
import tn.esprit.blizzard.iski.entities.User;
import tn.esprit.blizzard.services.interfaces.OrganizerRequestServiceRemote;

/**
 * FXML Controller class
 *
 * @author Joey Badass
 */
public class BecomeOrgController implements Initializable {

	private OrganizerRequestServiceRemote organizerRequestService;
	private Context context;
	@FXML
	private Label lblFilePath;
	@FXML
	private JFXButton btnSelectFile;
	@FXML
	private JFXButton btnConfirm;
	@FXML
	private JFXButton btnCancel;

	/**
	 * Initializes the controller class.
	 */

	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("Dialog init");

	}

	@FXML
	private void onSelectFileBtAction(ActionEvent event) {
		System.out.println("select file btn clicked");
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.doc", "*.docx", "*.pdf"),
				new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File selectedFile = fc.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
		if (selectedFile != null) {
			lblFilePath.setText(selectedFile.getPath());
		}

	}

	@FXML
	private void onConfirmBtAction(ActionEvent event) throws NamingException {
		System.out.println("confirm btn clicked");
		context = new InitialContext();
		organizerRequestService = (OrganizerRequestServiceRemote) context.lookup(
				"iski-ear/iski-ejb/OrganizerRequestService!tn.esprit.blizzard.services.interfaces.OrganizerRequestServiceRemote");
		OrganizerRequest o = new OrganizerRequest();
		User u = new User();
		u = Main.getLoggedUser();
		o.setUser(u);
		System.out.println("UUUUUUUUUUUUUUUUUUU________" + o.getUser().getIdUser());
		o.setStatus("Pending");
		o.setCv(lblFilePath.getText());
		String date = new Date().toString();
		o.setDate(date);
		System.out.println("UUUUUUxxxxxxxxxxxxx________" + o.getUser().getIdUser());
		organizerRequestService.add(o);
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	private void OnCancelBtAction(ActionEvent event) {
		System.out.println("cancel btn clicked");
		((Node) event.getSource()).getScene().getWindow().hide();
	}

}
