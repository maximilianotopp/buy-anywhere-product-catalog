package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.enums.OrderByEnum;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.criteria.*;

@RestController
@RequestMapping("/v1/categories")
public class CategoriesController {
    private CategoryRepository repository;

    public CategoriesController(CategoryRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Category> get(
            @RequestParam(value = "filterBy", required = false) String filterBy,
            @RequestParam(value = "showDeleted", required = false, defaultValue = "false") boolean showDeleted,
            @RequestParam(value = "orderBy", required = false, defaultValue = "alpha") OrderByEnum orderBy,
            @RequestParam(value = "reverse", required = false, defaultValue = "false") boolean reverseOrder) {

        Specification<Category> specification = Specification
                .where((root, criteriaQuery, criteriaBuilder) ->
                        getPredicateShowDeletedAndOrdered(
                                showDeleted, orderBy, reverseOrder, root, criteriaQuery, criteriaBuilder)
                );

        if(filterBy != null && filterBy.length() > 3){
            specification = specification.and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filterBy + "%"));
        }

        return repository.findAll(specification);
    }

    private Predicate getPredicateShowDeletedAndOrdered(boolean showDeleted, OrderByEnum orderBy, boolean reverseOrder,
                                                        Root<Category> root, CriteriaQuery<?> criteriaQuery,
                                                        CriteriaBuilder criteriaBuilder) {
        Path<Category> orderByPath = orderBy == OrderByEnum.alpha
                ? root.get("name")
                : root.get("displayOrder");

        Order order = !reverseOrder
                ? criteriaBuilder.asc(orderByPath)
                : criteriaBuilder.desc(orderByPath);

        criteriaQuery.orderBy(order);

        return criteriaBuilder.equal(root.<String>get("deleted"), showDeleted ? 1 : 0);
    }
}
