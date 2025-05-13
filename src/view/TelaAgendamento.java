package view;

import controller.ReservaController;
import model.DadosSistema;
import model.Reserva;
import model.Usuario;
import util.ArquivoUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaAgendamento extends JFrame {
    private JComboBox<String> comboEspaco;
    private JComboBox<String> comboData;
    private JComboBox<String> comboHorario;
    private JButton botaoConfirmar, botaoVoltar;
    private Usuario usuarioLogado;

    public TelaAgendamento(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("COWORKZONE - Agendamento");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titulo = new JLabel("COWORKZONE");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(130, 10, 200, 30);
        add(titulo);

        JLabel subtitulo = new JLabel("Agendar Espaço");
        subtitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        subtitulo.setBounds(120, 40, 200, 30);
        add(subtitulo);

        JLabel labelEspaco = new JLabel("Espaço");
        labelEspaco.setBounds(50, 80, 300, 20);
        add(labelEspaco);

        comboEspaco = new JComboBox<>(DadosSistema.espacos.toArray(new String[0]));
        comboEspaco.setBounds(50, 100, 300, 30);
        add(comboEspaco);

        JLabel labelData = new JLabel("Data");
        labelData.setBounds(50, 140, 300, 20);
        add(labelData);

        comboData = new JComboBox<>(DadosSistema.datas.toArray(new String[0]));
        comboData.setBounds(50, 160, 300, 30);
        add(comboData);

        JLabel labelHorario = new JLabel("Horário");
        labelHorario.setBounds(50, 200, 300, 20);
        add(labelHorario);

        comboHorario = new JComboBox<>(DadosSistema.horarios.toArray(new String[0]));
        comboHorario.setBounds(50, 220, 300, 30);
        add(comboHorario);

        botaoConfirmar = new JButton("Confirmar Reserva");
        botaoConfirmar.setBackground(new Color(51, 51, 41));
        botaoConfirmar.setForeground(Color.WHITE);
        botaoConfirmar.setBounds(50, 270, 300, 30);
        add(botaoConfirmar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(50, 310, 300, 30);
        add(botaoVoltar);

        botaoConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String espaco = comboEspaco.getSelectedItem().toString();
                String data = comboData.getSelectedItem().toString();
                String horario = comboHorario.getSelectedItem().toString();

                if (espaco.isEmpty() || data.isEmpty() || horario.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos para reservar.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // ✅ Aqui verifica se já existe uma reserva
                if (ReservaController.reservaExiste(espaco, data, horario)) {
                    JOptionPane.showMessageDialog(null, "❌ O espaço " + espaco + " já está reservado para " + data + " às " + horario + ".", "Reserva Indisponível", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Caso contrário, cria a reserva
                ReservaController.criarReserva(espaco, data, horario, usuarioLogado);
                JOptionPane.showMessageDialog(null, "✅ Reserva confirmada para " + espaco + ", dia " + data + ", às " + horario);
                new TelaDashboard(usuarioLogado).setVisible(true);
                dispose();
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
        Usuario usuarioTeste = new Usuario("João Teste", "teste@email.com", "123");
        new TelaAgendamento(usuarioTeste).setVisible(true);
    }
}
