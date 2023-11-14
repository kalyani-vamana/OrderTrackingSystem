package ordersTrackingSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity 
@Table (name = "order_items")
public class OrderItem {
	@EmbeddedId @NotNull 
	private OrderItemCompositeKey orderItemCK;
	
	public OrderItem() {
		this.orderItemCK = new OrderItemCompositeKey();
	}
	
	@Column (name = "qty")
	@PositiveOrZero (message = "qty should be >= 0")
	@NotNull (message = "quantity cannot be null")
	private Integer quantity;
	
	@Column (name = "price") 
	@PositiveOrZero (message = "qty should be >= 0")
	@NotNull (message = "price cannot be null")
	private Double totalPrice;
	
	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "order_id", insertable = false, updatable = false)
	private Order order;
	
	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY) 
	@JoinColumn (name = "prodid", insertable = false, updatable = false) 
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public OrderItemCompositeKey getOrderItemCK() {
		return orderItemCK;
	}

	public void setOrderItemCK(OrderItemCompositeKey orderItemCK) {
		this.orderItemCK = orderItemCK;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override 
	public String toString() {
		return String.format("%-8d %-8d %-5d %-8.2f\n", 
				this.orderItemCK.getOrderId(), this.orderItemCK.getProductId(),
				this.quantity, this.totalPrice);
	}
}