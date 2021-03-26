package fr.epita.pfa.terroirback.controller;

import fr.epita.pfa.terroirback.dto.CommandeDto;
import fr.epita.pfa.terroirback.service.CommandeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("Api pour la gestion client")
public class CustomerController {

    @Autowired
    private CommandeService commandeService;

    @ApiOperation("Passer une commande")
    @PostMapping("/customer/order")
    public ResponseEntity postAnOrder(@RequestBody List<CommandeDto> commandeDto) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            commandeService.passAnOrder(commandeDto, email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Récuperer la liste des commande d'un client")
    @GetMapping("/customer/order")
    public ResponseEntity getOrder() {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok().body(commandeService.getOrder(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
