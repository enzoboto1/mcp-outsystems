package dev.mcp_outsys;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UsuarioService {

    @Tool(name = "criar_novo_usuario", description = "Cria um novo usuário passando o Nome e CPF (Valide o CPF) .")
    public void criarUsuario(String nome, String cpf) {
        WebClient.create("https://ebv.outsystemscloud.com/UsuariosMCP/rest/MCP")
                .post()
                .uri("/CriarUsuario")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Usuario(nome, cpf))
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Resposta da API: " + response))
                .doOnError(error -> System.err.println("Erro: " + error.getMessage()))
                .subscribe();
    }

    @Tool(name = "buscar_usuario_por_cpf", description = "Buscar um usuário por CPF.")
    public Usuario buscarUsuarioPorCPF(String cpf) {
        WebClient webClient = WebClient.create("https://ebv.outsystemscloud.com/UsuariosMCP/rest/MCP");

        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/BuscarUsuarioPorCPF")
                            .queryParam("CPF", cpf)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Usuario.class)
                    .doOnNext(usuario -> System.out.println("Usuário encontrado: " + usuario.getNome()))
                    .block();

        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
            return null;
        }
    }

}
