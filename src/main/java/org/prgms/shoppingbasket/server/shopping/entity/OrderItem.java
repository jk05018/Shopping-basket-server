package org.prgms.shoppingbasket.server.shopping.entity;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;

@Getter
public class OrderItem {
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

	public OrderItem(UUID productId, int price, int quantity, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		checkNotNull(productId, "productId는 null이면 안됩니다!");
		checkArgument(price > 0, "price는 음수이면 안됩니다.");
		checkArgument(quantity > 0, "quantity는 음수이면 안됩니다.");

		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public OrderItem(UUID productId, int price, int quantity) {
		this(productId, price, quantity, LocalDateTime.now(), LocalDateTime.now());
	}
}
