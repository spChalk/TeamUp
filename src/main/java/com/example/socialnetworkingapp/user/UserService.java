package com.example.socialnetworkingapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addNewUser(Usr user) {
        Optional<Usr> userOptional = this.userRepository.findUserByEmail(user.getEmail());

        if(userOptional.isPresent()) {
            throw new IllegalStateException("email taken. Please try another one.");
        }
        this.userRepository.save(user);
    }

    public void logUser(String email, String password) {

        Optional<Usr> userOptional = this.userRepository.findUserByEmail(email);

        if(userOptional.isPresent() && Objects.equals(userOptional.get().getPassword(), password)) {
            System.out.println("Access Granted!");
            /* TODO: redirect to home. */
            return;
        }

        throw new IllegalStateException("Invalid credentials. Please try again.");
    }

    /*public List<Student> getStudents() {
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
    }*/
}
