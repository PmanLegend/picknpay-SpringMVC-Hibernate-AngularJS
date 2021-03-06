package com.picknpay.dao;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<PK extends Serializable, T> {
	
	private final Class<T> persisClass;
	
	@SuppressWarnings("unchecked")
	public AbstractDao(){
		this.persisClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@Autowired
        SessionFactory sessionFac;

	protected Session getSession(){
		return sessionFac.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persisClass, key);
	}

	public void persist(T entity) {
		getSession().persist(entity);
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}
        
        public void update(T entity) {
		getSession().update(entity);
	}
	
	protected Criteria createEntityCriteria(){
		return getSession().createCriteria(persisClass);
	}

}
