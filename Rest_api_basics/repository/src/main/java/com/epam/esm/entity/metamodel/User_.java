package com.epam.esm.entity.metamodel;

import com.epam.esm.entity.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.epam.esm.entity.BaseEntity_ {

	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> login;

	public static final String NAME = "name";
	public static final String LOGIN = "login";

}

