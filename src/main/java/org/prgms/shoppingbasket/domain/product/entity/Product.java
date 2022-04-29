package org.prgms.shoppingbasket.domain.product.entity;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;

@Getter
public class Product {
	/* 상품 식별 번호 */
	private final UUID productId;
	/* 상품명 */
	private String productName;
	/* 상품 가격 */
	private int price;
	/* 상품 잔여 수량 */
	private int remainQuantity;
	/* 상품 설명 */
	private String description;
	/* 상품 생성 시간 */
	private final LocalDateTime createdAt;
	/* 상품 마지막 update 시간 */
	private LocalDateTime updatedAt;

	public Product(UUID productId, String productName, int price, int remainQuantity, String description) {
		checkNotNull(productId, "productId는 null이면 안됩니다.");
		checkArgument( productName != null && checkLength(0, 20, productName),
			"productName은 1자 이상 20자 미만이어야 합니다");
		checkArgument(price > 0, "product의 가격은 음수이면 안됩니다. price = {}", price);
		checkArgument(remainQuantity > 0, "product의 재고는 음수이면 안됩니다. remainQuantity = {}", remainQuantity);
		checkArgument(description != null && checkLength(0, 500, description),
			"description은 1자 이상 500자 미만이어야 합니다.");

		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.remainQuantity = remainQuantity;
		this.description = description;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public Product(String productName, int price, int remainQuantity, String description) {
		this(UUID.randomUUID(), productName, price, remainQuantity, description);
	}

	private boolean checkLength(int lower, int upper, String param) {
		return lower < param.length() && param.length() < upper;
	}

	public void updateProductName(String productName) {
		this.productName = productName;
		this.updatedAt = LocalDateTime.now();
	}

	public void updatePrice(int price) {
		this.price = price;
		this.updatedAt = LocalDateTime.now();
	}

	public void updateRemainQuantity(int remainQuantity) {
		this.remainQuantity = remainQuantity;
		this.updatedAt = LocalDateTime.now();
	}

	public void updateDescription(String description) {
		this.description = description;
		this.updatedAt = LocalDateTime.now();
	}
}
