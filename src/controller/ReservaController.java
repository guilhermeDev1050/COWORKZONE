package controller;

import model.Reserva;
import model.Usuario;
import util.ArquivoUtil;

import java.util.ArrayList;
import java.util.List;

public class ReservaController {
    private static List<Reserva> reservas = new ArrayList<>();

    public static void criarReserva(String espaco, String data, String horario, Usuario usuario) {
        Reserva novaReserva = new Reserva(espaco, data, horario, usuario);
        reservas.add(novaReserva);
        ArquivoUtil.salvarNovaReserva(novaReserva);
    }

    public static boolean cancelarReserva(String espaco, String data, String horario, Usuario usuario) {
        boolean sucesso = reservas.removeIf(r ->
                r.getUsuario().equals(usuario) &&
                        r.getEspaco().equals(espaco) &&
                        r.getData().equals(data) &&
                        r.getHorario().equals(horario)
        );
        if (sucesso) {
            ArquivoUtil.atualizarReservasCSV(reservas); // 🔥 Atualiza o CSV depois de cancelar
        }
        return sucesso;
    }

    public static boolean editarReserva(String espacoAntigo, String dataAntiga, String horarioAntigo,
                                        String novoEspaco, String novaData, String novoHorario, Usuario usuario) {
        for (Reserva r : reservas) {
            if (r.getUsuario().equals(usuario)
                    && r.getEspaco().equals(espacoAntigo)
                    && r.getData().equals(dataAntiga)
                    && r.getHorario().equals(horarioAntigo)) {

                r.setEspaco(novoEspaco);
                r.setData(novaData);
                r.setHorario(novoHorario);
                ArquivoUtil.atualizarReservasCSV(reservas); // Atualiza CSV após edição
                return true;
            }
        }
        return false;
    }

    public static List<Reserva> listarReservasPorUsuario(Usuario usuario) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getUsuario().equals(usuario)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    public static List<Reserva> listarTodasReservas() {
        return reservas;
    }

    public static boolean reservaExiste(String espaco, String data, String horario) {
        for (Reserva r : reservas) {
            if (r.getEspaco().equals(espaco) && r.getData().equals(data) && r.getHorario().equals(horario)) {
                return true;
            }
        }
        return false;
    }

    public static void adicionarReserva(Reserva reserva) {
        reservas.add(reserva);
    }
}
