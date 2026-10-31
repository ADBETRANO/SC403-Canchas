package com.golazo.controller;

import com.golazo.service.CanchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IndexController {

    //Se usa el servicio de canchas
    private final CanchaService canchaService;

    //  El siguiente metodo responde al url http://localhost/  (catálogo, HU-09)
    @GetMapping("/")
    public String paginaIndex(Model model) {
        model.addAttribute("canchas", canchaService.getCanchas(true));
        model.addAttribute("tiposCancha", canchaService.getTiposCancha());
        return "index";
    }

    //  El siguiente metodo responde al url http://localhost/catalogo/{idTipoCancha}
    @GetMapping("/catalogo/{idTipoCancha}")
    public String catalogoPorTipo(@PathVariable("idTipoCancha") Integer idTipoCancha, Model model) {
        model.addAttribute("idTipoActual", idTipoCancha);
        model.addAttribute("canchas", canchaService.getCanchasPorTipo(idTipoCancha));
        model.addAttribute("tiposCancha", canchaService.getTiposCancha());
        return "index";
    }

}
