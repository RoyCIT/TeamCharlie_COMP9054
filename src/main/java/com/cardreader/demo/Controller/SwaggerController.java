package com.cardreader.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * SwaggerController Class to default root request to the
 * swagger-ui page
 */
@Controller
@RequestMapping("/")
public class SwaggerController
{

    @RequestMapping(method = RequestMethod.GET)
    public String swagger()
    {
        return "redirect:/swagger-ui.html";
    }

}