package org.prgms.shoppingbasket.server.shopping.entity;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import lombok.Getter;

@Getter
public class Order {
	/* 주문 식별번호 */
	private final UUID orderId;
	/* 바우처 식별번호 */
	private UUID voucherId;
	/* 고객 이메일 */
	private String email;
	/* 고객 주소 */
	private String address;
	/* 고객 지번 번호 */
	private String postcode;
	/* 주문 상품 리스트 */
	private final List<OrderItem> orderItems;
	/* 주문 생성 시간 */
	private final LocalDateTime createdAt;
	/* 주문 마지만 update 시간 */
	private LocalDateTime updatedAt;

	public Order(UUID orderId, UUID voucherId, String email, String address, String postcode,
		List<OrderItem> orderItems, LocalDateTime createdAt, LocalDateTime updatedAt) {
		checkNotNull(orderId, "orderId는 null이면 안됩니다.");
		checkNotNull(voucherId, "voucherId는 null이면 안됩니다.");
		checkArgument(checkEmail(email), "email은 형식에 맞춰서 기입해야 합니다.");
		checkNotNull(address, "address 는 공백이면 안됩니다");
		checkNotNull(postcode, "postcode 는 공백이면 안됩니다");

		this.orderId = orderId;
		this.voucherId = voucherId;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.orderItems = orderItems;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Order(UUID voucherId, String email, String address, String postcode,
		List<OrderItem> orderItems) {
		this(UUID.randomUUID(), voucherId, email, address, postcode, orderItems, LocalDateTime.now(),
			LocalDateTime.now());
	}

	private boolean checkEmail(String email) {
		return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", email);
	}

	public void updateEmail(String email) {
		this.email = email;
		this.updatedAt = LocalDateTime.now();
	}

	public void updateAddress(String address) {
		this.address = address;
		this.updatedAt = LocalDateTime.now();
	}

	public void supdatePostcode(String postcode) {
		this.postcode = postcode;
		this.updatedAt = LocalDateTime.now();
	}
}
