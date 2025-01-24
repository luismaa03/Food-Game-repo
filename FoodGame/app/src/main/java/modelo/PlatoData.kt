package modelo

object PlatoData {
    private val platosDesayunos = mutableListOf(
        Plato("Tortilla francesa", 200.0, "Huevos, leche, sal, pimienta", "tortilla_francesa", "1. Batir los huevos con la leche, sal y pimienta.\n2. Calentar una sartén con aceite.\n3. Verter la mezcla de huevos en la sartén.\n4. Cocinar a fuego medio hasta que la tortilla esté cuajada.\n5. Doblar la tortilla por la mitad y servir."),
        Plato("Tostadas con aguacate", 150.0, "Pan, aguacate, tomate, aceite de oliva", "tostada_aguacate", "1. Tostar el pan.\n2. Machacar el aguacate con un tenedor.\n3. Untar el aguacate sobre el pan tostado.\n4. Cortar el tomate en rodajas y colocarlas sobre el aguacate.\n5. Añadir un chorrito de aceite de oliva y servir."),
        Plato("Batido verde", 180.0, "Espinacas, plátano, leche de almendras, semillas de chía", "batido_verde", "1. Lavar las espinacas.\n2. Pelar y cortar el plátano.\n3. Colocar las espinacas, el plátano, la leche de almendras y las semillas de chía en una licuadora.\n4. Batir hasta obtener una mezcla homogénea.\n5. Servir frío."),
        Plato("Yogur con frutas y granola", 220.0, "Yogur natural, fresas, arándanos, granola", "yogurt_granola", "1. Lavar las fresas y los arándanos.\n2. Cortar las fresas en trozos pequeños.\n3. Colocar el yogur natural en un recipiente.\n4. Añadir las fresas, los arándanos y la granola encima del yogur.\n5. Mezclar ligeramente y servir.")
    )
    private val platosAlmuerzos = mutableListOf(
        Plato("Ensalada de pasta", 350.0, "Pasta, tomate, lechuga, atún, aceite de oliva", "ensalada_pasta", "1. Cocer la pasta según las instrucciones del paquete.\n2. Lavar y cortar el tomate y la lechuga.\n3. Escurrir el atún.\n4. Mezclar la pasta, el tomate, la lechuga y el atún en un bol.\n5. Aliñar con aceite de oliva y servir."),
        Plato("Pollo asado con patatas", 450.0, "Pollo, patatas, cebolla, ajo, especias", "pollo_patatas", "1. Precalentar el horno a 200 grados.\n2. Salpimentar el pollo y colocarlo en una bandeja de horno.\n3. Pelar y cortar las patatas en rodajas.\n4. Picar la cebolla y el ajo.\n5. Colocar las patatas, la cebolla y el ajo alrededor del pollo.\n6. Añadir las especias al gusto.\n7. Hornear durante 45 minutos o hasta que el pollo esté dorado y las patatas estén tiernas.\n8. Servir."),
        Plato("Salteado de verduras con tofu", 320.0, "Tofu, zanahoria, brócoli, pimiento, salsa de soja", "salteado_verduras", "1. Cortar el tofu en cubos y dorarlo en una sartén con aceite.\n2. Lavar y cortar la zanahoria, el brócoli y el pimiento en trozos pequeños.\n3. Saltear las verduras en la sartén hasta que estén tiernas pero crujientes.\n4. Añadir el tofu y un chorrito de salsa de soja.\n5. Cocinar por unos minutos más y servir caliente."),
        Plato("Lentejas estofadas", 400.0, "Lentejas, zanahoria, patata, cebolla, ajo, especias", "lentejas_estofadas", "1. Lavar las lentejas.\n2. Pelar y cortar la zanahoria, la patata y la cebolla en trozos pequeños.\n3. Picar el ajo.\n4. Cocinar todos los ingredientes en una olla con agua y especias al gusto hasta que las lentejas estén tiernas.\n5. Servir caliente.")
    )

    private val platosMeriendas = mutableListOf(
        Plato("Smoothie de frutas", 150.0, "Plátano, fresas, leche de almendras", "smoothie", "1. Pelar y cortar el plátano.\n2. Lavar y cortar las fresas.\n3. Colocar el plátano, las fresas y la leche de almendras en una licuadora.\n4. Licuar hasta obtener una mezcla suave.\n5. Servir frío."),
        Plato("Sándwich de jamón y queso", 250.0, "Pan, jamón, queso, tomate, lechuga", "sandwich", "1. Tostar el pan (opcional).\n2. Colocar el jamón y el queso sobre una rebanada de pan.\n3. Lavar y cortar el tomate y la lechuga.\n4. Añadir el tomate y la lechuga sobre el jamón y el queso.\n5. Cubrir con la otra rebanada de pan.\n6. Servir."),
        Plato("Hummus con palitos de zanahoria", 180.0, "Hummus, zanahorias", "hummus", "1. Lavar y pelar las zanahorias.\n2. Cortar las zanahorias en palitos.\n3. Colocar el hummus en un bol pequeño.\n4. Servir los palitos de zanahoria junto con el hummus."),
        Plato("Tostadas de mantequilla de maní y plátano", 200.0, "Pan integral, mantequilla de maní, plátano", "tostadas_platano", "1. Tostar el pan integral.\n2. Untar una capa de mantequilla de maní sobre las tostadas.\n3. Cortar el plátano en rodajas finas.\n4. Colocar las rodajas de plátano sobre las tostadas.\n5. Servir.")
    )


    fun getPlatosDesayunos(): MutableList<Plato> {
        return platosDesayunos
    }

    fun getPlatosAlmuerzos(): MutableList<Plato> {
        return platosAlmuerzos
    }

    fun getPlatosMeriendas(): MutableList<Plato> {
        return platosMeriendas
    }
}