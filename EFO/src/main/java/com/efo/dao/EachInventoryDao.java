package com.efo.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.efo.entity.EachInventory;
import com.efo.entity.ProductOrders;
import com.efo.interfaces.IEachInventory;

@Transactional
@Repository
public class EachInventoryDao implements IEachInventory {

	@Autowired
	SessionFactory sessionFactory;
	
	private Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void create(EachInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.save(inventory);
		tx.commit();
		session.disconnect();
	}

	@Override
	public EachInventory retrieve(Long id) {
		Session session = session();
		EachInventory inventory = (EachInventory) session.createCriteria(EachInventory.class).add(Restrictions.idEq(id)).uniqueResult();
		session.disconnect();
		
		return inventory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EachInventory> retrieveRawList() {
		Session session = session();
		List<EachInventory> invList = session.createCriteria(EachInventory.class).list();
		session.disconnect();
		
		return invList;
	}

	@Override
	public void update(EachInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.update(inventory);
		tx.commit();
		session.disconnect();
	}

	@Override
	public void delete(EachInventory inventory) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		session.delete(inventory);
		tx.commit();
		session.disconnect();
	}

	public void stockShelf(EachInventory inventory, Integer qty) {
		Session session = session();
		Transaction tx = session.beginTransaction();
		
		for(int i=0; i < qty; i++) {
			EachInventory inv = new EachInventory();
			inv.setSku(inventory.getSku());
			inv.setInvoice_num(inventory.getInvoice_num());
			inv.setOrdered(new Date());
			inv.setWholesale(inventory.getWholesale());
			session.save(inv);
			if (i % 20 == 0) {
		        session.flush();
		        session.clear();
			}
		}
		
		tx.commit();
		session.disconnect();
	}
	
	@SuppressWarnings("unchecked")
	public void markAsDelivered(ProductOrders order, int qty) {
		String upd = "UPDATE EachInventory SET received = :received WHERE id = :id";
		String hql = "SELECT id FROM EachInventory WHERE invoice_num = :invoice_num AND sku = :sku AND received IS null";
		Session session = session();
		Transaction tx = session.beginTransaction();
		List<Long> rows = session.createQuery(hql).setString("invoice_num", order.getInvoice_num())
											.setString("sku", order.getSku()).setMaxResults(qty).list();
		for (Long id : rows) {
			session.createQuery(upd).setDate("received", order.getDelivery_date()).setLong("id", id).executeUpdate();
		}
		
		tx.commit();
		session.disconnect();
	}
	
	public double getAmountOrdered(String sku) {
		String hql = "SELECT COUNT(*) FROM EachInventory WHERE sku = :sku AND ordered IS NOT null AND received IS null";
		Session session = session();
		long amount = (long) session.createQuery(hql).setString("sku", sku).uniqueResult();
		session.disconnect();
		
		return new Long(amount).doubleValue();
	}
	
	public double getAmountReceived(String sku) {
		String hql = "SELECT COUNT(*) FROM EachInventory WHERE sku = :sku AND received IS NOT null AND sold IS null";
		Session session = session();
		long amount = (long) session.createQuery(hql).setString("sku", sku).uniqueResult();
		session.disconnect();
		
		return new Long(amount).doubleValue();
	}

	public double getAmountCommitted(String sku) {
		String hql = "SELECT COUNT(*) FROM EachInventory WHERE sku = :sku AND sold IS NOT null AND shipped IS null";
		Session session = session();
		long amount = (long) session.createQuery(hql).setString("sku", sku).uniqueResult();
		session.disconnect();
		
		return new Long(amount).doubleValue();
	}


}
