package modelo

data class dataClassTickets (
    val uuid: String,
    var numero_ticket: Int,
    var titulo_ticket: String,
    var descripcion_ticket: String,
    var autor_ticket: String,
    var email_autor: String,
    var creacion_ticket: String,
    var estado_ticket: String,
    var finalizacion_ticket: String
)