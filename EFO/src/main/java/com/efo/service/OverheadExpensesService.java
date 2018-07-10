package com.efo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.efo.dao.OverheadExpensesDao;
import com.efo.entity.OverheadExpenses;
import com.efo.interfaces.IOverheadExpenses;

public class OverheadExpensesService implements IOverheadExpenses {

	@Autowired
	OverheadExpensesDao expensesDao;
	
	@Override
	public void create(OverheadExpenses expenses) {
		expensesDao.create(expenses);
	}

	@Override
	public OverheadExpenses retrieve(Long id) {
		return expensesDao.retrieve(id);
	}

	@Override
	public List<OverheadExpenses> retrieveRawList() {
		return expensesDao.retrieveRawList();
	}

	@Override
	public void update(OverheadExpenses expenses) {
		expensesDao.update(expenses);
	}
	
	public void merge(OverheadExpenses expenses) {
		expensesDao.merge(expenses);
	}
	
	@Override
	public void delete(OverheadExpenses expenses) {
		expensesDao.delete(expenses);
	}

}
