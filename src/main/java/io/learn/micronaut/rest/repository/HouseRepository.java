package io.learn.micronaut.rest.repository;

import io.learn.micronaut.rest.entity.House;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

@MongoRepository
public interface HouseRepository extends CrudRepository<House, String> {
}
