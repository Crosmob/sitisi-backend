package com.sitisimenu.models;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "side_dishes_summary", types = Menu.class)
public interface MenuSideDishesProjection {
	public String getSideDishes();
}
