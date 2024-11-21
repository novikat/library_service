package novikat.library_service.services;

import novikat.library_service.models.Category;
import novikat.library_service.models.request.UpdateCategoryRequest;
import novikat.library_service.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getCategories() {
        return new HashSet<>(this.categoryRepository.findAll());
    }

    @Override
    @Transactional
    public Category addCategory(String name) {
        if(this.categoryRepository.existsByNameIgnoreCase(name)){
            throw new RuntimeException("Category `" + name + "` already exists");
        }
        else{
            Category category = new Category();
            category.setName(name);

            return this.categoryRepository.save(category);
        }
    }

    @Override
    public Set<Category> getCategoriesByIdIn(Set<UUID> categoriesId) {
        return this.categoryRepository.findAllByIdIn(categoriesId);
    }

    @Override
    public Category findById(UUID id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category with id `" + id + "` doesn`t exist"));
    }

    @Override
    @Transactional
    public Category updateCategory(UpdateCategoryRequest request) {
        Category category = this.findById(request.id());

        category.setName(request.name());

        return this.categoryRepository.save(category);
    }
}
