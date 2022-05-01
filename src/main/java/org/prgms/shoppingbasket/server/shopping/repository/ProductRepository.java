package org.prgms.shoppingbasket.server.shopping.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.shoppingbasket.server.shopping.entity.Product;

public interface ProductRepository {

	Product save(Product product);

	Product update(Product product);

	Optional<Product> findById(UUID productId);

	List<Product> findAll();

	void deleteAll();
}
