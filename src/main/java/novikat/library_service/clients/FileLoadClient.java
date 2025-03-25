package novikat.library_service.clients;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@FeignClient(name = "file-loader")
public interface FileLoadClient {
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> upload(@RequestPart MultipartFile file, @RequestParam final UUID bookId);
    @GetMapping(value = "/file")
    ResponseEntity<ByteArrayResource> download(@RequestParam final UUID bookId);
    @DeleteMapping(value = "/file")
    void delete(@RequestParam final UUID bookId);
}
