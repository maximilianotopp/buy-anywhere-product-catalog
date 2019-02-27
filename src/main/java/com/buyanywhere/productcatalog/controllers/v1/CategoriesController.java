package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.dto.CategoryDto;
import com.buyanywhere.productcatalog.enums.OrderByEnum;
import com.buyanywhere.productcatalog.models.Category;
import com.buyanywhere.productcatalog.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/categories")
public class CategoriesController extends BaseController {
    private CategoryRepository repository;

    public CategoriesController(CategoryRepository repository, ModelMapper mapper) {
        super(mapper);
        this.repository = repository;
    }

    @GetMapping
    public Iterable<CategoryDto> get(
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

        List<Category> categories = repository.findAll(specification);

        return categories.stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
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
