package com.sitisimenu.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sitisimenu.models.Menu;
import com.sitisimenu.services.MenuService;

@Controller
@RequestMapping("/check")
public class CheckController {
	
	@Autowired
	private MenuService menuService;
	
	//http://localhost:8888/check/checkTodayAndTomorrow"
	@RequestMapping(value="/checkTodayAndTomorrow", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
    public String checkTodayAndTomorrow(){
		
		//Get the days
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
		//Today
		Date now = new Date();
		String today = df.format(now);
		//Tomorrow
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DATE, 1);
		String tomorrow = df.format(c.getTime());
		
		//Get the institutions.
		List<String> institutions = menuService.getInstitutions();
		
		StringBuilder result = new StringBuilder();
		result.append("Name;" + today + "(Lunch);" + today + "(Dinner);" + tomorrow + "(Lunch);" + tomorrow + "(Dinner)\n");
		
		String errorStr = "error";
		//String warningStr = "warning";
		String warningStr = "ok";		//Treat warning as ok.
		String okStr = "ok";
		
		for (String institution : institutions) {
			List<Menu> menus = menuService.getMenusForInstitutionAndDays(institution, today, tomorrow);
			
			String todayLunchStatus  = errorStr;
			String todayDinnerStatus = errorStr;
			String tomorrowLunchStatus  = errorStr;
			String tomorrowDinnerStatus = errorStr;

			for (Menu menu : menus) {
				if (df.format(menu.getMealDay()).equalsIgnoreCase(today)) {
					if (menu.getMealType().equals("Μεσημεριανό")) {
						if (menu.getMainDishes().equals("") || menu.getSideDishes().equals("")) {
							todayLunchStatus = warningStr;
						} else {
							todayLunchStatus = okStr;
						}
					} else if (menu.getMealType().equals("Βραδινό")) {
						if (menu.getMainDishes().equals("") || menu.getSideDishes().equals("")) {
							todayDinnerStatus = warningStr;
						} else {
							todayDinnerStatus = okStr;
						}
					}
				} else if (df.format(menu.getMealDay()).equalsIgnoreCase(
						tomorrow)) {
					if (menu.getMealType().equals("Μεσημεριανό")) {
						if (menu.getMainDishes().equals("") || menu.getSideDishes().equals("")) {
							tomorrowLunchStatus = warningStr;
						} else {
							tomorrowLunchStatus = okStr;
						}
					} else if (menu.getMealType().equals("Βραδινό")) {
						if (menu.getMainDishes().equals("") || menu.getSideDishes().equals("")) {
							tomorrowDinnerStatus = warningStr;
						} else {
							tomorrowDinnerStatus = okStr;
						}
					}
				}
			}
			result.append(institution + ";" + todayLunchStatus + ";" + todayDinnerStatus + ";" + tomorrowLunchStatus + ";" + tomorrowDinnerStatus + "\n");
		}
		
		return result.toString();
	}
}
