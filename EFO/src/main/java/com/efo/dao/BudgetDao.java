package com.efo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.Budget;
import com.efo.interfaces.IBudget;

@Transactional
@Repository
public class BudgetDao implements IBudget {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(budget);
		tx.commit();
		session.disconnect();
	}

	@Override
	public Budget retrieve(Long reference) {
		Session session = session();
		Budget budget = (Budget) session.createCriteria(Budget.class).add(Restrictions.idEq(reference)).uniqueResult();
		session.disconnect();
		
		return budget;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Budget> retrieveRawList(String department) {
		Session session = session();
		List<Budget> bList = session.createCriteria(Budget.class).add(Restrictions.eq("department", department)).list();
		session.disconnect();
		
		return bList;
	}

	@Override
	public void update(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(budget);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void merge(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.merge(budget);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(Budget budget) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(budget);
		tx.commit();
		session.disconnect();
	}

}
