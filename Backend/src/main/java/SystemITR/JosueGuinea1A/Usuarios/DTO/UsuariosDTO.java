package SystemITR.JosueGuinea1A.Usuarios.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuariosDTO {

    private Long id_usuario;
    private String nombre;
    private String correo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_registro;
}
