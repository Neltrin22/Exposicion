package udea.edu.labs20262_gr10.firebaseexpo.repository

import android.content.Context
import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import udea.edu.labs20262_gr10.firebaseexpo.model.Report

class ReportRepository {

    // TODO 5: Inicializar las instancias de FirebaseFirestore y FirebaseStorage
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    // TODO 6: Subir la imagen seleccionada a Firebase Storage y obtener la URL pública
    suspend fun uploadImageAndGetUrl(context: Context, imageUri: Uri): Result<String> {
        return try {
            /*
            val fileName = "reports_images/${System.currentTimeMillis()}.jpg"
            val storageRef = storage.reference.child(fileName)

            val bytes = context.contentResolver.openInputStream(imageUri)?.use { it.readBytes() }
                ?: return Result.failure(Exception("No se pudo leer la imagen seleccionada"))

            storageRef.putBytes(bytes).await()

            val downloadUrl = storageRef.downloadUrl.await()
            Result.success(downloadUrl.toString())
            */
            Result.failure(Exception("Subida a Storage no implementada aún. Revisa TODO 6."))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // TODO 7: Guardar el objeto Reporte en la colección 'reports' de Cloud Firestore
    suspend fun saveReport(report: Report): Result<Boolean> = suspendCancellableCoroutine { continuation ->
        /*
        firestore.collection("reports")
            .add(report)
            .addOnSuccessListener {
                if (continuation.isActive) {
                    continuation.resume(Result.success(true))
                }
            }
            .addOnFailureListener { e ->
                if (continuation.isActive) {
                    continuation.resume(Result.failure(e))
                }
            }
        */
        continuation.resume(Result.failure(Exception("Guardado en Firestore no implementado aún. Revisa TODO 7.")))
    }

    // TODO 8: Escuchar reportes en tiempo real desde Cloud Firestore con addSnapshotListener
    fun getReportsRealtime(): Flow<List<Report>> = callbackFlow {
        /*
        val query = firestore.collection("reports")
            .orderBy("timestamp", Query.Direction.DESCENDING)

        val listenerRegistration = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val reportsList = snapshot.documents.mapNotNull { document ->
                    document.toObject(Report::class.java)
                }
                trySend(reportsList)
            }
        }

        awaitClose {
            listenerRegistration.remove()
        }
        */
        trySend(emptyList())
        awaitClose { }
    }
}
