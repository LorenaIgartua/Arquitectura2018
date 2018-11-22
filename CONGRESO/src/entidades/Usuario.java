package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Usuario {

	@Id
	@GeneratedValue
	@Column(name="idUsuario", nullable = false)
	private int id;
	
	@Column(name="nombre", nullable = false)
	private String nombre;
	
	@Column(nullable = true)
	private String apellido;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Empresa empresa;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@Column(nullable = true)
	private List<Tema> conocimientos;
	
	@ManyToMany(mappedBy="autores", fetch = FetchType.LAZY)
	@Column(nullable = true)
	@JsonIgnore
	private List<Trabajo> articulosPropios;
	
	public Usuario (String n, String a) {
		this.nombre = n;
		this.apellido = a;
		this.empresa = null;
		this.conocimientos = new ArrayList<Tema>();
		this.articulosPropios = new ArrayList<Trabajo>();
	}
	
	public Usuario () {
		this.nombre = null;
		this.apellido = null;
		this.empresa = null;
		this.conocimientos = new ArrayList<Tema>();
		this.articulosPropios = new ArrayList<Trabajo>();	
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Tema> getConocimientos() {
		return Collections.unmodifiableList(conocimientos);
	}

	public void setConocimientos(List<Tema> conocimientos) {
		this.conocimientos = conocimientos;
	}

	public List<Trabajo> getArticulosPropios() {
		return Collections.unmodifiableList(articulosPropios);
	}

	public void setArticulosPropios(List<Trabajo> articulosPropios) {
		this.articulosPropios.clear();
		this.articulosPropios.addAll(articulosPropios);
	}
	
	public void agregarConocimiento(Tema palabraClave) {
		this.conocimientos.add(palabraClave);
	}

	public void agregarTrabajoPropio(Trabajo trabajo) {
		this.articulosPropios.add(trabajo);
	}
	

	@Override
	public String toString() {
		return "Usuario [id= " + id + " nombre= " + nombre + " " + apellido + ", empresa= " + empresa 
//				+ ", conocimientos= " + conocimientos 
//				+ ", articulosPropios= " + articulosPropios.toString() 
				+ "]";
	}
	
//	public boolean equals (Usuario usuario) {
//		if (this.getNombre().equals(usuario.getNombre()) && this.getApellido().equals(usuario.getApellido())) {
//			return true;
//		}
//		return false;
//	}

	}