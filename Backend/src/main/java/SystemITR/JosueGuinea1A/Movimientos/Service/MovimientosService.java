package SystemITR.JosueGuinea1A.Movimientos.Service;

import SystemITR.JosueGuinea1A.Cuentas.Entity.CuentasEntity;
import SystemITR.JosueGuinea1A.Cuentas.Repository.CuentasRepository;
import SystemITR.JosueGuinea1A.Movimientos.DTO.MovimientosDTO;
import SystemITR.JosueGuinea1A.Movimientos.Entity.MovimientosEntity;
import SystemITR.JosueGuinea1A.Movimientos.Repository.MovimientosRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientosService {

    private final MovimientosRepository repo;
    private final CuentasRepository cuentasRepo;

    public MovimientosService(MovimientosRepository repo, CuentasRepository cuentasRepo) {
        this.repo = repo;
        this.cuentasRepo = cuentasRepo;
    }

    private MovimientosDTO convertirADTO(MovimientosEntity entity) {
        MovimientosDTO dto = new MovimientosDTO();
        dto.setId_movimiento(entity.getIdMovimiento());
        dto.setId_cuenta(entity.getIdCuenta());
        dto.setFecha(entity.getFecha());
        dto.setTipo(entity.getTipo());
        dto.setCategoria(entity.getCategoria());
        dto.setDescripcion(entity.getDescripcion());
        dto.setMonto(entity.getMonto());
        return dto;
    }

    private void validar(MovimientosDTO dto) {
        if (dto.getId_cuenta() == null) {
            throw new IllegalArgumentException("La cuenta es obligatoria");
        }
        Optional<CuentasEntity> cuenta = cuentasRepo.findById(dto.getId_cuenta());
        if (cuenta.isEmpty()) {
            throw new IllegalArgumentException("La cuenta seleccionada no existe");
        }
        if (cuenta.get().getActiva().equals("N")) {
            throw new IllegalArgumentException("No se pueden registrar movimientos en una cuenta inactiva");
        }
        if (dto.getTipo() == null || !List.of("INGRESO", "GASTO").contains(dto.getTipo())) {
            throw new IllegalArgumentException("El tipo debe ser INGRESO o GASTO");
        }
        if (dto.getCategoria() == null || dto.getCategoria().isBlank()) {
            throw new IllegalArgumentException("La categoria es obligatoria");
        }
        if (dto.getCategoria().length() > 80) {
            throw new IllegalArgumentException("La categoria no puede superar los 80 caracteres");
        }
        if (dto.getDescripcion() != null && dto.getDescripcion().length() > 300) {
            throw new IllegalArgumentException("La descripcion no puede superar los 300 caracteres");
        }
        if (dto.getMonto() == null || dto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
    }

    private void copiarDatos(MovimientosDTO dto, MovimientosEntity entity) {
        entity.setIdCuenta(dto.getId_cuenta());
        entity.setTipo(dto.getTipo());
        entity.setCategoria(dto.getCategoria());
        entity.setDescripcion(dto.getDescripcion());
        entity.setMonto(dto.getMonto());
    }

    public List<MovimientosDTO> obtenerPorCuenta(Long idCuenta, String tipo, String categoria) {
        List<MovimientosDTO> dtos = new ArrayList<>();
        for (MovimientosEntity entity : repo.findByIdCuenta(idCuenta)) {
            if (tipo != null && !tipo.isBlank() && !entity.getTipo().equals(tipo)) {
                continue;
            }
            if (categoria != null && !categoria.isBlank()
                    && !entity.getCategoria().toLowerCase().contains(categoria.toLowerCase())) {
                continue;
            }
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }

    public MovimientosDTO crear(MovimientosDTO dto) {
        validar(dto);
        MovimientosEntity entity = new MovimientosEntity();
        entity.setFecha(LocalDate.now());
        copiarDatos(dto, entity);
        return convertirADTO(repo.save(entity));
    }

    public MovimientosDTO actualizar(Long idMovimiento, MovimientosDTO dto) {
        validar(dto);
        Optional<MovimientosEntity> existente = repo.findById(idMovimiento);
        if (existente.isEmpty()) {
            return null;
        }
        MovimientosEntity entity = existente.get();
        copiarDatos(dto, entity);
        return convertirADTO(repo.save(entity));
    }

    public boolean eliminar(Long idMovimiento) {
        if (!repo.existsById(idMovimiento)) {
            return false;
        }
        repo.deleteById(idMovimiento);
        return true;
    }
}
