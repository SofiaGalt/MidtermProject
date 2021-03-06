package com.skilldistillery.jpameangirls.entities;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Comment comment;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("MeanGirls");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		comment = em.find(Comment.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		comment = null;
	}

	@Test
	@DisplayName("Test Comment entity mapping")
	void test1() {
		assertNotNull(comment);
		assertEquals("Hi everyone! i cant help that im so popular", comment.getContent());
	}
	
	@Test
	@DisplayName("Test Comment to Clique mapping")
	void test_2() {
//		SELECT clique.name FROM comment JOIN clique ON comment.clique_id = clique.id WHERE comment.id = 1;
//		+-----------+
//		| name      |
//		+-----------+
//		| Cafeteria |
//		+-----------+
		assertNotNull(comment);
		assertNotNull(comment.getClique());
		assertEquals("Cafeteria", comment.getClique().getName());
	}
	
	@Test
	@DisplayName("Test Comment to Student mapping")
	void test3() {
		assertNotNull(comment);
		assertNotNull(comment.getStudent());
		assertEquals("Regina", comment.getStudent().getFirstName());
		assertEquals("George", comment.getStudent().getLastName());
		/*
		 * mysql> SELECT student.first_name, student.last_name FROM student JOIN comment ON comment.student_id = student.id WHERE comment.id = 1;
+------------+-----------+
| first_name | last_name |
+------------+-----------+
| Regina     | George    |
+------------+-----------+
1 row in set (0.00 sec)
		 */
		
	}
	
	@Test
	@DisplayName("Test Comment to CommentVote mapping")
	void test_4() {
		assertNotNull(comment);
		assertNotNull(comment.getCommentVotes());
		assertTrue(comment.getCommentVotes().size() > 0);
	
		
	}
	
	@Test
	@DisplayName("Test Comment to replies mapping")
	void test5() {
//		note: this will fail until we add a reply to the sql file.
		assertNotNull(comment);
		assertNotNull(comment.getReplies());
		assertTrue(comment.getReplies().size() > 0);
	}

}
