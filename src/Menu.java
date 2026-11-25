import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private Impressora impressora;
    private Scanner sc;

    public Menu(Impressora impressora, Scanner sc) {
        this.impressora = impressora;
        this.sc = sc;
    }

    void mostrarMenu() {
        System.out.println("\n===== MENU IMPRESSORA ELGIN =====");
        System.out.println("1  - Configurar impressora");
        System.out.println("2  - Abrir conexao");
        System.out.println("3  - Fechar conexao");
        System.out.println("4  - Imprimir texto");
        System.out.println("5  - Imprimir QR Code");
        System.out.println("6  - Imprimir codigo de barras");
        System.out.println("7  - Imprimir XML SAT");
        System.out.println("8  - Imprimir XML de cancelamento SAT");
        System.out.println("9  - Abrir gaveta");
        System.out.println("10 - Sinal sonoro");
        System.out.println("11 - Avancar papel");
        System.out.println("12 - Status do papel");
        System.out.println("0  - Sair");
    }

    void executar() {
        int opcao;

        do {
            mostrarMenu();
            opcao = lerNumero("Digite a opcao desejada: ");

            switch (opcao) {
                case 1:
                    int tipo = lerNumeroEntre("Tipo (1=USB, 2=Serial, 3=Rede, 4=Bluetooth): ", 1, 4);
                    System.out.print("Modelo (ex.: i9): ");
                    String modelo = sc.nextLine();
                    System.out.print("Conexao (ex.: USB, COM2 ou IP): ");
                    String conexao = sc.nextLine();
                    int parametro = lerNumero("Parametro (0 para USB): ");
                    impressora.configurarImpressora(tipo, modelo, conexao, parametro);
                    break;

                case 2:
                    impressora.abrirConexao();
                    break;

                case 3:
                    impressora.fecharConexao();
                    break;

                case 4:
                    if (!impressora.verificarConexao()) break;
                    System.out.print("Texto a imprimir: ");
                    String texto = sc.nextLine();
                    int copias = lerNumeroEntre("Quantas copias (1 a 10)? ", 1, 10);
                    impressora.imprimirTexto(texto, copias);
                    break;

                case 5:
                    if (!impressora.verificarConexao()) break;
                    System.out.print("Conteudo do QR Code (ex.: um site): ");
                    String conteudoQR = sc.nextLine();
                    impressora.imprimirQRCode(conteudoQR);
                    break;

                case 6:
                    if (!impressora.verificarConexao()) break;
                    impressora.imprimirCodigoBarras();
                    break;

                case 7:
                    if (!impressora.verificarConexao()) break;
                    String xmlVenda = lerArquivo("XMLSAT.xml");
                    if (xmlVenda != null) {
                        impressora.imprimirXMLSAT(xmlVenda);
                    }
                    break;

                case 8:
                    if (!impressora.verificarConexao()) break;
                    String xmlCanc = lerArquivo("CANC_SAT.xml");
                    if (xmlCanc != null) {
                        impressora.imprimirXMLCancelamento(xmlCanc);
                    }
                    break;

                case 9:
                    if (!impressora.verificarConexao()) break;
                    impressora.abrirGaveta();
                    break;

                case 10:
                    if (!impressora.verificarConexao()) break;
                    int bips = lerNumeroEntre("Quantos bips (1 a 63)? ", 1, 63);
                    impressora.sinalSonoro(bips);
                    break;

                case 11:
                    if (!impressora.verificarConexao()) break;
                    int linhas = lerNumeroEntre("Quantas linhas avancar (1 a 20)? ", 1, 20);
                    impressora.avancarPapel(linhas);
                    break;

                case 12:
                    if (!impressora.verificarConexao()) break;
                    impressora.statusPapel();
                    break;

                case 0:
                    System.out.println("Saindo... ate mais!");
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    int lerNumero(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                int numero = sc.nextInt();
                sc.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas numeros!");
                sc.nextLine();
            }
        }
    }

    int lerNumeroEntre(String mensagem, int min, int max) {
        while (true) {
            int numero = lerNumero(mensagem);
            if (numero >= min && numero <= max) {
                return numero;
            }
            System.out.println("Digite um numero entre " + min + " e " + max + ".");
        }
    }

    String lerArquivo(String nomeArquivo) {
        try {
            return Files.readString(Path.of(nomeArquivo));
        } catch (IOException e) {
            System.out.println("Arquivo nao encontrado: " + nomeArquivo
                    + " (coloque-o na pasta principal do projeto)");
            return null;
        }
    }
}