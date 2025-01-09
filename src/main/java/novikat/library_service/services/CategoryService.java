package novikat.library_service.services;

import novikat.library_service.domain.models.Category;
import novikat.library_service.domain.request.UpdateCategoryRequest;

import java.util.Set;
import java.util.UUID;

public interface CategoryService {

    Set<Category> findAll();
    Category create(String name);
    Set<Category> findAllByIdIn(Set<UUID> categoriesId);
    Category update(UpdateCategoryRequest request);
    Category findById(UUID id);

    void delete(UUID id);
}
