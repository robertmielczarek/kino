package implementation;

import javax.ws.rs.core.Response.Status.Family;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.Statuses;

import exceptions.FailedDatabaseOperationException;

final class WebServiceResponseValidator {

	static void validateResponseStatus(ClientResponse response)
			throws FailedDatabaseOperationException {

		Family responseFamily = response.getStatusInfo().getFamily();
		if (responseFamily == Statuses.from(400).getFamily()) {
			throw new FailedDatabaseOperationException("Client error");
		} else if (responseFamily == Statuses.from(500).getFamily()) {
			throw new FailedDatabaseOperationException("Server error");
		}
	}

}
