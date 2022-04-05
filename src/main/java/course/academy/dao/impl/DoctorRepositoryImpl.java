package course.academy.dao.impl;

import course.academy.dao.DoctorRepository;
import course.academy.entities.Doctor;
import course.academy.exception.NonexistingEntityException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static course.academy.entities.enums.Role.DOCTOR;

public class DoctorRepositoryImpl implements DoctorRepository {
    private Map<Long, Doctor> doctors = new HashMap<>();

    @Override
    public Collection<Doctor> findAll() {
        return doctors.values();
    }

    @Override
    public Doctor findById(Long id) throws NonexistingEntityException {
        Doctor doctor = doctors.get(id);
        if (doctor == null) {
            throw new NonexistingEntityException("Doctor with ID'" + id + "' does not exist.");
        }
        return doctor;
    }

    @Override
    public Doctor create(Doctor doctor) {
        doctor.setRole(DOCTOR);
        doctors.put(doctor.getId(), doctor);
        return doctor;
    }

    @Override
    public void addAll(Collection<Doctor> entities) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Doctor update(Doctor doctor) throws NonexistingEntityException {
        Doctor oldDoctor = findById(doctor.getId());
        if (oldDoctor == null) {
            throw new NonexistingEntityException("Administrator with ID'" + doctor.getId() + "' does not exist.");
        }
        doctors.put(doctor.getId(), doctor);
        return doctor;
    }

    @Override
    public Doctor deleteById(Long id) throws NonexistingEntityException {
        Doctor doctor = doctors.remove(id);
        if (doctor == null) {
            throw new NonexistingEntityException("Doctor with ID'" + id + "' does not exist.");
        }
        return doctor;
    }

    @Override
    public long count() {
        return doctors.size();
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
