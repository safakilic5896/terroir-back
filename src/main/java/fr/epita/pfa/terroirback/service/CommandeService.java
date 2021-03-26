package fr.epita.pfa.terroirback.service;

import fr.epita.pfa.terroirback.dao.*;
import fr.epita.pfa.terroirback.database.*;
import fr.epita.pfa.terroirback.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommandeService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RProductOrderDao rProductOrderDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private MarketDao marketDao;

    @Autowired
    private EmailService emailService;

    @Transactional(rollbackFor = Exception.class)
    public void passAnOrder(List<CommandeDto> commandeDto, String customerEmail) throws Exception {
        try {
            Map<Long,Map<Long, List<CommandeDto>>> groupsByIdStand = commandeDto.stream().collect(
                    Collectors.groupingBy(CommandeDto::getIdMarket,
                    Collectors.groupingBy(CommandeDto::getIdTrader)
            ));
            Optional<Customer> customer = customerDao.findByEmail(customerEmail);
           groupsByIdStand.values().forEach(market -> {
               market.values().forEach(subGroups -> {
                   try {
                       processToSaveAnOrder(subGroups, customer.get());
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   sendOrderMail(subGroups, customer.get());
               });
           });
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void sendOrderMail(List<CommandeDto> commande, Customer customer) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        float totalPrice = 0F;
        Optional<Trader> trader = traderDao.findById(commande.get(0).getIdTrader());
        StringBuilder msg = new StringBuilder("Vous avez un panier à constituer pour" + " " + customer.getName() + " " + customer.getFname() + ":").append("\n");
        msg.append("Pour le marché: " + productDao.getOne(commande.get(0).getIdProduct()).getMarket().getName()).append("\n");
        mailMessage.setTo(trader.get().getEmail());
        mailMessage.setSubject("Vous avez un nouveau panier à constituer pour " + customer.getName() + " " + customer.getFname() + " pour la date " + commande.get(0).getDateOrder().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        mailMessage.setFrom("<MAIL>");
        for (CommandeDto commandeDto : commande) {
          Product product = productDao.getOne(commandeDto.getIdProduct());
            totalPrice += commandeDto.getPrice() * commandeDto.getAmount();
            msg.append("* ").append(product.getName()).append(": ").append(commandeDto.getPrice()).append("€ * ").append(commandeDto.getAmount()).append("kg \n");
        };
        msg.append("TOTAL: ").append(totalPrice).append("€ \n");
        msg.append("En cas de probléme, veuillez contacter ce numéro: " + customer.getPhoneNumber());
        mailMessage.setText(String.valueOf(msg));
        emailService.sendEmail(mailMessage);
    }

    public CustomerOrderDto getOrder(String email) throws Exception {
        try {
            Optional<Customer> customer = customerDao.findByEmail(email);
            return customerToDto(customer.get());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Build Commande Object with same stand id with CommandeDto List
     * @param commandeDto²
     */
    @Transactional(rollbackFor = Exception.class)
    void processToSaveAnOrder(List<CommandeDto> commandeDto, Customer customer) throws Exception {
        try {
            float totalPrice = 0F;
            Set<RProductOrder> productSet = new HashSet<>();
            Optional<Trader> trader = traderDao.findById(commandeDto.get(0).getIdTrader());
            LocalDate dateOrder = commandeDto.get(0).getDateOrder().plusDays(1);
            LocalDateTime dateReservation = commandeDto.get(0).getDateReservation();
            Market market = marketDao.getOne(commandeDto.get(0).getIdMarket());
            Commande commandeToSave = Commande.builder().totalPrice(totalPrice)
                    .dateOrder(dateOrder).dateTimeReservation(dateReservation).trader(trader.get())
                    .customer(customer).validate(false).dateValidate(null).market(market).build();
            for (CommandeDto commande : commandeDto) {
                Optional<Product> product = productDao.findById(commande.getIdProduct());
                totalPrice += commande.getPrice();
                productSet.add(RProductOrder.builder().price(commande.getPrice())
                        .amont(commande.getAmount()).product(product.get()).build());
            }
            commandeToSave.setTotalPrice(totalPrice);
            commandeToSave = orderDao.saveAndFlush(commandeToSave);
            for (RProductOrder rProductOrder : productSet) {
                rProductOrder.setCommande(commandeToSave);
                rProductOrderDao.save(rProductOrder);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private CustomerOrderDto customerToDto(Customer customer) {
        return CustomerOrderDto.builder().id(customer.getId())
                .city(customer.getCity())
                .codePostal(customer.getCodePostal())
                .email(customer.getEmail())
                .fname(customer.getFname())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .commandeProductDto(commandeProductToDto(customer.getCommande())).build();
    }

    private List<CommandeProductDto> commandeProductToDto(Set<Commande> commande) {
        return commande.stream().map(commandeItem ->
            CommandeProductDto.builder().id(commandeItem.getId())
                    .dateOrder(commandeItem.getDateOrder())
                    .dateTimeReservation(commandeItem.getDateTimeReservation())
                    .dateValidate(commandeItem.getDateValidate())
                    .totalPrice(commandeItem.getTotalPrice())
                    .trader(traderToDto(commandeItem.getTrader()))
                    .product(productOrderToDto(commandeItem.getRProductOrder(), commandeItem.getTrader().getId())).build()
        ).collect(Collectors.toList());
    }

    private TraderDto traderToDto(Trader trader) {
       return TraderDto.builder().phoneNumber(trader.getPhoneNumber())
               .name(trader.getName())
               .fname(trader.getFname())
               .id(trader.getId())
               .email(trader.getEmail())
               .description(trader.getDescription())
               .product(null)
               .build();
    }

    private List<ProductOrderDto> productOrderToDto(Set<RProductOrder> rProductOrder, long idTrader) {
        return rProductOrder.stream().map(productItem -> ProductOrderDto.builder().amount(productItem.getAmont())
                .id(productItem.getProduct().getId())
                .name(productItem.getProduct().getName())
                .origin(productItem.getProduct().getOrigin())
                .photo(productItem.getProduct().getPhoto())
                .price(productItem.getProduct().getPrice())
                .type(productItem.getProduct().getType())
                .market(marketToDto(productItem.getProduct().getMarket(), idTrader))
                .build()).collect(Collectors.toList());
    }

    private MarketBindTraderDto marketToDto(Market market, long idTrader) {
        return MarketBindTraderDto.builder()
                .name(market.getName())
                .idMarket(market.getId())
                .description(market.getDescription())
                .codePostal(market.getCodePostal())
                .city(market.getCity())
                .adress(market.getAdress())
                .idTrader(idTrader)
                .build();
    }
}
