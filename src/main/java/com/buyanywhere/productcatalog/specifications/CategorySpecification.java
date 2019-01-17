package com.buyanywhere.productcatalog.specifications;

import com.buyanywhere.productcatalog.models.Category;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class CategorySpecification implements Specification<Category> {
    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder builder){
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
