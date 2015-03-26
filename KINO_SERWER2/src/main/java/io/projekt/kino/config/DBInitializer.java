/**
 * 
 */
package io.projekt.kino.config;

import io.projekt.kino.services.UzytkownikService;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBInitializer
{
	private static Logger logger = LoggerFactory.getLogger(DBInitializer.class);

	@Autowired
	private UzytkownikService userService;

	@Value("${init-db:false}")
	private String initDatabase;

	@PostConstruct
	public void init()
	{
		try {
			logger.info("############## InitDatabase :" + initDatabase + " ########################");
			if (Boolean.parseBoolean(initDatabase)) {
				initDatabase();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void initDatabase()
	{
		logger.info("Initializing Database with sample data");
	}
}
