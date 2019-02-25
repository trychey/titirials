package com.baeldung.dao.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baeldung.domain.Item;
import com.baeldung.dao.repositories.CustomItemRepository;

@Repository
public class CustomItemRepositoryImpl implements CustomItemRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void deleteCustom(Item item) {
		entityManager.remove(item);
	}

	@Override
	public Item findItemById(Long id) {
		return entityManager.find(Item.class, id);
	}

	@Override
	public void findThenDelete(Long id) {
		final Item item = entityManager.find(Item.class, id);
		entityManager.remove(item);
	}

	@Override
	public List<Item> findItemsByColorAndGrade() {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
		Root<Item> itemRoot = criteriaQuery.from(Item.class);

		Predicate predicateForBlueColor = criteriaBuilder.equal(itemRoot.get("color"), "blue");
		Predicate predicateForRedColor = criteriaBuilder.equal(itemRoot.get("color"), "red");
		Predicate predicateForColor = criteriaBuilder.or(predicateForBlueColor, predicateForRedColor);

		Predicate predicateForGradeA = criteriaBuilder.equal(itemRoot.get("grade"), "A");
		Predicate predicateForGradeB = criteriaBuilder.equal(itemRoot.get("grade"), "B");
		Predicate predicateForGrade = criteriaBuilder.or(predicateForGradeA, predicateForGradeB);

		// final search filter
		Predicate finalPredicate = criteriaBuilder.and(predicateForColor, predicateForGrade);

		criteriaQuery.where(finalPredicate);

		List<Item> phones = entityManager.createQuery(criteriaQuery).getResultList();
		return phones;
	}

	@Override
	public List<Item> findItemByColorOrGrade() {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
		Root<Item> itemRoot = criteriaQuery.from(Item.class);

		Predicate predicateForBlueColor = criteriaBuilder.equal(itemRoot.get("color"), "red");
		Predicate predicateForGradeA = criteriaBuilder.equal(itemRoot.get("grade"), "D");
		Predicate predicateForColor = criteriaBuilder.and(predicateForBlueColor, predicateForGradeA);

		Predicate predicateForRedColor = criteriaBuilder.equal(itemRoot.get("color"), "blue");
		Predicate predicateForGradeB = criteriaBuilder.equal(itemRoot.get("grade"), "B");
		Predicate predicateForGrade = criteriaBuilder.and(predicateForRedColor, predicateForGradeB);

		// final search filter
		Predicate finalPredicate = criteriaBuilder.or(predicateForColor, predicateForGrade);

		criteriaQuery.where(finalPredicate);

		List<Item> phones = entityManager.createQuery(criteriaQuery).getResultList();
		return phones;
	}
}
