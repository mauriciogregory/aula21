package com.springDh.aula21.services;

import com.springDh.aula21.dao.IDao;
import com.springDh.aula21.model.Usuario;

import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private final IDao<Usuario> usuarioIDao;

    public UsuarioService(IDao<Usuario> usuarioIDao) {
        this.usuarioIDao = usuarioIDao;
    }

    public Usuario salvar(Usuario usuario){
        return usuarioIDao.salvar(usuario);
    }

    public List<Usuario> buscarTodos(){
        return usuarioIDao.buscarTodos();
    }

    public void excluir(Integer id){
        usuarioIDao.excluir(id);
    }

    public Optional<Usuario> buscar(Integer id){
        return usuarioIDao.buscar(id);
    }

    public Usuario atualizar(Usuario usuario){
        return usuarioIDao.atualizar(usuario);
    }
}
