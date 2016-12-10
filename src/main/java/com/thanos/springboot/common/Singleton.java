package com.thanos.springboot.common;

/**
 * @author solarknight created on 2016/11/27 13:45
 * @version 1.0
 */
public enum Singleton {

    INSTANCE("hihihi");

    Singleton(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Ordinal: " + this.ordinal() + ", name: " + this.name;
    }
}
