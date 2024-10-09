package io.learn.micronaut.rest.service;

import io.learn.micronaut.rest.entity.House;
import io.learn.micronaut.rest.repository.HouseRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class HouseService {
    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> getAll() {
        return houseRepository.findAll();
    }

    public House get(String id) {
        return houseRepository.findById(id).get();
    }

    public boolean save(House house) {
        houseRepository.save(house);
        return true;
    }

    public boolean delete(String id) {
        houseRepository.deleteById(id);
        return true;
    }
}
