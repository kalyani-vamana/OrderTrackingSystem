package ordersTrackingSystem.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity 
@Table (name = "customer_orders")
public class Order {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY) 
	@Column (name = "order_id")
	private Integer orderId;
	
	@Column (name = "order_date")
	private LocalDate orderDate;
	
	@Column (name = "cust_id")
	private Integer customerId;
	
	@Column (name = "status")
	private Character status;
	
	@Column (name = "expected_delivery_date")
	@Future
	private LocalDate expectedDeliveryDate;
	
	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY) 
	@JoinColumn (name = "cust_id", insertable = false, updatable = false) 
	private Customer customer;
	
	@JsonIgnore
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public LocalDate getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	
	@Override 
	public String toString() {
		return String.format("%-8d %-20s %-8d %-5s %-20s\n",
				this.orderId, this.orderDate, this.customerId, this.status, this.expectedDeliveryDate);
	}
}