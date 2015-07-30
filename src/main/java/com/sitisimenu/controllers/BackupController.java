package com.sitisimenu.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sitisimenu.models.Menu;
import com.sitisimenu.services.MenuService;

@Controller
@RequestMapping("/backup")
public class BackupController {
	
	@Autowired
	private MenuService menuService;
	
	//http://localhost:8888/backup/getBackup
	@RequestMapping(value = "/getBackup", method = RequestMethod.GET, produces = "text/csv; charset=utf-8")
	@ResponseBody
	public String getBackup(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "inline;filename=backup.csv");
		StringBuilder csv = new StringBuilder();
		for (Menu m : menuService.getAllMenus()) {
			csv.append(	m.getInstitution() 	+ ";" + 
						m.getMealDay() 		+ ";" +
						m.getMealType() 	+ ";" +
						m.getMainDishes() 	+ ";" +
						m.getSideDishes() 	+ "\n");
		}
		return csv.toString();
	}
	
	//http://localhost:8888/backup/putBackup"
	@RequestMapping(value="/putBackup", method=RequestMethod.POST)
	@ResponseBody
    public String putBackup(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
            	String inputCsv =  new String(file.getBytes(), "UTF-8");
            	Scanner scanner = new Scanner(inputCsv);
            	while (scanner.hasNextLine()) {
            		String line = scanner.nextLine().trim();
            		if (line.equals("")) {
						continue;
					}
            		String lineParts[] = line.split(";", -1);
            		if (lineParts.length!=5) {
            			scanner.close();
            			throw new Exception("Line:" + line + " must have 5 elements separated by ;. Found " + lineParts.length + " elements.");
            		}
            		String institution = lineParts[0];
            		String myDate = lineParts[1]; //+"T12:00:00";
            		String mealType = lineParts[2];
            		String mainDishes = lineParts[3];
            		String sideDishes = lineParts[4];
            		
            		//Convert date string to date oblect.
            		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            		//df.setTimeZone(TimeZone.getTimeZone("Europe/Athens"));
            		Date mealDay = df.parse(myDate);
            		
            		//Create a menu.
            		Menu menu = new Menu(institution, mealDay, mealType, mainDishes, sideDishes);
            		this.menuService.insertMenu(menu);
            	}
            	scanner.close();
                return "Restore OK.";
            } catch (Exception e) {
            	System.out.println(e.getMessage());
                return "Restore Failed.";
            }
        } else {
            return "Restore Failed because of empty file.";
        }
    }
}
