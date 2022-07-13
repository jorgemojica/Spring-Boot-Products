package com.ini.products.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ini.products.entitys.Store;

public interface StoreDAO extends JpaRepository<Store, Long> {
	
	@Query(value = "SELECT * FROM store s INNER JOIN city c ON s.id = c.id Where c.name = ?1", nativeQuery = true)
	Optional<List<Store>> findStoreByNameCity(String nombre);
	
	@Query(value = "SELECT * FROM store s INNER JOIN products p ON s.id = p.id", nativeQuery = true)
	Optional<List<Store>> storesWithProducts();
	
	@Query(value = "SELECT * FROM store s WHERE s.id_city = ?1", nativeQuery = true)
	Optional<List<Store>> getStoresByIdCity(Long id);

}
