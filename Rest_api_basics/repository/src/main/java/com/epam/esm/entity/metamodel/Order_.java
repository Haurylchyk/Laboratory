package com.epam.esm.entity.metamodel;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ extends com.epam.esm.entity.BaseEntity_ {

	public static volatile SingularAttribute<Order, LocalDateTime> date;
	public static volatile ListAttribute<Order, GiftCertificate> giftCertificateList;
	public static volatile SingularAttribute<Order, Integer> cost;
	public static volatile SingularAttribute<Order, User> user;

	public static final String DATE = "date";
	public static final String GIFT_CERTIFICATE_LIST = "giftCertificateList";
	public static final String COST = "cost";
	public static final String USER = "user";

}

