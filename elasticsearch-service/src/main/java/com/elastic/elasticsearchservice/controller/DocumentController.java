package com.elastic.elasticsearchservice.controller;

import com.elastic.elasticsearchservice.dto.NestedObject;
import com.elastic.elasticsearchservice.dto.RequestDto;
import com.elastic.elasticsearchservice.service.ElasticsearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final ElasticsearchService elasticsearchService;

    public DocumentController(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @PostMapping("/{index}/{id}")
    public ResponseEntity<?> indexDocument(@PathVariable String index, @PathVariable String id, @RequestBody Object source) throws IOException, InterruptedException {
        elasticsearchService.indexDocument(index, id, source);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{index}/{field}/{value}")
//    public ResponseEntity<?> searchDocuments(@PathVariable String index, @PathVariable String field, @PathVariable String value) throws IOException {
//        List<NestedObject> results = elasticsearchService.searchDocuments(index, field, value);
//        return ResponseEntity.ok(results);
//    }
//
//    @GetMapping("/doc/{index}/{field}/{value}")
//    public ResponseEntity<?> searchDoc(@PathVariable String index, @PathVariable String field, @PathVariable String value) throws IOException {
//        List<String> results = elasticsearchService.searchDoc(index, field, value);
//        return ResponseEntity.ok(results);
//    }
//
//    @PutMapping("/{index}/{id}")
//    public ResponseEntity<?> updateDocument(@PathVariable String index, @PathVariable String id, @RequestBody NestedObject updatedSource) throws IOException {
//        elasticsearchService.updateDocument(index, id, updatedSource);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/{indexes}")
//    public ResponseEntity<?> searchDocuments(@PathVariable List<String> indexes) throws IOException {
//        List<NestedObject> results = elasticsearchService.searchDocuments(indexes);
//        return ResponseEntity.ok(results);
//    }
//
//    @DeleteMapping("/{index}")
//    public ResponseEntity<?> delete(@PathVariable String index) throws IOException {
//        elasticsearchService.deleteAllDocuments(index);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
//
//    @GetMapping("nested/{index}/{nestedField}/{nestedValue}")
//    public ResponseEntity<?> searchDocumentsByNestedField(@PathVariable String index, @PathVariable String nestedField, @PathVariable String nestedValue) throws IOException {
//        List<NestedObject> results = elasticsearchService.searchDocumentsByNestedField(index, nestedField, nestedValue);
//        return ResponseEntity.ok(results);
//    }


}

