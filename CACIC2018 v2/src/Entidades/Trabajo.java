package Entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Trabajo {
	
	@Id
	@GeneratedValue
	@Column(name="idTrabajo", updatable = false, nullable = false)
	private int id;
	
	@Column
	private String titulo;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	        name = "autor", 
	        joinColumns = { @JoinColumn(name = "idTrabajo") }, 
	        inverseJoinColumns = { @JoinColumn(name = "idUsuario") }
	    )
	@Column(nullable = true)
	private List<Usuario> autores;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	        name = "Palabra_Clave", 
	        joinColumns = { @JoinColumn(name = "idTrabajo") }, 
	        inverseJoinColumns = { @JoinColumn(name = "idTema") }
	    )
	private List<Tema> palabrasClave;
	
	@Column(nullable = true)
	private boolean esPoster;

	
	public Trabajo (String titulo) {
		this.id = 0;
		this.titulo = titulo;
		this.autores = new ArrayList<Usuario>();
		this.palabrasClave = new ArrayList<Tema>();
		this.esPoster = false;
	}
	
	public Trabajo () {
		this.id = 0;
		this.titulo = null;
		this.autores = new ArrayList<Usuario>();
		this.palabrasClave = new ArrayList<Tema>();
		this.esPoster = false;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isEsPoster() {
		return esPoster;
	}


	public void setEsPoster(boolean esPoster) {
		this.esPoster = esPoster;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public List<Usuario> getAutores() {
		return Collections.unmodifiableList(autores);
	}


	public void setAutor(List<Usuario> autores) {
		this.autores.clear();
		this.autores.addAll(autores);
	}

	public List<Tema> getPalabrasClave() {
		return Collections.unmodifiableList(palabrasClave);
	}


	public void setPalabrasClave(List<Tema> palabrasClave) {
		this.palabrasClave.clear();
		this.palabrasClave.addAll(palabrasClave);
	}
	
	public void agregarPalabraClave(Tema palabraClave) {
		this.palabrasClave.add(palabraClave);
	}
	
	public void agregarAutor(Usuario autor) {
		this.autores.add(autor);
	}


	@Override
	public String toString() {
		return "Trabajo [titulo=" + titulo + ", esPoster = " + esPoster 
//	+ "autores=" + autores + 
//				", "  + "palabrasClave=" + palabrasClave 
				+ "]";
	}
	
}
