package org.prgms.shoppingbasket.server.shopping.entity;

import static com.google.common.base.Preconditions.*;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;

@Getter
public final class OrderItem implements Serializable {
	/* Product 식별번호 */
	private final UUID productId;
	/* Product 가격 */
	private final int price;
	/* Product 수량 */
	private final int quantity;

	private OrderItem(UUID productId, int price, int quantity) {
		checkNotNull(productId, "productId는 null이면 안됩니다!");
		checkArgument(price > 0, "price는 음수이면 안됩니다.");
		checkArgument(quantity > 0, "quantity는 음수이면 안됩니다.");

		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
	}

	public static OrderItem create(UUID productId, int price, int quantity) {
		return new OrderItem(productId, price, quantity);
	}

	/**
	 * 데이터베이스 Binding 용도
	 */
	public static OrderItem bind(UUID id, int price, int quantity) {
		return new OrderItem(id, price, quantity);
	}

}
