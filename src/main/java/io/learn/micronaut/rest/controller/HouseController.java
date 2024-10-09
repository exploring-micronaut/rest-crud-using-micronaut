package io.learn.micronaut.rest.controller;

import io.learn.micronaut.rest.entity.House;
import io.learn.micronaut.rest.service.HouseService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@Controller("/houses")
@ExecuteOn(TaskExecutors.BLOCKING)
public class HouseController {
    private final HouseService houseService;

    HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @Get
    Iterable<House> list() {
        return houseService.getAll();
    }

    @Get("/{houseId}")
    House get(@PathVariable String houseId) {
        return houseService.get(houseId);
    }

    @Post
    @Status(HttpStatus.CREATED)
    Boolean save(@Body House savingHouse) {
        return houseService.save(savingHouse);
    }

    @Put
    Boolean update(@Body House updatingHouse) {
        return houseService.save(updatingHouse);
    }

    @Delete("/{houseId}")
    Boolean delete(@PathVariable String houseId) {
        return houseService.delete(houseId);
    }
}
