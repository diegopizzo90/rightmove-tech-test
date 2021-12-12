package com.diegopizzo.rightmovetechtest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.diegopizzo.network.base.isTrue
import com.diegopizzo.rightmovetechtest.R
import com.diegopizzo.rightmovetechtest.ui.theme.DefaultText
import com.diegopizzo.rightmovetechtest.ui.theme.RightmoveTechTestTheme
import com.diegopizzo.rightmovetechtest.ui.theme.defaultPadding
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

@Composable
fun ShowAveragePropertiesPrice(average: String?, isLoading: Boolean?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        DefaultText(
            text = stringResource(R.string.average_price_label),
            modifier = Modifier.padding(bottom = defaultPadding)
        )
        DefaultText(text = average ?: stringResource(id = R.string.average_not_available))
        if (isLoading.isTrue()) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RightmoveTechTestTheme {
        ShowAveragePropertiesPrice("1234567.98", isLoading = true)
    }
}