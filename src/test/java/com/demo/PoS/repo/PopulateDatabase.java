package com.demo.PoS.repo;

import com.demo.PoS.model.entity.*;
import com.demo.PoS.model.enums.DiscountStatus;
import com.demo.PoS.model.enums.OrderStatus;
import com.demo.PoS.model.enums.ServiceSlotStatus;
import com.demo.PoS.repository.*;
import com.demo.PoS.repository.relations.OrderProductRepository;
import com.demo.PoS.service.OrderService;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PopulateDatabase {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ProvidedServiceRepository providedServiceRepository;
    @Autowired
    private ServiceSlotRepository serviceSlotRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    private final Faker faker = new Faker();

    @AfterEach
    void flushDb() {
        System.out.println("Flushing records to database");
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void cleanDb() {
        reservationRepository.deleteAll();
        receiptRepository.deleteAll();
        paymentRepository.deleteAll();
        orderProductRepository.deleteAll();
        reservationRepository.deleteAll();
        serviceSlotRepository.deleteAll();
        providedServiceRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        discountRepository.deleteAll();
        employeeRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void populateDiscounts() {
        double minRate = 0.1;
        double maxRate = 0.9;
        double discountRateValue = faker.number().randomDouble(2, (long) (minRate * 100), (long) (maxRate * 100)) / 100;
        Discount discount = Discount.builder()
                .name(faker.lorem().characters(10))
                .discountRate(BigDecimal.valueOf(discountRateValue))
                .validFrom(LocalDateTime.now())
                .validUntil(LocalDateTime.now().plusDays(30))
                .discountStatus(DiscountStatus.ACTIVE)
                .build();

        discountRepository.save(discount);
    }
    @Test
    @org.junit.jupiter.api.Order(10)
    void populateProducts() {
        int ITEM_COUNT = 4;
        for (int i = 0; i < ITEM_COUNT; i++) {
            Product item = new Product();
            item.setName(faker.food().dish().toLowerCase());
            item.setDescription(faker.lorem().characters(10, 250));
            item.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)));
            item.setStock(faker.number().numberBetween(1, 1000));
            if (i % 2 == 0) {
                item.setDiscount(discountRepository.findAll().getFirst());
            }
            productRepository.save(item);
        }
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    void populateUsers() {
        Employee e1 = Employee.builder()
                .name(faker.name().firstName())
                .surname(faker.name().lastName())
                .build();
        employeeRepository.save(e1);

        Employee e2 = Employee.builder()
                .name(faker.name().firstName())
                .surname(faker.name().lastName())
                .build();
        employeeRepository.save(e2);

        Customer c1 = Customer.builder()
                .name(faker.name().firstName())
                .surname(faker.name().lastName())
                .build();
        customerRepository.save(c1);

        Customer c2 = Customer.builder()
                .name(faker.name().firstName())
                .surname(faker.name().lastName())
                .build();
        customerRepository.save(c2);
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    void populateOrders() {
        Order order = Order.builder()
                .customer(customerRepository.findAll().getFirst())
                .orderStatus(OrderStatus.CREATED)
                .build();
        orderRepository.save(order);
    }

    @Test
    @org.junit.jupiter.api.Order(40)
    void addAllItemsToOrder() {
        Order order = orderRepository.findAll().getFirst();
        for (Product product : productRepository.findAll()) {
            orderProductRepository.save(
                    orderService.addProduct(order, product, faker.number().numberBetween(1, 10))
            );
        }
        orderRepository.save(order);
    }

    @Test
    @org.junit.jupiter.api.Order(50)
    void populateServices() {
        ProvidedService service = ProvidedService.builder()
                .name(faker.verb().ingForm())
                .description(faker.lorem().maxLengthSentence(255))
                .price(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 1000)))
                .build();
        providedServiceRepository.save(service);
    }

    @Test
    @org.junit.jupiter.api.Order(60)
    void populateSlots() {
        ServiceSlot slot1 = ServiceSlot.builder()
                .serviceSlotStatus(ServiceSlotStatus.FREE)
                .startTime(LocalDateTime.now().withDayOfMonth(20).withHour(14).truncatedTo(ChronoUnit.HOURS))
                .endTime(LocalDateTime.now().withDayOfMonth(20).withHour(16).truncatedTo(ChronoUnit.HOURS))
                .providedService(providedServiceRepository.findAll().getFirst())
                .employee(employeeRepository.findAll().getFirst())
                .build();

        ServiceSlot slot2 = ServiceSlot.builder()
                .serviceSlotStatus(ServiceSlotStatus.FREE)
                .startTime(LocalDateTime.now().withDayOfMonth(19).withHour(14).truncatedTo(ChronoUnit.HOURS))
                .endTime(LocalDateTime.now().withDayOfMonth(19).withHour(16).truncatedTo(ChronoUnit.HOURS))
                .providedService(providedServiceRepository.findAll().getFirst())
                .employee(employeeRepository.findAll().getFirst())
                .build();

        serviceSlotRepository.save(slot1);
        serviceSlotRepository.save(slot2);
    }
    @Test
    @org.junit.jupiter.api.Order(70)
    void addReservationsToOrder() {
        Order order = orderRepository.findAll().getFirst();
        ServiceSlot slot = serviceSlotRepository.findAll().getFirst();
        Reservation reservation = Reservation.builder()
                .order(order)
                .serviceSlot(slot)
                .build();
        slot.setServiceSlotStatus(ServiceSlotStatus.RESERVED);
        serviceSlotRepository.save(slot);
        reservationRepository.save(reservation);
    }
}
