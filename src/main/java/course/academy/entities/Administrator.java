package course.academy.entities;

import course.academy.entities.enums.Gender;
import course.academy.entities.enums.Role;

public class Administrator extends User {

    public Administrator() {
    }

    public Administrator(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender) {
        super(firstName, lastName, email, telNumber, username, password, gender);
    }

    public Administrator(String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender, Role role) {
        super(firstName, lastName, email, telNumber, username, password, gender, role);
    }

    public Administrator(Long id, String firstName, String lastName, String email, String telNumber, String username, String password, Gender gender, Role role) {
        super(id, firstName, lastName, email, telNumber, username, password, gender, role);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Administrator ");
        sb.append(super.toString());
        return sb.toString();
    }
}
