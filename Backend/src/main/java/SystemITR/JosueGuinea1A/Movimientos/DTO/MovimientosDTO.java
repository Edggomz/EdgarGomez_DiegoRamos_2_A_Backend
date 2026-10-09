package SystemITR.JosueGuinea1A.Movimientos.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MovimientosDTO {

    private Long id_movimiento;
    private Long id_cuenta;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private String tipo;
    private String categoria;
    private String descripcion;
    private BigDecimal monto;
}
