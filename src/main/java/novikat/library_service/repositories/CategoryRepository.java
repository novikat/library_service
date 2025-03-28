package novikat.library_service.repositories;

import novikat.library_service.domain.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByNameIgnoreCase(String name);

    List<Category> findAllByIdIn(List<UUID> categoriesId);
    @Modifying
    @Query(nativeQuery = true, value = "delete from book_category where category_id = ?1")
    void deleteAllBookCategoryByCategoryId(UUID categoryId);

}
