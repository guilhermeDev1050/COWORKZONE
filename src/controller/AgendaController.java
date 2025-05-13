package controller;

import model.Agenda;
import model.DadosSistema;

import java.util.List;

public class AgendaController {

    public static boolean cadastrarAgenda(String espaco, String data, String horario) {
        Agenda novaAgenda = new Agenda(espaco, data, horario);

        for (Agenda a : DadosSistema.agendaDisponivel) {
            if (a.conflitaCom(espaco, data, horario)) {
                return false;
            }
        }
        DadosSistema.adicionarEspaco(espaco);
        DadosSistema.adicionarData(data);
        DadosSistema.adicionarHorario(horario);
        DadosSistema.adicionarAgenda(novaAgenda);

        return true;
    }
    public static List<Agenda> listarAgendas() {
        return DadosSistema.agendaDisponivel;
    }
}
