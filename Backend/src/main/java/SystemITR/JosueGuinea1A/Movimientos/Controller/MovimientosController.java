package SystemITR.JosueGuinea1A.Movimientos.Controller;

import SystemITR.JosueGuinea1A.ApiResponse.ApiResponse;
import SystemITR.JosueGuinea1A.Movimientos.DTO.MovimientosDTO;
import SystemITR.JosueGuinea1A.Movimientos.Service.MovimientosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(originPatterns = "*")
public class MovimientosController {

    private final MovimientosService service;

    public MovimientosController(MovimientosService service) {
        this.service = service;
    }

    @GetMapping("/cuenta/{idCuenta}")
    public ResponseEntity<ApiResponse<List<MovimientosDTO>>> obtenerPorCuenta(
            @PathVariable Long idCuenta,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String categoria) {
        try {
            List<MovimientosDTO> lista = service.obtenerPorCuenta(idCuenta, tipo, categoria);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movimientos obtenidos correctamente", lista));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudieron obtener los movimientos"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovimientosDTO>> crear(@RequestBody MovimientosDTO dto) {
        try {
            MovimientosDTO creado = service.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Movimiento registrado correctamente", creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo registrar el movimiento"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovimientosDTO>> actualizar(@PathVariable Long id, @RequestBody MovimientosDTO dto) {
        try {
            MovimientosDTO actualizado = service.actualizar(id, dto);
            if (actualizado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "El movimiento no existe"));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Movimiento actualizado correctamente", actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo actualizar el movimiento"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            if (!service.eliminar(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "El movimiento no existe"));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Movimiento eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo eliminar el movimiento"));
        }
    }
}
