package com.decolatech.easytravel.common.dashboard;

import com.decolatech.easytravel.config.security.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "API para dados agregados do dashboard administrativo")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Operation(summary = "Reservas ativas por rank de pacote")
    @GetMapping("/reservas-ativas-por-rank")
    public ResponseEntity<?> getReservasAtivasPorRank() {
        return ResponseEntity.ok(dashboardService.getReservasAtivasPorRank());
    }

    @Operation(summary = "Reservas canceladas por mês")
    @GetMapping("/reservas-canceladas-por-mes")
    public ResponseEntity<?> getReservasCanceladasPorMes() {
        return ResponseEntity.ok(dashboardService.getReservasCanceladasPorMes());
    }

    @Operation(summary = "Total de reservas por pacote")
    @GetMapping("/total-reservas-por-pacote")
    public ResponseEntity<?> getTotalReservasPorPacote() {
        return ResponseEntity.ok(dashboardService.getTotalReservasPorPacote());
    }

    @Operation(summary = "Vendas por método de pagamento")
    @GetMapping("/vendas-por-pagamento")
    public ResponseEntity<?> getVendasPorPagamento() {
        return ResponseEntity.ok(dashboardService.getVendasPorPagamento());
    }

    @Operation(summary = "Usuários por método de pagamento")
    @GetMapping("/usuarios-por-metodo-pagamento")
    public ResponseEntity<?> getUsuariosPorMetodoPagamento() {
        return ResponseEntity.ok(dashboardService.getUsuariosPorMetodoPagamento());
    }

    @Operation(summary = "Receita total por mês")
    @GetMapping("/receita-por-mes")
    public ResponseEntity<?> getReceitaPorMes() {
        return ResponseEntity.ok(dashboardService.getReceitaPorMes());
    }

    @Operation(summary = "Faturamento por pacote")
    @GetMapping("/faturamento-por-pacote")
    public ResponseEntity<?> getFaturamentoPorPacote() {
        return ResponseEntity.ok(dashboardService.getFaturamentoPorPacote());
    }

    @Operation(summary = "Total de vendas por cidade de destino")
    @GetMapping("/vendas-por-cidade")
    public ResponseEntity<?> getVendasPorCidade() {
        return ResponseEntity.ok(dashboardService.getVendasPorCidade());
    }

    @Operation(summary = "Total de reservas sem pagamentos efetivados")
    @GetMapping("/reservas-sem-pagamento")
    public ResponseEntity<?> getReservasSemPagamento() {
        return ResponseEntity.ok(dashboardService.getReservasSemPagamento());
    }
}

