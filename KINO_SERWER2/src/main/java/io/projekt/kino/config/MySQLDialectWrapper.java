package io.projekt.kino.config;

import org.hibernate.Hibernate;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MySQLDialectWrapper extends org.hibernate.dialect.MySQLDialect{
	
	MySQLDialectWrapper(){
		super();
		registerFunction("DAYOFWEEK", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "DAYOFWEEK(?1)" ) );
	}
}
