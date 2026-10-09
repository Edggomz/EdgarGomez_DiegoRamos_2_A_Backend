package SystemITR.JosueGuinea1A.Usuarios.Service;

import SystemITR.JosueGuinea1A.Usuarios.DTO.UsuariosDTO;
import SystemITR.JosueGuinea1A.Usuarios.Entity.UsuariosEntity;
import SystemITR.JosueGuinea1A.Usuarios.Repository.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    private final UsuariosRepository repo;

    public UsuariosService(UsuariosRepository repo) {
        this.repo = repo;
    }

    private UsuariosDTO convertirADTO(UsuariosEntity entity) {
        UsuariosDTO dto = new UsuariosDTO();
        dto.setId_usuario(entity.getIdUsuario());
        dto.setNombre(entity.getNombre());
        dto.setCorreo(entity.getCorreo());
        dto.setFecha_registro(entity.getFechaRegistro());
        return dto;
    }

    public List<UsuariosDTO> obtenerTodos() {
        List<UsuariosDTO> dtos = new ArrayList<>();
        for (UsuariosEntity entity : repo.findAll()) {
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }

    public UsuariosDTO obtenerPorId(Long idUsuario) {
        Optional<UsuariosEntity> entity = repo.findById(idUsuario);
        if (entity.isEmpty()) {
            return null;
        }
        return convertirADTO(entity.get());
    }
}
