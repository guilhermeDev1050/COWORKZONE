package controller;

import model.Usuario;
import util.ArquivoUtil;

import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private static List<Usuario> usuarios = new ArrayList<>();

    // Versão normal: cadastra e salva no CSV
    public static boolean cadastrarUsuario(String nome, String email, String senha) {
        return cadastrarUsuario(nome, email, senha, true); // chama a versão abaixo, pedindo para salvar
    }

    // Versão com controle se deve salvar no CSV
    public static boolean cadastrarUsuario(String nome, String email, String senha, boolean salvarNoCSV) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return false; // Email já cadastrado
            }
        }
        Usuario novoUsuario = new Usuario(nome, email, senha);
        usuarios.add(novoUsuario);

        if (salvarNoCSV) {
            ArquivoUtil.salvarNovoUsuario(novoUsuario);
        }

        return true;
    }

    public static Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    public static Usuario login(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }

    public static List<Usuario> listarUsuarios() {
        return usuarios;
    }
}
