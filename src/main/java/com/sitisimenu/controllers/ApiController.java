package com.sitisimenu.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sitisimenu.models.Menu;
import com.sitisimenu.services.MenuService;

@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private MenuService menuService;
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/api/getTodayAndTomorowDishesForInstitution?inst=Ιωαννίνων"
	@RequestMapping(value = "/getTodayAndTomorowDishesForInstitution", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> getTodayAndTomorowDishesForInstitution(@RequestParam("inst") String inst) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
		//Today
		Date now = new Date();
		String day1 = df.format(now);
		//Tomorrow
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DATE, 1);
		String day2 = df.format(c.getTime());
	    
		List<Menu> menus = menuService.getMenusForInstitutionAndDays(inst, day1, day2);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> today = new HashMap<String, Object>();
		Map<String, Object> todayLunch = new HashMap<String, Object>();
		Map<String, Object> todayDiner = new HashMap<String, Object>();
		Map<String, Object> tomorow = new HashMap<String, Object>();
		Map<String, Object> tomorowLunch = new HashMap<String, Object>();
		Map<String, Object> tomorowDiner = new HashMap<String, Object>();
		
		for (Menu menu : menus) {
			if (df.format(menu.getMealDay()).equalsIgnoreCase(day1)) {
				today.put("day", day1);
				if (menu.getMealType().equals("Μεσημεριανό")) {
					todayLunch.put("kyriosPiata", menu.getMainDishes());
					todayLunch.put("synodeytika", menu.getSideDishes());
				} else if(menu.getMealType().equals("Βραδινό")) {
					todayDiner.put("kyriosPiata", menu.getMainDishes());
					todayDiner.put("synodeytika", menu.getSideDishes());
				}
			} else if (df.format(menu.getMealDay()).equalsIgnoreCase(day2)) {
				tomorow.put("day", day2);
				if (menu.getMealType().equals("Μεσημεριανό")) {
					tomorowLunch.put("kyriosPiata", menu.getMainDishes());
					tomorowLunch.put("synodeytika", menu.getSideDishes());
				} else if(menu.getMealType().equals("Βραδινό")) {
					tomorowDiner.put("kyriosPiata", menu.getMainDishes());
					tomorowDiner.put("synodeytika", menu.getSideDishes());
				}
			}
		}

		today.put("mesimeri", todayLunch);
		today.put("bradi", todayDiner);
		
		tomorow.put("mesimeri", tomorowLunch);
		tomorow.put("bradi", tomorowDiner);
		
		retMap.put("shmera", today);
		retMap.put("ayrio", tomorow);
		retMap.put("institution", inst);
		return retMap;
	}
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/api/getInstitutions"
	@RequestMapping(value = "/getInstitutions", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<String> getInstitutions() {
		return menuService.getInstitutions();
	}
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/api/getMealTypes"
	@RequestMapping(value = "/getMealTypes", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<String> getMealTypes() {
		return menuService.getMealTypes();
	}
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/api/getMainDishes"
	@RequestMapping(value = "/getMainDishes", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<String> getMainDishes() {
		return menuService.getMainDishes();
	}
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/api/getSideDishes"
	@RequestMapping(value = "/getSideDishes", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<String> getSideDishes() {
		return menuService.getSideDishes();
	}
	
	//curl --insecure --user user1:user1 -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET "http://localhost:8888/api/removeSpaces"
	@RequestMapping(value = "/removeSpaces", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String removeSpaces() {
		menuService.removeSpaces();
		return "OK";
	}
}
