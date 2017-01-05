package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cheaplist.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
	
	@Query("SELECT sh FROM Shop sh where idgoogle = :idGoogle")
	Shop findShopByIdgoogle(@Param("idGoogle") String idGoogle);

}
