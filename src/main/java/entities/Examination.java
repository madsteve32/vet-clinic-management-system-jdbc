package entities;

import dao.Identifiable;
import entities.enums.Status;

import java.io.Serializable;
import java.time.LocalDate;

public class Examination implements Identifiable<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private LocalDate date;
    private Status status;

    public Examination() {
    }

    public Examination(String name, LocalDate date, Status status) {
        this.name = name;
        this.date = date;
        this.status = status;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Examination ");
        sb.append("| id=").append(id);
        sb.append(" | name=").append(name);
        sb.append(" | date=").append(date);
        sb.append(" | examinationStatus=").append(status);
        return sb.toString();
    }
}
