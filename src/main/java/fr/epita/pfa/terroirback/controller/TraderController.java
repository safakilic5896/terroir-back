package fr.epita.pfa.terroirback.controller;

import fr.epita.pfa.terroirback.dto.AllMarketDto;
import fr.epita.pfa.terroirback.dto.CommandeProductDto;
import fr.epita.pfa.terroirback.dto.TraderAllProduct;
import fr.epita.pfa.terroirback.service.MarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.time.LocalDateTime;
import java.util.Date;
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
            String email = null;
            if (SecurityContextHolder.getContext() != null) {
                email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            List<AllMarketDto> market = marketService.findAllMarketByEmail(email);
            return ResponseEntity.ok().body(market);
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récupere tous les trader selon une localité")
    @GetMapping("/trader/codePostal/{codePostal}")
    public ResponseEntity getByCodePostal(@PathVariable(value = "codePostal") String codePostal) {
        try {
            String email = null;
            if (SecurityContextHolder.getContext() != null) {
                email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            List<AllMarketDto> market = marketService.findMarketByCodePostal(codePostal, email);
            return ResponseEntity.ok().body(market);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récupere tous les trader selon le type de stand")
    @GetMapping("/trader/type/{type}")
    public ResponseEntity getAllTrader(@PathVariable(value = "type") String type) {
        try {
            String email = null;
            if (SecurityContextHolder.getContext() != null) {
                email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            List<AllMarketDto> market = marketService.findMarketByTypeStand(type, email);
            return ResponseEntity.ok().body(market);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récupere tous les trader selon le type et le code postal")
    @GetMapping("/trader/{codePostal}/{type}")
    public ResponseEntity getByCodePostalAndByType(@PathVariable(value = "codePostal") String codePostal, @PathVariable(value = "type") String type) {
        try {
            String email = null;
            if (SecurityContextHolder.getContext() != null) {
                email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            List<AllMarketDto> market = marketService.findMarketByTypeStandAndCodePostal(type, codePostal, email);
            return ResponseEntity.ok().body(market);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récuperer tous les produits par vendeur")
    @GetMapping("/trader/product")
    public ResponseEntity getProductByTrader() {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok().body(marketService.getProductByTrader(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récuperer toutes les commandes par un vendeur")
    @GetMapping("/trader/order")
    public ResponseEntity getOrder() {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok().body(marketService.getOrder(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récuperer toutes les commandes selon le statut")
    @GetMapping("/trader/order/market/{market}")
    public ResponseEntity getOrderByMarket(@PathVariable("market") long market) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok().body(marketService.getOrderByMarketId(email, market));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("récuperer toutes les commande selon la date")
    @GetMapping("/trader/order/date/{date}")
    public ResponseEntity getOrderByDate(@PathVariable("date") String date) {
      try{
          String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          return ResponseEntity.ok().body(marketService.getOrderByDate(email, date));
      } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
    }

    @ApiOperation("récuperer toutes les commande selon la date et idMarket")
    @GetMapping("/trader/order/{date}/{id}")
    public ResponseEntity getOrderByIdAndDate(@PathVariable("date") String date, @PathVariable("id") long id) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok().body(marketService.getOrderByDateAndId(email, date, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation("Valider une commande par un trader")
    @PutMapping("/trader/order/{id}")
    public ResponseEntity validateOrder(@PathVariable long id) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            marketService.validateCommande(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
