package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.enums.OrderByEnum;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

@RestController
@RequestMapping("/v1/categories")
public class CategoriesController {
    private CategoryRepository repository;

    public CategoriesController(CategoryRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Iterable<Category> get(
            @RequestParam(value = "filterBy", required = false) String filterBy,
            @RequestParam(value = "showDeleted", required = false, defaultValue = "false") boolean showDeleted,
            @RequestParam(value = "orderBy", required = false, defaultValue = "alpha") OrderByEnum orderBy,
            @RequestParam(value = "reverse", required = false, defaultValue = "false") boolean reverseOrder) {

        Specification<Category> specification = Specification
                .where(
                        getPredicateDeletedAndSorted(showDeleted, orderBy, reverseOrder)
                );

        if(filterBy.length() > 3){
            specification = specification.and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.like(root.get("name"), "%" + filterBy + "%"));
        }

        return repository.findAll(specification);
    }

    private Specification<Category> getPredicateDeletedAndSorted(boolean showDeleted,
                                                                 OrderByEnum orderBy,
                                                                 boolean reverseOrder) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            Path<Category> orderByPath = orderBy == OrderByEnum.alpha
                    ? root.get("name")
                    : root.get("displayOrder");

            Order order = !reverseOrder
                    ? criteriaBuilder.asc(orderByPath)
                    : criteriaBuilder.desc(orderByPath);

            criteriaQuery.orderBy(order);

            return criteriaBuilder.equal(root.<String>get("deleted"), showDeleted ? 1 : 0);
        };
    }
}
