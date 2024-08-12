package com.example.entity;

public class Cook {

        public int id;
        public String name;
        public Double price;
        public String describe;
        public String cooking_type;
        public String grade;

    public Cook(int id, String name, Double price, String describe, String cooking_type, String grade) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.describe = describe;
        this.cooking_type = cooking_type;
        this.grade = grade;
    }

    public Cook() {
    }

    @Override
    public String toString() {
        return "Cook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", describe='" + describe + '\'' +
                ", cooking_type='" + cooking_type + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setCooking_type(String cooking_type) {
        this.cooking_type = cooking_type;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescribe() {
        return describe;
    }

    public String getCooking_type() {
        return cooking_type;
    }

    public String getGrade() {
        return grade;
    }
}