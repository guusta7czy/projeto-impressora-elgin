public class Impressora {

    private int tipo;
    private String modelo;
    private String conexao;
    private int parametro;
    private boolean conexaoAberta = false;

    private static final String ASS_QRCODE_CANCELAMENTO =
            "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0"
                    + "E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQ"
                    + "sE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+Z"
                    + "fO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

    public Impressora() {
        this.tipo = 1;
        this.modelo = "i9";
        this.conexao = "USB";
        this.parametro = 0;
    }

    void configurarImpressora(int tipo, String modelo, String conexao, int parametro) {
        this.tipo = tipo;
        this.modelo = modelo;
        this.conexao = conexao;
        this.parametro = parametro;
        System.out.println("Dados salvos com sucesso.");
    }

    boolean verificarConexao() {
        if (!conexaoAberta) {
            System.out.println("Conexao fechada. Abra a conexao antes.");
            return false;
        }
        return true;
    }

    boolean isConexaoAberta() {
        return conexaoAberta;
    }

    void abrirConexao() {
        if (conexaoAberta) {
            System.out.println("A conexao ja esta aberta.");
            return;
        }
        try {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexao com a impressora ok.");
            } else {
                System.out.println("Erro ao abrir conexao. Retorno: " + retorno
                        + " (confira se a impressora esta ligada e o cabo conectado)");
            }
        } catch (UnsatisfiedLinkError | NoClassDefFoundError e) {
            System.out.println("Nao foi possivel carregar a DLL: " + e.getMessage());
        }
    }

    void fecharConexao() {
        if (!conexaoAberta) {
            System.out.println("A conexao ja esta fechada.");
            return;
        }
        int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
        if (retorno == 0) {
            conexaoAberta = false;
            System.out.println("Conexao fechada.");
        } else {
            System.out.println("Erro ao fechar conexao. Retorno: " + retorno);
        }
    }

    void imprimirTexto(String texto, int copias) {
        for (int i = 1; i <= copias; i++) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(texto, 1, 0, 0);
            if (retorno < 0) {
                System.out.println("Erro ao imprimir a copia " + i + ". Retorno: " + retorno);
                return;
            }
            cortar();
        }
        System.out.println(copias + " copia(s) impressa(s) com sucesso.");
    }

    void imprimirQRCode(String dados) {
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dados, 4, 2);
        if (retorno == 0) {
            cortar();
            System.out.println("QR Code impresso com sucesso.");
        } else {
            System.out.println("Erro ao imprimir QR Code. Retorno: " + retorno);
        }
    }

    void imprimirCodigoBarras() {
        String dados = "{A012345678912";
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, dados, 100, 2, 2);
        if (retorno == 0) {
            cortar();
            System.out.println("Codigo de barras impresso com sucesso.");
        } else {
            System.out.println("Erro ao imprimir codigo de barras. Retorno: " + retorno);
        }
    }

    void imprimirXMLSAT(String conteudoXML) {
        int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(conteudoXML, 0);
        if (retorno == 0) {
            cortar();
            System.out.println("Cupom SAT impresso com sucesso.");
        } else {
            System.out.println("Erro ao imprimir XML SAT. Retorno: " + retorno);
        }
    }