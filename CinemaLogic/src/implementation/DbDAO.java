package implementation;

import interfaces.DAO;
import interfaces.RestElement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import annotations.DatabaseForeign;
import annotations.WebServiceIgnore;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import config.AppConfig;
import exceptions.FailedDatabaseOperationException;

class DbDAO<T extends RestElement> implements DAO<T> {

	private Class<T> typeParameterClass;
	private WebResource service;

	public DbDAO(Class<T> dataClass) {
		this.typeParameterClass = dataClass;

		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(config);

		this.service = client.resource(UriBuilder
				.fromUri(AppConfig.restConnectionString)
				.path(this.typeParameterClass).build());
	}

	public int create(T object) throws FailedDatabaseOperationException {

		Map<Field, Object> ignoredFieldsValues = this
				.webServiceIgnoreFields(object);

		ClientResponse response = null;
		try {
			response = this.service.path("/").type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, object);
		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		this.reassignIgnoredFields(object, ignoredFieldsValues);

		WebServiceResponseValidator.validateResponseStatus(response);

		return response.getEntity(Integer.class);
	}

	public T read(int objectId) throws FailedDatabaseOperationException {

		ClientResponse response = null;
		try {
			response = this.service.path(String.valueOf(objectId))
					.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		WebServiceResponseValidator.validateResponseStatus(response);

		T object = response.getEntity(typeParameterClass);

		this.createForeign(object);

		return object;
	}

	public void update(T object) throws FailedDatabaseOperationException {

		Map<Field, Object> ignoredFieldsValues = this
				.webServiceIgnoreFields(object);

		ClientResponse response = null;
		try {
			response = this.service.path(String.valueOf(object.getId()))
					.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, object);
		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		this.reassignIgnoredFields(object, ignoredFieldsValues);

		WebServiceResponseValidator.validateResponseStatus(response);
	}

	public void delete(int objectId) throws FailedDatabaseOperationException {

		ClientResponse response = null;
		try {
			response = this.service.path(String.valueOf(objectId))
					.accept(MediaType.APPLICATION_JSON)
					.delete(ClientResponse.class);
		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		WebServiceResponseValidator.validateResponseStatus(response);
	}

	public List<T> readAll() throws FailedDatabaseOperationException {

		ClientResponse response = null;
		try {
			response = this.service.path("list")
					.accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
		} catch (Exception e) {
			throw new FailedDatabaseOperationException();
		}

		WebServiceResponseValidator.validateResponseStatus(response);

		T[] resultArray = (T[]) Array.newInstance(this.typeParameterClass, 0);
		T[] allObjects = (T[]) response.getEntity(resultArray.getClass());

		List<T> allobjectsList = new ArrayList<>(Arrays.asList(allObjects));

		// for (T t : allobjectsList) {
		// t.createForeign();
		// }

		return allobjectsList;
	}

	public void createForeign(T object) throws FailedDatabaseOperationException {

		for (Field field : object.getClass().getDeclaredFields()) {
			Annotation[] annotations = field.getDeclaredAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType() == DatabaseForeign.class) {
					Field idField;

					try {
						String foreignFieldName = ((DatabaseForeign) annotation)
								.referenceFieldId();
						idField = object.getClass().getDeclaredField(
								foreignFieldName);
					} catch (NoSuchFieldException | SecurityException e) {
						throw new FailedDatabaseOperationException();
					}

					Integer foreignId = null;
					try {
						idField.setAccessible(true);
						foreignId = (Integer) idField.get(object);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new FailedDatabaseOperationException();
					}

					if (foreignId == null) {
						continue;
					}

					DAO<? extends RestElement> foreignDAO = new DbDAO<>(
							(Class<? extends RestElement>) field.getType());

					try {
						field.setAccessible(true);
						field.set(object, foreignDAO.read(foreignId));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new FailedDatabaseOperationException();
					}
				}
			}
		}
	}

	private Map<Field, Object> webServiceIgnoreFields(T object)
			throws FailedDatabaseOperationException {

		Map<Field, Object> ignoreFieldsValues = new HashMap<Field, Object>();

		for (Field field : object.getClass().getDeclaredFields()) {

			Annotation[] annotations = field.getDeclaredAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType() == WebServiceIgnore.class) {

					Object ignoreFieldValue = null;
					field.setAccessible(true);

					try {
						ignoreFieldValue = field.get(object);
						field.set(object, null);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new FailedDatabaseOperationException();
					}

					ignoreFieldsValues.put(field, ignoreFieldValue);
				}
			}
		}

		return ignoreFieldsValues;
	}

	private T reassignIgnoredFields(T object,
			Map<Field, Object> ignoredFieldValues)
			throws FailedDatabaseOperationException {

		for (Entry<Field, Object> entry : ignoredFieldValues.entrySet()) {
			try {
				entry.getKey().set(object, entry.getValue());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new FailedDatabaseOperationException();
			}
		}

		return object;
	}
}
