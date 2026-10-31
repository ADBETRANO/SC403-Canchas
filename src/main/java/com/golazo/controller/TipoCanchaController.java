package com.golazo.controller;

import com.golazo.domain.TipoCancha;
import com.golazo.service.TipoCanchaService;
import jakarta.validation.Valid;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tipoCancha")
public class TipoCanchaController {

    //Se usa el servicio para tipos de cancha
    private final TipoCanchaService tipoCanchaService;
    private final MessageSource messageSource;

    //  El siguiente metodo responde al url http://localhost/tipoCancha/listado
    @GetMapping("/listado")
    public String listado(Model model) {
        var tiposCancha = tipoCanchaService.getTiposCancha(false);
        model.addAttribute("tiposCancha", tiposCancha);
        model.addAttribute("totalTiposCancha", tiposCancha.size());
        return "tipoCancha/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid TipoCancha tipoCancha, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.actualizado";
        if (bindingResult.hasErrors()) {
            titulo = "error";
            mensaje = "tipoCancha.error04";
        } else {
            try {
                tipoCanchaService.save(tipoCancha);
            } catch (DataIntegrityViolationException e) {
                titulo = "error";
                mensaje = "tipoCancha.error05";
            }
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/tipoCancha/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idTipoCancha, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            tipoCanchaService.delete(idTipoCancha);
        } catch (IllegalArgumentException e) {
            titulo = "error"; // No existe
            detalle = "tipoCancha.error01";
        } catch (IllegalStateException e) {
            titulo = "error"; // Tiene canchas asociadas
            detalle = "tipoCancha.error02";
        } catch (Exception e) {
            titulo = "error"; // Cualquier otro error
            detalle = "tipoCancha.error03";
        }
        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/tipoCancha/listado";
    }

}
