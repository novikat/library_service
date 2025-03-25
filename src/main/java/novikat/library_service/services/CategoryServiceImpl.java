package novikat.library_service.services;

import novikat.library_service.domain.models.Category;
import novikat.library_service.domain.request.UpdateCategoryRequest;
import novikat.library_service.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(this.categoryRepository.findAll());
    }

    @Override
    @Transactional
    public Category create(String name) {
        if(this.categoryRepository.existsByNameIgnoreCase(name)){
            throw new RuntimeException("Category `" + name + "` already exists");
        }
        else{
            Category category = Category.Builder.builder()
                    .name(name)
                    .build();

            return this.categoryRepository.save(category);
        }
    }

    @Override
    public List<Category> findAllByIdIn(List<UUID> categoriesId) {
        return this.categoryRepository.findAllByIdIn(categoriesId);
    }

    @Override
    public Category findById(UUID id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category with id `" + id + "` doesn`t exist"));
    }

    @Override
    @Transactional
    public Category update(UpdateCategoryRequest request) {
        Category category = this.findById(request.id());

        category.setName(request.name());

        return this.categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.categoryRepository.deleteAllBookCategoryByCategoryId(id);
        this.categoryRepository.deleteById(id);
    }
}
