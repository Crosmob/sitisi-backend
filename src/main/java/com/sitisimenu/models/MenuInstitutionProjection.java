package com.sitisimenu.models;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "institution_summary", types = Menu.class)
public interface MenuInstitutionProjection {
	public String getInstitution();
}
