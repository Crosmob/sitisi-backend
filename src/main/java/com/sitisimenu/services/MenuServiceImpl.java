package com.sitisimenu.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sitisimenu.models.Menu;
import com.sitisimenu.repositories.MenuRepository;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	protected String cleanDishes(String dishesStr) {
		dishesStr = dishesStr.replaceAll("1.", ", ");
		dishesStr = dishesStr.replaceAll("^\\s*,", "");
		dishesStr = dishesStr.replaceAll("2.", " ή ");
		dishesStr = dishesStr.replaceAll("\\d\\)", "ή ");
		dishesStr = dishesStr.replaceAll("\\s+Η\\s+", " ή ");
		dishesStr = dishesStr.replaceAll("\\s+Ή\\s+", " ή ");
		dishesStr = dishesStr.replaceAll("\\s+η\\s+", " ή ");
		dishesStr = dishesStr.replaceAll("^\\s*ή+", "");
		dishesStr = dishesStr.replaceAll(";", ",");
		dishesStr = dishesStr.replaceAll("-", " - ");
		dishesStr = dishesStr.replaceAll("\\s+", " ");
		dishesStr = dishesStr.replaceAll(" ,", ",");
		dishesStr = dishesStr.trim();
		
		return dishesStr;
	}
	
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<String> getInstitutions() {
		List<String> response = new ArrayList<String>();
		for (Menu m : menuRepository.findInstitutions()) {
			response.add(m.getInstitution());
		}
		return response;
	}

	@Override
	public List<String> getMealTypes() {
		List<String> response = new ArrayList<String>();
		for (Menu m : menuRepository.findMealTypes()) {
			response.add(m.getMealType());
		}
		return response;
	}

	@Override
	public List<String> getMainDishes() {
		List<String> response = new ArrayList<String>();
		for (Menu m : menuRepository.findMainDishes()) {
			response.add(m.getMainDishes());
		}
		return response;
	}

	@Override
	public List<String> getSideDishes() {
		List<String> response = new ArrayList<String>();
		for (Menu m : menuRepository.findSideDishes()) {
			response.add(m.getSideDishes());
		}
		return response;
	}

	@Override
	public List<Menu> getMenusForInstitutionAndDays(String inst, String day1,
			String day2) {
		return menuRepository.findMenuForInstitutionAndDays(inst, day1, day2);
	}

	@Override
	public void removeSpaces() {
		for (Menu m : this.menuRepository.findAll()) {
			//Main dishes.
			m.setMainDishes(this.cleanDishes(m.getMainDishes()));
			//Side dishes
			m.setSideDishes(this.cleanDishes(m.getSideDishes()));
		}
	}

	@Override
	public List<Menu> getAllMenus() {
		return (List<Menu>) this.menuRepository.findAll();
	}

	@Override
	public void insertMenu(Menu menu) {
		//Check if the menu exists. If exists, update the dishes.
		Menu oldMenu = this.menuRepository.findByInstitutionAndMealDayAndMealType(menu.getInstitution(), menu.getMealDay(), menu.getMealType());
		if (oldMenu!=null) {
			oldMenu.setMainDishes(menu.getMainDishes());
			oldMenu.setSideDishes(menu.getSideDishes());
		} else {
			this.menuRepository.save(menu);
		}
	}
}
