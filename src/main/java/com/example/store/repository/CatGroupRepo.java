package com.example.store.repository;

import com.example.store.dto.CatGroupDto;
import com.example.store.dto.CategoryDto;
import com.example.store.entity.Category;
import com.example.store.entity.Category_group;
import com.example.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatGroupRepo extends JpaRepository<Category_group,Integer> {
    //@Query("SELECT c FROM category_group c WHERE c.id_category_group LIKE ?1")
    //public List<Category_group> Group(Integer id_category_group);

    //SELECT c.id_category_group, c.name_category_group, count(p.category_id) FROM (Category_group c INNER JOIN Category g on g.category_group_id = c.id_category_group) INNER JOIN Good p on p.category_id = g.id_category GROUP BY c.id_category_group
    @Query("SELECT new com.example.store.dto.CatGroupDto(c.id_category_group, c.name_category_group, count(p.category.category_group.id_category_group)) FROM Category_group c INNER JOIN Category g on g.category_group.id_category_group = c.id_category_group INNER JOIN Good p on p.category.id_category = g.id_category GROUP BY c.id_category_group")
    List<CatGroupDto> getCategoryGroupAndProduct();

    @Query("SELECT p FROM Category_group p WHERE p.name_category_group LIKE %?1%")
    public List<Category_group> findByNameCategoryGroup(String name_category_group);
}
