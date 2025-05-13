package view;

import controller.ReservaController;
import model.Usuario;
import model.Reserva;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaEditarPerfil extends JFrame {
    private JTextField campoNome;
    private JTextField campoEmail;
    private JTextArea areaReservas;
    private JButton botaoSalvar, botaoVoltar;
    private Usuario usuarioLogado;

    public TelaEditarPerfil(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("COWORKZONE - Perfil");
        setSize(400, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titulo = new JLabel("COWORKZONE");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(130, 10, 200, 30);
        add(titulo);

        JLabel perfil = new JLabel("Perfil");
        perfil.setFont(new Font("SansSerif", Font.BOLD, 18));
        perfil.setBounds(160, 40, 100, 30);
        add(perfil);

        JLabel labelNome = new JLabel("Nome");
        labelNome.setBounds(50, 80, 300, 20);
        add(labelNome);

        campoNome = new JTextField(usuarioLogado.getNome());
        campoNome.setBounds(50, 100, 300, 30);
        add(campoNome);

        JLabel labelEmail = new JLabel("E-mail");
        labelEmail.setBounds(50, 140, 300, 20);
        add(labelEmail);

        campoEmail = new JTextField(usuarioLogado.getEmail());
        campoEmail.setBounds(50, 160, 300, 30);
        add(campoEmail);

        JLabel labelReservas = new JLabel("Reservas");
        labelReservas.setBounds(50, 200, 300, 20);
        add(labelReservas);

        areaReservas = new JTextArea();
        areaReservas.setEditable(false);
        areaReservas.setBounds(50, 220, 300, 60);
        add(areaReservas);

        List<Reserva> reservas = ReservaController.listarReservasPorUsuario(usuarioLogado);
        for (Reserva r : reservas) {
            areaReservas.append(r.getEspaco() + ", " + r.getData() + ", " + r.getHorario() + "\n");
        }

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.setBackground(new Color(51, 51, 41));
        botaoSalvar.setForeground(Color.WHITE);
        botaoSalvar.setBounds(50, 300, 140, 30);
        add(botaoSalvar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(210, 300, 140, 30);
        add(botaoVoltar);

        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String novoNome = campoNome.getText().trim();
                String novoEmail = campoEmail.getText().trim();

                if (!novoNome.isEmpty()) usuarioLogado.setNome(novoNome);
                if (!novoEmail.isEmpty()) usuarioLogado.setEmail(novoEmail);

                JOptionPane.showMessageDialog(null, "Perfil atualizado com sucesso!");
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaDashboard(usuarioLogado).setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Esta tela deve ser chamada com um usuário logado.");
    }
}
