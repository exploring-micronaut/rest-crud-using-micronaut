package io.learn.micronaut.rest.controller;

import io.learn.micronaut.rest.client.HouseClient;
import io.learn.micronaut.rest.entity.House;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class HouseControllerTest {
    @Inject
    HouseClient houseClient;

    House newHouse;

    @BeforeEach
    void setup() {
        newHouse = new House();
        newHouse.setNumber(2108);
        newHouse.setStreetName("Wilsons Warber Dr");
        newHouse.setCity("Leander");
        newHouse.setState("Texas");
        newHouse.setZipCode(78641);
    }

    @Test
    void list_empty() {
        // empty database
        assertEquals(0, StreamSupport.stream(houseClient.getAll().spliterator(), false).count());
    }

    @Test
    void create_get_validate() {
        // create a house to validate the POST API
        HttpResponse<House> createdHouse = houseClient.create(newHouse);
        assertEquals(HttpStatus.CREATED, createdHouse.getStatus());

        // call get api using the createdHouse id.
        House previouslyCreatedHouse = createdHouse.body();
        House getHouse = houseClient.get(previouslyCreatedHouse.getId());
        assertEquals(newHouse.getNumber(), getHouse.getNumber());
        assertEquals(newHouse.getStreetName(), getHouse.getStreetName());
    }

    @Test
    void create_get_update_get_validate() throws CloneNotSupportedException {
        // create a house to validate the POST API
        HttpResponse<House> createdHouse = houseClient.create(newHouse);
        assertEquals(HttpStatus.CREATED, createdHouse.getStatus());

        // call get api using the createdHouse id.
        House previouslyCreatedHouse = createdHouse.body();
        House getHouse = houseClient.get(previouslyCreatedHouse.getId());
        assertEquals(newHouse.getNumber(), getHouse.getNumber());
        assertEquals(newHouse.getStreetName(), getHouse.getStreetName());

        // update house details using put api
        House updatingHouse = (House) getHouse.clone();
        updatingHouse.setStreetName("Wilsons Warber Dr.");
        HttpResponse<House> updatedHouse = houseClient.update(updatingHouse);
        assertEquals(HttpStatus.ACCEPTED, updatedHouse.getStatus());

        // call get api using the updatedHouse id.
        House previouslyUpdatedHouse = updatedHouse.body();
        House recentHouse = houseClient.get(previouslyUpdatedHouse.getId());
        assertEquals("Wilsons Warber Dr.", recentHouse.getStreetName());
    }

    @Test
    void create_get_delete_get_validate() throws CloneNotSupportedException {
        // create two houses to validate the POST API
        HttpResponse<House> createdHouse = houseClient.create(newHouse);
        assertEquals(HttpStatus.CREATED, createdHouse.getStatus());

        House anotherHouse = (House) newHouse.clone();
        anotherHouse.setNumber(2108);
        HttpResponse<House> anotherSavedHouse = houseClient.create(anotherHouse);
        assertEquals(HttpStatus.CREATED, anotherSavedHouse.getStatus());

        // call getAll api and count the houses.
        Iterable<House> houseList = houseClient.getAll();
        assertEquals(2, StreamSupport.stream(houseList.spliterator(), false).count());

        // delete house using delete api
        houseClient.delete(anotherSavedHouse.body().getId());

        // call getAll to verify the count
        Iterable<House> updatedHouseList = houseClient.getAll();
        assertEquals(1, StreamSupport.stream(updatedHouseList.spliterator(), false).count());
    }

    @AfterEach
    void tearDown() {
        // clear table records.
        houseClient.deleteAll();
    }
}
