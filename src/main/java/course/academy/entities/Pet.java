package course.academy.entities;

import course.academy.dao.Identifiable;

import java.io.Serializable;

public class Pet implements Identifiable<Long> {
    private Long id;
    private String name;
    private String breed;
    private int weight;
    private Long clientId;
    private PetPassport petPassport;

    public Pet() {
    }

    public Pet(String name, String breed, int weight, Long clientId) {
        this.name = name;
        this.breed = breed;
        this.weight = weight;
        this.clientId = clientId;
    }

    public Pet(String name, String breed, int weight, PetPassport petPassport) {
        this.name = name;
        this.breed = breed;
        this.weight = weight;
        this.petPassport = petPassport;
    }

    public Pet(String name, String breed, int weight, Long clientId, PetPassport petPassport) {
        this.name = name;
        this.breed = breed;
        this.weight = weight;
        this.clientId = clientId;
        this.petPassport = petPassport;
    }

    public Pet(Long id, String name, String breed, int weight, Long clientId) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.weight = weight;
        this.clientId = clientId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public PetPassport getPetPassport() {
        return petPassport;
    }

    public void setPetPassport(PetPassport petPassport) {
        this.petPassport = petPassport;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pet ");
        sb.append("| id=").append(id);
        sb.append(" | name=").append(name);
        sb.append(" | breed=").append(breed);
        sb.append(" | weight=").append(weight);
        sb.append(" | clientId=").append(clientId);
        sb.append(" | petPassport=").append(petPassport).append(" |");
        return sb.toString();
    }
}
