package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.ProductOrders;
import com.efo.interfaces.IProductOrders;

@Transactional
@Repository
public class ProductOrdersDao implements IProductOrders {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(ProductOrders orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(orders);
		tx.commit();
		session.disconnect();
	}

	@Override
	public ProductOrders retrieve(int id) {
		Session session = session();
		ProductOrders orders = (ProductOrders) session.createCriteria(ProductOrders.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return orders;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductOrders> retrieveRawList() {
		Session session = session();
		List<ProductOrders> orderList = session.createCriteria(ProductOrders.class).list();
		session.disconnect();
		
		return orderList;
	}

	@Override
	public void update(ProductOrders orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(orders);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(ProductOrders orders) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(orders);
		tx.commit();
		session.disconnect();
	}

}