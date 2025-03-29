package com.kipl.common;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

@SuppressWarnings("serial")
public class CustomPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {
	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		if (name != null) {
			String uppercaseName = name.getText().toUpperCase();
			return Identifier.toIdentifier(uppercaseName);
		}
		return super.toPhysicalTableName(name, context);
	}
}