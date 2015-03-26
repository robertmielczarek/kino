package io.projekt.kino.config;

import org.dellroad.stuff.spring.HibernateIsolationJpaDialect;

public class CustomHibernateJpaDialect extends HibernateIsolationJpaDialect {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomHibernateJpaDialect(){
		super();
	}
}