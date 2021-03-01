package ar.com.ada.second.online.endpointsdefinition.controller;


import ar.com.ada.second.online.endpointsdefinition.model.dto.ItemDTO;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.BinaryOperator;

@RestController
@RequestMapping (value = "/items")
public class ItemController {

    //Este atributo no se utilizará en futuros ejercicios. No tomarlo como base.
    private  List<ItemDTO> items = ArrayList<>(Arrays.asList(
            new ItemDTO(1L, "CocaCola", "Bebida gaseosa", 123456),
            new ItemDTO(2L, "Palitos salados", "Snack", 123457),
            new ItemDTO(3L, "Cerveza X", "Bebida alcoholica", 123458)
    ));

    @GetMapping({ "/", ""})
    public ResponseEntity getItemsMethod() {
        return ResponseEntity.ok().body(items);
    }


    @PostMapping({"/", ""})
    public ResponseEntity postItemMethod(@Valid @RequestBody ItemDTO item) throws URISyntaxException {
        //long id = Math.abs(new Random().nextLong()) % 100;

        //long itemsCounter = items.stream().count();
        //ItemDTO lastItem = items.stream()
        //  .skip(itemsCounter -1)
        //  .findFirst()
        //  .orElse(null);

        ItemDTO lastItem = items.stream()
                .reduce((first, second) -> second)
                .orElse(null);

        Long id = (lastItem != null)
                ? lastItem.getId() + 1
                : 1;

        item.setId(id);

        items.add(item);

        URI uri = new URI("/items/" + id);
        return ResponseEntity.created(uri).body(item);
    }

    @GetMapping({ "/{itemId}", "/{itemId}/"})
    public ResponseEntity getItemByIdMethod(@PathVariable Long itemId) {

         /* antes lo que hacíamos era:

        ItemDTO itemById = null;

        for (int I = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(itemId)) {
                itemById = items.get(i);
                }
         */

        ItemDTO itemById = items.stream()             //acá obtengo una lista de un solo objeto y saco el objeto de ahí para que el return me de lo que necesito
                .filter((item) -> item.getId().equals(itemId))
                .findFirst()                          // aunque sea una lista de un solo objeto de esta manera tomo el primer objeto de la lista que obtuve en "filter"
                .orElse(null);                  //si no hay elementos, devolveme un null para que no de "undefined"

        HttpStatus httpStatus = (itemById == null)
                ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;

        return ResponseEntity.ok(httpStatus).body(itemById);
    }

    @DeleteMapping({ "/{itemId}", "/{itemId}/"})
    public ResponseEntity deleteItemByIdMethod(@PathVariable Long itemId) {

        ItemDTO itemByIdToDelete = items.stream()
                .filter((item) ->{
                    return item.getId().equals(itemId);
                })
                .findFirst()
                .orElse(null);

        items.remove(itemByIdToDelete);

        HttpStatus httpStatus = (itemByIdToDelete == null)
                ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;

        return ResponseEntity.ok(httpStatus).body(itemByIdToDelete);
    }
// TODO: Probar este otro delete porque no funciona "itemNotFound"

    // OTRA MANERA DE HACER EL DELETE y esta sí muestra mensaje de error:

    /*@DeleteMapping ({ "/{itemId}", "/{itemId}/"})
    public ResponseEntity deleteItemByIdMethod(@PathVariable Long itemId) {

        Boolean hasDelete = items.removeIf(item -> item.getId().equals(itemId));
        itemNotFound.put("error", HttpStatus.BAD_REQUEST.toString());
        itemNotFound.put("message", "item does not exist");

        return (hasDelete)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().body(NotFound);   // 400
    }*/

}
