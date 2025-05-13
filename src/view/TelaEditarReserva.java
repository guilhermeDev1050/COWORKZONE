package view;

import controller.ReservaController;
import model.Reserva;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TelaReservaEspaco extends TelaBase {
    private CampoTextoCustom txtNome;
    private JComboBox<String> cbData, cbHorario, cbEspaco;
    private ReservaController reservaController;
    private String emailUsuario;

    public TelaReservaEspaco(ReservaController reservaController, String emailUsuario) {
        super("Agendar Espaço", 400, 460);
        this.reservaController = reservaController;
        this.emailUsuario = emailUsuario;

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JLabel lblTitulo = criarTitulo("Agendar Espaço");
        lblTitulo.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBackground(COR_CLARO);
        painelFormulario.setBorder(new EmptyBorder(10, 40, 10, 40));

        txtNome = new CampoTextoCustom("Seu nome");
        painelFormulario.add(txtNome);
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 12)));

        cbData = new JComboBox<>();
        LocalDate hoje = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            cbData.addItem(hoje.plusDays(i).toString());
        }
        cbData.setBorder(BorderFactory.createTitledBorder("Data"));
        cbData.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        painelFormulario.add(cbData);
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 12)));

        cbHorario = new JComboBox<>();
        for (int h = 8; h <= 18; h++) {
            cbHorario.addItem(String.format("%02d:00", h));
        }
        cbHorario.setBorder(BorderFactory.createTitledBorder("Horário"));
        cbHorario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        painelFormulario.add(cbHorario);
        painelFormulario.add(Box.createRigidArea(new Dimension(0, 12)));

        cbEspaco = new JComboBox<>();
        atualizarEspacos();
        cbEspaco.setBorder(BorderFactory.createTitledBorder("Espaço"));
        cbEspaco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        painelFormulario.add(cbEspaco);

        add(painelFormulario, BorderLayout.CENTER);

        BotaoCustom btnReservar = new BotaoCustom("Reservar");
        btnReservar.setBorder(new EmptyBorder(12, 20, 12, 20));
        btnReservar.addActionListener(e -> realizarReserva());

        JPanel painelBotao = new JPanel();
        painelBotao.setBackground(COR_CLARO);
        painelBotao.add(btnReservar);

        add(painelBotao, BorderLayout.SOUTH);
    }

    private void realizarReserva() {
        String nome = txtNome.getText().trim();
        String data = (String) cbData.getSelectedItem();
        String horario = (String) cbHorario.getSelectedItem();
        String espaco = (String) cbEspaco.getSelectedItem();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe seu nome.");
            return;
        }

        if (espaco == null || espaco.equals("Nenhum espaço disponível")) {
            JOptionPane.showMessageDialog(this, "Nenhum espaço disponível para agendamento.");
            return;
        }

        Reserva reserva = new Reserva(nome, data, horario, espaco, emailUsuario);

        if (reservaController.realizarReserva(reserva)) {
            JOptionPane.showMessageDialog(this, "Reserva feita com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao realizar reserva. Verifique a disponibilidade.");
        }
    }

    private void atualizarEspacos() {
        cbEspaco.removeAllItems();
        List<String> espacos = reservaController.listarEspacos();

        if (espacos == null || espacos.isEmpty()) {
            cbEspaco.addItem("Nenhum espaço disponível");
            cbEspaco.setEnabled(false);
        } else {
            for (String espaco : espacos) {
                cbEspaco.addItem(espaco);
            }
            cbEspaco.setEnabled(true);
        }
    }
}
