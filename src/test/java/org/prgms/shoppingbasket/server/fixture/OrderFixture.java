package org.prgms.shoppingbasket.server.fixture;

import java.util.ArrayList;
import java.util.List;

import org.prgms.shoppingbasket.server.shopping.entity.Order;
import org.prgms.shoppingbasket.server.shopping.entity.OrderItem;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.entity.Voucher;
import org.prgms.shoppingbasket.server.shopping.service.OrderService;
import org.prgms.shoppingbasket.server.shopping.service.ProductService;
import org.prgms.shoppingbasket.server.shopping.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class OrderFixture {

	@Autowired
	VoucherService voucherService;
	@Autowired
	ProductService productService;
	@Autowired
	OrderService orderService;

	public Order createOrder(){
		// given
		final Voucher voucher = voucherService.createVoucher("FIXED_AMOUNT_VOUCHER", 10000,
			"10000원 할인입니다!");

		final Product product1 = productService.createProduct("product1", 10000, 20, "product1");
		final Product product2 = productService.createProduct("product2", 20000, 30, "product2");

		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(OrderItem.create(product1.getId(), product1.getPrice(), 10));
		orderItems.add(OrderItem.create(product2.getId(), product2.getPrice(), 10));

		return orderService.createOrder(voucher.getVoucherId(), "han@email.com", "gangnam", "12345",
			orderItems);
	}


}
