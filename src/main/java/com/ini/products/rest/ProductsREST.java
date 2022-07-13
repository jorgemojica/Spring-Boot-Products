package com.ini.products.rest;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ini.products.dao.ProductsDAO;
import com.ini.products.entitys.Product;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("products")
public class ProductsREST {
	
	@Autowired
	private ProductsDAO productDAO;
	
	@GetMapping
	public ResponseEntity<List<Product>> getProduct(){
		List<Product> products = productDAO.findAll();
		return ResponseEntity.ok(products);
	}
	
	@RequestMapping(value="{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId){
		Optional<Product> optionalProduct = productDAO.findById(productId);
		if(optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		Product newProduct = productDAO.save(product);
		return ResponseEntity.ok(newProduct);
	}
	
	@DeleteMapping(value="{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId){
		productDAO.deleteById(productId);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		Optional<Product> optionalProduct = productDAO.findById(product.getId());
		if(optionalProduct.isPresent()) {
			Product updateProduct = optionalProduct.get();
			updateProduct.setName(product.getName());
			productDAO.save(updateProduct);
			return ResponseEntity.ok(updateProduct);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(value="name/{name}")
	public ResponseEntity<Product> findProductByName(@PathVariable("name") String name){
		Optional<Product> op = productDAO.findProductByName(name);
		if(op.isPresent()) {
			return ResponseEntity.ok(op.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(value="letter/{letter}")
	public ResponseEntity<List<Product>> findProductByLetter(@PathVariable("letter") String letter){
		List<Product> products = productDAO.findProductByLetter(letter);
		return ResponseEntity.ok(products);
	}
	
	@RequestMapping(value = "store/{id}")
	public ResponseEntity<List<Product>> listProductsByStore(@PathVariable("id") Long id){
		List<Product> listProducts = productDAO.listProductsByStore(id);
		if(listProducts.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(listProducts);
		}
	}
	
	@PostMapping(value = "addstoreproduct/{idStore}/{idProduct}")
	public ResponseEntity<Void> addStoreProduct(@PathVariable("idStore") Long idStore, @PathVariable("idProduct") Long idProduct) {
		productDAO.addStoreProduct(idStore, idProduct);
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping(value = "deletestoreproduct/{idStore}/{idProduct}")
	public ResponseEntity<Void> deleteStoreProduct(@PathVariable("idStore") Long idStore, @PathVariable("idProduct") Long idProduct){
		productDAO.deleteStoreProduct(idStore, idProduct);
		return ResponseEntity.ok(null);
	}
	
	//@GetMapping //localhost:8080
	@RequestMapping(value = "hello", method = RequestMethod.GET)
	public String hello() {
		return "Hello World";
	}

}
