package br.com.ecommerce.dao;

import br.com.ecommerce.model.Usuario;

public interface UsuarioDao extends Dao<Usuario> {
    Usuario autenticar(String email, String senha);
    Usuario findByEmail(String email);
}