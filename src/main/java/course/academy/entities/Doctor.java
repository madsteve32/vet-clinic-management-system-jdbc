package course.academy.entities;

import course.academy.entities.enums.Gender;
import course.academy.entities.enums.Role;

import java.util.List;

public class Doctor extends User {
    private List<Appointment> appointments;

    public Doctor() {}

    public Doctor(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender) {
        super(firstName, lastName, email, telNumber, username, password, gender);
    }

    public Doctor(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender, Role role) {
        super(firstName, lastName, email, telNumber, username, password, gender, role);
    }

    public Doctor(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender, List<Appointment> appointments) {
        super(firstName, lastName, email, telNumber, username, password, gender);
        this.appointments = appointments;
    }

    public Doctor(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender, Role role, List<Appointment> appointments) {
        super(firstName, lastName, email, telNumber, username, password, gender, role);
        this.appointments = appointments;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Doctor ");
        sb.append(super.toString());
        sb.append(" | appointments=").append(appointments).append(" |");
        return sb.toString();
    }
}
