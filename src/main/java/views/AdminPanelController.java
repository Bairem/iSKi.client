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

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import tn.esprit.blizzard.iski.entities.User;
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
	@FXML
	private Label lblMenu;
	@FXML
	private TableView<User> tvUsers;
	@FXML
	private Tab TabUsers;
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

        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory
                = //
                new Callback<TableColumn<User, String>, TableCell<User, String>>() {
            @Override
            public TableCell call(final TableColumn<User, String> param) {
                final TableCell<User, String> cell = new TableCell<User, String>() {
                	final ImageView iv = new ImageView("ressources/if_ban_icon.png");
                	
                    final JFXButton btn = new JFXButton("Ban", iv);
                    
                    

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                User user = getTableView().getItems().get(getIndex());
                                System.out.println(user.getFirstName()
                                        + "   " + user.getLastName());
                                userService.remove(user.getIdUser());
                                initialize(url, rb);
                                
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

}
