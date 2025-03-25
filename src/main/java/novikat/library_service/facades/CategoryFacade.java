package novikat.library_service.facades;

import novikat.library_service.domain.models.Category;
import novikat.library_service.domain.request.UpdateCategoryRequest;
import novikat.library_service.domain.response.CategoryResponse;
import novikat.library_service.services.CategoryService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CategoryFacade {
    private CategoryService categoryService;

    public CategoryFacade(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<CategoryResponse> findAll(){
        return this.categoryService.findAll().stream()
                .map(category ->
                        new CategoryResponse(
                                category.getId(),
                                category.getName()
                        ))
                .collect(Collectors.toList());
    }

    public CategoryResponse create(String name){
        Category category = this.categoryService.create(name);

        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    public CategoryResponse update(UpdateCategoryRequest request){
        Category category = this.categoryService.update(request);

        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
