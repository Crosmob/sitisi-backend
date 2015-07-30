package com.sitisimenu.models;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "main_dishes_summary", types = Menu.class)
public interface MenuMainDishesProjection {
	public String getMainDishes();
}
