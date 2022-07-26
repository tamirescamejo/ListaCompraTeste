package br.com.zup.testelistacompras.produto

import android.view.View
import android.widget.EditText
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import br.com.zup.testelistacompras.R



@RunWith(AndroidJUnit4::class)
internal class ProdutoFragmentTest {

    @Test
    fun editTextShowError_whenBothIsEmpty() {
        val scenario = launchFragmentInContainer<ProdutoFragment>()
        onView(withId(R.id.bvAdicionar)).perform(click())
        onView(withId(R.id.etNomeProduto))
            .check(matches(hasErrorText("Por favor preencha o campo de nome")))
        onView(withId(R.id.etDetalheProduto))
            .check(matches(hasErrorText("Por favor preencha o campo de detalhe")))
    }

    @Test
    fun detalheShowErrorAndNomeDoesnt_whenOnlyDetalheIsEmpty() {
        val scenario = launchFragmentInContainer<ProdutoFragment>()
        onView(withId(R.id.etNomeProduto)).perform(typeText("jkgkjgk"))
        onView(withId(R.id.bvAdicionar)).perform(click())
        onView(withId(R.id.etDetalheProduto))
            .check(matches(hasErrorText("Por favor preencha o campo de detalhe")))
        onView(withId(R.id.etNomeProduto))
            .check(matches(hasNoErrorText()))
    }


    fun hasNoErrorText(): BoundedMatcher<View?, EditText> {
        return object : BoundedMatcher<View?, EditText>(EditText::class.java) {

            override fun matchesSafely(view: EditText): Boolean {
                return view.error == null
            }

            override fun describeTo(description: org.hamcrest.Description?) {
                description?.appendText("has no error text: ");
            }
        }
    }

    @Test
    fun recuperarDadosCampoEdicao_OnlyNomeEmpty_reutrnNull(){
        val prodFragment =ProdutoFragment()
        val produto = prodFragment.recuperarDadosCampoEdicao(
            nomeProduto = "",
            descricaoProduto = "alguma coisa"
        )
        assert(produto == null)
    }

    @Test
    fun recuperarDadosCampoEdicao_OnlyDescricaoEmpty_reutrnNull(){
        val prodFragment =ProdutoFragment()
        val produto = prodFragment.recuperarDadosCampoEdicao(
            nomeProduto = "alguma coisa",
            descricaoProduto = ""
        )
        assert(produto == null)
    }
}