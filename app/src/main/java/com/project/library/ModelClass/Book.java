package com.project.library.ModelClass;

public class Book {
    String Name, NoOfPage, WriterName;

    Book() {

    }

    public Book(String name, String noOfPage, String writerName) {
        Name = name;
        NoOfPage = noOfPage;
        WriterName = writerName;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNoOfPage(String noOfPage) {
        NoOfPage = noOfPage;
    }

    public void setWriterName(String writerName) {
        WriterName = writerName;
    }

    public String getName() {
        return Name;
    }

    public String getNoOfPage() {
        return NoOfPage;
    }

    public String getWriterName() {
        return WriterName;
    }
}
