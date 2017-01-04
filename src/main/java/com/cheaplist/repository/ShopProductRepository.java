package com.cheaplist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cheaplist.model.ShopProduct;
import java.util.List;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer> {
	
	@Query("SELECT sp FROM ShopProduct sp where sp.product.id = :idProduct")
	List<ShopProduct> findPriceByProduct(@Param("idProduct") int idProduct);
	
	@Query("SELECT sp FROM ShopProduct sp where sp.product.id = :idProduct and sp.shop.id = :idShop")
	List<ShopProduct> findPriceByProductShop(@Param("idProduct") int idProduct,@Param("idShop") int idShop);

}
