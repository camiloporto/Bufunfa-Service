package br.com.bufunfa.finance.user.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import br.com.bufunfa.finance.user.modelo.User;
import br.com.bufunfa.finance.user.repository.UserRepository;

@Component
@Configurable
public class UserDataTestCleaner {
	
	@Autowired
	private UserRepository userRepository;
	
	public void clearAllUsers() {
		List<User> users = userRepository.findAll();
		System.err.println("removing " + users.size() + " users...");
		for (User user : users) {
			userRepository.delete(user.getId());
		}
		System.err.println("OK");
	}
	
}
