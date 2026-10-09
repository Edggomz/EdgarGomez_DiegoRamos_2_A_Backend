package SystemITR.JosueGuinea1A.Cuentas.Controller;

import SystemITR.JosueGuinea1A.ApiResponse.ApiResponse;
import SystemITR.JosueGuinea1A.Cuentas.DTO.CuentasDTO;
import SystemITR.JosueGuinea1A.Cuentas.Service.CuentasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@CrossOrigin
public class CuentasController {

    private final CuentasService service;

    public CuentasController(CuentasService service) {
        this.service = service;
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<CuentasDTO>>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        try {
            List<CuentasDTO> lista = service.obtenerPorUsuario(idUsuario);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cuentas obtenidas correctamente", lista));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudieron obtener las cuentas"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CuentasDTO>> obtenerPorId(@PathVariable Long id) {
        try {
            CuentasDTO dto = service.obtenerPorId(id);
            if (dto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "La cuenta no existe"));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Cuenta obtenida correctamente", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo obtener la cuenta"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CuentasDTO>> crear(@RequestBody CuentasDTO dto) {
        try {
            CuentasDTO creada = service.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Cuenta registrada correctamente", creada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo registrar la cuenta"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CuentasDTO>> actualizar(@PathVariable Long id, @RequestBody CuentasDTO dto) {
        try {
            CuentasDTO actualizada = service.actualizar(id, dto);
            if (actualizada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "La cuenta no existe"));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Cuenta actualizada correctamente", actualizada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo actualizar la cuenta"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            if (!service.eliminar(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "La cuenta no existe"));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Cuenta eliminada correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo eliminar la cuenta"));
        }
    }
}
