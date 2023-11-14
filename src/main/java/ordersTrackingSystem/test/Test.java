package ordersTrackingSystem.test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ordersTrackingSystem.repositories.CustomerRepo;
import ordersTrackingSystem.repositories.OrderItemRepo;
import ordersTrackingSystem.repositories.OrderRepo;
import ordersTrackingSystem.repositories.ProductRepo;

//@Component
public class Test implements CommandLineRunner {
	@Autowired 
	CustomerRepo customerRepo;
	
	@Autowired 
	ProductRepo productRepo;
	
	@Autowired 
	OrderRepo orderRepo;
	
	@Autowired 
	OrderItemRepo orderItemRepo;
	
//	@Transactional(propagation = Propagation.REQUIRED)
//	public void addOrder(LocalDate orderDate, 
//			Integer customerId, Character status, LocalDate deliveredDate, 
//			Integer prodid, Integer quantity, Double price) {
//		Order order = new Order();
//		order.setOrderDate(orderDate);
//		order.setCustomerId(customerId);
//		order.setStatus(status);
//		order.setExpectedDeliveryDate(deliveredDate);
//		
//		orderRepo.save(order);
//		
//		OrderItem orderItem = new OrderItem();
//		orderItem.getOrderItemCK().setOrderId(order.getOrderId());
//		orderItem.getOrderItemCK().setProductId(prodid);
//		orderItem.setQuantity(quantity);
//		orderItem.setTotalPrice(price);
//		
//		orderItemRepo.save(orderItem);
//	}

	@Override
	public void run(String... args) throws Exception {
		// 5
//		for (var v : customerRepo.findAll()) {
//			System.out.println(v);
//		}
		
		// 6
//		for (var v : productRepo.findAll()) {
//			System.out.println(v);
//		}
		
		// 7 
//		for (var v : orderRepo.findByCustomerId(6)) {
//			System.out.println(v);
//		}
		
		// 8 
//		for (var v : orderRepo.findByStatus('n')) {
//			System.out.println(v);
//		}
		
		// 9 
//		for (var v : orderItemRepo.getOrderItemDetails()) {
//			System.out.println(v.getProductName() + " " + v.getQuantity() + " "+ v.getPrice());
//		}
		
//		for(var v : orderRepo.findAll()) {
//			System.out.println(v);
//			for (var w : orderItemRepo.findAll()) {
//				System.out.printf("%-20d %-40s %-40s %-10s %-8d %-8.2f\n", 
//						v.getOrderId(), v.getOrderDate(), v.getExpectedDeliveryDate(), 
//						v.getStatus(), w.getQuantity(), w.getTotalPrice());
//				System.out.println(w);
//			}
//		}
		
		// 1. 1
//		Customer customer = new Customer();
//		customer.setName("Kanye West");
//		customer.setEmail("kanye@gmail.com");
//		customer.setMobile("1111111111");
//		
//		customerRepo.save(customer);
		
		// 1. 2 
//		var optionalCustomer = customerRepo.findById(12);
//		if (optionalCustomer.isPresent()) {
//			var customer = optionalCustomer.get();
//			customer.setName("Ye");
//			customerRepo.save(customer);
//		} else {
//			System.out.println("id not found");
//		}
		
		// 1. 3
//		var optionalCustomer = customerRepo.findById(13);
//		if (optionalCustomer.isPresent()) {
//			customerRepo.deleteById(13);
//		} else {
//			System.out.println("id not found");
//		}
		
		// 2. 1
//		Product product = new Product();
//		product.setProductName("GoPro Camera");
//		product.setProductDescription("camera");
//		product.setPrice(89.25);
//		
//		productRepo.save(product);
		
//		Product product = new Product();
//		product.setProductName("test");
//		product.setProductDescription("test");
//		product.setPrice(0.0);
//		
//		productRepo.save(product);
		
		// 2. 2 
//		var optionalProduct = productRepo.findById(110);
//		if (optionalProduct.isPresent()) {
//			var product = optionalProduct.get();
//			product.setProductDescription("Action camera");
//			productRepo.save(product);
//		} else {
//			System.out.println("id not found");
//		}
		
		// 2. 3
//		var optionalProduct = productRepo.findById(111);
//		if (optionalProduct.isPresent()) {
//			productRepo.deleteById(111);
//		} else {
//			System.out.println("id not found");
//		}
		
		// 4 
//		var optionalOrder = orderRepo.findById(111);
//		if (optionalOrder.isPresent()) {
//			var order = optionalOrder.get();
//			order.setStatus('n');
//		} else {
//			System.out.println("id not found");
//		}
		
		// 10 
//		for (var v : customerRepo.findByNameContaining("Brown")) {
//			System.out.println(v);
//		}
		
		// 12
//		for (var v : orderRepo.findAllByOrderId(202)) {
//			System.out.println(v);
//		}
		
		// 13
//		List<Object[]> results = orderRepo.getProductsOrderedByCustomer(2);
//		for (Object[] row : results) {
//		    Integer custId = (Integer) row[0];
//		    Integer prodId = (Integer) row[1];
//		    String prodName = (String) row[2];
//		    
//		    System.out.println
//		    ("Customer ID: " + custId + ", Product ID: " + prodId + ", Product Name: " + prodName);
//		}
		
		// 14 
//		LocalDate orderDate = LocalDate.of(2023, 10, 20);
//		LocalDate deliveryDate = LocalDate.of(2023, 10, 31);
//		
//		for (var v : orderRepo.findOrdersBetween(orderDate, deliveryDate)) {
//			System.out.println(v);
//		}
		
	}
	
}