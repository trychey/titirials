package org.baeldung.persistence.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.baeldung.persistence.IEnhancedSpecification;
import org.baeldung.persistence.model.User;
import org.baeldung.web.util.SpecSearchCriteria;

public class UserSpecification implements IEnhancedSpecification<User> {

	private SpecSearchCriteria criteria;
	private boolean lowPrecedenceCriteria;

	@Override
	public boolean isOfLowPrecedence() {
		return lowPrecedenceCriteria;
	}

	public UserSpecification(final SpecSearchCriteria criteria) {
		super();
		this.criteria = criteria;
		this.lowPrecedenceCriteria = criteria.isLowPrecedence();
	}

	public UserSpecification(final SpecSearchCriteria criteria, final boolean lowerPrecedence) {
		super();
		this.criteria = criteria;
		this.lowPrecedenceCriteria = lowerPrecedence;
	}

	public SpecSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
		switch (criteria.getOperation()) {
		case EQUALITY:
			return builder.equal(root.get(criteria.getKey()), criteria.getValue());
		case NEGATION:
			return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
		case GREATER_THAN:
			return builder.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
		case LESS_THAN:
			return builder.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
		case LIKE:
			return builder.like(root.<String> get(criteria.getKey()), criteria.getValue().toString());
		case STARTS_WITH:
			return builder.like(root.<String> get(criteria.getKey()), criteria.getValue() + "%");
		case ENDS_WITH:
			return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue());
		case CONTAINS:
			return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue() + "%");
		default:
			return null;
		}
	}

}
