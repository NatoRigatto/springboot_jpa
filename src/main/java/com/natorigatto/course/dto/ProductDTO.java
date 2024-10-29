package com.natorigatto.course.dto;

import java.util.Set;

public class ProductDTO {

	private String name;
	private String description;
	private Double price;
	private String imgUrl;
	private Set<Long> categoriesId;

	public ProductDTO() {
	}

	public ProductDTO(String name, String description, Double price, String imgUrl, Set<Long> categoriesId) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.categoriesId = categoriesId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Set<Long> getCategoriesId() {
		return categoriesId;
	}

	public void setCategoriesId(Set<Long> categoriesId) {
		this.categoriesId = categoriesId;
	}

}
