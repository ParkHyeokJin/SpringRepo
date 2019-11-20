package batch.fileToDB;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "PERSON")
public class Person {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String studentId;
	private int korean;
	private int english;
	private int Math;
	private double avg;

	public Person() {
	}
	
	public Person(String name, String studentId, int korean, int english, int math, double avg) {
		this.name = name;
		this.studentId = studentId;
		this.korean = korean;
		this.english = english;
		this.Math = math;
		this.avg = avg;
	}
	
	public void setAvg(double avg) {
		this.avg = avg;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", studentId=" + studentId + ", korean=" + korean + ", english="
				+ english + ", Math=" + Math + ", avg=" + avg + "]";
	}

}
