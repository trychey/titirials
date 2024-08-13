package org.copyobjects.deepcopy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book implements Cloneable {
    private String name;
    private int pageCount;
    private Author author;


    @Override
    public Book clone() throws CloneNotSupportedException {
        Book book = (Book) super.clone();
        book.setAuthor((Author) book.getAuthor().clone());
        return book;
    }
}
