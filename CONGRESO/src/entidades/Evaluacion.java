package entidades;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Evaluacion {

	@Id
	@GeneratedValue
	@Column(name="idEvaluacion", nullable = false)
	private int id;
	
	@ManyToOne
	private Trabajo trabajo;
	
	@ManyToOne
	private Usuario evaluador;
	
	@Column(nullable = true)
	private LocalDate fecha;
	
	@Column(nullable = true)
	private String comentario;
	
	public Evaluacion () {
		this.trabajo = null;
		this.evaluador = null;
		this.fecha = LocalDate.now();
		this.comentario = null;
	}
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Trabajo getTrabajo() {
		return trabajo;
	}
	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}
	public Usuario getEvaluador() {
		return evaluador;
	}
	public void setEvaluador(Usuario evaluador) {
		this.evaluador = evaluador;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return "Evaluacion [id=" + id + ", trabajo=" + trabajo 
				+ ", evaluador=" + evaluador 
				+ ", fecha=" + fecha + "]";
	}

}
