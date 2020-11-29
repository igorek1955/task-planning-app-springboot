package ru.todoapp.services;

import org.springframework.stereotype.Service;
import ru.todoapp.models.User;
import ru.todoapp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements CrudService<User, Long> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User findById(Long aLong) {
        Optional<User> user = userRepository.findById(aLong);
        if(!user.isPresent()){
            throw new NullPointerException("Recipe not found. For id value: " + aLong.toString());
        }
        return user.get();
    }

    @Override
    public User save(User object) {
        return userRepository.save(object);
    }

    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }


    @Override
    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }

    public User findByName(String name){
        return userRepository.findByName(name).get();
    }
}
