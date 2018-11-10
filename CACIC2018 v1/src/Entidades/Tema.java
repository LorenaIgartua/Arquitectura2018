package Entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tema {

	@Id
	@GeneratedValue
	@Column(name="idTema", updatable = false, nullable = false)
	private int id;
	@Column(nullable = true)
	private String nombre;
	@Column(nullable = true)
	private boolean esEspecifico;
	
	public Tema () {
		this.nombre = "";
		this.esEspecifico = false;
	}
	
	
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}




	public boolean isEsEspecifico() {
		return esEspecifico;
	}




	public Tema (String nombre) {
		this.nombre = nombre;
		this.esEspecifico = false;
	}
	
	public Tema (String nombre, boolean espec) {
		this.nombre = nombre;
		this.esEspecifico = espec;
	}
	

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public boolean esEspecifico() {
		return this.esEspecifico;
	}


	public void setEsEspecifico(boolean espec) {
		this.esEspecifico = espec;
	}


	@Override
	public String toString() {
		if (this.esEspecifico)
			return "Tema [nombre=" + nombre + ", es Especifico.]";
		else
			return "Tema [nombre=" + nombre + ", es General.]";
	}
	
}
