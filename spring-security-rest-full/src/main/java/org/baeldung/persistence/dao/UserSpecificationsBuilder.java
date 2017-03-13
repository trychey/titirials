package org.baeldung.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.persistence.IEnhancedSpecification;
import org.baeldung.persistence.model.User;
import org.baeldung.web.util.SearchOperation;
import org.baeldung.web.util.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public final class UserSpecificationsBuilder {

	private final List<SpecSearchCriteria> params;

	public UserSpecificationsBuilder() {
		params = new ArrayList<SpecSearchCriteria>();
	}

	// API

	public final UserSpecificationsBuilder with(final String key, final String operation, final Object value,
			final String prefix, final String suffix) {
		return with(null, key, operation, value, prefix, suffix);
	}

	public final UserSpecificationsBuilder with(final String precedenceIndicator, final String key,
			final String operation, final Object value, final String prefix, final String suffix) {
		SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
		if (op != null) {
			if (op == SearchOperation.EQUALITY) // the operation may be complex
												// operation
			{
				final boolean startWithAsterisk = prefix != null && prefix.contains("*");
				final boolean endWithAsterisk = suffix != null && suffix.contains("*");

				if (startWithAsterisk && endWithAsterisk) {
					op = SearchOperation.CONTAINS;
				} else if (startWithAsterisk) {
					op = SearchOperation.ENDS_WITH;
				} else if (endWithAsterisk) {
					op = SearchOperation.STARTS_WITH;
				}
			}
			params.add(new SpecSearchCriteria(precedenceIndicator, key, op, value));
		}
		return this;
	}

	public Specification<User> build() {

		if (params.size() == 0) {
			return null;
		}

		final List<IEnhancedSpecification<User>> specs = new ArrayList<>();
		for (final SpecSearchCriteria param : params) {
			specs.add(new UserSpecification(param));
		}

		specs.sort((spec0, spec1) -> {
			return Boolean.compare(spec0.isOfLowPrecedence(), spec1.isOfLowPrecedence());
		});

		Specification<User> result = specs.get(0);
		for (int i = 1; i < specs.size(); i++) {
			IEnhancedSpecification<User> optionalSpec = specs.get(i);

			if (optionalSpec.isOfLowPrecedence())
				result = Specifications.where(result).or(specs.get(i));
			else
				result = Specifications.where(result).and(specs.get(i));
		}
		return result;
	}
	
	public final UserSpecificationsBuilder with(UserSpecification spec) {
		params.add(spec.getCriteria());
		return this;
	}
}
