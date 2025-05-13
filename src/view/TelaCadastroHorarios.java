package view;

import model.DadosSistema;
import model.Agenda;
import model.Usuario;
import util.ArquivoUtil;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TelaCadastroHorarios extends JFrame {
    private JTextField campoEspaco;
    private JFormattedTextField campoData;
    private JFormattedTextField campoHorario;
    private JButton botaoSalvar, botaoVoltar;

    public TelaCadastroHorarios(String usuario, String senha) {
        if (!usuario.equals("admin") || !senha.equals("admin123")) {
            JOptionPane.showMessageDialog(null, "Acesso negado. Apenas administradores podem acessar esta tela.");
            dispose();
            return;
        }

        setTitle("COWORKZONE - Cadastro de Horários");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel titulo = new JLabel("Cadastrar Espaço, Data e Horário");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBounds(70, 20, 320, 30);
        add(titulo);

        campoEspaco = new JTextField();
        campoEspaco.setBounds(50, 70, 340, 40);
        campoEspaco.setBorder(BorderFactory.createTitledBorder("Espaço"));
        add(campoEspaco);

        try {
            MaskFormatter maskData = new MaskFormatter("##/##/####");
            maskData.setPlaceholderCharacter('_');
            campoData = new JFormattedTextField(maskData);
            campoData.setBounds(50, 130, 340, 40);
            campoData.setBorder(BorderFactory.createTitledBorder("Data"));
            add(campoData);

            MaskFormatter maskHora = new MaskFormatter("##:##");
            maskHora.setPlaceholderCharacter('_');
            campoHorario = new JFormattedTextField(maskHora);
            campoHorario.setBounds(50, 190, 340, 40);
            campoHorario.setBorder(BorderFactory.createTitledBorder("Horário"));
            add(campoHorario);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.setBounds(90, 250, 120, 40);
        botaoSalvar.setBackground(new Color(51, 51, 41));
        botaoSalvar.setForeground(Color.WHITE);
        add(botaoSalvar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(230, 250, 120, 40);
        add(botaoVoltar);

        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String espaco = campoEspaco.getText().trim();
                String data = campoData.getText().trim();
                String horario = campoHorario.getText().trim();

                boolean erro = false;
                if (espaco.isEmpty()) {
                    campoEspaco.setBorder(BorderFactory.createLineBorder(Color.RED));
                    erro = true;
                } else {
                    campoEspaco.setBorder(BorderFactory.createTitledBorder("Espaço"));
                }
                if (!data.matches("\\d{2}/\\d{2}/\\d{4}") || !dataValida(data)) {
                    campoData.setBorder(BorderFactory.createLineBorder(Color.RED));
                    erro = true;
                } else {
                    campoData.setBorder(BorderFactory.createTitledBorder("Data"));
                }
                if (!horario.matches("\\d{2}:\\d{2}") || !horarioValido(horario)) {
                    campoHorario.setBorder(BorderFactory.createLineBorder(Color.RED));
                    erro = true;
                } else {
                    campoHorario.setBorder(BorderFactory.createTitledBorder("Horário"));
                }

                if (erro) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
                    return;
                }

                Agenda novaAgenda = new Agenda(espaco, data, horario);
                boolean existe = false;
                for (Agenda a : DadosSistema.agendaDisponivel) {
                    if (a.conflitaCom(espaco, data, horario)) {
                        existe = true;
                        break;
                    }
                }

                if (existe) {
                    JOptionPane.showMessageDialog(null, "Este horário já está cadastrado para este espaço.");
                } else {
                    DadosSistema.adicionarEspaco(espaco);
                    DadosSistema.adicionarData(data);
                    DadosSistema.adicionarHorario(horario);
                    DadosSistema.adicionarAgenda(novaAgenda);

                    ArquivoUtil.salvarNovoEspaco(espaco);

                    JOptionPane.showMessageDialog(null, "Horário cadastrado com sucesso!");
                    campoEspaco.setText("");
                    campoData.setText("");
                    campoHorario.setText("");
                    campoEspaco.setBorder(BorderFactory.createTitledBorder("Espaço"));
                    campoData.setBorder(BorderFactory.createTitledBorder("Data"));
                    campoHorario.setBorder(BorderFactory.createTitledBorder("Horário"));
                }
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaDashboard(new Usuario("Admin", "admin", "admin123")).setVisible(true);
                dispose();
            }
        });
    }

    private boolean dataValida(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean horarioValido(String horario) {
        try {
            String[] partes = horario.split(":");
            int hora = Integer.parseInt(partes[0]);
            int minuto = Integer.parseInt(partes[1]);
            return hora >= 0 && hora < 24 && minuto >= 0 && minuto < 60;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        new TelaCadastroHorarios("admin", "admin123").setVisible(true);
    }
}
