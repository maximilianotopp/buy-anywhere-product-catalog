package com.buyanywhere.productcatalog.controllers.v1;

import com.buyanywhere.productcatalog.models.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/v1/greeting")
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{name}"
    )
    public Greeting greeting(
            @RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}/{name}"
    )
    public Greeting greeting(
            @PathVariable long id,
            @PathVariable String name) {
        return new Greeting(id, String.format(template, name));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/"
    )
    public Greeting greeting(@RequestBody Greeting greetingData){
        return greetingData;
    }
}