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

import com.ini.products.dao.CityDAO;
import com.ini.products.entitys.City;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("city")
public class CityREST {
	
	@Autowired
	private CityDAO cityDAO;
	
	@GetMapping
	public ResponseEntity<List<City>> getProduct(){
		List<City> listaCiudades = cityDAO.findAll();
		return ResponseEntity.ok(listaCiudades);
	}
	
	@RequestMapping(value="{id}")
	public ResponseEntity<City> getProductById(@PathVariable("id") Long id){
		Optional<City> oc = cityDAO.findById(id);
		if(oc.isPresent()) {
			return ResponseEntity.ok(oc.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<City> createCity(@RequestBody City city){
		City c = cityDAO.save(city);
		return ResponseEntity.ok(c);
	}
	
	@DeleteMapping(value="{id}")
	public ResponseEntity<Void> deleteCity(@PathVariable("id") Long id){
		cityDAO.deleteById(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping
	public ResponseEntity<City> updateCity(@RequestBody City city){
		Optional<City> oc = cityDAO.findById(city.getId());
		if(oc.isPresent()) {
			City c = oc.get();
			c.setName(city.getName());
			cityDAO.save(c);
			return ResponseEntity.ok(c);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

}
