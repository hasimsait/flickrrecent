package edu.sabanciuniv.newsstarterexample.model;

import java.io.Serializable;
//TODO this class is not used in this, it was on the actual homework, if you see this, I didn't take the time to delete the references to this
public class Category implements Serializable {
    int id;
    String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;            // What to display in the Spinner list.
    }
    public Category() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
