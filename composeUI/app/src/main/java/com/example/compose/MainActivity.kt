package com.example.compose

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    Greeting("Android")
//                }
                Column {
                    Text(text = "Hello world!")
                    Text(text = "Test")
                    Row() {
                        Text(text = "Row1")
                        Text(text = "Row2")
                        ClickCounter()
                    }
                    RadioButtonGroup()
                    ExampleScreen()
                    RecyclerView()
                }
            }
        }
    }
}

@Composable
fun ExampleScreen(viewModel: MainViewModel = MainViewModel()) {
    val uiState = viewModel.uiState

    ExampleReusableComponent(someData = uiState.dataToDisplayOnScreen) {
        viewModel.somethingRelatedToBusinessLogic()
    }

}

@Composable
fun ExampleReusableComponent(someData: List<String>, onDoSomething: () -> Unit) {
    Button(onClick = onDoSomething) {
        Text(text = if(someData.size > 0) someData[0] else "empty!")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Composable
fun RecyclerView(names: List<String> = List(1000) { "$it" }) {
    LazyColumn() {
        items(items = names) {
            item -> MessageCard(item)
        }
    }
}
@Composable
fun MessageCard(msg: String) {
    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor by animateColorAsState(targetValue = if(isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface)
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            isExpanded = !isExpanded
            println("click => $msg")
        }
        .padding(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        // We toggle the isExpanded variable when we click on this Column
        Column() {
            Text("当前索引:")
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = "索引为--------> $msg ,这是一个可展开和关闭的 Text 控件:" +
                            "微凉的晨露 沾湿黑礼服\n" +
                            "石板路有雾 父在低诉\n" +
                            "无奈的觉悟 只能更残酷\n" +
                            "一切都为了通往圣堂的路",
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}


@Composable
fun RadioButtonGroup() {
    val options = listOf("选项③", "选项④", "选项⑤", "选项⑥")
    val selectedButton = remember { mutableStateOf(options.first()) }
    //RadioButton 不带 text 文本控件,
    Row() {
        options.forEach {
            val isSelected = it == selectedButton.value
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    selectedButton.value = it
                }) {
                RadioButton(
                    selected = isSelected, onClick = null,
                )
                Text(it, textAlign = TextAlign.Justify)
            }
        }
    }
}

@Composable
fun ClickCounter() {
    var clickTime by remember {
        mutableStateOf(1)
    }


    Button(onClick = {
        clickTime ++
    }) {
        Text("I've been clicked $clickTime times")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Greeting("Android")
    }
}