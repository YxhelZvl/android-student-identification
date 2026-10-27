package com.yxhelzvl.umsnh_id.core.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.yxhelzvl.umsnh_id.core.data.SessionManager
import com.yxhelzvl.umsnh_id.features.home.ui.HomeScreen
import com.yxhelzvl.umsnh_id.features.home.ui.HomeScreenRoot
import com.yxhelzvl.umsnh_id.features.home.ui.HomeViewModel

import com.yxhelzvl.umsnh_id.features.login.ui.LoginScreen
import com.yxhelzvl.umsnh_id.features.login.ui.LoginScreenRoot
import com.yxhelzvl.umsnh_id.features.login.ui.LoginViewModel
import com.yxhelzvl.umsnh_id.features.qrcreate.ui.QrCreateScreenRoot
import com.yxhelzvl.umsnh_id.features.qrcreate.ui.QrCreateViewModel
import com.yxhelzvl.umsnh_id.features.qrscanner.ui.QrScannerScreen
import com.yxhelzvl.umsnh_id.features.qrscanner.ui.QrScannerScreenRoot
import com.yxhelzvl.umsnh_id.features.register.ui.RegisterScreen
import com.yxhelzvl.umsnh_id.features.register.ui.RegisterScreenRoot
import com.yxhelzvl.umsnh_id.features.register.ui.RegisterViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yxhelzvl.umsnh_id.R
import com.yxhelzvl.umsnh_id.features.biblioteca.ui.BibliotecaScreen
import com.yxhelzvl.umsnh_id.features.prestado.biblioteca.MisLibrosPrestadosScreen
import com.yxhelzvl.umsnh_id.features.prestado.biblioteca.PedirPrestadoScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Route

@Composable
fun NavigationWrapper () {
    val navController = rememberNavController()
    val sessionManager: SessionManager = koinInject()
    var startDestination by remember { mutableStateOf<Any?>(null) }
    var id_user by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }

    if(loading){
        //FloatingLoader()
    }

    //verificamos estado de la sesion al iniciar
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val isValid = sessionManager.isSessionValid()
            startDestination = if(isValid){
                val userId = sessionManager.getCurrentUserId() ?: ""
                id_user = userId
                Home(userId)
            }else{
                Login
            }
            loading = false
        }

    }


    startDestination?.let { destination ->
        NavHost(navController = navController, startDestination = destination ){

            composable<Login> {
                LoginScreenRoot(
                    viewModel = koinViewModel<LoginViewModel>(),
                    navigateToHome = { id_student -> navController.navigate(Home(id_student = id_student)){
                        popUpTo<Login>{inclusive = true}
                    } },
                    navigateToRegister = {navController.navigate(Register)}
                )
            }

            composable<Register> {
                RegisterScreenRoot(
                    viewModel = koinViewModel<RegisterViewModel>(),
                    navigateToLogin = {navController.navigate(Login){
                        popUpTo<Login>{inclusive = true}
                    } }
                )
            }

            composable<Home> { backStackEntry ->
                val id_student = backStackEntry.toRoute<Home>()
                val home: Home = backStackEntry.toRoute()
                HomeScreenRoot(
                    viewModel = koinViewModel<HomeViewModel>(),
                    id_student = home.id_student,

                    navigateToGenerarQr = {id_student -> navController.navigate(QrCreate(id_student=id_student))},
                    navigateToEscanearQr = {navController.navigate(QrScanner)},
                    navigateToLogin = {navController.navigate(Login){
                        popUpTo<Home>{inclusive = true}
                    } },
                    navigateToBiblioteca = {navController.navigate(Biblioteca)}
                )
            }

            composable<QrScanner> {
                QrScannerScreenRoot (
                )
            }
            composable<QrCreate> {
                val qrCreate : QrCreate = it.toRoute()
                QrCreateScreenRoot(
                    viewModel = koinViewModel<QrCreateViewModel>(),
                    navigateToBack = {navController.popBackStack()}
                )
            }

            composable<Biblioteca>{
                BibliotecaScreen(navigateToPrestadoB = {
                    navController.navigate(PrestadoB)
                },
                    navigateToMisPrestados = {navController.navigate(MisLibrosPrestados)}
                )
            }
            composable<PrestadoB> {
                PedirPrestadoScreen()
            }

            composable<MisLibrosPrestados> {
                MisLibrosPrestadosScreen()
            }

        }
    }

}

