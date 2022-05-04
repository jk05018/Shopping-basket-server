package org.prgms.shoppingbasket.server.shopping.controller;

import java.util.List;
import java.util.Optional;

import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/api/v1/products")
	public List<Product> productList(){
		final List<Product> allProducts = productService.getAllProducts();
		for (Product allProduct : allProducts) {
			System.out.println(allProduct.getProductName());
			System.out.println(allProduct.getPrice());

		}
		return allProducts;
	}
}