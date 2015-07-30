package com.sitisimenu.models;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "meal_type_summary", types = Menu.class)
public interface MenuMealTypeProjection {
	public String getMealType();
}
