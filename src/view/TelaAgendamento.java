package view;

import controller.AgendaController;
import controller.ReservaController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaCadastroAgenda extends TelaBase {
    private JComboBox<String> comboEspacos;
    private JFormattedTextField txtData, txtHorario;
    private final AgendaController agendaController;
    private final ReservaController reservaController;

    public TelaCadastroAgenda(AgendaController agendaController, ReservaController reservaController) {
        super("Cadastro de Agenda", 400, 500); // Título e tamanho da janela
        this.agendaController = agendaController;
        this.reservaController = reservaController;

        JLabel lblTitulo = criarTitulo("Cadastro de Agenda");
        lblTitulo.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new BoxLayout(painelCampos, BoxLayout.Y_AXIS));
        painelCampos.setBackground(COR_BRANCO);
        painelCampos.setBorder(new EmptyBorder(10, 40, 10, 40));

        // ComboBox de espaços
        comboEspacos = new JComboBox<>();
        comboEspacos.setBorder(BorderFactory.createTitledBorder("Espaço"));
        comboEspacos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        carregarEspacosDisponiveis();
        painelCampos.add(comboEspacos);
        painelCampos.add(Box.createRigidArea(new Dimension(0, 10)));

        // Campo com máscara para data
        try {
            MaskFormatter maskData = new MaskFormatter("##/##/####");
            maskData.setPlaceholderCharacter('_');
            txtData = new JFormattedTextField(maskData);
            txtData.setBorder(BorderFactory.createTitledBorder("Data (dd/MM/yyyy)"));
            txtData.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            txtData.setFont(FONTE_TEXTO);
            painelCampos.add(txtData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        painelCampos.add(Box.createRigidArea(new Dimension(0, 10)));

        // Campo com máscara para horário
        try {
            MaskFormatter maskHora = new MaskFormatter("##:##");
            maskHora.setPlaceholderCharacter('_');
            txtHorario = new JFormattedTextField(maskHora);
            txtHorario.setBorder(BorderFactory.createTitledBorder("Horário (HH:mm)"));
            txtHorario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            txtHorario.setFont(FONTE_TEXTO);
            painelCampos.add(txtHorario);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        add(painelCampos, BorderLayout.CENTER);

        JButton btnCadastrar = criarBotao("Cadastrar");
        btnCadastrar.setForeground(COR_PRETO);  // Corrigido aqui
        btnCadastrar.setBackground(COR_ACENTO);
        btnCadastrar.setBorder(new EmptyBorder(10, 20, 10, 20));
        btnCadastrar.addActionListener(e -> cadastrarAgenda());
        add(btnCadastrar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void carregarEspacosDisponiveis() {
        List<String> espacos = reservaController.listarEspacos();
        comboEspacos.removeAllItems();
        if (espacos != null && !espacos.isEmpty()) {
            for (String espaco : espacos) {
                comboEspacos.addItem(espaco);
            }
        } else {
            comboEspacos.addItem("Nenhum espaço disponível");
            comboEspacos.setEnabled(false);
        }
    }

    private void cadastrarAgenda() {
        String espaco = (String) comboEspacos.getSelectedItem();
        String data = txtData.getText().trim();
        String horario = txtHorario.getText().trim();

        if (espaco == null || data.isEmpty() || horario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarData(data, "dd/MM/yyyy")) {
            JOptionPane.showMessageDialog(this, "Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarData(horario, "HH:mm")) {
            JOptionPane.showMessageDialog(this, "Horário inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean sucesso = agendaController.cadastrarAgenda(espaco, data, horario);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Agenda cadastrada com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Já existe um agendamento nesse espaço e horário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarData(String valor, String formato) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            sdf.setLenient(false);
            sdf.parse(valor);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
