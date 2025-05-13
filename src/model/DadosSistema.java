package model;

import util.ArquivoUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DadosSistema {
    public static List<String> espacos = new ArrayList<>();
    public static List<String> datas = new ArrayList<>();
    public static List<String> horarios = new ArrayList<>();
    public static List<Agenda> agendaDisponivel = new ArrayList<>();

    private static final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

    public static void adicionarEspaco(String espaco) {
        if (!espacos.contains(espaco)) {
            espacos.add(espaco);
        }
    }
    public static void adicionarData(String data) {
        if (!datas.contains(data)) {
            datas.add(data);
            Collections.sort(datas, new Comparator<String>() {
                @Override
                public int compare(String d1, String d2) {
                    try {
                        Date date1 = formatoData.parse(d1);
                        Date date2 = formatoData.parse(d2);
                        return date1.compareTo(date2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return d1.compareTo(d2);
                    }
                }
            });
        }
    }
    public static void adicionarHorario(String horario) {
        if (!horarios.contains(horario)) {
            horarios.add(horario);
            Collections.sort(horarios);
        }
    }
    public static void adicionarAgenda(Agenda agenda) {
        for (Agenda a : agendaDisponivel) {
            if (a.conflitaCom(agenda.getEspaco(), agenda.getData(), agenda.getHorario())) {
                return;
            }
        }
        agendaDisponivel.add(agenda);
    }
    public static void adicionarReservaDireta(Reserva reserva) {
        controller.ReservaController.adicionarReserva(reserva);
    }
}
