package course.academy.entities;

import course.academy.dao.Identifiable;

import java.io.Serializable;
import java.time.LocalDate;

public class PetPassport implements Identifiable<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long petId;
    private LocalDate dewormingDate;
    private LocalDate vaccinationDate;
    private LocalDate examinationDate;

    public PetPassport() {
    }

    public PetPassport(Long petId, LocalDate dewormingDate, LocalDate vaccinationDate, LocalDate examinationDate) {
        this.petId = petId;
        this.dewormingDate = dewormingDate;
        this.vaccinationDate = vaccinationDate;
        this.examinationDate = examinationDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public LocalDate getDewormingDate() {
        return dewormingDate;
    }

    public void setDewormingDate(LocalDate dewormingDate) {
        this.dewormingDate = dewormingDate;
    }

    public LocalDate getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(LocalDate vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public LocalDate getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(LocalDate examinationDate) {
        this.examinationDate = examinationDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PetPassport ");
        sb.append("| id=").append(id);
        sb.append(" | dewormingDate=").append(dewormingDate);
        sb.append(" | vaccinationDate=").append(vaccinationDate);
        sb.append(" | examinationDate=").append(examinationDate).append(" |");
        return sb.toString();
    }
}
