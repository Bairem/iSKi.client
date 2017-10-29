/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Duration;
import tn.esprit.blizzard.iski.entities.OrganizerRequest;
import tn.esprit.blizzard.iski.entities.User;
import tn.esprit.blizzard.services.interfaces.OrganizerRequestServiceRemote;
import tn.esprit.blizzard.services.interfaces.UserServiceRemote;

/**
 * FXML Controller class
 *
 * @author Joey Badass
 */
public class AdminPanelController implements Initializable {

	Context context;
	UserServiceRemote userService;
	private ObservableList<User> userList;

	private OrganizerRequestServiceRemote organizerRequestService;
	private ObservableList<OrganizerRequest> organizerRequestList;

	@FXML
	private Label lblMenu;
	@FXML
	private TableView<User> tvUsers;
	@FXML
	private TableView<OrganizerRequest> tvRequest;
	@FXML
	private Tab TabUsers;
	@FXML
	private TableColumn<OrganizerRequest, Integer> tcRID;
	@FXML
	private TableColumn<OrganizerRequest, String> tcDate;
	@FXML
	private TableColumn<User, Integer> tcUser;
	@FXML
	private TableColumn<OrganizerRequest, String> tcCV;
	@FXML
	private TableColumn<OrganizerRequest, Integer> tcStatus;
	@FXML
	private TableColumn tcRActions;
	@FXML
	private TableColumn<User, String> tcCin;
	@FXML
	private TableColumn<User, String> tcFirstname;
	@FXML
	private TableColumn<User, String> tcLastname;
	@FXML
	private TableColumn<User, String> tcGender;
	@FXML
	private TableColumn<User, String> tcBirthdate;
	@FXML
	private TableColumn<User, String> tcEmail;
	@FXML
	private TableColumn<User, String> tcPhonenumber;
	@FXML
	private TableColumn tcActions;
	@FXML
	private TableColumn<User, Integer> tcId;
	@FXML
	private Tab TabOrganizerRequest;
	@FXML
	private TableColumn<User, String> tcType;

	/**
	 * Initializes the controller class.
	 */

	public void initialize(URL url, ResourceBundle rb) {
		this.initUserTab();
		this.initOrganizeRequestTab();

	}

	@SuppressWarnings("unchecked")
	private void initOrganizeRequestTab() {
		this.tcDate.setCellValueFactory(new PropertyValueFactory<OrganizerRequest, String>("date"));
		this.tcRID.setCellValueFactory(new PropertyValueFactory<OrganizerRequest, Integer>("id"));
		this.tcStatus.setCellValueFactory(new PropertyValueFactory<OrganizerRequest, Integer>("status"));
		this.tcCV.setCellValueFactory(new PropertyValueFactory<OrganizerRequest, String>("cv"));

		 tcRActions.setCellValueFactory(new PropertyValueFactory<>("DMY"));

		Callback<TableColumn<OrganizerRequest, String>, TableCell<OrganizerRequest, String>> cellFactory = //
				new Callback<TableColumn<OrganizerRequest, String>, TableCell<OrganizerRequest, String>>() {
					@Override
					public TableCell call(final TableColumn<OrganizerRequest, String> param) {
						final TableCell<OrganizerRequest, String> cell = new TableCell<OrganizerRequest, String>() {
							final ImageView iv = new ImageView("ressources/if_ban_icon.png");
							final ImageView iv1 = new ImageView("ressources/if_accept_103286.png");
							//HBOX hb = new HBox(btnAccept,btnDecline)
							final JFXButton btnAccept = new JFXButton("", iv1);
							
							final JFXButton btnDecline = new JFXButton("", iv);
							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btnAccept.setOnAction(event -> {
										OrganizerRequest req = getTableView().getItems().get(getIndex());
										req.setStatus("Accepted");
										organizerRequestService.update(req);
										
										initOrganizeRequestTab();
										
									});
									
									btnAccept.setVisible(true);
									
									btnDecline.setOnAction(event -> {
										OrganizerRequest req = getTableView().getItems().get(getIndex());
										req.setStatus("Declined");
										organizerRequestService.update(req);
										initOrganizeRequestTab();
										
									});
									
									setGraphic(btnDecline);
									setText(null);
								}
							}
							
							
						};
						return cell;
					}
				};

		 tcRActions.setCellFactory(cellFactory);
		try {
			organizerRequestList = this.LoadOrganizerRequestList();
			tvRequest.setItems(organizerRequestList);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initUserTab() {
		this.tcCin.setCellValueFactory(new PropertyValueFactory<User, String>("cin"));
		this.tcFirstname.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
		this.tcLastname.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
		this.tcGender.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
		this.tcPhonenumber.setCellValueFactory(new PropertyValueFactory<User, String>("number"));
		this.tcEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		this.tcId.setCellValueFactory(new PropertyValueFactory<User, Integer>("idUser"));
		this.tcBirthdate.setCellValueFactory(new PropertyValueFactory<User, String>("birthDate"));
		this.tcType.setCellValueFactory(new PropertyValueFactory<User, String>("userType"));

		tcActions.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

		Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = //
				new Callback<TableColumn<User, String>, TableCell<User, String>>() {
					@Override
					public TableCell call(final TableColumn<User, String> param) {
						final TableCell<User, String> cell = new TableCell<User, String>() {
							
							final ImageView iv = new ImageView("ressources/if_ban_icon.png");

							final JFXButton btn = new JFXButton("", iv);
							

							
							
							@Override
							public void updateItem(String item, boolean empty) {
								try {
									context = new InitialContext();
								} catch (NamingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								try {
									userService = (UserServiceRemote) context
											.lookup("iski-ear/iski-ejb/UserService!tn.esprit.blizzard.services.interfaces.UserServiceRemote");
								} catch (NamingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								System.out.println("JNDI OK");
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
									setText(null);
								} else {
									btn.setOnAction(event -> {
										User user = getTableView().getItems().get(getIndex());
										System.out.println(user.getFirstName() + "   " + user.getLastName());
										userService.remove(user.getIdUser());
										Notifications notbuilder = Notifications.create();
										notbuilder.title("Deleted Successfully ");
										notbuilder.graphic(null);
										notbuilder.hideAfter(Duration.seconds(7));
										notbuilder.position(Pos.BOTTOM_RIGHT);
										notbuilder.showConfirm();
										initUserTab();

									});
									setGraphic(btn);
									setText(null);
								}
							}
						};
						return cell;
					}
				};

		tcActions.setCellFactory(cellFactory);
		try {
			userList = this.LoadUserList();
			tvUsers.setItems(userList);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ObservableList<User> LoadUserList() throws NamingException {

		context = new InitialContext();
		userService = (UserServiceRemote) context
				.lookup("iski-ear/iski-ejb/UserService!tn.esprit.blizzard.services.interfaces.UserServiceRemote");

		System.out.println("JNDI OK");

		userList = FXCollections.observableArrayList(userService.findAll());

		return userList;

	}

	private ObservableList<OrganizerRequest> LoadOrganizerRequestList() throws NamingException {

		context = new InitialContext();
		organizerRequestService = (OrganizerRequestServiceRemote) context.lookup(
				"iski-ear/iski-ejb/OrganizerRequestService!tn.esprit.blizzard.services.interfaces.OrganizerRequestServiceRemote");

		System.out.println("JNDI OK");

		organizerRequestList = FXCollections.observableArrayList(organizerRequestService.findPendingRequests("Pending"));

		return organizerRequestList;

	}

}
