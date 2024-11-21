package novikat.library_service.repositories;

import novikat.library_service.models.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, UUID> {
}
