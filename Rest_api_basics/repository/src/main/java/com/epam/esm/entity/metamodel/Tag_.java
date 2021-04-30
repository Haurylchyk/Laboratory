package com.epam.esm.entity.metamodel;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tag.class)
public abstract class Tag_ extends com.epam.esm.entity.BaseEntity_ {

	public static volatile ListAttribute<Tag, GiftCertificate> certificates;
	public static volatile SingularAttribute<Tag, String> name;

	public static final String CERTIFICATES = "certificates";
	public static final String NAME = "name";

}

