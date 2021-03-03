package fr.epita.pfa.terroirback.controller;

import fr.epita.pfa.terroirback.dto.Hello;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("Api test requête hello")
public class HelloController {

    @ApiOperation("api de teste qui retourne hello")
    @GetMapping(value= "/hello")
    public Hello sayHello() {
        return new Hello("Hello World");
    }
}
