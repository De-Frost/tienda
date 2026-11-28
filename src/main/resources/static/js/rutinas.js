
/**
 * Agrega un producto al carrito asumiendo cantidad = 1.
 * @param {HTMLFormElement} formulario - El objeto form que contiene el ID del producto.
 */
function addCart(formulario) {
    // 1. Obtención de datos y ruta (solo el ID del producto)
    var idProducto = $(formulario).find('input[name="idProducto"]').val();
    var ruta = $(formulario).attr('action') || '/carrito/agregar'; // Lee la ruta del atributo 'action'

    // 2. Seguridad (CSRF Token)
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    // 3. Petición AJAX (solo se envía el ID del producto)
    $.ajax({
        url: ruta,
        type: 'POST',
        data: {
            // ** CRÍTICO: SOLO ENVIAMOS idProducto **
            idProducto: idProducto
        },
        beforeSend: function (xhr) {
            if (csrfHeader && csrfToken) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        },
        success: function (response) {
            // Actualiza el fragmento HTML del carrito
            $("#resultBlock").html(response);
            console.log("Producto agregado con cantidad por defecto (1).");
        },
        error: function (xhr, status, error) {
            // Manejo de errores
            var mensaje = xhr.responseText || 'Error en la conexión.';
            alert("Error al agregar producto: " + mensaje);
        }
    });
}
