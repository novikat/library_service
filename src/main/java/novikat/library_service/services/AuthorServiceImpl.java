package novikat.library_service.services;

import novikat.library_service.domain.models.Author;
import novikat.library_service.domain.request.CreateAuthorRequest;
import novikat.library_service.domain.request.UpdateAuthorRequest;
import novikat.library_service.repositories.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorServiceImpl implements AuthorService{

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public Author create(CreateAuthorRequest request) {
        if(this.authorRepository.existsByFirstNameAndLastName(request.firstName(), request.lastName())){
            throw new RuntimeException("Author " + request.firstName() + " " + request.lastName() + " already exist." );
        }
        else{
            Author author = Author.Builder.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .biography(request.biography())
                    .build();
            return this.authorRepository.save(author);
        }
    }

    @Override
    public Page<Author> findAll(Pageable pageable) {
        return this.authorRepository.findAll(pageable);
    }

    @Override
    public List<Author> findByLastname(String lastName) {
        return this.authorRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    @Override
    public List<Author> findByIdIn(List<UUID> authorsId) {
        return this.authorRepository.findAllByIdIn(authorsId);
    }

    @Override
    @Transactional
    public Author update(UpdateAuthorRequest request) {
        Author author = this.findById(request.id());

        Optional.ofNullable(request.firstName()).ifPresent(author::setFirstName);
        Optional.ofNullable(request.lastName()).ifPresent(author::setLastName);
        Optional.ofNullable(request.biography()).ifPresent(author::setBiography);

        return this.authorRepository.save(author);
    }

    @Override
    public Author findById(UUID id) {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with id `" + id + "` doesn`t exist"));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if(this.findById(id).getBooks().isEmpty()){
            this.authorRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Author can`t be deleted because of connected books. Delete books first.");
        }

    }
}
