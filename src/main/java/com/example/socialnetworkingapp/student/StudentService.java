package com.example.socialnetworkingapp.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    public void addNewStudent(Student student) {

        Optional<Student> studentOptional = this.studentRepository.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()) {
            throw new IllegalStateException("email taken!");
        }
        this.studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {

        boolean exists = this.studentRepository.existsById(studentId);

        if(!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exist!");
        }

        this.studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does not exist!"));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken!");
            }
            student.setEmail(email);
        }
    }
}
