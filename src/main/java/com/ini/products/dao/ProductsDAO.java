package com.ini.products.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.ini.products.entitys.Product;

public interface ProductsDAO extends JpaRepository<Product, Long>{
	
	@Query(value = "SELECT * FROM products WHERE name = ?1", nativeQuery = true)
	Optional<Product> findProductByName(String name);
	
	@Query(value = "SELECT id, name FROM products WHERE name LIKE %?1%", nativeQuery = true)
	List<Product> findProductByLetter(String name);
	
	@Query(value = "SELECT p.id, p.name FROM store s INNER JOIN store_product sp "
			+ "ON s.id = sp.fk_store INNER JOIN products p ON p.id = sp.fk_product WHERE s.id = ?1", nativeQuery = true)
	List<Product> listProductsByStore(Long id);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO store_product VALUES(?1, ?2)", nativeQuery = true)
	void addStoreProduct(Long idStore, Long idProduct);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM store_product WHERE fk_store = ?1 AND fk_product = ?2", nativeQuery = true)
	void deleteStoreProduct(Long idStore, Long idProduct);

}
