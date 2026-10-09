package SystemITR.JosueGuinea1A.Cuentas.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CuentasDTO {

    private Long id_cuenta;
    private Long id_usuario;
    private String nombre;
    private String tipo;
    private BigDecimal saldo_inicial;
    private String activa;
    private BigDecimal total_ingresos;
    private BigDecimal total_gastos;
    private BigDecimal saldo_actual;
}
