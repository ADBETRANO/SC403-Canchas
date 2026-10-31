// Carga los datos de la cancha en el modal Agregar o Editar
document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('canchaModal');
    if (!modal) {
        return;
    }
    const form = document.getElementById('canchaForm');
    const header = document.getElementById('canchaModalHeader');
    const titulo = document.getElementById('canchaModalTitulo');
    const id = document.getElementById('canchaId');
    const nombre = document.getElementById('canchaNombre');
    const tipo = document.getElementById('canchaTipo');
    const superficie = document.getElementById('canchaSuperficie');
    const capacidad = document.getElementById('canchaCapacidad');
    const descripcion = document.getElementById('canchaDescripcion');
    const activo = document.getElementById('canchaActivo');
    const rutaImagen = document.getElementById('canchaRutaImagen');
    const imagenFile = document.getElementById('canchaImagenFile');
    const preview = document.getElementById('canchaPreview');
    function limpiarPreview() {
        preview.removeAttribute('src');
        preview.classList.add('d-none');
    }
    function mostrarPreviewActual(ruta) {
        if (ruta) {
            preview.src = ruta;
            preview.classList.remove('d-none');
        } else {
            limpiarPreview();
        }
    }
    imagenFile.addEventListener('change', function () {
        mostrarImagen(this, 'canchaPreview');
    });
    modal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        if (!button) {
            return;
        }
        const modo = button.getAttribute('data-modo');
        // Se limpia cualquier información de una apertura anterior
        form.reset();
        id.value = '';
        rutaImagen.value = '';
        imagenFile.value = '';
        limpiarPreview();
        titulo.textContent = button.getAttribute('data-titulo') || '';
        header.classList.remove('bg-info', 'bg-warning', 'text-white');
        if (modo === 'editar') {
            id.value = button.getAttribute('data-id') || '';
            nombre.value = button.getAttribute('data-nombre') || '';
            tipo.value = button.getAttribute('data-tipo') || '';
            superficie.value = button.getAttribute('data-superficie') || 'Sintética';
            capacidad.value = button.getAttribute('data-capacidad') || '';
            descripcion.value = button.getAttribute('data-descripcion') || '';
            activo.checked = button.getAttribute('data-activo') === 'true';
            rutaImagen.value = button.getAttribute('data-imagen') || '';
            mostrarPreviewActual(rutaImagen.value);
            header.classList.add('bg-warning');
        } else {
            // Una cancha nueva inicia activa
            activo.checked = true;
            header.classList.add('bg-info', 'text-white');
        }
    });
});
