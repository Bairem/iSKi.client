package delegate;

import java.util.List;

import tn.esprit.blizzard.iski.entities.User;
import tn.esprit.blizzard.services.interfaces.UserServiceRemote;

public class UserServiceDelegate {

	private static String jndiName = "iski-ear/iski-ejb/UserService!tn.esprit.blizzard.services.interfaces.UserServiceRemote";

	private static UserServiceRemote getProxy() {
		return (UserServiceRemote) locator.ServiceLocator.getInstance().getProxy(jndiName);
	}

	public static void addUser(User user) {
		getProxy().add(user);
	}

	public static User findUserById(Integer idUser) {
		return getProxy().findById(idUser);
	}

	public static void updateUser(User user) {
		getProxy().update(user);
	}

	public static List<User> findAllUsers() {
		return getProxy().findAll();
	}

	public static void deleteUserById(Integer idUser) {
		getProxy().remove(idUser);
	}

	public static User findByEmail(String email) {
		return getProxy().findByEmail(email);
	}

}
