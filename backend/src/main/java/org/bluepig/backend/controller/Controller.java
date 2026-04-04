package org.bluepig.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping(value = {"","/hello"})
    @ResponseBody
    public String hello() {
        return "no hello";
    }
}
