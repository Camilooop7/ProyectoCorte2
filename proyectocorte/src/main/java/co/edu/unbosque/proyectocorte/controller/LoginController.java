package co.edu.unbosque.proyectocorte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.proyectocorte.dto.LoginEnvioDTO;
import co.edu.unbosque.proyectocorte.dto.LoginRespuestaDTO;
import co.edu.unbosque.proyectocorte.exception.SesionException;
import co.edu.unbosque.proyectocorte.service.LoginService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/login" })
public class LoginController {

    @Autowired
    private LoginService authService;

    @PostMapping(path = "/inicio")
    public ResponseEntity<?> login(
            @RequestParam String correo,
            @RequestParam String contrasena) {
        try {
            LoginEnvioDTO req = new LoginEnvioDTO();
            req.setCorreo(correo);
            req.setContrasena(contrasena);

            LoginRespuestaDTO resp = authService.login(req);
            return ResponseEntity.ok(resp);
        } catch (SesionException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno en el login");
        }
    }
}
