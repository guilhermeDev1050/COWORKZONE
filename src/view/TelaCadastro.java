package view;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastro extends JFrame {
    private JTextField campoNome;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoCadastrar, botaoVoltar;
    private JLabel labelLogin;

    public TelaCadastro() {
        setTitle("COWORKZONE - Cadastro");
        setSize(400, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titulo = new JLabel("COWORKZONE");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(120, 10, 200, 30);
        add(titulo);

        JLabel subtitulo = new JLabel("Cadastre-se");
        subtitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        subtitulo.setBounds(135, 40, 200, 30);
        add(subtitulo);

        JLabel labelNome = new JLabel("Nome");
        labelNome.setBounds(50, 70, 300, 20);
        add(labelNome);

        campoNome = new JTextField();
        campoNome.setBounds(50, 90, 300, 30);
        add(campoNome);

        JLabel labelEmail = new JLabel("E-mail");
        labelEmail.setBounds(50, 130, 300, 20);
        add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(50, 150, 300, 30);
        add(campoEmail);

        JLabel labelSenha = new JLabel("Senha");
        labelSenha.setBounds(50, 190, 300, 20);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(50, 210, 300, 30);
        add(campoSenha);

        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBackground(new Color(51, 51, 41));
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setBounds(50, 260, 300, 30);
        add(botaoCadastrar);


        labelLogin = new JLabel("<html>Já tem uma conta? <span style='text-decoration: underline;'>Faça login</span></html>");
        labelLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        labelLogin.setForeground(Color.BLACK);
        labelLogin.setBounds(110, 300, 250, 20);
        add(labelLogin);

        labelLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new TelaLogin().setVisible(true);
                dispose();
            }
        });

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(50, 330, 300, 30);
        botaoVoltar.setBackground(new Color(51, 51, 41));
        botaoVoltar.setForeground(Color.WHITE);
        add(botaoVoltar);

        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText().trim();
                String email = campoEmail.getText().trim();
                String senha = new String(campoSenha.getPassword()).trim();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!email.matches("^[\\w.-]+@(?:gmail|hotmail|outlook)\\.com$")) {
                    JOptionPane.showMessageDialog(null, "Informe um e-mail válido!!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean sucesso = UsuarioController.cadastrarUsuario(nome, email, senha);
                if (sucesso) {
                    Usuario usuario = new Usuario(nome, email, senha);
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                    new TelaDashboard(usuario).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar. Verifique os dados ou tente outro e-mail.");
                }
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaLogin().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new TelaCadastro().setVisible(true);
    }
}
