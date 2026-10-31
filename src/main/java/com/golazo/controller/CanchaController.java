package com.golazo.controller;

import com.golazo.domain.Cancha;
import com.golazo.service.CanchaService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cancha")
public class CanchaController {

    //Se usa el servicio de canchas
    private final CanchaService canchaService;
    private final MessageSource messageSource;

    //  El siguiente metodo responde al url http://localhost/cancha/listado
    @GetMapping("/listado")
    public String listado(Model model) {
        var canchas = canchaService.getCanchas(false);
        model.addAttribute("canchas", canchas);
        model.addAttribute("totalCanchas", canchas.size());
        var tiposCancha = canchaService.getTiposCancha();
        model.addAttribute("tiposCancha", tiposCancha);
        return "cancha/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Cancha cancha, BindingResult bindingResult,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String mensaje = "mensaje.actualizado";
        if (bindingResult.hasErrors()) {
            titulo = "error";
            mensaje = "cancha.error04";
        } else {
            try {
                canchaService.save(cancha, imagenFile);
            } catch (DataIntegrityViolationException e) {
                titulo = "error";
                mensaje = "cancha.error05";
            }
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(mensaje, null, Locale.getDefault()));
        return "redirect:/cancha/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idCancha, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            canchaService.delete(idCancha);
        } catch (IllegalArgumentException e) {
            titulo = "error"; // No existe
            detalle = "cancha.error01";
        } catch (IllegalStateException e) {
            titulo = "error"; // Tiene reservas, horarios o tarifas asociadas
            detalle = "cancha.error02";
        } catch (Exception e) {
            titulo = "error"; // Cualquier otro error
            detalle = "cancha.error03";
        }
        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/cancha/listado";
    }

}
