package SystemITR.JosueGuinea1A.Movimientos.Repository;

import SystemITR.JosueGuinea1A.Movimientos.Entity.MovimientosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientosRepository extends JpaRepository<MovimientosEntity, Long> {

    List<MovimientosEntity> findByIdCuenta(Long idCuenta);
}
