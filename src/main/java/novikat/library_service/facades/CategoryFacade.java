package novikat.library_service.facades;

import novikat.library_service.domain.models.Category;
import novikat.library_service.domain.request.UpdateCategoryRequest;
import novikat.library_service.domain.response.CategoryResponse;
import novikat.library_service.services.CategoryService;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
@Component
public class CategoryFacade {
    private CategoryService categoryService;

    public CategoryFacade(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Set<CategoryResponse> getCategories(){
        return this.categoryService.getCategories().stream()
                .map(category ->
                        new CategoryResponse(
                                category.getId(),
                                category.getName()
                        ))
                .collect(Collectors.toSet());
    }

    public CategoryResponse addCategory(String name){
        Category category = this.categoryService.addCategory(name);

        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    public CategoryResponse updateCategory(UpdateCategoryRequest request){
        Category category = this.categoryService.updateCategory(request);

        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
