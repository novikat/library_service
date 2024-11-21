package novikat.library_service.controllers;

import novikat.library_service.facades.CategoryFacade;
import novikat.library_service.models.request.UpdateCategoryRequest;
import novikat.library_service.models.response.CategoryResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryFacade categoryFacade;

    public CategoryController(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
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
}
