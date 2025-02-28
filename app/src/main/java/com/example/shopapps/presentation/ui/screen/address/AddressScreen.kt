import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shopapps.domain.model.UserLocation
import com.example.shopapps.domain.model.user.Address
import com.example.shopapps.presentation.ui.navigation.CheckOut
import com.example.shopapps.presentation.ui.screen.address.AddressVIewModel

@Composable
fun AddressScreen(
    onConfirm:(String,String)->Unit,
    disMissDialog : (Boolean)->Unit
) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(onValueChange = { name = it }, value = name, maxLines = 1, placeholder = {
                Text("Name")
            })
            OutlinedTextField(onValueChange = { address = it }, value = address, maxLines = 1, placeholder = {
                Text("Address")
            })
            Button(onClick = {
                if (name.isNotEmpty() && address.isNotEmpty()) {
                    onConfirm(name,address)
                    disMissDialog(false)
                }
            }) {
                Text("Add Address")
            }
        }

}