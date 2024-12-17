package novikat.library_service.controllers;

import novikat.library_service.facades.CategoryFacade;
import novikat.library_service.domain.request.UpdateCategoryRequest;
import novikat.library_service.domain.response.CategoryResponse;
import novikat.library_service.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryFacade categoryFacade;
    private CategoryService categoryService;

    public CategoryController(CategoryFacade categoryFacade, CategoryService categoryService) {
        this.categoryFacade = categoryFacade;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public Set<CategoryResponse> getCategories(){
        return this.categoryFacade.getCategories();
    }

    @PostMapping("/add")
    public CategoryResponse addCategory(@RequestParam String name){
        return this.categoryFacade.addCategory(name);
    }

    @PutMapping("/update")
    public CategoryResponse updateCategory(@RequestBody UpdateCategoryRequest request){
        return this.categoryFacade.updateCategory(request);
    }

    @DeleteMapping("/delete")
    public void deleteCategory(@RequestParam UUID id){
        this.categoryService.deleteCategory(id);
    }
}
