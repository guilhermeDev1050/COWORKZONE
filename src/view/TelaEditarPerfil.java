package view;

import controller.UsuarioController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaPerfilUsuario extends TelaBase {
    private final CampoTextoCustom txtNome;
    private final CampoTextoCustom txtEmail;
    private final BotaoCustom btnSalvar;
    private final UsuarioController usuarioController;
    private String emailAtual;

    public TelaPerfilUsuario(UsuarioController usuarioController, String emailAtual) {
        super("Meu Perfil", 400, 280);
        this.usuarioController = usuarioController;
        this.emailAtual = emailAtual;

        JLabel lblTitulo = criarTitulo("Meu Perfil");
        lblTitulo.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new BoxLayout(painelCampos, BoxLayout.Y_AXIS));
        painelCampos.setBackground(COR_CLARO);
        painelCampos.setBorder(new EmptyBorder(10, 40, 10, 40));

        txtNome = new CampoTextoCustom("Nome");
        txtEmail = new CampoTextoCustom("E-mail");

        painelCampos.add(txtNome);
        painelCampos.add(Box.createRigidArea(new Dimension(0, 12)));
        painelCampos.add(txtEmail);

        add(painelCampos, BorderLayout.CENTER);

        carregarDadosDoUsuario();

        btnSalvar = new BotaoCustom("Salvar Alterações");
        btnSalvar.setBorder(new EmptyBorder(12, 20, 12, 20));
        btnSalvar.addActionListener(e -> salvarPerfil());

        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(COR_CLARO);
        painelBotao.add(btnSalvar);
        add(painelBotao, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarDadosDoUsuario() {
        String nome = usuarioController.getNome(emailAtual);
        txtNome.setText(nome != null ? nome : "");
        txtEmail.setText(emailAtual);
    }

    private void salvarPerfil() {
        String novoNome = txtNome.getText().trim();
        String novoEmail = txtEmail.getText().trim();

        if (novoNome.isEmpty() || novoEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!novoEmail.matches(".+@.+\\..+")) {
            JOptionPane.showMessageDialog(this, "E-mail inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean sucesso = usuarioController.atualizarUsuario(emailAtual, novoNome, novoEmail);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Perfil atualizado com sucesso!");
            emailAtual = novoEmail;
        } else {
            JOptionPane.showMessageDialog(this, "Erro: e-mail já está em uso por outro usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
