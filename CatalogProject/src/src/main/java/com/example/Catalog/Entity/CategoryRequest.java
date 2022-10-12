package com.example.Catalog.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
	private String levelName;
	private String createdBy;
	private String lastUpdatedBy;
	private String hierarchyLevel;
}
