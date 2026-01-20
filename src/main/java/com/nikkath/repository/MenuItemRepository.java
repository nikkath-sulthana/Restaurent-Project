package com.nikkath.repository;

import com.nikkath.model.Category;
import com.nikkath.model.FoodType;
import com.nikkath.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository → marks it as a Spring Bean
//Extending JpaRepository → gives you CRUD operations automatically
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
List<MenuItem> findByCategory(Category category);
List<MenuItem> findByFoodType(FoodType foodType);
}
