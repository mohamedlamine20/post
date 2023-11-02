package com.mohamed.post.controller;

import com.mohamed.post.model.Post;
import com.mohamed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostRepository postRepository;
    @GetMapping
    public ResponseEntity<List<Post>> getAll(){
        return ResponseEntity.ok(postRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post>getById(@PathVariable String id){
        return ResponseEntity.ok(postRepository.findById(id).orElseThrow(()->new RuntimeException("not found")));
    }

    @PostMapping
    public ResponseEntity<Post>save(@RequestBody @Validated  Post post){
        return ResponseEntity.ok(postRepository.save(post));
    }


    @PutMapping
    public ResponseEntity<Post>update(@RequestBody Post post){
        return ResponseEntity.ok(postRepository.save(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}
