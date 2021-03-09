package fr.epita.pfa.terroirback.controller;

import fr.epita.pfa.terroirback.dto.EmailInscription;
import fr.epita.pfa.terroirback.service.EmailInscriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api("Api enregistrement en base lors de la confirmation de l'adresse mail")
public class UserConnection {

    @Autowired
    private EmailInscriptionService emailInscriptionService;

    @ApiOperation("api enregistrement de l'adresse mail")
    @PostMapping(value= "/emailInscription")
    public ResponseEntity emailInscription(@RequestBody EmailInscription emailInscription) {
        try {
            emailInscriptionService.postEmail(emailInscription);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
