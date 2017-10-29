package delegate;

import java.util.List;

import tn.esprit.blizzard.iski.entities.OrganizerRequest;
import tn.esprit.blizzard.iski.entities.User;
import tn.esprit.blizzard.services.interfaces.OrganizerRequestServiceRemote;

public class OrganizerRequestServiceDelegate {
	private static String jndiName = "iski-ear/iski-ejb/OrganizerRequestService!tn.esprit.blizzard.services.interfaces.OrganizerRequestServiceRemote";

	private static OrganizerRequestServiceRemote getProxy() {
		return (OrganizerRequestServiceRemote) locator.ServiceLocator.getInstance().getProxy(jndiName);
	}
	public static void add(OrganizerRequest req) {
		getProxy().add(req);
	}

	public static OrganizerRequest findById(Integer id) {
		return getProxy().findById(id);
	}

	public static void update(OrganizerRequest req) {
		getProxy().update(req);
	}

	public static List<OrganizerRequest> findAll() {
		return getProxy().findAll();
	}

	public static void delete(Integer id) {
		getProxy().remove(id);
	}

	public static List<OrganizerRequest> findPending(String status) {
		return getProxy().findPendingRequests(status);
	}

}
