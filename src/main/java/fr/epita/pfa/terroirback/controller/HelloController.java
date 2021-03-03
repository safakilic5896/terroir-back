package fr.epita.pfa.terroirback.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("Api test requête hello")
public class HelloController {

    @ApiOperation("api de teste qui retourne hello")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }
}
