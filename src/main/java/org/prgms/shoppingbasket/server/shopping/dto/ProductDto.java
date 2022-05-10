package org.prgms.shoppingbasket.server.shopping.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	/* 상품 식별 번호 */
	private UUID id;
	/* 상품명 */
	private String name;
	/* 상품 가격 */
	private int price;
	/* 상품 잔여 수량 */
	private int remainQuantity;
	/* 상품 설명 NULL 가능 */
	private String description;
	/* 상품 생성 시간 */
	private LocalDateTime createdAt;
	/* 상품 마지막 update 시간 */
	private LocalDateTime updatedAt;

	public static ProductDto of(Product product){
		return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getRemainQuantity(),
			product.getDescription(), product.getCreatedAt(), product.getUpdatedAt());
	}
}
