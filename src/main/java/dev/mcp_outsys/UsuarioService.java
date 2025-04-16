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

    @Tool(name = "deletar_usuario_por_id", description = "Deletar um usuário por ID.")
    public void deletarUsuarioPorId(Long idUsuario) {
        WebClient webClient = WebClient.create("https://ebv.outsystemscloud.com/UsuariosMCP/rest/MCP");

        try {
            webClient.delete()
                    .uri(uriBuilder -> uriBuilder
                            .path("/DeletarUsuario")
                            .queryParam("IdUsuario", idUsuario)
                            .build())
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            System.out.println("Usuário com ID " + idUsuario + " deletado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    @Tool(name = "alterar_usuario", description = "Alterar dados de um usuário passando ID, NOME e CPF.")
    public void alterarUsuario(Usuario usuario) {
        WebClient webClient = WebClient.create("https://ebv.outsystemscloud.com/UsuariosMCP/rest/MCP");

        try {
            webClient.put()
                    .uri("/AlterarUsuario")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(usuario)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            System.out.println("Usuário com ID " + usuario.getIdUsuario() + " alterado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao alterar usuário: " + e.getMessage());
        }
    }

}
