package co.edu.unbosque.proyectocorte2.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectocorte2.dto.LoginEnvioDTO;
import co.edu.unbosque.proyectocorte2.dto.LoginRespuestaDTO;
import co.edu.unbosque.proyectocorte2.enty.Persona;
import co.edu.unbosque.proyectocorte2.exception.ExceptionCheker;
import co.edu.unbosque.proyectocorte2.exception.SesionException;
import co.edu.unbosque.proyectocorte2.repository.LoginRepository;

@Service
public class LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private ModelMapper modelMapper;

    public LoginRespuestaDTO login(LoginEnvioDTO req) {
        try {
            String correo = req.getCorreo();
            String pass   = req.getContrasena();

            Optional<Persona> op = loginRepository.findByCorreo(correo);
            
            Persona p = op.orElseThrow(SesionException::new);

            ExceptionCheker.checkerSesion(correo, pass, p.getContrasena());

            String rol = (p.getRol() == null) ? "" : p.getRol().trim().toUpperCase();
            String redirect;
            switch (rol) {
                case "ADMIN"      -> redirect = "/admin/dashboard";
                case "PROFESOR"   -> redirect = "/profesor/inicio";
                case "ESTUDIANTE" -> redirect = "/estudiante/home";
                default -> {
                    rol = "USUARIO";
                    redirect = "/home";
                }
            }

            return new LoginRespuestaDTO(
                    p.getId(),
                    p.getNombre(),
                    p.getCorreo(),
                    rol,
                    redirect,
                    "Autenticación exitosa"
            );

        } catch (SesionException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error interno durante login para correo", req.getCorreo(), e);
            throw e;
        }
    }
}
