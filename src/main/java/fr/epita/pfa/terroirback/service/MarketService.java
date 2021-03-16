package fr.epita.pfa.terroirback.service;

import fr.epita.pfa.terroirback.dao.MarketDao;
import fr.epita.pfa.terroirback.dao.RTraderMarketDao;
import fr.epita.pfa.terroirback.dao.TraderDao;
import fr.epita.pfa.terroirback.database.*;
import fr.epita.pfa.terroirback.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class MarketService {

    @Autowired
    private MarketDao marketDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private RTraderMarketDao rTraderMarketDao;

    public List<AllMarketDto> findMarketByCodePostal(String codePostal) throws Exception {
        try {
            return marketDao.findByCodePostal(codePostal).stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<AllMarketDto> findAllMarket() throws Exception {
        try {
            return marketDao.findAll().stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<MarketOnly> findOnlyAllMarket() throws Exception {
        try {
            return marketDao.findAll().stream().map(this::MarketToMarketOnly).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<AllMarketDto> findMarketByTypeStand(String type) throws Exception {
        try {
            return marketDao.findByTypeStand(type).stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<AllMarketDto> findMarketByTypeStandAndCodePostal(String type, String codePostal) throws Exception {
        try {
            return marketDao.findByCodePostalAndType(codePostal, type).stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public void postMarketForTrader(TraderMarketDto traderMarkerDto, String email) throws Exception {
        try {
            Optional<Trader> trader = traderDao.findByEmail(email);
            Optional<Market> market = marketDao.findById(traderMarkerDto.getIdMarket());
            rTraderMarketDao.save(RTraderMarket.builder().trader(trader.get()).market(market.get()).build());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<MarketBindTraderDto> getMarketForTrader(String email) throws Exception {
        try {
            Optional<Trader> trader = traderDao.findByEmail(email);
            Long idTrader = trader.get().getId();
            return trader.get().getRTraderMarket().stream().map(rTraderMarket -> TraderToMarketBinDto(rTraderMarket.getMarket(), idTrader)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void addMarket(MarketOnly marketOnly) throws Exception {
        try {
            marketDao.save(Market.builder().adress(marketOnly.getAdress()).city(marketOnly.getCity()).codePostal(marketOnly.getCodePostal()
            ).description(marketOnly.getDescription()).name(marketOnly.getName()).build());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private AllMarketDto marketToAllMarketDto(Market market) {
        return AllMarketDto.builder().adress(market.getAdress())
                .city(market.getCity())
                .codePostal(market.getCodePostal())
                .description(market.getDescription())
                .id(market.getId())
                .name(market.getName())
                .openingTimeDto(OpeningTimeToDto(market.getOpeningTime()))
                .traderDto(TraderToDto(market.getProduct())).build();
    }

    private List<OpeningTimeDto> OpeningTimeToDto(Set<OpeningTime> openingTime) {
       return openingTime.stream().map(openingTimeItem -> OpeningTimeDto.builder().day(openingTimeItem.getDay())
              .id(openingTimeItem.getId())
              .timeBegin(openingTimeItem.getTimeBegin())
              .timeEnd(openingTimeItem.getTimeEnd()).build()).collect(Collectors.toList());
    }

    private List<TraderDto> TraderToDto(Set<Product> product) {
        return product.stream().map(productItem-> TraderDto.builder()
                .description(productItem.getTrader().getDescription())
                .email(productItem.getTrader().getEmail())
                .id(productItem.getTrader().getId())
                .fname(productItem.getTrader().getFname())
                .name(productItem.getTrader().getName())
                .phoneNumber(productItem.getTrader().getPhoneNumber())
                .product(ProductToDto(productItem)).build()).collect(Collectors.toList());
    }

    private ProductDto ProductToDto(Product product) {
        return ProductDto.builder()
                .IdProduct(product.getId())
                .description(product.getDescription())
                .idMarket(product.getMarket().getId())
                .idTrader(product.getTrader().getId())
                .origin(product.getOrigin())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .type(product.getType())
                .market(MarketOnly.builder().adress(product.getMarket().getAdress()).city(product.getMarket().getAdress())
                    .codePostal(product.getMarket().getCodePostal()).description(product.getMarket().getDescription())
                        .id(product.getMarket().getId()).name(product.getMarket().getName()).build())
                .build();
    }

    private MarketBindTraderDto TraderToMarketBinDto(Market market, Long idTrader) {
        return MarketBindTraderDto.builder()
                .adress(market.getAdress())
                .city(market.getCity())
                .codePostal(market.getCodePostal())
                .description(market.getDescription())
                .idMarket(market.getId())
                .name(market.getName())
                .idTrader(idTrader)
                .build();
    }

    private MarketOnly MarketToMarketOnly(Market market) {
        return MarketOnly.builder().id(market.getId())
                .adress(market.getAdress())
                .city(market.getCity())
                .codePostal(market.getCodePostal())
                .name(market.getName())
                .build();
    }
}
