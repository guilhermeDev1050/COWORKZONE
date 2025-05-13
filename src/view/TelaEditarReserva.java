package view;

import controller.ReservaController;
import model.DadosSistema;
import model.Usuario;
import model.Reserva;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaEditarReserva extends JFrame {
    private JComboBox<String> comboEspaco;
    private JComboBox<String> comboData;
    private JComboBox<String> comboHorario;
    private JButton botaoSalvar, botaoCancelar;

    private Usuario usuarioLogado;
    private Reserva reservaOriginal;

    public TelaEditarReserva(Usuario usuario, Reserva reserva) {
        this.usuarioLogado = usuario;
        this.reservaOriginal = reserva;

        setTitle("COWORKZONE - Editar Reserva");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titulo = new JLabel("COWORKZONE");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(130, 10, 200, 30);
        add(titulo);

        JLabel subtitulo = new JLabel("Editar Reserva");
        subtitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        subtitulo.setBounds(120, 40, 200, 30);
        add(subtitulo);

        JLabel labelEspaco = new JLabel("Espaço");
        labelEspaco.setBounds(50, 80, 300, 20);
        add(labelEspaco);

        comboEspaco = new JComboBox<>(DadosSistema.espacos.toArray(new String[0]));
        comboEspaco.setSelectedItem(reserva.getEspaco());
        comboEspaco.setBounds(50, 100, 300, 30);
        add(comboEspaco);

        JLabel labelData = new JLabel("Data");
        labelData.setBounds(50, 140, 300, 20);
        add(labelData);

        comboData = new JComboBox<>(DadosSistema.datas.toArray(new String[0]));
        comboData.setSelectedItem(reserva.getData());
        comboData.setBounds(50, 160, 300, 30);
        add(comboData);

        JLabel labelHorario = new JLabel("Horário");
        labelHorario.setBounds(50, 200, 300, 20);
        add(labelHorario);

        comboHorario = new JComboBox<>(DadosSistema.horarios.toArray(new String[0]));
        comboHorario.setSelectedItem(reserva.getHorario());
        comboHorario.setBounds(50, 220, 300, 30);
        add(comboHorario);

        botaoSalvar = new JButton("Salvar Alterações");
        botaoSalvar.setBackground(new Color(51, 51, 41));
        botaoSalvar.setForeground(Color.WHITE);
        botaoSalvar.setBounds(50, 270, 140, 30);
        add(botaoSalvar);

        botaoCancelar = new JButton("Cancelar Reserva");
        botaoCancelar.setBounds(210, 270, 140, 30);
        add(botaoCancelar);

        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String novoEspaco = comboEspaco.getSelectedItem().toString();
                String novaData = comboData.getSelectedItem().toString();
                String novoHorario = comboHorario.getSelectedItem().toString();

                boolean sucesso = ReservaController.editarReserva(
                        reservaOriginal.getEspaco(),
                        reservaOriginal.getData(),
                        reservaOriginal.getHorario(),
                        novoEspaco,
                        novaData,
                        novoHorario,
                        usuarioLogado
                );

                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Reserva atualizada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar reserva.");
                }
            }
        });

        botaoCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean sucesso = ReservaController.cancelarReserva(
                        reservaOriginal.getEspaco(),
                        reservaOriginal.getData(),
                        reservaOriginal.getHorario(),
                        usuarioLogado
                );

                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Reserva cancelada com sucesso.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao cancelar reserva.");
                }
            }
        });
    }
}
