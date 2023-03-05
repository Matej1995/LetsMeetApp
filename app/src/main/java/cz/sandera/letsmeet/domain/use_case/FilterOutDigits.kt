package cz.sandera.letsmeet.domain.use_case

class FilterOutDigits {

    operator fun invoke(text: String): String = text.filter { it.isDigit() }

}