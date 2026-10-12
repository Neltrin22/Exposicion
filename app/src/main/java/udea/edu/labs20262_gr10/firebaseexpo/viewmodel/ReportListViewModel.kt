package udea.edu.labs20262_gr10.firebaseexpo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import udea.edu.labs20262_gr10.firebaseexpo.model.Report
import udea.edu.labs20262_gr10.firebaseexpo.repository.ReportRepository

class ReportListViewModel : ViewModel() {

    private val repository = ReportRepository()

    val reports: StateFlow<List<Report>> = repository.getReportsRealtime()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
