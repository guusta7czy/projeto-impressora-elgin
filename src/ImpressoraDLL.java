import com.sun.jna.Library;
import com.sun.jna.Native;

public interface ImpressoraDLL extends Library {

    ImpressoraDLL INSTANCE = Native.load("E1_Impressora01", ImpressoraDLL.class);

    int AbreConexaoImpressora(int tipo, String modelo, String conexao, int parametro);
    int FechaConexaoImpressora();
    int ImpressaoTexto(String dados, int posicao, int stilo, int tamanho);
    int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);
    int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);
    int ImprimeXMLSAT(String dados, int param);
    int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    int AbreGaveta(int pino, int ti, int tf);
    int SinalSonoro(int qtd, int tempoInicio, int tempoFim);
    int AvancaPapel(int linhas);
    int StatusImpressora(int param);
    int Corte(int avanco);
}