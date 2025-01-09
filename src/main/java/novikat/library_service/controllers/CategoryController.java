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
    public Set<CategoryResponse> findAll(){
        return this.categoryFacade.findAll();
    }

    @PostMapping()
    public CategoryResponse create(@RequestParam String name){
        return this.categoryFacade.create(name);
    }

    @PutMapping()
    public CategoryResponse update(@RequestBody UpdateCategoryRequest request){
        return this.categoryFacade.update(request);
    }

    @DeleteMapping()
    public void delete(@RequestParam UUID id){
        this.categoryService.delete(id);
    }
}
