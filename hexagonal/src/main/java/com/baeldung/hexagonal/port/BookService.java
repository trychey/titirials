package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.core.domain.Book;

public interface BookService {

    public void addBook(Book book);
    public Book getBook(String title);
}
