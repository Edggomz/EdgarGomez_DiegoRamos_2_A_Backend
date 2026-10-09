package SystemITR.JosueGuinea1A.Cuentas.Repository;

import SystemITR.JosueGuinea1A.Cuentas.Entity.CuentasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentasRepository extends JpaRepository<CuentasEntity, Long> {

    List<CuentasEntity> findByIdUsuario(Long idUsuario);
}
