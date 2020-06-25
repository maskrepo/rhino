package fr.convergence.proddoc.util.serdes

import fr.convergence.proddoc.model.ParametreTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

private val TOPIC = "dbserver1.gtc.p_parametre"

internal class MaskDeserializerTest {

    @Test
    internal fun shouldDeserializeClass() {
        val maskDeserializer = MaskDeserializer()
        var fileContent =
            MaskDeserializerTest::class.java.getResource("/fr.convergence.proddoc.util.serdes/parametre_test.json")
                .readText()

        val testObject = maskDeserializer.deserialize(TOPIC, fileContent.toByteArray())!!
        Assertions.assertNotNull(testObject)
        Assertions.assertEquals(testObject::class.java, ParametreTest::class.java)

        val testParametre: ParametreTest = testObject as ParametreTest
        Assertions.assertEquals("CLEF_TEST", testParametre.cle)
        Assertions.assertEquals("CLEF_VALEUR_Ë&€", testParametre.valeur)
    }

    @Test
    internal fun shouldFailIfTableNameNotFoundInJson() {
        val maskDeserializer = MaskDeserializer()

        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            { maskDeserializer.deserialize(TOPIC, "{ \"bad\" : \"format\" }".toByteArray())!! })
    }

    @Test
    internal fun shouldFailIfClassFromTableNameNotFound() {
        val maskDeserializer = MaskDeserializer()
        var fileContent =
            MaskDeserializerTest::class.java.getResource("/fr.convergence.proddoc.util.serdes/parametre_test_unknow_table.json")
                .readText()

        val assertThrows = Assertions.assertThrows(
            IllegalArgumentException::class.java,
            { maskDeserializer.deserialize(TOPIC, fileContent.toByteArray())!! }
        )
        Assertions.assertEquals("No class with annotation @MaskTable(\"table_de_test_introuvable\") found", assertThrows.message)
    }
}