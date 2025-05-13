package view;

import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaConfirmacaoReserva extends JFrame {
    public TelaConfirmacaoReserva(String espaco, String data, String horario, Usuario usuarioLogado) {
        setTitle("COWORKZONE - Reserva Confirmada");
        setSize(400, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titulo = new JLabel("COWORKZONE");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(120, 10, 200, 30);
        add(titulo);

        JLabel mensagem = new JLabel("Reserva Confirmada");
        mensagem.setFont(new Font("SansSerif", Font.BOLD, 18));
        mensagem.setBounds(110, 50, 250, 30);
        add(mensagem);

        JLabel submensagem = new JLabel("Espaço: " + espaco + " | Data: " + data + " | Hora: " + horario);
        submensagem.setFont(new Font("SansSerif", Font.PLAIN, 14));
        submensagem.setBounds(40, 85, 320, 20);
        add(submensagem);

        JButton botaoOk = new JButton("OK");
        botaoOk.setBackground(new Color(51, 51, 41));
        botaoOk.setForeground(Color.WHITE);
        botaoOk.setBounds(140, 130, 120, 30);
        add(botaoOk);

        botaoOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaDashboard(usuarioLogado).setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Esta tela deve ser chamada com os dados da reserva confirmada e o usuário logado.");
    }
}
