package course.academy.entities;

import course.academy.dao.Identifiable;
import course.academy.entities.enums.ExaminationType;
import course.academy.entities.enums.Status;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static course.academy.entities.enums.Status.WAITING;

public class Appointment implements Identifiable<Long> {
    private Long id;
    private ExaminationType examinationType;
    private LocalDateTime chosenDateTime;
    private Status status = WAITING;
    private Long chosenDoctorId;
    private Long clientId;
    private Long examinationId;

    public Appointment() {
    }

    public Appointment(Long id, ExaminationType examinationType, LocalDateTime chosenDateTime, Status status, Long chosenDoctorId, Long clientId) {
        this.id = id;
        this.examinationType = examinationType;
        this.chosenDateTime = chosenDateTime;
        this.status = status;
        this.chosenDoctorId = chosenDoctorId;
        this.clientId = clientId;
    }

    public Appointment(Long id, ExaminationType examinationType, LocalDateTime chosenDateTime, Status status, Long chosenDoctorId, Long clientId, Long examinationId) {
        this.id = id;
        this.examinationType = examinationType;
        this.chosenDateTime = chosenDateTime;
        this.status = status;
        this.chosenDoctorId = chosenDoctorId;
        this.clientId = clientId;
        this.examinationId = examinationId;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getChosenDoctorId() {
        return chosenDoctorId;
    }

    public void setChosenDoctorId(Long chosenDoctorId) {
        this.chosenDoctorId = chosenDoctorId;
    }

    public Long getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(Long examinationId) {
        this.examinationId = examinationId;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        final StringBuilder sb = new StringBuilder("Appointment ");
        sb.append("| id= ").append(id);
        sb.append(" | clientID= ").append(clientId);
        sb.append(" | chosenDoctorId= ").append(chosenDoctorId);
        sb.append(" | examinationType= ").append(examinationType);
        sb.append(" | chosenDateTime= ").append(chosenDateTime.format(formatter));
        sb.append(" | examinationId= ").append(examinationId);
        sb.append(" | status= ").append(status.name());
        return sb.toString();
    }
}
