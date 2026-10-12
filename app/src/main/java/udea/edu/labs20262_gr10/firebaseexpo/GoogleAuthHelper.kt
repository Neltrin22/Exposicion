package udea.edu.labs20262_gr10.firebaseexpo

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

class GoogleAuthHelper(private val context: Context) {

    // TODO 1: Inicializar las instancias de FirebaseAuth y CredentialManager
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val credentialManager: CredentialManager = CredentialManager.create(context)

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun signInWithGoogle(webClientId: String): Result<FirebaseUser> {
        return try {
            // TODO 2: Configurar la opción de inicio de sesión de Google (GetGoogleIdOption)
            /*
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()
            */

            // TODO 3: Solicitar credenciales al CredentialManager y autenticar con Firebase
            /*
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)

                val user = suspendCancellableCoroutine<FirebaseUser> { continuation ->
                    auth.signInWithCredential(firebaseCredential)
                        .addOnSuccessListener { authResult ->
                            val user = authResult.user
                            if (user != null) {
                                continuation.resume(user)
                            } else {
                                continuation.resumeWithException(Exception("Usuario nulo"))
                            }
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
                }
                Result.success(user)
            } else {
                Result.failure(Exception("Tipo de credencial no soportado"))
            }
            */
            Result.failure(Exception("Paso no implementado aún. Revisa los TODOs en GoogleAuthHelper.kt"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        // TODO 4: Cerrar sesión en Firebase
        auth.signOut()
    }
}
