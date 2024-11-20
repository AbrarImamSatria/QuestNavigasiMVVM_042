package com.example.pertemuan7prak

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pertemuan7prak.model.ListGender
import com.example.pertemuan7prak.ui.view.DetailMahasiswa
import com.example.pertemuan7prak.ui.view.FormMahasiswa
import com.example.pertemuan7prak.ui.viewmodel.MahasiswaViewModel

enum class Halaman{
    Form,
    Data
}

@Composable
fun Navigasi(
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(),
    navHost: NavHostController = rememberNavController()
){
    Scaffold { isipadding ->
        val uiState by viewModel.dataModel.collectAsState()
        NavHost(
            modifier = modifier.padding(isipadding),
            navController = navHost, startDestination = Halaman.Form.name
        ){
            composable(route = Halaman.Form.name){
                val konteks = LocalContext.current
                FormMahasiswa(

                    //Di bawah ini merupakan dari parameter halaman formulir
                    listGender = ListGender.listGender.map {
                        isi -> konteks.resources.getString(isi)
                    },
                    onSubmitClick = {
                        viewModel.saveDataMhs(it)
                        navHost.navigate(Halaman.Data.name)
                    }
                )
            }
            composable(route = Halaman.Data.name){
                DetailMahasiswa(
                    dataMhs = uiState,
                    onBackClick = {
                        navHost.popBackStack()
                    }
                )
            }

        }
    }

}