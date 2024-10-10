package io.learn.micronaut.rest.client;

import io.learn.micronaut.rest.entity.House;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

@Client("/houses")
public interface HouseClient {
    @Get
    Iterable<House> getAll();

    @Get("/{id}")
    House get(@PathVariable String id);

    @Post
    HttpResponse<House> create(@Body House house);

    @Put
    HttpResponse<House> update(@Body House house);

    @Delete("/{id}")
    HttpResponse<Boolean> delete(@PathVariable String id);

    @Delete
    HttpResponse<Boolean> deleteAll();
}
