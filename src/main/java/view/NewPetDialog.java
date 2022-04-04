package view;

import entities.Pet;

import java.util.Scanner;

public class NewPetDialog implements EntityDialog<Pet> {
    public static Scanner scanner = new Scanner(System.in);

    @Override
    public Pet input() {
        Pet pet = new Pet();
        while (pet.getName() == null) {
            System.out.println("Please write name:");
            String name = scanner.nextLine();
            if (name.length() < 2) {
                System.out.println("Error: Name should be at least 2 characters.");
            } else {
                pet.setName(name);
            }
        }
        while (pet.getBreed() == null) {
            System.out.println("Please write breed:");
            String breed = scanner.nextLine();
            if (breed.length() < 3) {
                System.out.println("Error: Breed should be at least 3 characters.");
            } else {
                pet.setBreed(breed);
            }
        }
        while (pet.getWeight() <= 0) {
            System.out.println("Please write weight:");
            int weight = Integer.parseInt(scanner.nextLine());
            if (weight <= 0) {
                System.out.println("Weight must be positive number and greater than zero.");
            } else {
                pet.setWeight(weight);
            }
        }
        return pet;
    }
}
