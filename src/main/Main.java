package main;

import controller.AgendaController;
import controller.ReservaController;
import controller.UsuarioController;
import view.TelaLogin;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Define o estilo visual do sistema para o do SO
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Não foi possível aplicar o look and feel.");
        }

        // Inicializa os controllers
        UsuarioController usuarioController = new UsuarioController();
        AgendaController agendaController = new AgendaController();
        ReservaController reservaController = new ReservaController(agendaController); // integração com a agenda

        // Pré-cadastro de espaços disponíveis (poderia vir de arquivo ou banco futuramente)
        String[] espacos = { "Sala de Reunião 1", "Sala de Reunião 2", "Auditório" };
        for (String espaco : espacos) {
            reservaController.adicionarEspaco(espaco);
        }

        // Pré-cadastro de horários disponíveis na agenda
        agendaController.cadastrarAgenda("Sala de Reunião 1", "25/04/2025", "14:00");
        agendaController.cadastrarAgenda("Sala de Reunião 2", "25/04/2025", "15:00");
        agendaController.cadastrarAgenda("Auditório", "26/04/2025", "10:00");

        // Inicializa a interface gráfica de forma segura na EDT
        SwingUtilities.invokeLater(() -> {
            new TelaLogin(usuarioController, agendaController, reservaController);
        });
    }
}
