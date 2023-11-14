package ordersTrackingSystem.rest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import ordersTrackingSystem.entities.Customer;
import ordersTrackingSystem.entities.Order;
import ordersTrackingSystem.entities.OrderItem;
import ordersTrackingSystem.entities.OrderItemCompositeKey;
import ordersTrackingSystem.entities.Product;
import ordersTrackingSystem.repositories.AllProductDetailsDTO;
import ordersTrackingSystem.repositories.CustomerRepo;
import ordersTrackingSystem.repositories.OrderDTO;
import ordersTrackingSystem.repositories.OrderItemDTO;
import ordersTrackingSystem.repositories.OrderItemDetailsDTO;
import ordersTrackingSystem.repositories.OrderItemRepo;
import ordersTrackingSystem.repositories.OrderItemsDTO;
import ordersTrackingSystem.repositories.OrderRepo;
import ordersTrackingSystem.repositories.ProductRepo;
import ordersTrackingSystem.repositories.ProductsOrderedByCustomerDTO;

@RestController
public class OrdersTrackingSystemController {
	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	ProductRepo productRepo;

	@Autowired
	OrderRepo orderRepo;

	@Autowired
	OrderItemRepo orderItemRepo;

	// 1. 1
	@PreAuthorize("hasRole('ADMIN')") // method level security configuration
	@Operation(summary = "add customer", 
			description = "adds a new customer by taking a request body")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "customer added"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@PostMapping("/customers/add")
	public Customer addCustomer(@Valid @RequestBody Customer customer) {
		try {
			return customerRepo.save(customer);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 1. 2
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "update customer", 
			description = "updates name of the custpmer for the given customer id")
	@Parameter(description = "enter customer id")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "customer details updated"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@PutMapping("/customers/update/{customerId}")
	public Customer updateCustomer(@Valid @PathVariable("customerId") Integer customerId,
			@RequestParam("name") String name) {
		try {
			var optionalCustomer = customerRepo.findById(customerId);
			if (optionalCustomer.isPresent()) {
				var customer = optionalCustomer.get();
				customer.setName(name);
				return customerRepo.save(customer);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer id not found");
			}
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 1. 3
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "delete a customer", 
			description = "deletes a customer with the given customer id")
	@Parameter(description = "enter")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "customer deleted"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@DeleteMapping("/customers/delete/{customerId}")
	public void deleteCustomer(@PathVariable("customerId") Integer customerId) {
		Optional<Customer> optionalCustomer = customerRepo.findById(customerId);
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			customerRepo.delete(customer);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer id not found");
		}
	}

	// 2. 1
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "add product", description = "adds a product by taking a request body")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "product added"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@PostMapping("/products/add")
	public Product addProduct(@Valid @RequestBody Product product) {
		try {
			return productRepo.save(product);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 2. 2
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "update product", description = "updates product for the given product id")
	@Parameter(description = "enter product id")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "product details updated"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@PutMapping("/products/update/{productId}")
	public Product updateProduct(@Valid @PathVariable("productId") Integer productId,
			@RequestParam("name") String name) {
		try {
			var optionalProduct = productRepo.findById(productId);
			if (optionalProduct.isPresent()) {
				var product = optionalProduct.get();
				product.setProductName(name);
				productRepo.save(product);
				return product;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product id not found");
			}
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 2. 3
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "delete a product")
	@Parameter(description = "enter product id")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "product deleted"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@DeleteMapping("/products/delete/{productId}")
	public void deleteProduct(@PathVariable("productId") Integer productId) {
		Optional<Product> optionalProduct = productRepo.findById(productId);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			productRepo.delete(product);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product id not found");
		}
	}

	// 3. 1
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "add order", description = "adds an order and updates order item table by taking request bodies")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "order added"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@PostMapping("/orders/add")
	public void addOrder(@Valid @RequestBody OrderDTO orderDTO) {
		try {
			Order order = new Order();
			order.setOrderDate(orderDTO.getOrderDate());
			order.setCustomerId(orderDTO.getCustomerId());
			order.setStatus(orderDTO.getStatus());
			order.setExpectedDeliveryDate(orderDTO.getExpectedDeliveryDate());

			Order savedOrder = orderRepo.save(order);

			for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
				OrderItem orderItem = new OrderItem();
				OrderItemCompositeKey orderItemCK = new OrderItemCompositeKey();

				orderItemCK.setOrderId(savedOrder.getOrderId());
				orderItemCK.setProductId(orderItemDTO.getProductId());

				orderItem.setOrderItemCK(orderItemCK);

				orderItem.setQuantity(orderItemDTO.getQuantity());
				orderItem.setTotalPrice(orderItemDTO.getTotalPrice());

				/* @ManyToOne */
				orderItem.setOrder(savedOrder);

				/* @OneToMany */
				savedOrder.getOrderItems().add(orderItem);
			}
			orderRepo.save(savedOrder);
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 3. 2
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "delete order", description = "deletes order and updates order item table")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "order deleted"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@DeleteMapping("/orders/delete/{orderId}")
	public void deleteOrder(@PathVariable("orderId") Integer orderId) {
		Optional<Order> optionalOrder = orderRepo.findById(orderId);
		if (optionalOrder.isPresent()) {
			Order order = optionalOrder.get();
			orderRepo.delete(order);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order id not found");
		}
	}

