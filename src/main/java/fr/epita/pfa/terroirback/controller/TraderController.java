package fr.epita.pfa.terroirback.controller;

import fr.epita.pfa.terroirback.dto.AllMarketDto;
import fr.epita.pfa.terroirback.service.MarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("Api pour la gestion trader")
public class TraderController {

    @Autowired
    private MarketService marketService;

    @ApiOperation("récupere tous les trader")
    @GetMapping("/trader")
    public ResponseEntity getAllTrader() {
        try {
            List<AllMarketDto> market = marketService.findAllMarket();
            return ResponseEntity.ok().body(market);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récupere tous les trader selon une localité")
    @GetMapping("/trader/codePostal/{codePostal}")
    public ResponseEntity getByCodePostal(@PathVariable(value = "codePostal") String codePostal) {
        try {
            List<AllMarketDto> market = marketService.findMarketByCodePostal(codePostal);
            return ResponseEntity.ok().body(market);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récupere tous les trader selon le type de stand")
    @GetMapping("/trader/type/{type}")
    public ResponseEntity getAllTrader(@PathVariable(value = "type") String type) {
        try {
            List<AllMarketDto> market = marketService.findMarketByTypeStand(type);
            return ResponseEntity.ok().body(market);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récupere tous les trader selon le type et le code postal")
    @GetMapping("/trader/{codePostal}/{type}")
    public ResponseEntity getByCodePostalAndByType(@PathVariable(value = "codePostal") String codePostal, @PathVariable(value = "type") String type) {
        try {
            List<AllMarketDto> market = marketService.findMarketByTypeStandAndCodePostal(type, codePostal);
            return ResponseEntity.ok().body(market);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
