package org.prgms.shoppingbasket.server.shopping.entity;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Product {
	/* 상품 식별 번호 */
	private final UUID id;
	/* 상품명 */
	private String name;
	/* 상품 가격 */
	private int price;
	/* 상품 잔여 수량 */
	private int remainQuantity;
	/* 상품 설명 NULL 가능 */
	private String description;
	/* 상품 생성 시간 */
	private final LocalDateTime createdAt;
	/* 상품 마지막 update 시간 */
	private LocalDateTime updatedAt;

	protected Product(UUID id, String name, int price, int remainQuantity
		, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
		checkNotNull(id, "productId는 null이면 안됩니다.");
		validateFields(name, price, remainQuantity);

		this.id = id;
		this.name = name;
		this.price = price;
		this.remainQuantity = remainQuantity;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	private boolean checkLength(int lower, int upper, String param) {
		return lower < param.length() && param.length() < upper;
	}

	private void validateFields(String name, int price, int remainQuantity){
		checkArgument(name != null && checkLength(0, 20, name),
			"productName은 1자 이상 20자 미만이어야 합니다");
		checkArgument(price > 0, "product의 가격은 음수이면 안됩니다. price = {}", price);
		checkArgument(remainQuantity > 0, "product의 재고는 음수이면 안됩니다. remainQuantity = {}", remainQuantity);
	}

	public static Product create(String name, int price, int remainQuantity, String description) {
		return new Product(UUID.randomUUID(), name, price, remainQuantity, description, LocalDateTime.now(),
			LocalDateTime.now());
	}

	/**
	 * 데이터베이스 Binding 용
	 */
	public static Product bind(UUID id, String name, int price, int remainQuantity
		, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
		return new Product(id, name, price, remainQuantity, description, createdAt, updatedAt);
	}

	public void updateProduct(String name, int price, int remainQuantity) {
		validateFields(name, price, remainQuantity);

		this.name = name;
		this.price = price;
		this.remainQuantity = remainQuantity;
		this.updatedAt = LocalDateTime.now();
	}

}
