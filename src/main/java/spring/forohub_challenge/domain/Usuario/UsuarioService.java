package spring.forohub_challenge.domain.Usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.forohub_challenge.infra.errors.ValidarIntegridad;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public DatosRespuestaUsuario crearUsuario(DatosUsuario datosUsuario){
        String hashedPassword = passwordEncoder.encode(datosUsuario.password());
    var usuario = new Usuario(
            datosUsuario.nombre(),
            datosUsuario.email(),
            hashedPassword);

    usuarioRepository.save(usuario);
    return new DatosRespuestaUsuario(usuario);
    }
    public List<DatosListaUsuario> getUsuarios(){
        List<Usuario> usuariosActivos = usuarioRepository.findByActivoTrue();
        return usuariosActivos.stream()
                .map(DatosListaUsuario::new)
                .collect(Collectors.toList());
    }
    public DatosRespuestaUsuario actualizarUsuario(DatosActualizarUsuario datosActualizarUsuario){
        if (usuarioRepository.findById(datosActualizarUsuario.id()).isPresent()){
            throw new ValidarIntegridad("No se encontro la id del Usuario");
        }
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());

        if (datosActualizarUsuario.password() != null){
            String hashedPassword = passwordEncoder.encode(datosActualizarUsuario.password());
            usuario.setPassword(hashedPassword);
        }

        usuario.setNombre(datosActualizarUsuario.nombre());
        usuario.setEmail(datosActualizarUsuario.email());
        usuarioRepository.save(usuario);
        return new DatosRespuestaUsuario(usuario);
    }
    public void usuarioBorrado(Long id){
    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ValidarIntegridad("No se encontro ningun usuario con esa id"));
    usuario.usuarioBorrado();
    usuarioRepository.save(usuario);
    }


}
