package com.decolatech.easytravel.common.dashboard;

import com.decolatech.easytravel.common.dashboard.dto.*;
import com.decolatech.easytravel.common.dashboard.metodos.DashboardExcelExporter;
import com.decolatech.easytravel.config.security.SecurityConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard/export")
@Tag(name = "Exportação de Dashboard", description = "API para exportação de dados agregados do dashboard administrativo")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class DashboardExportController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/reservas-ativas-por-rank")
    public void exportReservasAtivasPorRank(HttpServletResponse response) throws IOException {
        List<ReservasAtivasPorRankDTO> data = dashboardService.getReservasAtivasPorRank();
        Workbook workbook = DashboardExcelExporter.exportReservasAtivasPorRank(data);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reservas_ativas_por_rank.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/reservas-canceladas-por-mes")
    public void exportReservasCanceladasPorMes(HttpServletResponse response) throws IOException {
        List<ReservasCanceladasPorMesDTO> data = dashboardService.getReservasCanceladasPorMes();
        Workbook workbook = DashboardExcelExporter.exportReservasCanceladasPorMes(data);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reservas_canceladas_por_mes.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/total-reservas-por-pacote")
    public void exportTotalReservasPorPacote(HttpServletResponse response) throws IOException {
        List<TotalReservasPorPacoteDTO> data = dashboardService.getTotalReservasPorPacote();
        Workbook workbook = DashboardExcelExporter.exportTotalReservasPorPacote(data);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=total_reservas_por_pacote.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/vendas-por-pagamento")
    public void exportVendasPorPagamento(HttpServletResponse response) throws IOException {
        List<VendasPorPagamentoDTO> data = dashboardService.getVendasPorPagamento();
        Workbook workbook = DashboardExcelExporter.exportVendasPorPagamento(data);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=vendas_por_pagamento.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/usuarios-por-metodo-pagamento")
    public void exportUsuariosPorMetodoPagamento(HttpServletResponse response) throws IOException {
        List<UsuariosPorMetodoPagamentoDTO> data = dashboardService.getUsuariosPorMetodoPagamento();
        Workbook workbook = DashboardExcelExporter.exportUsuariosPorMetodoPagamento(data);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios_por_metodo_pagamento.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/receita-por-mes")
    public void exportReceitaPorMes(HttpServletResponse response) throws IOException {
        List<ReceitaPorMesDTO> data = dashboardService.getReceitaPorMes();
        Workbook workbook = DashboardExcelExporter.exportReceitaPorMes(data);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=receita_por_mes.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/faturamento-por-pacote")
    public void exportFaturamentoPorPacote(HttpServletResponse response) throws IOException {
        List<FaturamentoPorPacoteDTO> data = dashboardService.getFaturamentoPorPacote();
        Workbook workbook = DashboardExcelExporter.exportFaturamentoPorPacote(data);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=faturamento_por_pacote.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/vendas-por-cidade")
    public void exportVendasPorCidade(HttpServletResponse response) throws IOException {
        List<VendasPorCidadeDTO> data = dashboardService.getVendasPorCidade();
        Workbook workbook = DashboardExcelExporter.exportVendasPorCidade(data);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=vendas_por_cidade.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/reservas-sem-pagamento")
    public void exportReservasSemPagamento(HttpServletResponse response) throws IOException {
        ReservasSemPagamentoDTO data = dashboardService.getReservasSemPagamento();
        Workbook workbook = DashboardExcelExporter.exportReservasSemPagamento(data);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reservas_sem_pagamento.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}

