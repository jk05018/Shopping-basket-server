package org.prgms.shoppingbasket.domain.order.entity;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;

@Getter
public class OrderItem {
	/* Order 식별번호 */
	private final UUID orderId;
	/* Product 식별번호 */
	private final UUID productId;
	/* Product 가격 */
	private final int price;
	/* Product 수량 */
	private final int quantity;
	/* Product 생성 시간 */
	private final LocalDateTime createdAt;
	/* Product 마지막 update 시간 */
	private LocalDateTime updatedAt;

	public OrderItem(UUID orderId, UUID productId, int price, int quantity) {
		checkNotNull(orderId, "orderId는 null이면 안됩니다!");
		checkNotNull(productId, "productId는 null이면 안됩니다!");
		checkArgument(price > 0, "price는 음수이면 안됩니다.");
		checkArgument(quantity > 0, "quantity는 음수이면 안됩니다.");

		this.orderId = orderId;
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
}
