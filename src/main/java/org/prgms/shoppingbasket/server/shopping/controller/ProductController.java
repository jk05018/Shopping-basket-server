package org.prgms.shoppingbasket.server.shopping.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.prgms.shoppingbasket.server.shopping.dto.ProductDto;
import org.prgms.shoppingbasket.server.shopping.entity.Product;
import org.prgms.shoppingbasket.server.shopping.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/api/v1/products")
	public List<ProductDto> productList() {
		return productService.getAllProducts().stream().map(p -> ProductDto.of(p)).collect(Collectors.toList());
	}

}
