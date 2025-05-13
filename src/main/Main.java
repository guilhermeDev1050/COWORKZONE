package main;

import util.ArquivoUtil;
import model.DadosSistema;
import view.TelaLogin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {


        ArquivoUtil.carregarUsuarios("src/arquivos/usuarios.csv");
        ArquivoUtil.carregarEspacos("src/arquivos/espacos.csv");
        ArquivoUtil.carregarReservas("src/arquivos/reservas.csv");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate hoje = LocalDate.now();
        for (int i = 0; i < 15; i++) {
            String dataFormatada = hoje.plusDays(i).format(formatter);
            DadosSistema.adicionarData(dataFormatada);
        }


        String[] horarios = {
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
                "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
                "17:00", "17:30"
        };
        for (String horario : horarios) {
            DadosSistema.adicionarHorario(horario);
        }


        javax.swing.SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}
