package com.example.momentofgestures.db;

import com.example.momentofgestures.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

abstract public class SimpleDataRepository<T extends Entity, ID extends Long> implements DataRepository<T, ID> {
    private List<T> dataList = new ArrayList<>();

    private static long index = 0;

    // create, update
    @Override
    public T save(T data) { // -> 이때의 T는 Entity

        if (Objects.isNull(data)) {
            throw new RuntimeException("Data is null.");
        }

        var previousData = dataList.stream()
                .filter(
                        it -> { return it.getId().equals(data.getId());}
                )
                .findFirst();

        if (previousData.isPresent()) {
            // update 필요 -> 기존 데이터가 이미 있음
            // 기존 데이터 지움
            dataList.remove(previousData.get());
            dataList.add(data);
        }
        else {
            // create 필요 -> 기존 데이터가 없었음
            index++;
            data.setId(index);
            dataList.add(data);
        }

        return data;
    }

    // read

    @Override
    public Optional<T> findById(ID id) { // -> "T extends Entity" 에서 제한뒀기 때문에 long 타입만 들어옴
        return dataList.stream()
                .filter( it -> {
                    return it.getId().equals(id);
                })
                .findFirst();
    }

    // delete
    @Override
    public void delete(ID id) {
        var wDeleteEntity = dataList.stream()
                .filter( it -> {
                    return it.getId().equals(id);
                })
                .findFirst();

        if (wDeleteEntity.isPresent()) {
            dataList.remove(wDeleteEntity.get());
        }
    }

    // find all
    @Override
    public List<T> findAll() {
        return dataList;
    }
}
