package com.awright.RunningMateRest.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/controller")
@CrossOrigin
public class Controller {
    @GetMapping
    public boolean getClientHasConnected(){
        return true;
    }
    
}
