package br.com.etiene.usuariocrud.crud;

import br.com.etiene.usuariocrud.crud.model.Usuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class CrudApplication {

    //Criando uma lista do tipo Usuário chamada dbUSuario
    private static List<Usuario> dbUsuario = new ArrayList<>();

    public static void main(String[] args) {
        // Criando um objeto do tipo Usuário
        Usuario etiene = new Usuario(1, "Etiene");

        // Adicionando objeto etiene à lista
        dbUsuario.add(etiene);

        Usuario cristina = new Usuario(2, "Cristina");
        dbUsuario.add(cristina);

        //Inicia o Spring
        SpringApplication.run(CrudApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {

        // retorna o valor com o parâmetro especificado
        return String.format("Hello %s!", name);
    }

    @GetMapping("/user")
    public List<Usuario> filtroByNames(@RequestParam(value = "name", required = false) String name) {
        if (name != null && !name.isEmpty()) {

            //Cria uma lista vazia de usuários
            List<Usuario> usuariosFiltrados = new ArrayList<>();

            //Percorre o banco de dados do usuário
            for (int i = 0; i < dbUsuario.size(); i++) {

                //Obtém o usuário pelo índice
                Usuario usuario = dbUsuario.get(i);

                //Verifica se o usuário obtido contém o nome do filtro
                if (usuario.getNome().toLowerCase().contains(name.toLowerCase())) {

                    //adiciona o usuário na lista
                    usuariosFiltrados.add(usuario);
                }
            }
            return usuariosFiltrados;
        } else {
            return dbUsuario;
        }
    }

    @PostMapping("user")
    public void criarUsuario(@RequestBody Usuario user) {
        dbUsuario.add(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Usuario> filterById(@PathVariable("id") int id) {

        //Cria o objeto resultado do tipo Usuário e atribui valor nulo a ele
        Usuario resultado = null;

        for (int i = 0; i < dbUsuario.size(); i++) {
            Usuario usuario = dbUsuario.get(i);

            if (usuario.getId() == id) {
                resultado = usuario;
                break;
            }
        }

        if (resultado != null) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/{id}")
    public void updateUser(@PathVariable("id") int id, @RequestBody Usuario user) {

        for (int i = 0; i < dbUsuario.size(); i++) {

            Usuario usuario = dbUsuario.get(i);

            if (usuario.getId() == id) {
                usuario.setNome(user.getNome());
                break;
            }
        }
    }

    @DeleteMapping("/user/{id}")
    public void deletarUsuario(@PathVariable("id") int id) {
        for (int i = 0; i < dbUsuario.size(); i++) {
            Usuario usuario = dbUsuario.get(i);

            if (usuario.getId() == id) {
                //Remove o objeto pelo índice da lista
                dbUsuario.remove(i);

               /* dbUsuario.remove(usuario);
               / Remove o objeto pela referência do objeto
               */
                break;
            }
        }
    }
}
