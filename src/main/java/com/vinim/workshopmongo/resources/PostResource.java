package com.vinim.workshopmongo.resources;

import com.vinim.workshopmongo.domain.Post;
import com.vinim.workshopmongo.resources.util.URL;
import com.vinim.workshopmongo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostResource {

    @Autowired
    private PostService service;

    @RequestMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {

        Post obj = service.findById(id);

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping("/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value="text", defaultValue="") String text) {

        text = URL.decodeParam(text);
        List<Post> list = service.findByTitle(text);

        return ResponseEntity.ok().body(list);
    }

    @RequestMapping("/fullsearch")
    public ResponseEntity<List<Post>> fullSearch(
                                                    @RequestParam(value="text", defaultValue="") String text,
                                                    @RequestParam(value="minDate", defaultValue="") String minDate,
                                                    @RequestParam(value="maxDate", defaultValue="") String maxDate
                                                ) {

        text = URL.decodeParam(text);
        Date min = URL.convertDate(minDate, new Date(0L));
        Date max = URL.convertDate(maxDate, new Date());
        List<Post> list = service.fullSearch(text, min, max);

        return ResponseEntity.ok().body(list);
    }
}
