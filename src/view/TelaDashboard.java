package view;

import controller.ReservaController;
import controller.UsuarioController;
import controller.AgendaController;
import model.Reserva;
import view.components.TelaBase;
import view.components.BotaoCustom;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends TelaBase {
    private BotaoCustom btnPerfil, btnAgendar, btnMinhasReservas, btnSair;
    private UsuarioController usuarioController;
    private ReservaController reservaController;
    private AgendaController agendaController;
    private String usuarioEmail;

    public TelaPrincipal(UsuarioController usuarioController, AgendaController agendaController, ReservaController reservaController, String usuarioEmail) {
        super("Área Principal", 450, 500);
        this.usuarioController = usuarioController;
        this.agendaController = agendaController;
        this.reservaController = reservaController;
        this.usuarioEmail = usuarioEmail;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        // Logo
        ImageIcon logoIcon = new ImageIcon("src/assets/logo - Coworking.png");
        JLabel lblLogo = new JLabel(logoIcon);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(lblLogo, BorderLayout.NORTH);

        // Painel de Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        painelBotoes.setBackground(COR_CLARO);
        painelBotoes.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Botão: Meu Perfil
        btnPerfil = new BotaoCustom("Meu Perfil");
        btnPerfil.addActionListener(e -> new TelaPerfilUsuario(usuarioController, usuarioEmail));
        painelBotoes.add(btnPerfil);
        painelBotoes.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botão: Agendar Espaço
        btnAgendar = new BotaoCustom("Agendar Espaço");
        btnAgendar.addActionListener(e -> new TelaReservaEspaco(reservaController, usuarioEmail));
        painelBotoes.add(btnAgendar);
        painelBotoes.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botão: Minhas Reservas
        btnMinhasReservas = new BotaoCustom("Minhas Reservas");
        btnMinhasReservas.addActionListener(e -> exibirReservas());
        painelBotoes.add(btnMinhasReservas);
        painelBotoes.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botão: Sair
        btnSair = new BotaoCustom("Sair");
        btnSair.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                new TelaLogin(usuarioController, agendaController, reservaController);
                dispose();
            }
        });
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.CENTER);
    }

    private void exibirReservas() {
        List<Reserva> reservas = reservaController.getReservasPorUsuario(usuarioEmail);
        StringBuilder sb = new StringBuilder();

        if (reservas == null || reservas.isEmpty()) {
            sb.append("Você ainda não possui reservas.");
        } else {
            for (Reserva r : reservas) {
                sb.append("Espaço: ").append(r.getEspaco())
                        .append(" | Data: ").append(r.getData())
                        .append(" | Horário: ").append(r.getHorario())
                        .append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Minhas Reservas", JOptionPane.INFORMATION_MESSAGE);
    }
}
