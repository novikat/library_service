package novikat.library_service.services;

import novikat.library_service.domain.models.Category;
import novikat.library_service.domain.request.UpdateCategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> findAll();
    Category create(String name);
    List<Category> findAllByIdIn(List<UUID> categoriesId);
    Category update(UpdateCategoryRequest request);
    Category findById(UUID id);

    void delete(UUID id);
}
