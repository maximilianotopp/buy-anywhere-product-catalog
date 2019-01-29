package com.buyanywhere.productcatalog.models;

public class Greeting {
    private long id;
    private String content;

    public Greeting(){

    }

    public Greeting(long id, String content){
        this.id = id;
        this.content = content;
    }

    public long getId(){
    return this.id;
    }

    public String getContent() {
        return this.content;
    }
}