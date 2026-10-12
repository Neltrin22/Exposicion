package udea.edu.labs20262_gr10.firebaseexpo.model

data class Report(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "", // Aquí guardaremos el link de la foto subida a Storage
    val userId: String = "",    // Para saber quién hizo el reporte
    val timestamp: Long = System.currentTimeMillis()
)
