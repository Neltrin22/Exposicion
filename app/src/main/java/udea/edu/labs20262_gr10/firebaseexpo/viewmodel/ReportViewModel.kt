package udea.edu.labs20262_gr10.firebaseexpo.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import udea.edu.labs20262_gr10.firebaseexpo.model.Report
import udea.edu.labs20262_gr10.firebaseexpo.repository.ReportRepository

class ReportViewModel : ViewModel() {

    private val repository = ReportRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _uploadResult = MutableStateFlow<String?>(null)
    val uploadResult: StateFlow<String?> = _uploadResult

    fun submitReport(
        context: Context,
        imageUri: Uri,
        title: String,
        description: String,
        userId: String,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _uploadResult.value = null

            try {
                val imageResult = repository.uploadImageAndGetUrl(context, imageUri)

                imageResult.onSuccess { imageUrl ->
                    val newReport = Report(
                        title = title,
                        description = description,
                        imageUrl = imageUrl,
                        userId = userId
                    )
                    val saveResult = repository.saveReport(newReport)

                    saveResult.onSuccess {
                        _uploadResult.value = "¡Reporte e imagen guardados exitosamente!"
                        onSuccess()
                    }.onFailure { exception ->
                        _uploadResult.value = "Error al guardar en Firestore: ${exception.localizedMessage}"
                    }
                }.onFailure { exception ->
                    _uploadResult.value = "Error al subir a Storage: ${exception.localizedMessage}"
                }
            } catch (e: Exception) {
                _uploadResult.value = "Error en el proceso: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearUploadResult() {
        _uploadResult.value = null
    }
}
