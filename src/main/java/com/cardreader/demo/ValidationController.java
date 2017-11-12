package com.cardreader.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ValidationController {

    @RequestMapping(value = "/api/panels/request", method = GET)
    public JsonModel jsonModel(@RequestParam(value="panelid", defaultValue="PanelId") String panelid,
                               @RequestParam(value="cardid", defaultValue="CardId") String cardid,
                               @RequestParam(value="allowed", defaultValue="false") String accessAllowed) {

        JsonModel jsonModel = JsonModel.getInstance();
        jsonModel.populate(panelid, cardid, accessAllowed);

        return jsonModel;
    }
}
