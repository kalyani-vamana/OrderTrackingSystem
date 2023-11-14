package ordersTrackingSystem.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItemCompositeKey implements Serializable {
	public OrderItemCompositeKey () {}	
	
	@Column (name = "order_id")
	private Integer orderId;
	
	@Column (name = "prodid")
	private Integer productId;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		return this.productId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItemCompositeKey other = (OrderItemCompositeKey) obj;
		return Objects.equals(orderId, other.orderId) && Objects.equals(productId, other.productId);
	}
}