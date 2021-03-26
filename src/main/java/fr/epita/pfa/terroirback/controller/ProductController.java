package fr.epita.pfa.terroirback.controller;

import fr.epita.pfa.terroirback.dto.PhotoDto;
import fr.epita.pfa.terroirback.dto.ProductDto;
import fr.epita.pfa.terroirback.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("Api pour la gestion Produit")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("ajout produit par un trader")
    @PostMapping("/product")
    @Secured("VENDEUR")
    public ResponseEntity post(@RequestBody ProductDto productDto) {
        try {
            long id = productService.addProduct(productDto);
            return ResponseEntity.ok(PhotoDto.builder().id(id).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/product")
    @Secured("VENDEUR")
    public ResponseEntity update(@RequestBody ProductDto productDto) {
        try {
            productService.updateProduct(productDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
