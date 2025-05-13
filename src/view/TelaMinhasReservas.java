package view;

import controller.ReservaController;
import model.Reserva;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaMinhasReservas extends JFrame {
    public TelaMinhasReservas(Usuario usuarioLogado) {
        setTitle("COWORKZONE - Minhas Reservas");
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Minhas Reservas", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setBounds(100, 10, 200, 30);
        add(titulo);

        JTextArea areaReservas = new JTextArea();
        areaReservas.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaReservas);
        scroll.setBounds(30, 50, 320, 180);
        add(scroll);

        List<Reserva> reservas = ReservaController.listarReservasPorUsuario(usuarioLogado);
        if (reservas.isEmpty()) {
            areaReservas.setText("Nenhuma reserva encontrada.");
        } else {
            for (Reserva r : reservas) {
                areaReservas.append("Espaço: " + r.getEspaco() + " | Data: " + r.getData() + " | Hora: " + r.getHorario() + "\n");
            }
        }

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(130, 250, 120, 30);
        botaoVoltar.setBackground(new Color(51, 51, 41));
        botaoVoltar.setForeground(Color.WHITE);
        add(botaoVoltar);

        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaDashboard(usuarioLogado).setVisible(true);
                dispose();
            }
        });
    }
}
