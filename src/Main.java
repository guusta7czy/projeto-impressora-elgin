import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Usuario usuario = new Usuario("admin", "1234");

        boolean logado = false;
        for (int tentativa = 1; tentativa <= 3; tentativa++) {
            System.out.print("Usuario: ");
            String login = sc.nextLine();
            System.out.print("Senha: ");
            String senha = sc.nextLine();

            if (usuario.autenticar(login, senha)) {
                logado = true;
                System.out.println("Bem-vindo, " + usuario.getLogin() + "!");
                break;
            }
            System.out.println("Usuario ou senha incorretos. Tentativas restantes: " + (3 - tentativa));
        }

        if (!logado) {
            System.out.println("Voce errou 3 vezes. Programa encerrado.");
            sc.close();
            return;
        }

        Impressora impressora = new Impressora();
        Menu menu = new Menu(impressora, sc);

        menu.executar();

        if (impressora.isConexaoAberta()) {
            impressora.fecharConexao();
        }
        sc.close();
    }
}