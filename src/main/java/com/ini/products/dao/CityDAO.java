package com.ini.products.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ini.products.entitys.City;

public interface CityDAO extends JpaRepository<City, Long> {

}
