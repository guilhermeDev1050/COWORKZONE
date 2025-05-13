package model;

public class Reserva {
    private String espaco;
    private String data;
    private String horario;
    private Usuario usuario;

    public Reserva(String espaco, String data, String horario, Usuario usuario) {
        this.espaco = espaco;
        this.data = data;
        this.horario = horario;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Reserva de " + usuario.getNome() + ": " + espaco + ", " + data + ", às " + horario;
    }
}
