package com.epam.esm.entity.metamodel;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GiftCertificate.class)
public abstract class GiftCertificate_ extends com.epam.esm.entity.BaseEntity_ {

	public static volatile SingularAttribute<GiftCertificate, Integer> duration;
	public static volatile ListAttribute<GiftCertificate, Tag> tagList;
	public static volatile SingularAttribute<GiftCertificate, Integer> price;
	public static volatile SingularAttribute<GiftCertificate, LocalDateTime> lastUpdateDate;
	public static volatile SingularAttribute<GiftCertificate, String> name;
	public static volatile SingularAttribute<GiftCertificate, String> description;
	public static volatile SingularAttribute<GiftCertificate, LocalDateTime> createDate;

	public static final String DURATION = "duration";
	public static final String TAG_LIST = "tagList";
	public static final String PRICE = "price";
	public static final String LAST_UPDATE_DATE = "lastUpdateDate";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String CREATE_DATE = "createDate";

}

