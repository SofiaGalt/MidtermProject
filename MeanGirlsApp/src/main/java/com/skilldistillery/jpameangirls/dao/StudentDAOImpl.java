package com.skilldistillery.jpameangirls.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.skilldistillery.jpameangirls.entities.Badge;
import com.skilldistillery.jpameangirls.entities.Clique;
import com.skilldistillery.jpameangirls.entities.Comment;
import com.skilldistillery.jpameangirls.entities.Student;

@Transactional
@Service
public class StudentDAOImpl implements StudentDAO{
	
	@PersistenceContext
	private EntityManager em;

//	CREATE
	@Override
	public Student create(Student student) {
		Badge newStudentBadge = em.find(Badge.class, 1);
		Clique cafeteria = em.find(Clique.class, 1);
		student.setCreatedDate(LocalDateTime.now());
		student.addBadge(newStudentBadge);
		student.addClique(cafeteria);

		em.persist(student);
		return student;
	}
	
//	FIND
	
	@Override
	public Student findById(int studentId) {
		return em.find(Student.class, studentId);
	}
	
	@Override
	public List<Student> findAll() {
		
		String query="SELECT s FROM Student s";
		return em.createQuery(query, Student.class).getResultList();
	}
	
	@Override
	public List<Clique> findAllCliquesForAStudent(int studentId) {
		String query = "SELECT s.cliques FROM Student s WHERE s.id=:sId";
		List<Object> results = em.createQuery(query, Object.class)
				.setParameter("sId", studentId)
				.getResultList();
		List<Clique> cliques = new ArrayList<>();
		results.stream().forEach(x -> cliques.add((Clique) x));
		return cliques;
	}
	@Override
	public List<Badge> findAllBadgesForAStudent(int studentId) {
		String query = "SELECT s.badges FROM Student s WHERE s.id=:sId";
		List<Object> results = em.createQuery(query, Object.class)
				.setParameter("sId", studentId)
				.getResultList();
		List<Badge> badges = new ArrayList<>();
		results.stream().forEach(x -> badges.add((Badge) x));
		return badges;
	}
	
	@Override
	public List<Comment> findAllCommentForAStudent(int studentId){
		String query = "SELECT s.comments FROM Student s WHERE s.id=:sId";
		List<Object> results = em.createQuery(query, Object.class)
				.setParameter("sId", studentId)
				.getResultList();
		List<Comment> comments = new ArrayList<>();
		results.stream().forEach(x -> comments.add((Comment) x));
		return comments;		
	}
	
//	UPDATE
	
	@Override
	public Student update(int id, Student student) {
		Student managedStudent = em.find(Student.class, id);
		managedStudent.setFirstName(student.getFirstName());
		managedStudent.setLastName(student.getLastName());
		managedStudent.setGender(student.getGender());
		managedStudent.setGradeLevel(student.getGradeLevel());
		managedStudent.setBirthdayDate(student.getBirthdayDate());
		managedStudent.setImageUrl(student.getImageUrl());
		return managedStudent;
	}
	
	@Override
	public Student addBadgeToStudent(Student student, int badgeId) {
		Student managedStudent = em.find(Student.class, student.getId());
//		managedBadge.getStudents();
		managedStudent.addBadge(em.find(Badge.class, badgeId));
		return managedStudent;
	}
	

//	DELETE
	
	@Override
	public boolean deletePermanently(int id) {
		Student managedStudent = em.find(Student.class, id);
		em.remove(managedStudent);
		boolean wasDeleted = ! em.contains(managedStudent);
		return wasDeleted;
	}

	

}
