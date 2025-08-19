package com.decolatech.easytravel.common;

import com.decolatech.easytravel.domain.booking.entity.Payment;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.RGBColor;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.DocumentFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class PaymentReceiptPdfGenerator {
    public byte[] generate(Payment payment) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Título estilizado
        Font fontTitle = new Font(Font.HELVETICA, 20, Font.BOLD, new RGBColor(255, 140, 0)); // Laranja
        Paragraph paragraph1 = new Paragraph("Comprovante de Pagamento", fontTitle);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph1);
        document.add(new Paragraph("\n"));

        // Mensagem de agradecimento estilizada
        Font fontThankYou = new Font(Font.HELVETICA, 16, Font.BOLD, new RGBColor(0, 180, 0)); // Verde
        Paragraph thankYou = new Paragraph("Obrigado pela sua compra e desejamos uma ótima viagem!", fontThankYou);
        thankYou.setAlignment(Element.ALIGN_CENTER);
        document.add(thankYou);
        document.add(new Paragraph("\n"));

        // Tabela de detalhes do pagamento
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        Font fontHeader = new Font(Font.HELVETICA, 12, Font.BOLD, new RGBColor(255, 140, 0)); // Laranja
        Font fontCell = new Font(Font.HELVETICA, 12, Font.NORMAL, new RGBColor(0, 0, 0));
        PdfPCell cellCampo = new PdfPCell(new Phrase("Campo", fontHeader));
        cellCampo.setBackgroundColor(new RGBColor(255, 230, 200)); // Laranja claro
        cellCampo.setPadding(5);
        table.addCell(cellCampo);
        PdfPCell cellValor = new PdfPCell(new Phrase("Valor", fontHeader));
        cellValor.setBackgroundColor(new RGBColor(220, 255, 220)); // Verde claro
        cellValor.setPadding(5);
        table.addCell(cellValor);

        // Dados do pagamento
        table.addCell(new PdfPCell(new Phrase("ID do Pagamento", fontCell)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(payment.getId()), fontCell)));
        table.addCell(new PdfPCell(new Phrase("Nome do Pacote", fontCell)));
        table.addCell(new PdfPCell(new Phrase(payment.getReservation() != null && payment.getReservation().getBundle() != null ? payment.getReservation().getBundle().getBundleTitle() : "-", fontCell)));
        table.addCell(new PdfPCell(new Phrase("Data do Pagamento", fontCell)));
        table.addCell(new PdfPCell(new Phrase(payment.getPaymentDate() != null ? payment.getPaymentDate().toString() : "-", fontCell)));
        table.addCell(new PdfPCell(new Phrase("Método de Pagamento", fontCell)));
        table.addCell(new PdfPCell(new Phrase(payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : "-", fontCell)));
        table.addCell(new PdfPCell(new Phrase("Valor Total", fontCell)));
        table.addCell(new PdfPCell(new Phrase(payment.getTotPrice() != null ? String.format("R$ %.2f", payment.getTotPrice()) : "-", fontCell)));
        table.addCell(new PdfPCell(new Phrase("Parcelas", fontCell)));
        table.addCell(new PdfPCell(new Phrase(payment.getInstallments() != null ? payment.getInstallments().toString() : "-", fontCell)));
        table.addCell(new PdfPCell(new Phrase("Reserva", fontCell)));
        table.addCell(new PdfPCell(new Phrase(payment.getReservation() != null ? String.valueOf(payment.getReservation().getId()) : "-", fontCell)));
        // Adiciona dados do usuário se disponíveis
        if (payment.getReservation() != null && payment.getReservation().getUser() != null) {
            table.addCell(new PdfPCell(new Phrase("Nome do Usuário", fontCell)));
            table.addCell(new PdfPCell(new Phrase(payment.getReservation().getUser().getName(), fontCell)));
            table.addCell(new PdfPCell(new Phrase("E-mail do Usuário", fontCell)));
            table.addCell(new PdfPCell(new Phrase(payment.getReservation().getUser().getEmail(), fontCell)));
        }

        document.add(table);
        document.add(new Paragraph("\n"));
        Font fontInfo = new Font(Font.HELVETICA, 10, Font.ITALIC, new RGBColor(255, 140, 0)); // Laranja
        Paragraph info = new Paragraph("Se tiver dúvidas, entre em contato com nosso suporte.", fontInfo);
        info.setAlignment(Element.ALIGN_CENTER);
        document.add(info);
        document.close();
        return baos.toByteArray();
    }
}
