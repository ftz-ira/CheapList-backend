package com.cheaplist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cheaplist.model.ListProduct;

public interface ListProductRepository extends JpaRepository<ListProduct, Integer> {
	
	@Query("SELECT lp FROM ListProduct lp where lp.shoppingList.id = :idList")
	List<ListProduct> findProductsByList(@Param("idList") int idList);
	
	@Query("SELECT lp FROM ListProduct lp where lp.shoppingList.id = :idList and lp.id = :idElement")
	ListProduct findProductByList(@Param("idList") int idList, @Param("idElement") int idElement);
	
	@Query("SELECT lp FROM ListProduct lp where lp.shoppingList.id = :idList and lp.product.id = :idProduct")
	ListProduct findElementByListBtProduct(@Param("idList") int idList, @Param("idProduct") int idProduct);
	
	@Query("SELECT COALESCE(sum(lp.productQuantity*sp.price),0) FROM ListProduct lp "
			+ "inner join lp.product "
			+ "left join lp.product.shopProducts as sp "
			+ "where lp.shoppingList.id = :idList and sp.shop.id = :idShop")
	double findPrice(@Param("idList") int idList, @Param("idShop") int idShop);


	@Query("SELECT count(*) FROM ListProduct lp "
			+ "inner join lp.product "
			+ "left outer join lp.product.shopProducts as sp "
			+ "where lp.shoppingList.id = :idList and sp.shop.id = :idShop")
	long findMissing(@Param("idList") int idList, @Param("idShop") int idShop);

	@Query("SELECT count(*) FROM ListProduct lp where lp.shoppingList.id = :idList")
	long countElement(@Param("idList") int idList);

	

}
