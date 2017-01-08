package com.cheaplist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cheaplist.model.ListProduct;
import com.cheaplist.model.ShopProduct;

public interface ListProductRepository extends JpaRepository<ListProduct, Integer> {
	
	@Query("SELECT lp FROM ListProduct lp where lp.shoppingList.id = :idList")
	List<ListProduct> findProductsByList(@Param("idList") int idList);

}
