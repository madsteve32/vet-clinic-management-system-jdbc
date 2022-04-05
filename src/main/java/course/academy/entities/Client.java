package course.academy.entities;

import course.academy.entities.enums.Gender;
import course.academy.entities.enums.Role;

import java.util.List;

public class Client extends User {
    private Pet pet;
    private List<Appointment> appointments;

    public Client() {}

    public Client(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender) {
        super(firstName, lastName, email, telNumber, username, password, gender);
    }

    public Client(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender, Role role) {
        super(firstName, lastName, email, telNumber, username, password, gender, role);
    }

    public Client(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender, Pet pet) {
        super(firstName, lastName, email, telNumber, username, password, gender);
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client ");
        sb.append(super.toString());
        sb.append(" | pet=").append(pet);
        sb.append(" | Appointment=").append(appointments).append(" |");
        return sb.toString();
    }
}
