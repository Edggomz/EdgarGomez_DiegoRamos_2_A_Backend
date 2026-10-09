package SystemITR.JosueGuinea1A.Movimientos.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "MOVIMIENTOS")
public class MovimientosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOVIMIENTO")
    private Long idMovimiento;

    @Column(name = "ID_CUENTA")
    private Long idCuenta;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "CATEGORIA")
    private String categoria;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "MONTO")
    private BigDecimal monto;
}
