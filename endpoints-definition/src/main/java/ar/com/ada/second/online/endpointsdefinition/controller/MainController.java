package ar.com.ada.second.online.endpointsdefinition.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/main") //Con esto le pongo el prefijo "main" a todos los segmentos
public class MainController {
    // Según la combinación que yo haga de los métodos con el segmento de mi url, voy a definir los endpoints
    // GET, PUT/PATCH, POST, DELETE => path (segmento de la url: localhost: 8080/{segment})

    @GetMapping({"/sayHi", "/", ""}) // de esta manera SpringBoot entiende que el método responde a ambos paths
    public ResponseEntity sayHelloWorld() {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("Hello", "world");

        return ResponseEntity.ok().body(responseBody);
        // return ResponseEntity.ok(responseBody);    esta es otra manera de hacer lo mismo
    }

    @GetMapping({"/sayOther"}) // de esta manera SpringBoot entiende que el método responde a ambos paths
    public ResponseEntity sayOther() {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("Hello", "other");

        return ResponseEntity.ok().body(responseBody);
        // return ResponseEntity.ok(responseBody);    esta es otra manera de hacer lo mismo
    }
}
