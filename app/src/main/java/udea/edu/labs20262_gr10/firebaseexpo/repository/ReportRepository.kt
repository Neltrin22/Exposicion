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

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    suspend fun uploadImageAndGetUrl(context: Context, imageUri: Uri): Result<String> {
        return try {
            val fileName = "reports_images/${System.currentTimeMillis()}.jpg"
            val storageRef = storage.reference.child(fileName)

            val bytes = context.contentResolver.openInputStream(imageUri)?.use { it.readBytes() }
                ?: return Result.failure(Exception("No se pudo leer la imagen seleccionada"))

            storageRef.putBytes(bytes).await()

            val downloadUrl = storageRef.downloadUrl.await()
            Result.success(downloadUrl.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun saveReport(report: Report): Result<Boolean> = suspendCancellableCoroutine { continuation ->
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
    }

    // Escuchar reportes en tiempo real
    fun getReportsRealtime(): Flow<List<Report>> = callbackFlow {
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
    }
}
