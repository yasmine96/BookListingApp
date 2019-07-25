package com.example.yasmine.booklisting;

public class BookList {

    private String mTitle;
   // private String mAuthor;
    private String mPublishingYear;
    private String mUrl;


    public BookList(String title  , String publishingYear ,String url){

        mTitle = title;
       // mAuthor = author;
        mPublishingYear = publishingYear;
        mUrl = url;

    }


    public String getTitle(){

        return mTitle;
    }


   /* public String getAuthor(){

        return mAuthor;
    }*/

    public String getPublishingYear(){

        return mPublishingYear;
    }
    public String getUrl(){

        return mUrl;
    }


}
