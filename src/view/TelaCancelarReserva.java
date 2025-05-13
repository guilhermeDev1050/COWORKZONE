package view;

import controller.ReservaController;
import model.Reserva;
import model.Usuario;
import util.ArquivoUtil; // ⚡️ Para atualizar o CSV após cancelamento!

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaCancelarReserva extends JFrame {
    private JComboBox<String> comboReservas;
    private JButton botaoCancelar, botaoVoltar;
    private Usuario usuarioLogado;

    public TelaCancelarReserva(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("COWORKZONE - Cancelar Reserva");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titulo = new JLabel("Cancelar Reserva");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setBounds(110, 20, 250, 30);
        add(titulo);

        JLabel labelSelecao = new JLabel("Selecione sua reserva:");
        labelSelecao.setBounds(50, 70, 300, 20);
        add(labelSelecao);

        comboReservas = new JComboBox<>();
        comboReservas.setBounds(50, 100, 300, 30);
        preencherReservas();
        add(comboReservas);

        botaoCancelar = new JButton("Cancelar Reserva");
        botaoCancelar.setBounds(50, 150, 140, 30);
        botaoCancelar.setBackground(new Color(51, 51, 41));
        botaoCancelar.setForeground(Color.WHITE);
        add(botaoCancelar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(210, 150, 140, 30);
        add(botaoVoltar);

        botaoCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selecionada = (String) comboReservas.getSelectedItem();
                if (selecionada == null) {
                    JOptionPane.showMessageDialog(null, "Nenhuma reserva selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] partes = selecionada.split(" \\| ");
                if (partes.length < 3) {
                    JOptionPane.showMessageDialog(null, "Formato de reserva inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String espaco = partes[0];
                String data = partes[1];
                String horario = partes[2];

                boolean sucesso = ReservaController.cancelarReserva(espaco, data, horario, usuarioLogado);
                if (sucesso) {
                    // Atualiza o CSV após cancelar
                    ArquivoUtil.atualizarReservasCSV(ReservaController.listarTodasReservas());

                    JOptionPane.showMessageDialog(null, "Reserva cancelada com sucesso!");
                    new TelaDashboard(usuarioLogado).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao cancelar reserva.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaDashboard(usuarioLogado).setVisible(true);
                dispose();
            }
        });
    }

    private void preencherReservas() {
        List<Reserva> reservas = ReservaController.listarReservasPorUsuario(usuarioLogado);
        for (Reserva r : reservas) {
            comboReservas.addItem(r.getEspaco() + " | " + r.getData() + " | " + r.getHorario());
        }
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Esta tela deve ser chamada com um usuário logado.");
    }
}
