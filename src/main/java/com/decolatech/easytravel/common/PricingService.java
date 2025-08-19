package com.decolatech.easytravel.common;

import com.decolatech.easytravel.domain.booking.enums.PaymentMethod;
import com.decolatech.easytravel.domain.bundle.entity.Bundle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PricingService {
    public BigDecimal calculateTotalPrice(Bundle bundle, PaymentMethod paymentMethod, Integer installments) {
        BigDecimal initialPrice = BigDecimal.valueOf(bundle.getInitialPrice());
        BigDecimal totalPrice = initialPrice;
        if (paymentMethod == PaymentMethod.PIX) {
            totalPrice = initialPrice.multiply(BigDecimal.valueOf(0.95)); // 5% de desconto
        } else if (paymentMethod == PaymentMethod.DEBIT) {
            totalPrice = initialPrice.multiply(BigDecimal.valueOf(0.90)); // 10% de desconto
        } else if (paymentMethod == PaymentMethod.CREDIT) {
            // Se for crédito e apenas 1 parcela, 8% de desconto
            if (installments != null && installments == 1) {
                totalPrice = initialPrice.multiply(BigDecimal.valueOf(0.92)); // 8% de desconto
            } else if (installments != null && installments > 1 && installments <= 4) {
                // Parcelamento até 4x sem juros, valor total não muda
            }
        } else if (paymentMethod == PaymentMethod.BANK_SLIP) {
            // Sem desconto
        }
        return totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Aplica desconto no valor total baseado no método de pagamento
     * @param totalValue Valor total informado pelo cliente
     * @param paymentMethod Método de pagamento escolhido
     * @param installments Número de parcelas (apenas para cartão de crédito)
     * @return Valor final com desconto aplicado
     */
    public Double applyPaymentDiscount(Double totalValue, PaymentMethod paymentMethod, Integer installments) {
        if (totalValue == null || totalValue <= 0) {
            throw new IllegalArgumentException("Valor total deve ser maior que zero");
        }

        BigDecimal value = BigDecimal.valueOf(totalValue);
        BigDecimal discount = BigDecimal.ZERO;

        switch (paymentMethod) {
            case PIX:
                // 10% de desconto para PIX
                discount = value.multiply(BigDecimal.valueOf(0.10));
                break;
            case DEBIT:
                // 8% de desconto para débito
                discount = value.multiply(BigDecimal.valueOf(0.08));
                break;
            case CREDIT:
                // Desconto varia conforme o número de parcelas
                if (installments != null) {
                    if (installments <= 3) {
                        discount = value.multiply(BigDecimal.valueOf(0.05)); // 5% até 3x
                    } else if (installments <= 6) {
                        discount = value.multiply(BigDecimal.valueOf(0.02)); // 2% de 4x até 6x
                    }
                    // Sem desconto para mais de 6 parcelas
                }
                break;
            default:
                // Sem desconto para outros métodos
                break;
        }

        return value.subtract(discount).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
