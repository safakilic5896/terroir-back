package fr.epita.pfa.terroirback.service;

import fr.epita.pfa.terroirback.dao.*;
import fr.epita.pfa.terroirback.database.*;
import fr.epita.pfa.terroirback.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MarketService {

    @Autowired
    private MarketDao marketDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private RTraderMarketDao rTraderMarketDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OpeningTimeDao openingTimeDao;

    public List<AllMarketDto> findMarketByCodePostal(String codePostal, String email) throws Exception {
        try {
            if (email == null || email.equals("anonymousUser")) {
                return marketDao.findByCodePostal(codePostal).stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
            } else {
                return marketDao.findByCodePostalAndEmail(codePostal, email).stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<AllMarketDto> findAllMarketByEmail(String email) throws Exception {
        try {
            return marketDao.findAllByEmail(email).stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
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

    public List<AllMarketDto> findMarketByTypeStand(String type, String email) throws Exception {
        try {
            if (email == null || email.equals("anonymousUser")) {
                return marketDao.findByTypeStand(type).stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
            } else {
               return marketDao.findByTypeStandAndEmail("%" + type + "%", "%" + type, type + "%", email).stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<AllMarketDto> findMarketByTypeStandAndCodePostal(String type, String codePostal, String email) throws Exception {
        try {
            if (email == null || email.equals("anonymousUser")) {
                return marketDao.findByCodePostalAndType(codePostal, type).stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
            } else {
                return marketDao.findByCodePostalTypeAndEmail(codePostal, "%" + type + "%", "%" + type, type + "%", email).stream().map(this::marketToAllMarketDto).collect(Collectors.toList());
            }
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
            Market market = marketDao.saveAndFlush(Market.builder().adress(marketOnly.getAdress()).city(marketOnly.getCity()).codePostal(marketOnly.getCodePostal()
            ).description(marketOnly.getDescription()).name(marketOnly.getName()).build());
            openingTimeDao.saveAll(marketOnly.getOpeningTimeDto().stream().map(openingTimeDto ->
                    OpeningTime.builder().day(openingTimeDto.getDay()).timeBegin(openingTimeDto.getTimeBegin()).timeEnd(openingTimeDto.getTimeEnd()).market(market).build()).collect(Collectors.toSet()));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public TraderAllProduct getProductByTrader(String email) {
       Optional<Trader> trader = traderDao.findByEmail(email);
       return simpleTraderToTraderAllProduct(trader.get());
    }

    public TraderOrderDto getOrder(String email) {
        Optional<Trader> trader = traderDao.findByEmail(email);
        return traderOrderToDto(trader.get());
    }

    public TraderOrderDto getOrderByMarketId(String email, long market) {
        Optional<Trader> order = traderDao.findByEmailAndIdMarket(email, market);
        return order.map(this::traderOrderToDto).orElse(null);
    }

    public TraderOrderDto getOrderByDate(String email, String date) throws ParseException {
        LocalDate d = LocalDate.parse(date);
        d = d.plusDays(1);
        Optional<Trader> trader = traderDao.findByEmailAndCommandeDate(email, d);
        return trader.map(this::traderOrderToDto).orElse(null);
    }

    public TraderOrderDto getOrderByDateAndId(String email, String date, long id) {
        LocalDate d = LocalDate.parse(date);
        d = d.plusDays(1);
        Optional<Trader> trader = traderDao.findByEmailIdAndDate(email ,id, d);
        return trader.map(this::traderOrderToDto).orElse(null);
    }

    private TraderOrderDto traderOrderToDto(Trader trader) {
        return TraderOrderDto.builder()
                .description(trader.getDescription())
                .email(trader.getEmail())
                .fname(trader.getFname())
                .id(trader.getId())
                .name(trader.getName())
                .phoneNumber(trader.getPhoneNumber())
                .commandeProductDto(commandeProductToDto(trader.getCommande()))
                .build();
    }

    private List<CommandeProductDto> commandeProductToDto(Set<Commande> commande) {
        return commande.stream().map(commandeItem -> CommandeProductDto.builder()
                .dateOrder(commandeItem.getDateOrder())
                .trader(TraderDto.builder().product(null).description(null).email(commandeItem.getCustomer().getEmail())
                        .id(commandeItem.getCustomer().getId()).fname(commandeItem.getCustomer().getFname())
                        .name(commandeItem.getCustomer().getName()).phoneNumber(commandeItem.getCustomer().getPhoneNumber())
                        .build())
                .totalPrice(commandeItem.getTotalPrice())
                .dateValidate(commandeItem.getDateValidate())
                .dateTimeReservation(commandeItem.getDateTimeReservation())
                .id(commandeItem.getId())
                .validate(commandeItem.isValidate())
                .product(commandeItem.getRProductOrder().stream().map(
                        productItem -> ProductOrderDto.builder()
                        .type(productItem.getProduct().getType())
                        .price(productItem.getProduct().getPrice())
                        .photo(productItem.getProduct().getPhoto())
                        .origin(productItem.getProduct().getOrigin())
                        .name(productItem.getProduct().getName())
                        .id(productItem.getProduct().getId())
                        .amount(productItem.getAmont())
                        .market(MarketBindTraderDto.builder()
                                .idMarket(productItem.getProduct().getMarket().getId())
                                .adress(productItem.getProduct().getMarket().getAdress())
                                .city(productItem.getProduct().getMarket().getCity())
                                .codePostal(productItem.getProduct().getMarket().getCity())
                                .description(productItem.getProduct().getMarket().getDescription())
                                .idTrader(productItem.getProduct().getTrader().getId())
                                .name(productItem.getProduct().getMarket().getName())
                                .build()
                ).build()).collect(Collectors.toList())).build()).collect(Collectors.toList());
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

    private TraderAllProduct simpleTraderToTraderAllProduct(Trader trader) {
        return TraderAllProduct.builder()
                .description(trader.getDescription())
                .email(trader.getEmail())
                .id(trader.getId())
                .fname(trader.getFname())
                .name(trader.getName())
                .phoneNumber(trader.getPhoneNumber())
                .product(listProductToDto(trader.getProduct())).build();
    }

    private ProductDto ProductToDto(Product product) {
        return ProductDto.builder()
                .IdProduct(product.getId())
                .photo(product.getPhoto())
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
                        .id(product.getMarket().getId()).name(product.getMarket().getName()).openingTimeDto(product.getMarket().getOpeningTime().stream()
                        .map(openingTime -> OpeningTimeDto.builder().timeEnd(openingTime.getTimeEnd()).timeBegin(openingTime.getTimeBegin()).id(openingTime.getId())
                        .day(openingTime.getDay()).build()).collect(Collectors.toList())).build())
                .build();
    }

    private List<ProductDto> listProductToDto(Set<Product> product) {
        return product.stream().map(productItem -> ProductDto.builder()
                .IdProduct(productItem.getId())
                .description(productItem.getDescription())
                .idMarket(productItem.getMarket().getId())
                .idTrader(productItem.getTrader().getId())
                .origin(productItem.getOrigin())
                .name(productItem.getName())
                .photo(productItem.getPhoto())
                .price(productItem.getPrice())
                .stock(productItem.getStock())
                .type(productItem.getType())
                .market(MarketOnly.builder().adress(productItem.getMarket().getAdress()).city(productItem.getMarket().getAdress())
                        .codePostal(productItem.getMarket().getCodePostal()).description(productItem.getMarket().getDescription())
                        .id(productItem.getMarket().getId()).name(productItem.getMarket().getName()).build())
                .build()).collect(Collectors.toList());
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
                .openingTimeDto(market.getOpeningTime().stream().map(openingTime -> OpeningTimeDto.builder().day(openingTime.getDay()).id(openingTime.getId()).
                        timeBegin(openingTime.getTimeBegin()).timeEnd(openingTime.getTimeEnd()).build()).collect(Collectors.toList()))
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

    public void validateCommande(long id) throws Exception {
        try {
            final SimpleMailMessage mailMessage = new SimpleMailMessage();
            orderDao.updateValidate(id);
            Commande order = orderDao.getOne(id);
            mailMessage.setTo(order.getCustomer().getEmail());
            StringBuilder msg = new StringBuilder("Votre panier du" + " " + order.getDateOrder() +  " à été validé par le vendeur" + " " + order.getTrader().getName() + " " + order.getTrader().getFname() + ":").append("\n");
            msg.append("Pour le marché de " + order.getMarket().getName()).append("\n");
            mailMessage.setTo(order.getCustomer().getEmail());
            mailMessage.setSubject("Votre panier à été validé par le vendeur" + " " + order.getTrader().getName() + " " + order.getTrader().getFname() + ", vous devez récuperer le " + order.getDateOrder().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " entre 9h et 12h");
            mailMessage.setFrom("<MAIL>");
           order.getRProductOrder().forEach(r ->  {
                msg.append("* ").append(r.getProduct().getName()).append(": ").append(r.getPrice()).append("€ * ").append(r.getAmont()).append("kg \n");
            });
            msg.append("TOTAL: ").append(order.getTotalPrice()).append("€ \n");
            msg.append("En cas de probléme, veuillez contacter ce numéro: " + order.getTrader().getPhoneNumber());
            mailMessage.setText(String.valueOf(msg));
            emailService.sendEmail(mailMessage);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
