package ai.art.image.generator.avatar.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.art.image.generator.avatar.R
import ai.art.image.generator.avatar.databinding.FragmentLoginBinding
import android.content.Intent
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import java.util.Arrays


class LoginFragment : Fragment() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private lateinit var callbackManager: CallbackManager
    private val EMAIL = "email"
    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // FacebookSdk.sdkInitialize(requireContext())

        callbackManager = CallbackManager.Factory.create()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.signInButton.setOnClickListener { signIn() }



//        binding.loginButton.setReadPermissions(Arrays.asList<String>(EMAIL))
//        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//            override fun onSuccess(loginResult: LoginResult) {
//                // App code
//            }
//            override fun onCancel() {
//                // App code
//            }
//            override fun onError(exception: FacebookException) {
//                // App code
//            }
//        })
//
//        LoginManager.getInstance().registerCallback(callbackManager, object :
//            FacebookCallback<LoginResult> {
//            override fun onSuccess(loginResult: LoginResult) {
//                Log.v("qweqwe", loginResult.toString())
//            }
//            override fun onCancel() {
//                // App code
//            }
//            override fun onError(exception: FacebookException) {
//                Log.v("qweqwe", exception.toString())
//            }
//        })


        callbackManager = CallbackManager.Factory.create()

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired


            //   LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                Log.v("qweqwe", "susccess")
                val account = task.getResult(ApiException::class.java)
                handleSignInResult(account)
            } catch (e: ApiException) {

                Log.v("qweqwe", e.toString())
            }
        }
    }

    private fun handleSignInResult(completedTask: GoogleSignInAccount) {
        val displayName = completedTask.displayName
        Log.v("qweqwe", displayName.toString())
        // Отобразить имя в UI или выполнить какое-либо другое действие
    }
}