package com.demo.PoS.repo;

import com.demo.PoS.model.entity.Customer;
import com.demo.PoS.model.entity.Employee;
import com.demo.PoS.model.entity.Order;
import com.demo.PoS.model.entity.Product;
import com.demo.PoS.model.enums.OrderStatus;
import com.demo.PoS.repository.CustomerRepository;
import com.demo.PoS.repository.EmployeeRepository;
import com.demo.PoS.repository.OrderRepository;
import com.demo.PoS.repository.ProductRepository;
import com.demo.PoS.repository.relations.OrderProductRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PopulateDatabase {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private final Faker faker = new Faker();

    @AfterEach
    void flushDb() {
        System.out.println("Flushing records to database");
    }

    @Test
    @org.junit.jupiter.api.Order(0)
    void cleanDb() {
        orderProductRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        employeeRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @org.junit.jupiter.api.Order(10)
    void populateProducts() {
        int ITEM_COUNT = 2;
        for (int i = 0; i < ITEM_COUNT; i++) {
            Product item = new Product();
            item.setName(faker.food().dish().toLowerCase());
            item.setDescription(faker.lorem().characters(10, 250));
            item.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)));
            item.setStock(faker.number().numberBetween(1, 1000));
            productRepository.save(item);
        }
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    void populateUsers() {
        Employee e = Employee.builder()
                .name(faker.name().firstName())
                .surname(faker.name().lastName())
                .build();
        Customer c = Customer.builder()
                .name(faker.name().firstName())
                .surname(faker.name().lastName())
                .build();
        employeeRepository.save(e);
        customerRepository.save(c);
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    void populateOrders() {
        Order order = Order.builder()
                .customer(customerRepository.findAll().getFirst())
                .employee(employeeRepository.findAll().getFirst())
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
                    order.addProduct(product, faker.number().numberBetween(1, 10))
            );
        }
        orderRepository.save(order);
    }
}
