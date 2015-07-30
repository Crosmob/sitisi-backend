package com.sitisimenu.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.sitisimenu.models.Menu;

@RepositoryRestResource
public interface MenuRepository extends CrudRepository<Menu, Long> {
	//curl --user user1:user1 -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/menus"
	//curl --user user1:user1 -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d '{"institution": "Ιωαννίνων","mealDay":"2015-01-05","mealType":"Μεσημεριανό","mainDishes":"Φασολάδα ή Φακές","sideDishes":"Πίκλες, Τυρί - Ελιές, Φρούτο"}' "http://localhost:8888/menus"
	//curl --user user1:user1 -H "Accept: application/json" -H "Content-Type: application/json" -X PUT -d '{"institution": "Ιωαννίνων","mealDay":"2015-01-05","mealType":"Μεσημεριανό","mainDishes":"Φασολάδα ή Φακές","sideDishes":"Πίκλες, Τυρί - Ελιές, Φρούτο"}' "http://localhost:8888/menus/3"
	//curl --user user1:user1 -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE "http://localhost:8888/menus/3"
	
	@RestResource(exported = false)
	public Menu findByInstitutionAndMealDayAndMealType(String institution, Date mealDay, String mealType);
	
	//CrudRepository escapes characters in nativeQuery (maybe it uses prepered statment inside java - not mysql), so they are sql injection - safe.
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/menus/search/findMenuForThreeDays?day1=2015-02-11&day2=2015-02-12&day3=2015-02-13"
	@Query(value = "SELECT * FROM menus WHERE meal_day IN (:day1, :day2, :day3)", nativeQuery = true)
	public List<Menu> findMenuForThreeDays(@Param("day1") String day1, @Param("day2") String day2, @Param("day3") String day3);	
		
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/menus/search/findMenuForInstitutionAndDays?inst=Ιωαννίνων&day1=2014-11-04&day2=2014-11-18"
	@Query(value = "SELECT * FROM menus WHERE institution=:inst AND meal_day IN (:day1, :day2)", nativeQuery = true)
	public List<Menu> findMenuForInstitutionAndDays(@Param("inst") String inst, @Param("day1") String day1, @Param("day2") String day2);
	
	//For the SQL bellow:
	//Use the hack with the local counter in order to assign an artificial id to 
	//Need to escape : with two \\ (: Is treated as parameter).
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/menus/search/findInstitutions?projection=institution_summary"
	@RestResource(exported = false)
	@Query(value = "SELECT @rownum \\:= @rownum - 1 AS id, institution, meal_day, meal_type, main_dishes, side_dishes FROM (SELECT DISTINCT  institution as institution, '2014-10-23' as meal_day, '' as meal_type, '' as main_dishes, '' as side_dishes FROM menus) AS tmp_menus JOIN (SELECT @rownum \\:= -1) AS r", nativeQuery = true)
    public List<Menu> findInstitutions();
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/menus/search/findMealTypes?projection=meal_type_summary"
	@RestResource(exported = false)
	@Query(value = "SELECT @rownum \\:= @rownum - 1 AS id, institution, meal_day, meal_type, main_dishes, side_dishes FROM (SELECT DISTINCT  '' as institution, '2014-10-23' as meal_day, meal_type as meal_type, '' as main_dishes, '' as side_dishes FROM menus) AS tmp_menus JOIN (SELECT @rownum \\:= -1) AS r", nativeQuery = true)
   	public List<Menu> findMealTypes();
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/menus/search/findMainDishes?projection=main_dishes_summary"
	@RestResource(exported = false)
	@Query(value = "SELECT @rownum \\:= @rownum - 1 AS id, institution, meal_day, meal_type, main_dishes, side_dishes FROM (SELECT DISTINCT  '' as institution, '2014-10-23' as meal_day, '' as meal_type, main_dishes as main_dishes, '' as side_dishes FROM menus) AS tmp_menus JOIN (SELECT @rownum \\:= -1) AS r", nativeQuery = true)
	public List<Menu> findMainDishes();
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/menus/search/findSideDishes?projection=side_dishes_summary"
	@RestResource(exported = false)
	@Query(value = "SELECT @rownum \\:= @rownum - 1 AS id, institution, meal_day, meal_type, main_dishes, side_dishes FROM (SELECT DISTINCT  '' as institution, '2014-10-23' as meal_day, '' as meal_type, '' as main_dishes, side_dishes as side_dishes FROM menus) AS tmp_menus JOIN (SELECT @rownum \\:= -1) AS r", nativeQuery = true)
	public List<Menu> findSideDishes();
}