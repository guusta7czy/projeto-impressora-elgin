# Projeto Impressora Térmica Elgin (Java + JNA)

Sistema de console em Java para controle e impressão em impressoras térmicas Elgin (modelos i8 e i9) via integração com biblioteca nativa (DLL).

## Funcionalidades
- Autenticação de usuário com limite de 3 tentativas
- Configuração de conexão (USB, Serial, Rede ou Bluetooth)
- Impressão de texto, QR Code e código de barras (CODE 128)
- Impressão de cupons fiscais XML SAT (venda e cancelamento)
- Controle de periféricos e status (abertura de gaveta, sinal sonoro, avanço e sensor de papel)

## Estrutura do Projeto
- `Main.java`: fluxo principal de autenticação e inicialização
- `Usuario.java`: validação de credenciais
- `Menu.java`: interface interativa de opções no console
- `Impressora.java`: métodos de comunicação com a DLL da impressora
- `ImpressoraDLL.java`: interface JNA para mapeamento das funções nativas