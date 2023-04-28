package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

@Data
public class AmountVO {
    private Integer total, tax_free, vat, point, discount;
}
