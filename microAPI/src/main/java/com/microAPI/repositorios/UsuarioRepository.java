package com.microAPI.repositorios;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.microAPI.entidades.Usuario;


public interface UsuarioRepository extends PagingAndSortingRepository  <Usuario, Integer> {


}
