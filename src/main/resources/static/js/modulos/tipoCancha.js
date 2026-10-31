// Carga los datos del tipo de cancha en el modal Agregar o Editar
document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('tipoCanchaModal');
    if (!modal) {
        return;
    }
    const form = document.getElementById('tipoCanchaForm');
    const header = document.getElementById('tipoCanchaModalHeader');
    const titulo = document.getElementById('tipoCanchaModalTitulo');
    const id = document.getElementById('tipoCanchaId');
    const descripcion = document.getElementById('tipoCanchaDescripcion');
    const activo = document.getElementById('tipoCanchaActivo');
    modal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        if (!button) {
            return;
        }
        const modo = button.getAttribute('data-modo');
        // Se limpia cualquier información de una apertura anterior
        form.reset();
        id.value = '';
        titulo.textContent = button.getAttribute('data-titulo') || '';
        header.classList.remove('bg-info', 'bg-warning', 'text-white');
        if (modo === 'editar') {
            id.value = button.getAttribute('data-id') || '';
            descripcion.value = button.getAttribute('data-descripcion') || '';
            activo.checked = button.getAttribute('data-activo') === 'true';
            header.classList.add('bg-warning');
        } else {
            // Un tipo nuevo inicia activo
            activo.checked = true;
            header.classList.add('bg-info', 'text-white');
        }
    });
});
