package com.buyanywhere.productcatalog.specifications;

import com.buyanywhere.productcatalog.models.Category;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategorySpecificationsBuilder {
    private final List<SearchCriteria> parameters;

    public CategorySpecificationsBuilder() {
        parameters = new ArrayList<SearchCriteria>();
    }

    public CategorySpecificationsBuilder with(String key, String operation, Object value) {
        parameters.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Category> build() {
        if (parameters.size() == 0) {
            return null;
        }

        List<Specification> specs = parameters.stream()
                .map(CategorySpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < parameters.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}
