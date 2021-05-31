package com.baeldung.hexarchitecture.application.port.in;

import com.baeldung.hexarchitecture.domain.Book;

public interface BookService {

    void createBook(CreateBookCommand createBookCommand);
    Book getBook(String id);
}
