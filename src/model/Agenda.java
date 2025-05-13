package model;

public class Agenda {
    private String espaco;
    private String data;
    private String horario;

    public Agenda(String espaco, String data, String horario) {
        this.espaco = espaco;
        this.data = data;
        this.horario = horario;
    }

    public String getEspaco() {
        return espaco;
    }

    public void setEspaco(String espaco) {
        this.espaco = espaco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return espaco + " - " + data + " às " + horario;
    }

    public boolean conflitaCom(String espaco, String data, String horario) {
        return this.espaco.equals(espaco) && this.data.equals(data) && this.horario.equals(horario);
    }
}
