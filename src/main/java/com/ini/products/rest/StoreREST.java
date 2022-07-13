package com.ini.products.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ini.products.dao.StoreDAO;
import com.ini.products.entitys.Product;
import com.ini.products.entitys.Store;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("store")
public class StoreREST {
	
	@Autowired
	private StoreDAO storeDAO;
	
	@GetMapping
	public ResponseEntity<List<Store>> getStores(){
		List<Store> listaTiendas = storeDAO.findAll();
		return ResponseEntity.ok(listaTiendas);
	}
	
	@RequestMapping(value="{id}")
	public ResponseEntity<Store> getStoreById(@PathVariable("id") Long id){
		Optional<Store> os = storeDAO.findById(id);
		if(os.isPresent()) {
			return ResponseEntity.ok(os.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Store> createStore(@RequestBody Store store){
		Store newStore = storeDAO.save(store);
		return ResponseEntity.ok(newStore);
	}
	
	@DeleteMapping(value="{id}")
	public ResponseEntity<Void> deleteStore(@PathVariable("id") Long id){
		storeDAO.deleteById(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping
	public ResponseEntity<Store> updateStore(@RequestBody Store store){
		Optional<Store> os = storeDAO.findById(store.getId());
		if(os.isPresent()) {
			Store p = os.get();
			p.setName(store.getName());
			storeDAO.save(p);
			return ResponseEntity.ok(p);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(value="name/{nameCity}")
	public ResponseEntity<List<Store>> findStoreByNameCity(@PathVariable("nameCity") String name){
		Optional<List<Store>> optionalStore = storeDAO.findStoreByNameCity(name);
		if(optionalStore.isPresent()) {
			return ResponseEntity.ok(optionalStore.get());
		}
		else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@RequestMapping(value = "swp")
	public ResponseEntity<List<Store>> storesWithProducts(){
		Optional<List<Store>> lista = storeDAO.storesWithProducts();
		if(lista.isPresent()) {
			return ResponseEntity.ok(lista.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@RequestMapping(value = "idCity/{idCity}")
	public ResponseEntity<List<Store>> getStoresByIdCity(@PathVariable("idCity") Long id){
		Optional<List<Store>> stores = storeDAO.getStoresByIdCity(id);
		if(stores.isPresent()) {
			return ResponseEntity.ok(stores.get());	
		} else {
			return ResponseEntity.noContent().build();
		}
	}

}
