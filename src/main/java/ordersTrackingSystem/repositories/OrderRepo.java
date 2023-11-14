package ordersTrackingSystem.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ordersTrackingSystem.entities.Order;

public interface OrderRepo extends JpaRepository<Order, Integer>, CrudRepository<Order, Integer> {
	// 7
	List<Order> findByCustomerId(Integer customerId);
	
	// 8
//	Optional<Order> findByStatus(Character status);
	List<Order> findAllByStatus(Character status);
	
	// 9
	@Query
	("select oi.product.productName as productName, oi.quantity as quantity, oi.totalPrice as price "
			+ "from OrderItem oi")
	List<OrderItemsDTO> getOrderItems();
		
	// 14 
	@Query 
	("select o from Order o where o.orderDate between :startDate and :endDate")
	List<Order> getOrdersBetween(@Param ("startDate") LocalDate startDate, 
								@Param("endDate") LocalDate endDate);
}
