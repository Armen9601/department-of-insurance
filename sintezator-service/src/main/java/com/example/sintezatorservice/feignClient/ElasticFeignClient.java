package com.example.sintezatorservice.feignClient;

import com.example.sintezatorservice.dto.NestedObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "document-service", url = "http://localhost:8087")
public interface ElasticFeignClient {

    @PostMapping("/documents/{index}/{id}")
    List<String> indexDocument(@PathVariable("index") String index, @PathVariable("id") String id, @RequestBody Object source);

    @GetMapping("/documents/doc/{index}/{field}/{value}")
    List<String> searchDoc(@PathVariable("index") String index, @PathVariable("field") String field, @PathVariable("value") String value);

    @PutMapping("/documents/{index}/{id}")
    ResponseEntity<?> updateDocument(@PathVariable("index") String index, @PathVariable("id") String id, @RequestBody NestedObject updatedSource);

    @GetMapping("/documents/{indexes}")
    ResponseEntity<?> searchDocuments(@PathVariable("indexes") List<String> indexes);

    @GetMapping("/documents/{index}/{nestedField}/{nestedValue}")
    ResponseEntity<?> searchDocumentsByNestedField(@PathVariable("index") String index, @PathVariable("nestedField") String nestedField, @PathVariable("nestedValue") String nestedValue);
}
