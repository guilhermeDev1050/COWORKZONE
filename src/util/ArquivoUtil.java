package util;

import model.Usuario;
import model.Reserva;
import model.DadosSistema;
import controller.UsuarioController;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ArquivoUtil {

    public static void carregarUsuarios(String caminho) {
        try (Scanner scanner = new Scanner(new File(caminho))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // pula o cabeçalho
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(",");
                if (partes.length >= 3) {
                    String nome = partes[0].trim();
                    String email = partes[1].trim();
                    String senha = partes[2].trim();
                    UsuarioController.cadastrarUsuario(nome, email, senha, false);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    public static void carregarEspacos(String caminho) {
        try (Scanner scanner = new Scanner(new File(caminho))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // Pula o cabeçalho
            while (scanner.hasNextLine()) {
                String espaco = scanner.nextLine().trim();
                if (!espaco.isEmpty()) {
                    DadosSistema.adicionarEspaco(espaco);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar espaços: " + e.getMessage());
        }
    }

    public static void carregarReservas(String caminho) {
        try (Scanner scanner = new Scanner(new File(caminho))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // Pula o cabeçalho
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(",");
                if (partes.length >= 4) {
                    String emailUsuario = partes[0].trim();
                    String espaco = partes[1].trim();
                    String data = partes[2].trim();
                    String horario = partes[3].trim();

                    Usuario usuario = UsuarioController.buscarUsuarioPorEmail(emailUsuario);
                    if (usuario != null) {
                        DadosSistema.adicionarReservaDireta(new Reserva(espaco, data, horario, usuario));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar reservas: " + e.getMessage());
        }
    }

    public static void salvarNovoUsuario(Usuario usuario) {
        try (FileWriter writer = new FileWriter("src/arquivos/usuarios.csv", true)) {
            writer.write(usuario.getNome() + "," +
                    usuario.getEmail() + "," +
                    usuario.getSenha() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar novo usuário: " + e.getMessage());
        }
    }

    public static void salvarNovoEspaco(String espaco) {
        try (FileWriter writer = new FileWriter("src/arquivos/espacos.csv", true)) {
            writer.write("\n" + espaco);
        } catch (IOException e) {
            System.out.println("Erro ao salvar novo espaço: " + e.getMessage());
        }
    }

    public static void salvarNovaReserva(Reserva reserva) {
        try (FileWriter writer = new FileWriter("src/arquivos/reservas.csv", true)) {
            writer.write(reserva.getUsuario().getEmail() + "," +
                    reserva.getEspaco() + "," +
                    reserva.getData() + "," +
                    reserva.getHorario() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar reserva: " + e.getMessage());
        }
    }

    public static void atualizarReservasCSV(List<Reserva> reservas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/arquivos/reservas.csv"))) {
            writer.println("email,espaco,data,horario"); // Cabeçalho
            for (Reserva reserva : reservas) {
                writer.println(reserva.getUsuario().getEmail() + "," +
                        reserva.getEspaco() + "," +
                        reserva.getData() + "," +
                        reserva.getHorario());
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar reservas: " + e.getMessage());
        }
    }
}
