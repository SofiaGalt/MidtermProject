package com.skilldistillery.jpameangirls.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.skilldistillery.jpameangirls.entities.User;

@Service
@Transactional
public class UserDAOJpaImpl implements UserDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public User createUser(User user) {
		em.persist(user);
		em.flush();
		return user;
	}

	@Override
	public User findUserById(int userId) {
		return em.find(User.class, userId);
	}

	@Override
	public List<User> findAllUsers() {
		
		String query ="SELECT u FROM User u";
		return em.createQuery(query,User.class).getResultList();
	}

	@Override
	public User findUserByUsername(String username) {
		String query = "SELECT u FROM User u WHERE username LIKE :username";
		return em.createQuery(query, User.class).setParameter("username", username).getResultList().get(0);
	}

	@Override
	public User findUserByEmail(String email) {
		String query = "SELECT u FROM User u WHERE email LIKE :email";
		return em.createQuery(query, User.class).setParameter("email", email).getResultList().get(0);
	}

	@Override
	public User updateUser(int id, User user) {
		
		User managedUser = em.find(User.class, id);

		managedUser.setEmail(user.getEmail());
		managedUser.setPassword(user.getPassword());
		managedUser.setUsername(user.getUsername());
		managedUser.setFirstName(user.getFirstName());
		managedUser.setLastName(user.getLastName());
		if(user.getEnabled() == null) {	
			managedUser.setEnabled(true);
		} else {
			managedUser.setEnabled(user.getEnabled());
		}
		if(user.getRole() == null) {
			managedUser.setRole("user");
		} else {
			managedUser.setRole(user.getRole());
		}
		
		managedUser.setBirthdayDate(user.getBirthdayDate());
		managedUser.setGender(user.getGender());

		return managedUser;
	}

	@Override
	public boolean deleteUser(int id) {
		User u = em.find(User.class, id);

		if (u != null) {
			em.remove(u);
		}

		boolean gameWasDeleted = !em.contains(u);

		return gameWasDeleted;
	}

}