	@GetMapping("/orders")
	public List<Order> getOrders() {
		return orderRepo.findAll();
	}

	// 4
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "update status of an order", description = "updates status of an order")
	@Parameter(description = "enter order id and give status parameter")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "order status updated"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@PutMapping("/orders/update-status/{orderId}")
	public Order updateOrderStatus(@PathVariable("orderId") Integer orderId, @RequestParam("status") Character status) {
		try {
			var optionalOrder = orderRepo.findById(orderId);
			if (optionalOrder.isPresent()) {
				var order = optionalOrder.get();
				if (order.getStatus() == 'd') {
					throw new 
					ResponseStatusException(HttpStatus.ALREADY_REPORTED, "order already delivered");
				}
				if (order.getStatus() == 'c') {
					throw new 
					ResponseStatusException(HttpStatus.LOCKED, "cannot update cancelled orders");
				}
				if (status == 'c') {
					order.setStatus(status);
					order.setExpectedDeliveryDate(null);
					orderRepo.save(order);
				} else {
					order.setStatus(status);
					orderRepo.save(order);
				}
				return order;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order id not found");
			}
		} catch (DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	// 5
	@CrossOrigin
	@Operation(summary = "list customers")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "customers retrieved"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return customerRepo.findAll();
	}

	/* 5. pagination */
	@CrossOrigin
	@Operation(summary = "list customers by page number", description = "list customers by page number")
	@Parameter(description = "enter page number")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "retrieved customer details"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/customers/{pageno}")
	public Page<Customer> getCustomerFromPage(@PathVariable("pageno") Integer pageno) {
		return customerRepo.findAll(PageRequest.of(pageno, 3));
	}

	// 6
	@CrossOrigin
	@Operation(summary = "list products")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "products retrieved"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/products")
	public List<Product> getProducts() {
		return productRepo.findAll();
	}

	/* 6. pagination */
	@CrossOrigin
	@Operation(summary = "list products by page number")
	@Parameter(description = "enter page number")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "products retrieved"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/products/{pageno}")
	public Page<Product> getProductOfPage(@PathVariable("pageno") Integer pageno) {
		Page<Product> page = productRepo.findAll(PageRequest.of(pageno, 3));
		return page;
	}

	// 7
	@Operation(summary = "get order", description = "gets order(s) by customer")
	@Parameter(description = "enter customer id")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "retrieved orders by customer"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/orders-by-customer/{customerId}")
	public List<Order> getOrdersByCustomerId(@PathVariable("customerId") Integer customerId) {
		return orderRepo.findByCustomerId(customerId);
	}

	// 8
	@CrossOrigin
	@Operation(summary = "orders by status", description = "gets order(s) by status")
	@Parameter(description = "enter status")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "retrieved orders by status"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/orders-by-status/{status}")
	public List<Order> getOrdersByStatus(@PathVariable("status") Character status) {
		return orderRepo.findAllByStatus(status);
	}

	// 9
	@CrossOrigin
	@Operation(summary = "gets orders in the specified order")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "retrieved orders in specified order"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/orders/inorder")
	public List<OrderItemsDTO> getOrdersInOrder() {
		return orderRepo.getOrderItems();
	}

	// 10
	@CrossOrigin
	@Operation(summary = "gets products that contains a string", description = "gets products containing the given string")
	@Parameter(description = "enter string")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "retrieved products containing a string"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/products-with-string/{string}")
	public List<Product> getProductsWithGivenString(@Valid @PathVariable("string") String string) {
		return productRepo.findByProductNameContaining(string);
	}

	// 11
	@CrossOrigin
	@Operation(summary = "gets product details", 
			description = "retrieves product(s) with a given product id")
	@Parameter(description = "enter product id")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "retrieved all product details"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/products/product-details/{productId}")
	public Object getProductDetailsRest
						(@PathVariable("productId") Integer productId) {
		try {
			var list = getProductDetails(productId);
			return list;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	public List<AllProductDetailsDTO> getProductDetails (Integer productId) {
		var optionalProduct = productRepo.findById(productId);
		if (optionalProduct.isPresent()) {
			return orderItemRepo.getAllProductSaleDetails(productId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product id not found");
		}
	}
	
//	public List<AllProductDetailsDTO> getProductDetails
//						(@Valid @PathVariable("productId") Integer productId) {
//		var optionalProduct = productRepo.findById(productId);
//		if (optionalProduct.isPresent()) {
//			return orderItemRepo.getAllProductSaleDetails(productId);
//		} else {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product id not found");
//		}
//	}

	// 12
	@Operation(summary = "all order details", 
			description = "retrieves all the order details for the given order id")
	@Parameter(description = "enter order id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "retrieved all order details"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("orders/all-order-details/{orderId}")
	public List<OrderItemDetailsDTO> getAllOrderDetails(@Valid @PathVariable("orderId") Integer orderId) {
		return orderItemRepo.findByOrderId(orderId);
	}

	// 13
	@Operation(summary = "products ordered by customer", 
			description = "retrieves all the products ordered by a customer")
	@Parameter(description = "enter customer id")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "retrived products ordered by a customer"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/products/products-ordered-by-customer/{customerId}")
	public List<ProductsOrderedByCustomerDTO> getProductsOrderedByCustomer(
			@Valid @PathVariable("customerId") Integer customerId) {
		return orderItemRepo.findAllProductsByCustomerId(customerId);
	}

	// 14
	@Operation(summary = "orders between a specified period", 
			description = "retrieves all the orders between a specific period")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", 
					description = "retrieved orders between a specified period"),
			@ApiResponse(responseCode = "404", description = "order(s) not found"),
			@ApiResponse(responseCode = "400", description = "bad request"),
			@ApiResponse(responseCode = "500", description = "internal server error") })
	@GetMapping("/orders/between-dates")
	public List<Order> getOrdersBetweenDates(@Valid @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		LocalDate parseStartDate = LocalDate.parse(startDate);
		LocalDate parseEndDate = LocalDate.parse(endDate);

		return orderRepo.getOrdersBetween(parseStartDate, parseEndDate);
	}
	/*
	 * @GetMapping("/customers") public List<Customer> getCustomers() { return
	 * customerRepo.findAll(); }
	 * 
	 * @GetMapping("/products") public List<Product> getProducts() { return
	 * productRepo.findAll(); }
	 * 
	 * @GetMapping("/orders") public List<Order> getOrders() { return
	 * orderRepo.findAll(); }
	 * 
	 * @GetMapping("/order-items") public List<OrderItem> getOrderItems() { return
	 * orderItemRepo.findAll(); }
	 */
}