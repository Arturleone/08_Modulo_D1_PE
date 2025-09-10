package com.example.a08_modulo_d1_pe

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    val timer = 10000L

    @get:Rule
    val rule = createComposeRule()

    @Before
    fun Main() {
        rule.setContent {
            MainActivity().Main()
        }
    }

    fun await() {
        rule.run {
            waitForIdle()
            Thread.sleep(timer)
        }
    }

    @Test
    fun `1`() {
        rule.run {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            assertEquals("com.example.a08_modulo_d1_pe", appContext.packageName)

            await()

            onNodeWithTag("OutSearch").performTextInput("@#$")
            await()

            onNodeWithTag("OutSearch").performTextClearance()
            onNodeWithTag("OutSearch").performTextInput("Matemática-Avançada")
            await()

            onNodeWithTag("OutSearch").performTextClearance()
            onNodeWithTag("OutSearch").performTextInput("Mate")
            await()

            onNodeWithTag("OutSearch").performTextClearance()
            await()

            onNodeWithTag("Grid").performClick()
            await()


            onNodeWithTag("curso_Lógica").performTouchInput { longClick() }
            await()

            onNodeWithTag("fechar").performClick()
            await()

            onNodeWithTag("List").performClick()
            await()

            onNodeWithTag("curso_Lógica").performClick()
            await()
        }

    }

    @Test
    fun `2`() {
        rule.run {

            await()
            onNodeWithTag("BntCadastroCurso").performClick()
            onNodeWithTag("BntSalvar").performClick()
            await()

            onNodeWithTag("OutNomeCompleto").performTextInput("Desenvolvimento de Aplicativos Móveis")
            await()

            onNodeWithTag("BntSalvar").performClick()
            await()

            onNodeWithTag("OutNomeBreve").performTextInput("Mobile")
            await()

            onNodeWithTag("BntSalvar").performClick()
            await()

            onNodeWithTag("DropCatego").performClick()
            await()
            onNodeWithText("Qualidade de Software").performClick()
            await()

            onNodeWithTag("BntSalvar").performClick()
            await()

            onNodeWithTag("BntCalendInicio").performClick()
            onNodeWithTag("BntFecharInicio").performClick()
            onNodeWithTag("OutInicio").performTextInput("15/02/2025")
            await()

            onNodeWithTag("BntSalvar").performClick()
            await()

            onNodeWithTag("OutInicio").performTextClearance()
            onNodeWithTag("BntCalendInicio").performClick()
            onNodeWithTag("BntFecharInicio").performClick()
            onNodeWithTag("OutInicio").performTextInput("12/11/2025")
            await()

            onNodeWithTag("BntCalendFim").performClick()
            onNodeWithTag("BntFecharFim").performClick()
            onNodeWithTag("OutFim").performTextInput("12/10/2025")
            await()

            onNodeWithTag("BntSalvar").performClick()
            await()

            onNodeWithTag("OutFim").performTextClearance()
            onNodeWithTag("BntCalendFim").performClick()
            onNodeWithTag("BntFecharFim").performClick()
            onNodeWithTag("OutFim").performTextInput("12/12/2025")
            await()

            onNodeWithTag("BntSalvar").performClick()
            await()

            onNodeWithTag("RowProf").performClick()
            await()

            listOf("Ana Silva", "Caio Martins", "Luciana Costa", "Felipe Nogueira", "Júlia Fernandes", "Marcos Lima").forEach {
                onNodeWithTag(it).performClick()
                await()
            }

            onNodeWithTag("BntFecharModal").performClick()
            await()

            onNodeWithTag("BntSalvar").performClick()
            await()
        }
    }
}