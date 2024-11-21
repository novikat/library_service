package novikat.library_service.services;

import novikat.library_service.models.Category;
import novikat.library_service.models.request.UpdateCategoryRequest;
import novikat.library_service.models.response.CategoryResponse;

import java.util.Set;
import java.util.UUID;

public interface CategoryService {

    Set<Category> getCategories();
    Category addCategory(String name);
    Set<Category> getCategoriesByIdIn(Set<UUID> categoriesId);
    Category updateCategory(UpdateCategoryRequest request);
    Category findById(UUID id);
}
