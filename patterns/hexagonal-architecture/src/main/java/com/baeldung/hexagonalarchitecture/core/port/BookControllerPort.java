package com.baeldung.hexagonalarchitecture.core.port;

import com.baeldung.hexagonalarchitecture.core.domain.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface BookControllerPort {
    @GetMapping("/books")
    Book getBook(@RequestParam Long id);
}
