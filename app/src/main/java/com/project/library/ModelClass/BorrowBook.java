package com.project.library.ModelClass;

public class BorrowBook {
    String BookName,WriterName;

    BorrowBook(){

    }

    public BorrowBook(String bookName, String writerName) {
        BookName = bookName;
        WriterName = writerName;
    }

    public String getBookName() {
        return BookName;
    }

    public String getWriterName() {
        return WriterName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public void setWriterName(String writerName) {
        WriterName = writerName;
    }
}
