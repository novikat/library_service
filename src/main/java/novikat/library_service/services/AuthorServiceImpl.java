package novikat.library_service.services;

import novikat.library_service.models.Author;
import novikat.library_service.models.request.CreateAuthorRequest;
import novikat.library_service.models.request.UpdateAuthorRequest;
import novikat.library_service.models.response.AuthorResponse;
import novikat.library_service.models.response.AuthorShortResponse;
import novikat.library_service.repositories.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService{

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public Author addAuthor(CreateAuthorRequest request) {
        if(this.authorRepository.existsByFirstNameAndLastName(request.firstName(), request.lastName())){
            throw new RuntimeException("Author " + request.firstName() + " " + request.lastName() + " already exist." );
        }
        else{
            Author author = new Author();
            author.setFirstName(request.firstName());
            author.setLastName(request.lastName());
            author.setBiography(request.biography());

            return this.authorRepository.save(author);
        }
    }

    @Override
    public Page<Author> getAuthors(Pageable pageable) {
        return this.authorRepository.findAll(pageable);
    }

    @Override
    public Set<Author> getAuthorsByLastName(String lastName) {
        return this.authorRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    @Override
    public Set<Author> getAuthorsByIdIn(Set<UUID> authorsId) {
        return this.authorRepository.findAllByIdIn(authorsId);
    }

    @Override
    @Transactional
    public Author updateAuthor(UpdateAuthorRequest request) {
        Author author = this.getAuthorById(request.id());

        Optional.ofNullable(request.firstName()).ifPresent(author::setFirstName);
        Optional.ofNullable(request.lastName()).ifPresent(author::setLastName);
        Optional.ofNullable(request.biography()).ifPresent(author::setBiography);

        return this.authorRepository.save(author);
    }

    @Override
    public Author getAuthorById(UUID id) {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with id `" + id + "` doesn`t exist"));
    }


}
