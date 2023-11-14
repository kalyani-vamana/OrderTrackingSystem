package ordersTrackingSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ordersTrackingSystem.entities.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {
	// 11
	@Query 
	("select oi.product.productName as productName, oi.order.customer.name as customerName, "
			+ "oi.order.orderDate as orderDate, oi.quantity as quantity, oi.totalPrice as price"
			+ " from OrderItem oi where oi.orderItemCK.productId = :productId")
	List<AllProductDetailsDTO> getAllProductSaleDetails (@Param("productId") Integer productId);
	
	// 12 
	@Query 
	("select oi.orderItemCK.orderId as orderId, oi.orderItemCK.productId as productId, oi.order.customerId "
		+ "as customerId, oi.order.orderDate as orderDate, oi.order.expectedDeliveryDate "
		+ "as deliveryDate, oi.order.status as status, oi.quantity as quantity, oi.totalPrice as price "
		+ "from OrderItem oi where oi.orderItemCK.orderId = :orderId")
	List<OrderItemDetailsDTO> findByOrderId (@Param("orderId") Integer orderId);
	
	// 13 
	@Query("select oi.order.customer.id as customerId, " +
	           "oi.order.customer.name as customerName, " +
	           "oi.order.orderId as orderId, " +
	           "oi.product.productId as productId, " +
	           "oi.product.productName as productName, " +
	           "oi.totalPrice as totalPrice " +
	           "from OrderItem oi " +
	           "where oi.order.customer.id = :customerId")
	List<ProductsOrderedByCustomerDTO> findAllProductsByCustomerId (@Param("customerId") Integer customerId);
}