package com.buyanywhere.productcatalog.controllers.v1;

import java.util.concurrent.atomic.AtomicLong;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.web.bind.annotation.*;

import com.buyanywhere.productcatalog.models.Category;


@RestController
@RequestMapping("v1")

public class CategoryController {

	private final AtomicLong countID = new AtomicLong();
	private String name;
	private int displayedOrder;
	private boolean deleted;

	@RequestMapping(
            method = RequestMethod.GET,
            value = {"create-category/{id}/{name}/{displayedOrder}/{deleted}"}
    )
	
	public Category createCategory(
			@PathVariable String name,
			@PathVariable int displayedOrder,
			@PathVariable boolean deleted) {
		return new Category(countID.incrementAndGet(), name, displayedOrder, deleted);
    }
}
