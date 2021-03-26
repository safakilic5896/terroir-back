package fr.epita.pfa.terroirback.service;

import fr.epita.pfa.terroirback.dao.MarketDao;
import fr.epita.pfa.terroirback.dao.ProductDao;
import fr.epita.pfa.terroirback.dao.TraderDao;
import fr.epita.pfa.terroirback.database.Market;
import fr.epita.pfa.terroirback.database.Product;
import fr.epita.pfa.terroirback.database.Trader;
import fr.epita.pfa.terroirback.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private MarketDao marketDao;

    @Transactional(rollbackFor = Exception.class)
    public long addProduct(ProductDto product) throws Exception {
        try {
            Optional<Trader> trader = traderDao.findById(product.getIdTrader());
            Optional<Market> market = marketDao.findById(product.getIdMarket());
            Product product1 = productDao.saveAndFlush(Product.builder().description(product.getDescription())
                    .name(product.getName()).
                            origin(product.getOrigin()).
                            price(product.getPrice()).
                            trader(trader.get()).
                            stock(product.getStock()).
                            market(market.get()).
                            type(product.getType()).
                            build());
            return product1.getId();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(ProductDto product) throws Exception {
        try {
            Optional<Trader> trader = traderDao.findById(product.getIdTrader());
            Optional<Market> market = marketDao.findById(product.getIdMarket());
            productDao.save(Product.builder().description(product.getDescription())
                    .name(product.getName()).
                            origin(product.getOrigin()).
                            price(product.getPrice()).
                            trader(trader.get()).
                            stock(product.getStock()).
                            market(market.get()).
                            type(product.getType()).
                            id(product.getIdProduct()).
                            build());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
