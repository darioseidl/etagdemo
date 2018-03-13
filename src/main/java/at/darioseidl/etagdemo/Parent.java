package at.darioseidl.etagdemo;

import javax.persistence.Entity;

@Entity
public class Parent extends BaseEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
