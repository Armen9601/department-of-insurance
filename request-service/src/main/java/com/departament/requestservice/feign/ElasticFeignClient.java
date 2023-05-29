package com.departament.requestservice.feign;

import com.departament.requestservice.dto.NestedObject;
import com.departament.requestservice.dto.RequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "document-service", url = "http://localhost:8087")
public interface ElasticFeignClient {

    @PostMapping("/documents/{index}/{id}")
    ResponseEntity<?> indexDocument(@PathVariable("index") String index, @PathVariable("id") String id, @RequestBody Object source);

    @GetMapping("/documents/{index}/{field}/{value}")
    ResponseEntity<?> searchDocuments(@PathVariable("index") String index, @PathVariable("field") String field, @PathVariable("value") String value);

    @PutMapping("/documents/{index}/{id}")
    ResponseEntity<?> updateDocument(@PathVariable("index") String index, @PathVariable("id") String id, @RequestBody NestedObject updatedSource);

    @GetMapping("/documents/{indexes}")
    ResponseEntity<?> searchDocuments(@PathVariable("indexes") List<String> indexes);

    @GetMapping("/documents/{index}/{nestedField}/{nestedValue}")
    ResponseEntity<?> searchDocumentsByNestedField(@PathVariable("index") String index, @PathVariable("nestedField") String nestedField, @PathVariable("nestedValue") String nestedValue);
}
