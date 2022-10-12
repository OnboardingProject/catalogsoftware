package com.example.Catalog.Service;

import com.example.Catalog.Entity.*;

public interface CategoryService {
	public Category saveCatalog(CategoryRequest catgeoryRequest);
	public Category updateCatalog(CategoryUpdateRequest catgeoryUpdateRequest);

}
