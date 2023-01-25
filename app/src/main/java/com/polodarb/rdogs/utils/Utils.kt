package com.polodarb.rdogs.utils

import java.util.Locale

object Utils {

    fun listConverter(list: Map<String, List<String>>): List<String> {
        return list.flatMap { e ->
            e.value.map { it ->
                "${e.key.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }} $it"
            }.ifEmpty {
                listOf(e.key.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                })
            }
        }
    }

}