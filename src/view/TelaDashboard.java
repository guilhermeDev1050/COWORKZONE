package view;

import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaDashboard extends JFrame {
    private JButton btnNovaReserva, btnMinhasReservas, btnCancelarReserva, btnEditarPerfil, btnSair, btnCadastroHorarios;
    private Usuario usuarioLogado;

    public TelaDashboard(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("COWORKZONE - Dashboard");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titulo = new JLabel("COWORKZONE");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(130, 10, 200, 30);
        add(titulo);

        JLabel boasVindas = new JLabel("Bem-vindo, " + usuarioLogado.getNome() + "!");
        boasVindas.setFont(new Font("SansSerif", Font.BOLD, 16));
        boasVindas.setBounds(100, 50, 250, 30);
        add(boasVindas);

        btnNovaReserva = new JButton("+ Nova Reserva");
        btnNovaReserva.setBounds(80, 100, 240, 30);
        configurarBotao(btnNovaReserva);
        add(btnNovaReserva);

        btnMinhasReservas = new JButton("Minhas Reservas");
        btnMinhasReservas.setBounds(80, 140, 240, 30);
        configurarBotao(btnMinhasReservas);
        add(btnMinhasReservas);

        btnCancelarReserva = new JButton("Cancelar Reserva");
        btnCancelarReserva.setBounds(80, 180, 240, 30);
        configurarBotao(btnCancelarReserva);
        add(btnCancelarReserva);

        btnEditarPerfil = new JButton("Editar Perfil");
        btnEditarPerfil.setBounds(80, 220, 240, 30);
        configurarBotao(btnEditarPerfil);
        add(btnEditarPerfil);

        int proximaAltura = 260;

        if (usuarioLogado.getEmail().equals("admin") && usuarioLogado.getSenha().equals("admin123")) {
            btnCadastroHorarios = new JButton("Cadastro de Espaços");
            btnCadastroHorarios.setBounds(80, proximaAltura, 240, 30);
            configurarBotao(btnCadastroHorarios);
            add(btnCadastroHorarios);
            proximaAltura += 40;

            btnCadastroHorarios.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new TelaCadastroHorarios("admin", "admin123").setVisible(true);
                    dispose();
                }
            });
        }

        btnSair = new JButton("Sair");
        btnSair.setBounds(80, proximaAltura, 240, 30);
        configurarBotao(btnSair);
        add(btnSair);

        // Ações dos botões
        btnNovaReserva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaAgendamento(usuarioLogado).setVisible(true);
                dispose();
            }
        });

        btnMinhasReservas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaMinhasReservas(usuarioLogado).setVisible(true);
                dispose();
            }
        });

        btnCancelarReserva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaCancelarReserva(usuarioLogado).setVisible(true);
                dispose();
            }
        });

        btnEditarPerfil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaEditarPerfil(usuarioLogado).setVisible(true);
                dispose();
            }
        });

        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaLogin().setVisible(true);
                dispose();
            }
        });
    }

    private void configurarBotao(JButton botao) {
        botao.setBackground(new Color(51, 51, 41));
        botao.setForeground(Color.WHITE);
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Esta tela deve ser chamada com um usuário logado.");
    }
}
