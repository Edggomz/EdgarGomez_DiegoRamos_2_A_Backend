package SystemITR.JosueGuinea1A.Cuentas.Service;

import SystemITR.JosueGuinea1A.Cuentas.DTO.CuentasDTO;
import SystemITR.JosueGuinea1A.Cuentas.Entity.CuentasEntity;
import SystemITR.JosueGuinea1A.Cuentas.Repository.CuentasRepository;
import SystemITR.JosueGuinea1A.Movimientos.Entity.MovimientosEntity;
import SystemITR.JosueGuinea1A.Movimientos.Repository.MovimientosRepository;
import SystemITR.JosueGuinea1A.Usuarios.Repository.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuentasService {

    private final CuentasRepository repo;
    private final UsuariosRepository usuariosRepo;
    private final MovimientosRepository movimientosRepo;

    public CuentasService(CuentasRepository repo, UsuariosRepository usuariosRepo, MovimientosRepository movimientosRepo) {
        this.repo = repo;
        this.usuariosRepo = usuariosRepo;
        this.movimientosRepo = movimientosRepo;
    }

    private CuentasDTO convertirADTO(CuentasEntity entity) {
        CuentasDTO dto = new CuentasDTO();
        dto.setId_cuenta(entity.getIdCuenta());
        dto.setId_usuario(entity.getIdUsuario());
        dto.setNombre(entity.getNombre());
        dto.setTipo(entity.getTipo());
        dto.setSaldo_inicial(entity.getSaldoInicial());
        dto.setActiva(entity.getActiva());

        BigDecimal ingresos = BigDecimal.ZERO;
        BigDecimal gastos = BigDecimal.ZERO;
        for (MovimientosEntity movimiento : movimientosRepo.findByIdCuenta(entity.getIdCuenta())) {
            if (movimiento.getTipo().equals("INGRESO")) {
                ingresos = ingresos.add(movimiento.getMonto());
            } else {
                gastos = gastos.add(movimiento.getMonto());
            }
        }
        dto.setTotal_ingresos(ingresos);
        dto.setTotal_gastos(gastos);
        dto.setSaldo_actual(entity.getSaldoInicial().add(ingresos).subtract(gastos));
        return dto;
    }

    private void validar(CuentasDTO dto) {
        if (dto.getId_usuario() == null || !usuariosRepo.existsById(dto.getId_usuario())) {
            throw new IllegalArgumentException("El usuario seleccionado no existe");
        }
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la cuenta es obligatorio");
        }
        if (dto.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede superar los 100 caracteres");
        }
        if (dto.getTipo() == null || !List.of("EFECTIVO", "BANCO", "TARJETA").contains(dto.getTipo())) {
            throw new IllegalArgumentException("El tipo debe ser EFECTIVO, BANCO o TARJETA");
        }
        if (dto.getSaldo_inicial() == null) {
            throw new IllegalArgumentException("El saldo inicial es obligatorio");
        }
        if (dto.getActiva() == null || !List.of("S", "N").contains(dto.getActiva())) {
            throw new IllegalArgumentException("El estado debe ser S o N");
        }
    }

    public List<CuentasDTO> obtenerPorUsuario(Long idUsuario) {
        List<CuentasDTO> dtos = new ArrayList<>();
        for (CuentasEntity entity : repo.findByIdUsuario(idUsuario)) {
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }

    public CuentasDTO obtenerPorId(Long idCuenta) {
        Optional<CuentasEntity> entity = repo.findById(idCuenta);
        if (entity.isEmpty()) {
            return null;
        }
        return convertirADTO(entity.get());
    }

    public CuentasDTO crear(CuentasDTO dto) {
        validar(dto);
        CuentasEntity entity = new CuentasEntity();
        entity.setIdUsuario(dto.getId_usuario());
        entity.setNombre(dto.getNombre());
        entity.setTipo(dto.getTipo());
        entity.setSaldoInicial(dto.getSaldo_inicial());
        entity.setActiva(dto.getActiva());
        return convertirADTO(repo.save(entity));
    }

    public CuentasDTO actualizar(Long idCuenta, CuentasDTO dto) {
        validar(dto);
        Optional<CuentasEntity> existente = repo.findById(idCuenta);
        if (existente.isEmpty()) {
            return null;
        }
        CuentasEntity entity = existente.get();
        if (dto.getActiva().equals("N") && convertirADTO(entity).getSaldo_actual().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Solo se puede desactivar una cuenta con saldo actual en 0.00");
        }
        entity.setIdUsuario(dto.getId_usuario());
        entity.setNombre(dto.getNombre());
        entity.setTipo(dto.getTipo());
        entity.setSaldoInicial(dto.getSaldo_inicial());
        entity.setActiva(dto.getActiva());
        return convertirADTO(repo.save(entity));
    }

    public boolean eliminar(Long idCuenta) {
        if (!repo.existsById(idCuenta)) {
            return false;
        }
        if (!movimientosRepo.findByIdCuenta(idCuenta).isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar la cuenta porque tiene movimientos asociados");
        }
        repo.deleteById(idCuenta);
        return true;
    }
}
