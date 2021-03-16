package fr.epita.pfa.terroirback.controller;

import fr.epita.pfa.terroirback.dto.MarketOnly;
import fr.epita.pfa.terroirback.dto.TraderMarketDto;
import fr.epita.pfa.terroirback.service.MarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("Api pour la gestion marché")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @ApiOperation("ajout marché par un trader")
    @PostMapping("/market/trader")
    @Secured("VENDEUR")
    public ResponseEntity post(@RequestBody TraderMarketDto traderMarkerDto) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            marketService.postMarketForTrader(traderMarkerDto, email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récuperer les marché lié à un d'un trader")
    @GetMapping("/market/trader")
    public ResponseEntity getMarketOfTrader() {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok().body(marketService.getMarketForTrader(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récuperer les marchés")
    @GetMapping("/market/all")
    public ResponseEntity get() {
        try {
            return ResponseEntity.ok().body(marketService.findAllMarket());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récuperer les marchés seulement")
    @GetMapping("/market/only")
    public ResponseEntity getOnlyMarket() {
        try {
            return ResponseEntity.ok().body(marketService.findOnlyAllMarket());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("ajout marché")
    @PostMapping("/market")
    public ResponseEntity postMarket(@RequestBody MarketOnly marketOnly) {
        try {
            marketService.addMarket(marketOnly);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
