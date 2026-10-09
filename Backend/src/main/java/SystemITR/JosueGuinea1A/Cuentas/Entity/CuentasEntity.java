package SystemITR.JosueGuinea1A.Cuentas.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "CUENTAS")
public class CuentasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CUENTA")
    private Long idCuenta;

    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "SALDO_INICIAL")
    private BigDecimal saldoInicial;

    @Column(name = "ACTIVA")
    private String activa;
}
