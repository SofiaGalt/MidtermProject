package com.skilldistillery.jpameangirls.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.skilldistillery.jpameangirls.entities.Clique;
import com.skilldistillery.jpameangirls.entities.Comment;
import com.skilldistillery.jpameangirls.entities.Student;

@Transactional
@Service
public class CommentDAOImpl implements CommentDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Comment findById(int commentId) {
		
		return em.find(Comment.class, commentId);
	}

	@Override
	public List<Comment> findAll() {
		
		return em.createQuery("select c from Comment c", Comment.class).getResultList();
	}

	@Override
	public Comment create(Comment comment) {
		comment.setCreatedDate(LocalDateTime.now());
		em.persist(comment);
		return comment;
	}
	
	@Override
	public Comment create(Comment comment, Student student, Clique clique) {
		comment.setCreatedDate(LocalDateTime.now());
		comment.setLastEdited(LocalDateTime.now());
		comment.setEnabled(true);
		comment.setFlagged(false);
		comment.setStudent(student);
		comment.setClique(clique);

		em.persist(comment);
		return comment;
	}
	

	@Override
	public Comment update(int id, Comment comment) {
		
		Comment c = em.find(Comment.class, id);
		
		c.setContent(comment.getContent());
		c.setLastEdited(LocalDateTime.now());
		
		return c;
	}
	
	@Override
	public Comment softDelete(int id) {
		Comment c = em.find(Comment.class, id);
		c.setEnabled(false);
		c.setLastEdited(LocalDateTime.now());
		return c;
	}

	@Override
	public boolean deletePermanently(int id) {
		
		Comment managedComment = em.find(Comment.class, id);
		em.remove(managedComment);
		return !em.contains(managedComment);
	}

	@Override
	public List<Comment> findCommentsByCliqueId(int cliqueId) {
		
		return em.createQuery("SELECT c FROM Comment c JOIN c.clique cl WHERE cl.id = :id AND c.enabled=TRUE", Comment.class).setParameter("id", cliqueId).getResultList();
	}

	@Override
	public List<Comment> findCommentsInTheLast24HoursFromCliqueWithId(int id) {
		
//		SELECT * FROM Comment c where c.clique_id = 1 and c.created_date >= '12/04/2011 12:00:00 AM';
//		I used the above query as a guideline.
		
		LocalDateTime yesterday = LocalDateTime.now().plusHours(24);
		String query = "SELECT c FROM Comment c JOIN c.clique cl WHERE cl.id = :id AND c.enabled=TRUE AND c.createdDate >= :yesterday";
		return em.createQuery(query, Comment.class).setParameter("id", id).setParameter("yesterday", yesterday).getResultList();
	}
}
