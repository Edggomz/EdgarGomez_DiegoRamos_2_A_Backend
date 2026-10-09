package SystemITR.JosueGuinea1A.Usuarios.Controller;

import SystemITR.JosueGuinea1A.ApiResponse.ApiResponse;
import SystemITR.JosueGuinea1A.Usuarios.DTO.UsuariosDTO;
import SystemITR.JosueGuinea1A.Usuarios.Service.UsuariosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuariosController {

    private final UsuariosService service;

    public UsuariosController(UsuariosService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuariosDTO>>> obtenerTodos() {
        try {
            List<UsuariosDTO> lista = service.obtenerTodos();
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios obtenidos correctamente", lista));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudieron obtener los usuarios"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuariosDTO>> obtenerPorId(@PathVariable Long id) {
        try {
            UsuariosDTO dto = service.obtenerPorId(id);
            if (dto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "El usuario no existe"));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario obtenido correctamente", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "No se pudo obtener el usuario"));
        }
    }
}
