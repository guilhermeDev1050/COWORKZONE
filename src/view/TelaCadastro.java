package view;

import controller.AgendaController;
import controller.ReservaController;
import controller.UsuarioController;
import view.components.TelaBase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaCadastroUsuario extends TelaBase {
    private JTextField txtNome, txtEmail;
    private JPasswordField txtSenha;
    private final UsuarioController usuarioController;
    private final AgendaController agendaController;
    private final ReservaController reservaController;

    public TelaCadastroUsuario(UsuarioController usuarioController, AgendaController agendaController, ReservaController reservaController) {
        super("Cadastro de Usuário", 400, 300);
        this.usuarioController = usuarioController;
        this.agendaController = agendaController;
        this.reservaController = reservaController;

        // Título
        JLabel lblTitulo = criarTitulo("Cadastro de Usuário");
        lblTitulo.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel dos campos
        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new BoxLayout(painelCampos, BoxLayout.Y_AXIS));
        painelCampos.setBackground(COR_BRANCO);
        painelCampos.setBorder(new EmptyBorder(10, 40, 10, 40));

        txtNome = new JTextField();
        txtNome.setBorder(BorderFactory.createTitledBorder("Nome"));
        txtNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        painelCampos.add(txtNome);
        painelCampos.add(Box.createRigidArea(new Dimension(0, 10)));

        txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("E-mail"));
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        painelCampos.add(txtEmail);
        painelCampos.add(Box.createRigidArea(new Dimension(0, 10)));

        txtSenha = new JPasswordField();
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha"));
        txtSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        painelCampos.add(txtSenha);

        add(painelCampos, BorderLayout.CENTER);

        // Botão de cadastro
        JButton btnCadastrar = criarBotao("Cadastrar");
        btnCadastrar.setBorder(new EmptyBorder(10, 0, 10, 0));
        btnCadastrar.addActionListener(e -> realizarCadastro());

        add(btnCadastrar, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void realizarCadastro() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches(".+@.+\\..+")) {
            JOptionPane.showMessageDialog(this, "Insira um e-mail válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean sucesso = usuarioController.cadastrarUsuario(email, senha);
        if (sucesso) {
            usuarioController.atualizarUsuario(email, nome, email); // define o nome após cadastro
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            dispose();
            new TelaLogin(usuarioController, agendaController, reservaController);
        } else {
            JOptionPane.showMessageDialog(this, "Erro: e-mail já cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
