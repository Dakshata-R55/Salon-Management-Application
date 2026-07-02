package com.Daksh.user.service.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Daksh.user.service.Model.User;
import com.Daksh.user.service.Repository.Repository;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private Repository userRepository;

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUser(User user) {
		// If caller supplies a populated User (with id), try to fetch fresh from DB
		if (user != null && user.getId() != null) {
			return userRepository.findById(user.getId()).orElse(null);
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	@Override
	public User updateUser(User user) {
		// save acts as update when id is present
		return userRepository.save(user);
	}

}
