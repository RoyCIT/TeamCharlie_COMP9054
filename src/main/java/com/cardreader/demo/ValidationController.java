package com.cardreader.demo;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private Validator validator;

//    @RequestMapping("/greeting")
//    public Validator validator(@RequestParam(value="name", defaultValue="World") String name) {
//        return new Validator(counter.incrementAndGet(),
//                String.format(template, name));
//    }

    @RequestMapping("/api/panels/request")
    //(method=GET)
    public Validator validator(@RequestParam(value="panelid", defaultValue="PanelId") String panelid,
                               @RequestParam(value="cardid", defaultValue="CardId") String cardid) {
        validator = new Validator(counter.incrementAndGet(),panelid,cardid);
        //return new Validator(counter.incrementAndGet(),panelid,cardid);

        validator.populateCurrentEvent();
        return validator;
    }

}
