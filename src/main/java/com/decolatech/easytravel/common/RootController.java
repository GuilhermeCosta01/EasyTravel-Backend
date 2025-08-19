package com.decolatech.easytravel.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("/")
    public String home() {
            return "<h2>🚀 API EasyTravel está online!</h2>\n" +
               "<p>Bem-vindo à plataforma EasyTravel.<br>\n" +
               "Consulte a documentação em <a href=\"/swagger-ui.html\">Swagger UI</a>.<br>\n" +
               "Para dúvidas ou suporte, entre em contato com a equipe de desenvolvimento.</p>";
    }
}
