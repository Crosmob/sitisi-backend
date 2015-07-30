package com.sitisimenu.services;

import java.util.List;

import com.sitisimenu.models.Menu;

public interface MenuService {
	public List<String> getInstitutions();
	public List<String> getMealTypes();
	public List<String> getMainDishes();
	public List<String> getSideDishes();
	public List<Menu>  getMenusForInstitutionAndDays(String inst, String day1, String day2);
	public void removeSpaces();
	public List<Menu> getAllMenus();
	public void insertMenu(Menu menu);
}
