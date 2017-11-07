package com.cardreader.demo;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ValidationController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private Validator validator;

    @RequestMapping(value = "/api/panels/request", method = GET)
    //(method=GET)
    public Validator validator(@RequestParam(value="panelid", defaultValue="PanelId") String panelid,
                               @RequestParam(value="cardid", defaultValue="CardId") String cardid) {
        validator = new Validator(counter.incrementAndGet(),panelid,cardid);
        //return new Validator(counter.incrementAndGet(),panelid,cardid);

        validator.populateCurrentEvent();
        return validator;
    }

}
