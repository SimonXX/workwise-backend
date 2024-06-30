package com.workwise.workwisebackend.support.utils;

import java.util.ArrayList;
import java.util.List;

public class EntityList<T> {

    private List<T> entityList = new ArrayList<>();

    public void printEntityList() {
        for (T entity : entityList) {
            System.out.println(entity);
        }
    }

    public T getEntityAtIndex(int index) {
        if (index >= 0 && index < entityList.size()) {
            return entityList.get(index);
        } else {
            return null;
        }
    }

    public void addEntity(T entity) {
        entityList.add(entity);
    }

    public void removeEntity(T entity) {
        entityList.remove(entity);
    }

    public List<T> getEntityList() {
        return entityList;
    }
}
