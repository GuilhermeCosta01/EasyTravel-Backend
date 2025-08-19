package com.decolatech.easytravel.common.dashboard.metodos;

import com.decolatech.easytravel.common.dashboard.dto.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.List;

public class DashboardExcelExporter {
    public static Workbook exportReservasAtivasPorRank(List<ReservasAtivasPorRankDTO> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reservas Ativas por Rank");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Rank");
        header.createCell(1).setCellValue("Reservas Ativas");
        int rowIdx = 1;
        for (ReservasAtivasPorRankDTO dto : data) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(dto.getBundleRank());
            row.createCell(1).setCellValue(dto.getReservasAtivas());
        }
        return workbook;
    }

    public static Workbook exportReservasCanceladasPorMes(List<ReservasCanceladasPorMesDTO> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reservas Canceladas por Mês");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Mês");
        header.createCell(1).setCellValue("Total Canceladas");
        int rowIdx = 1;
        for (ReservasCanceladasPorMesDTO dto : data) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(dto.getMes());
            row.createCell(1).setCellValue(dto.getTotalCanceladas());
        }
        return workbook;
    }

    public static Workbook exportTotalReservasPorPacote(List<TotalReservasPorPacoteDTO> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Total Reservas por Pacote");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Pacote ID");
        header.createCell(1).setCellValue("Total Reservas");
        int rowIdx = 1;
        for (TotalReservasPorPacoteDTO dto : data) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(dto.getPacoteId());
            row.createCell(1).setCellValue(dto.getTotalReservas());
        }
        return workbook;
    }

    public static Workbook exportVendasPorPagamento(List<VendasPorPagamentoDTO> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Vendas por Pagamento");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Método de Pagamento");
        header.createCell(1).setCellValue("Total Transações");
        header.createCell(2).setCellValue("Total Receita");
        int rowIdx = 1;
        for (VendasPorPagamentoDTO dto : data) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(dto.getPaymentMethod());
            row.createCell(1).setCellValue(dto.getTotalTransactions());
            row.createCell(2).setCellValue(dto.getTotalRevenue());
        }
        return workbook;
    }

    public static Workbook exportUsuariosPorMetodoPagamento(List<UsuariosPorMetodoPagamentoDTO> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuários por Método de Pagamento");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Método de Pagamento");
        header.createCell(1).setCellValue("Total Usuários");
        int rowIdx = 1;
        for (UsuariosPorMetodoPagamentoDTO dto : data) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(dto.getPaymentMethod());
            row.createCell(1).setCellValue(dto.getTotalUsuarios());
        }
        return workbook;
    }

    public static Workbook exportReceitaPorMes(List<ReceitaPorMesDTO> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Receita por Mês");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Mês");
        header.createCell(1).setCellValue("Faturamento");
        int rowIdx = 1;
        for (ReceitaPorMesDTO dto : data) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(dto.getMes());
            row.createCell(1).setCellValue(dto.getFaturamento());
        }
        return workbook;
    }

    public static Workbook exportFaturamentoPorPacote(List<FaturamentoPorPacoteDTO> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Faturamento por Pacote");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Pacote ID");
        header.createCell(1).setCellValue("Faturamento");
        int rowIdx = 1;
        for (FaturamentoPorPacoteDTO dto : data) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(dto.getPacoteId());
            row.createCell(1).setCellValue(dto.getFaturamento());
        }
        return workbook;
    }

    public static Workbook exportVendasPorCidade(List<VendasPorCidadeDTO> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Vendas por Cidade");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Cidade Destino");
        header.createCell(1).setCellValue("Total Vendas");
        int rowIdx = 1;
        for (VendasPorCidadeDTO dto : data) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(dto.getDestination());
            row.createCell(1).setCellValue(dto.getTotalVendas());
        }
        return workbook;
    }

    public static Workbook exportReservasSemPagamento(ReservasSemPagamentoDTO dto) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reservas Sem Pagamento");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Reservas Sem Pagamento");
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue(dto.getReservasSemPagamento());
        return workbook;
    }
}
