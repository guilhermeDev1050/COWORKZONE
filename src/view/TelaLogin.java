package view;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar, botaoCadastrar;

    public TelaLogin() {
        setTitle("COWORKZONE - Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);


        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/logo.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("Erro ao carregar ícone: " + e.getMessage());
        }


        JLabel titulo = new JLabel("COWORKZONE");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(130, 10, 200, 30);
        add(titulo);

        JLabel labelEmail = new JLabel("E-mail");
        labelEmail.setBounds(50, 60, 300, 20);
        add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(50, 80, 300, 30);
        add(campoEmail);

        JLabel labelSenha = new JLabel("Senha");
        labelSenha.setBounds(50, 120, 300, 20);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(50, 140, 300, 30);
        add(campoSenha);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBackground(new Color(51, 51, 41));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setBounds(50, 190, 300, 30);
        add(botaoEntrar);

        botaoCadastrar = new JButton("Criar conta");
        botaoCadastrar.setBorderPainted(false);
        botaoCadastrar.setContentAreaFilled(false);
        botaoCadastrar.setBounds(150, 230, 100, 20);
        add(botaoCadastrar);

        botaoEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = campoEmail.getText().trim();
                String senha = new String(campoSenha.getPassword()).trim();

                if (email.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                    return;
                }

                Usuario usuario = UsuarioController.login(email, senha);

                if (usuario != null) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido! Bem-vindo(a), " + usuario.getNome());
                    new TelaDashboard(usuario).setVisible(true);
                    dispose(); // Fecha a tela de login
                } else {
                    JOptionPane.showMessageDialog(null, "E-mail ou senha incorretos.");
                }
            }
        });



        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaCadastro().setVisible(true);
                dispose();
            }
        });

    }

    public static void main(String[] args) {
        new TelaLogin().setVisible(true);
    }
}
