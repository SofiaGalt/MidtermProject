package com.skilldistillery.jpameangirls.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Badge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String description;

	@Column(name = "image_url")
	private String imageUrl;
	
	private Boolean enabled;
	
	@ManyToMany
	@JoinTable(name="student_badge",
			joinColumns=@JoinColumn(name="badge_id"),
			inverseJoinColumns = @JoinColumn(name="student_id"))
	private List<Student> students;

//	Constructor
	public Badge() {
	}
	// Methods:
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public void addStudent(Student student) {
		if (students == null) {
			students = new ArrayList<>();
		}
		if (! students.contains(student)) {
			students.add(student);
			student.addBadge(this);
		}
	}
	
	public void removeStudent(Student student) {
		if (students != null && students.contains(student)) {
			students.remove(student);
			student.removeBadge(this);
		}
	}
	
	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Badge other = (Badge) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Badge [id=" + id + ", name=" + name + ", description=" + description + ", imageUrl=" + imageUrl + "]";
	}
}
