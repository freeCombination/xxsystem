package com.xx.system.common.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

public class MysqlDialectUtil extends MySQLDialect {


	public MysqlDialectUtil() {
		super();
		registerColumnType(Types.INTEGER, "int");
		registerColumnType(Types.VARCHAR, "longtext");
		registerColumnType(Types.VARCHAR, 16777215, "mediumtext");
		registerColumnType(Types.VARCHAR, 65535, "varchar($l)");
		registerHibernateType(Types.LONGVARCHAR, Hibernate.STRING.getName());

		registerColumnType(Types.NVARCHAR, 255, "nvarchar($l)");
		registerHibernateType(Types.NVARCHAR, Hibernate.STRING.getName());

		registerColumnType(Types.NCHAR, "nchar(1)");
		registerHibernateType(Types.NCHAR, Hibernate.CHARACTER.getName());

		registerColumnType(Types.NCLOB, "nvarchar(max)");
		registerHibernateType(Types.NCLOB, Hibernate.CLOB.getName());

	}

}
