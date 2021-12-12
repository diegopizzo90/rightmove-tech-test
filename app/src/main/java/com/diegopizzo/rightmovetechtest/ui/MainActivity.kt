package com.diegopizzo.rightmovetechtest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.livedata.observeAsState
import com.diegopizzo.rightmovetechtest.ui.component.ShowAveragePropertiesPrice
import com.diegopizzo.rightmovetechtest.ui.theme.RightmoveTechTestTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAveragePropertiesPrice()

        setContent {
            val viewState = viewModel.viewStates.observeAsState()
            RightmoveTechTestTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ShowAveragePropertiesPrice(
                        average = viewState.value?.averagePrice,
                        isLoading = viewState.value?.isLoading
                    )
                }
            }
        }
    }
}