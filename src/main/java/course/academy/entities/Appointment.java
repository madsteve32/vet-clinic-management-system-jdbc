package course.academy.entities;

import course.academy.dao.Identifiable;
import course.academy.entities.enums.ExaminationType;
import course.academy.entities.enums.Status;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static course.academy.entities.enums.Status.WAITING;

public class Appointment implements Identifiable<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long clientId;
    private Doctor chosenDoctor;
    private ExaminationType examinationType;
    private LocalDateTime chosenDateTime;
    private Examination examination;
    private Status status = WAITING;

    public Appointment() {
    }

    public Appointment(Long clientId, Doctor chosenDoctor, ExaminationType examinationType, LocalDateTime chosenDateTime) {
        this.clientId = clientId;
        this.chosenDoctor = chosenDoctor;
        this.examinationType = examinationType;
        this.chosenDateTime = chosenDateTime;
    }

    public Appointment(Long clientId, Doctor chosenDoctor, ExaminationType examinationType, LocalDateTime chosenDateTime, Examination examination, Status status) {
        this.clientId = clientId;
        this.chosenDoctor = chosenDoctor;
        this.examinationType = examinationType;
        this.chosenDateTime = chosenDateTime;
        this.examination = examination;
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Doctor getChosenDoctor() {
        return chosenDoctor;
    }

    public void setChosenDoctor(Doctor chosenDoctor) {
        this.chosenDoctor = chosenDoctor;
    }

    public ExaminationType getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(ExaminationType examinationType) {
        this.examinationType = examinationType;
    }

    public LocalDateTime getChosenDateTime() {
        return chosenDateTime;
    }

    public void setChosenDateTime(LocalDateTime chosenDateTime) {
        this.chosenDateTime = chosenDateTime;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        final StringBuilder sb = new StringBuilder("Appointment ");
        sb.append("| id= ").append(id);
        sb.append(" | clientID= ").append(clientId);
        sb.append(" | chosenDoctor= ").append(chosenDoctor.getFirstName());
        sb.append(" | examinationType= ").append(examinationType);
        sb.append(" | chosenDateTime= ").append(chosenDateTime.format(formatter));
        sb.append(" | examination= ").append(examination);
        sb.append(" | status= ").append(status.name()).append(" |");
        return sb.toString();
    }
}
