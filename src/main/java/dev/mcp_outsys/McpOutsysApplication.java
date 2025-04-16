package dev.mcp_outsys;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class McpOutsysApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpOutsysApplication.class, args);
    }

    @Bean
    public List<ToolCallback> outsysTools(UsuarioService usuarioService) {
        return List.of(ToolCallbacks.from(usuarioService));
    }

}
