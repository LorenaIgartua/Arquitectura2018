package Entidades;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Empresa {
	
	@Id
	@GeneratedValue
	@Column(name="idEmpresa", updatable = false, nullable = false)
	private int id;
	@Column(nullable = true)
	private String nombre;
	@OneToMany(mappedBy="empresa")  
	private List<Usuario> empleados;
	@Column(nullable = true)
	private String direccion;
	@Column(nullable = true)
	private int telefono;
	@Column(nullable = true)
	private int CUIT;
	
	public Empresa () {
		this.id = 0;
		this.nombre = null;
		this.direccion = null;
		this.telefono = 0;
		this.CUIT = 0;
	}
	
	public Empresa (String nombre) {
		this.id = 0;
		this.nombre = nombre;
		this.direccion = null;
		this.telefono = 0;
		this.CUIT = 0;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public int getCUIT() {
		return CUIT;
	}
	public void setCUIT(int cUIT) {
		CUIT = cUIT;
	}
	
	@Override
	public String toString() {
		return "Empresa [id=" + id + ", nombre=" + nombre + ", empleados=" + empleados + ", direccion=" + direccion
				+ ", telefono=" + telefono + ", CUIT=" + CUIT + "]";
	}
	
	public boolean equals (Empresa empresa) {
		if (this.getNombre().equals(empresa.getNombre())) {
			return true;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
