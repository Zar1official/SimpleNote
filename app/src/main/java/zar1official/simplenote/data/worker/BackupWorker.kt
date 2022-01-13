package zar1official.simplenote.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import zar1official.simplenote.domain.usecases.GetAllNotesFlowUseCase

class BackupWorker(
    context: Context,
    workerParameters: WorkerParameters
) :
    CoroutineWorker(context, workerParameters), KoinComponent {

    private val getAllNotesFlowUseCase by inject<GetAllNotesFlowUseCase>()

    override suspend fun doWork(): Result =
        when (val notes = getAllNotesFlowUseCase().firstOrNull()) {
            null -> {
                Result.failure()
            }
            else -> {
                Log.d(LOG, "saved ${notes.size} notes")
                Result.success()
            }
        }

    companion object {
        private const val LOG = "wm_log"
        const val INTERVAL = 15L
    }
}

