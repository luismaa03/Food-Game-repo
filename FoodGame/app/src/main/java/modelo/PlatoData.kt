package modelo

object PlatoData {
    private val platosDesayunos = mutableListOf(
        Plato(
            "Tortilla francesa",
            200.0,
            listOf(
                Ingrediente("huevos", "Huevos", "2", "Proteínas, vitaminas A, D, E y B12"),
                Ingrediente("leche", "Leche", "50ml", "Calcio, proteínas, vitaminas A y D"),
                Ingrediente("sal", "Sal", "al gusto", "Sodio"),
                Ingrediente("pimienta", "Pimienta", "al gusto", "Antioxidantes")
            ),
            "tortilla_francesa",
            "1. Batir los huevos con la leche, sal y pimienta.\n2. Calentar una sartén con aceite.\n3. Verter la mezcla de huevos en la sartén.\n4. Cocinar a fuego medio hasta que la tortilla esté cuajada.\n5. Doblar la tortilla por la mitad y servir.",
            preguntas = listOf(
                Pregunta(
                    "¿Cuál es el ingrediente principal de la tortilla francesa?",
                    listOf("Harina", "Huevo", "Leche", "Mantequilla"),
                    "Huevo",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué tipo de cocción se usa para hacer una tortilla francesa?",
                    listOf("Horneado", "Hervido", "Frito", "A la plancha"),
                    "A la plancha",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Cuál de estos ingredientes puedes añadir para hacer una tortilla francesa más nutritiva?",
                    listOf("Chocolate", "Espinacas", "Azúcar", "Refresco"),
                    "Espinacas",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )
        ),
        Plato(
            "Tostadas con aguacate",
            150.0,
            listOf(
                Ingrediente("pan", "Pan", "2 rebanadas", "Carbohidratos, fibra"),
                Ingrediente("aguacate", "Aguacate", "1/2", "Grasas saludables, vitaminas C, E, K y B6"),
                Ingrediente("tomate", "Tomate", "1/2", "Vitaminas C y K, antioxidantes"),
                Ingrediente("aceiteoliva", "Aceite de oliva", "al gusto", "Grasas monoinsaturadas, antioxidantes")
            ),
            "tostada_aguacate",
            "1. Tostar el pan.\n2. Machacar el aguacate con un tenedor.\n3. Untar el aguacate sobre el pan tostado.\n4. Cortar el tomate en rodajas y colocarlas sobre el aguacate.\n5. Añadir un chorrito de aceite de oliva y servir.",
            preguntas = listOf(
                Pregunta(
                    "¿Cuál de estos nutrientes aporta el aguacate?",
                    listOf("Proteínas", "Grasas saludables", "Carbohidratos", "Azúcares"),
                    "Grasas saludables",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Por qué se recomienda usar pan integral en las tostadas?",
                    listOf("Tiene más fibra", "Es más dulce", "Se tuesta más rápido", "No tiene gluten"),
                    "Tiene más fibra",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué ingrediente de la receta es una buena fuente de antioxidantes?",
                    listOf("Aguacate", "Aceite de oliva", "Tomate", "Pan"),
                    "Tomate",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )

        ),
        Plato(
            "Batido verde",
            180.0,
            listOf(
                Ingrediente("espinacas", "Espinacas", "50g", "Vitaminas A, C y K, hierro"),
                Ingrediente("platano", "Plátano", "1", "Potasio, vitaminas B6 y C"),
                Ingrediente("leche_almendras", "Leche de almendras", "200ml", "Vitaminas E y D, calcio"),
                Ingrediente("semillas", "Semillas de chía", "1 cucharada", "Fibra, omega-3, calcio")
            ),
            "batido_verde",
            "1. Lavar las espinacas.\n2. Pelar y cortar el plátano.\n3. Colocar las espinacas, el plátano, la leche de almendras y las semillas de chía en una licuadora.\n4. Batir hasta obtener una mezcla homogénea.\n5. Servir frío.",
            preguntas = listOf(
                Pregunta(
                    "¿Qué beneficio aporta el plátano en este batido?",
                    listOf("Aporta potasio", "Da color verde", "Es rico en proteínas", "Tiene omega-3"),
                    "Aporta potasio",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Cuál de estos ingredientes es una buena fuente de hierro?",
                    listOf("Plátano", "Espinacas", "Leche de almendras", "Semillas de chía"),
                    "Espinacas",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Por qué se recomienda añadir semillas de chía al batido?",
                    listOf("Aportan calcio y omega-3", "Dan un color más intenso", "Hacen el batido más dulce", "Son bajas en fibra"),
                    "Aportan calcio y omega-3",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )

        ),
        Plato(
            "Yogur con frutas y granola",
            220.0,
            listOf(
                Ingrediente("yogur", "Yogur natural", "150g", "Proteínas, calcio, probióticos"),
                Ingrediente("fresas", "Fresas", "50g", "Vitamina C, antioxidantes"),
                Ingrediente("arandanos", "Arándanos", "30g", "Antioxidantes, vitaminas C y K"),
                Ingrediente("granola", "Granola", "2 cucharadas", "Fibra, carbohidratos")
            ),
            "yogurt_granola",
            "1. Lavar las fresas y los arándanos.\n2. Cortar las fresas en trozos pequeños.\n3. Colocar el yogur en un bol.\n4. Añadir las fresas y los arándanos.\n5. Espolvorear la granola por encima y servir.",
            preguntas = listOf(
                Pregunta(
                    "¿Qué nutriente principal aporta el yogur natural?",
                    listOf("Proteínas", "Vitamina C", "Fibra", "Omega-3"),
                    "Proteínas",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Cuál de estas frutas de la receta es rica en antioxidantes?",
                    listOf("Fresas", "Arándanos", "Plátano", "Manzana"),
                    "Arándanos",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Por qué la granola es un buen complemento para este desayuno?",
                    listOf("Aporta fibra y energía", "Hace el yogur más líquido", "Es rica en proteínas", "No aporta nutrientes"),
                    "Aporta fibra y energía",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )

        )
    )

    private val platosAlmuerzos = mutableListOf(
        Plato(
            "Ensalada de pasta",
            350.0,
            listOf(
                Ingrediente("pasta", "Pasta", "100g", "Carbohidratos"),
                Ingrediente("tomate", "Tomate", "1", "Vitaminas C y K, antioxidantes"),
                Ingrediente("lechuga", "Lechuga", "50g", "Vitaminas A y K, fibra"),
                Ingrediente("atun", "Atún", "80g", "Proteínas, omega-3"),
                Ingrediente("aceiteoliva", "Aceite de oliva", "al gusto", "Grasas monoinsaturadas, antioxidantes")
            ),
            "ensalada_pasta",
            "1. Cocer la pasta según las instrucciones del paquete.\n2. Lavar y cortar el tomate y la lechuga.\n3. Escurrir el atún.\n4. Mezclar la pasta, el tomate, la lechuga y el atún en un bol.\n5. Aliñar con aceite de oliva y servir.",
            preguntas = listOf(
                Pregunta(
                    "¿Qué nutriente principal aporta el yogur natural?",
                    listOf("Proteínas", "Vitamina C", "Fibra", "Omega-3"),
                    "Proteínas",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Cuál de estas frutas de la receta es rica en antioxidantes?",
                    listOf("Fresas", "Arándanos", "Plátano", "Manzana"),
                    "Arándanos",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Por qué la granola es un buen complemento para este desayuno?",
                    listOf("Aporta fibra y energía", "Hace el yogur más líquido", "Es rica en proteínas", "No aporta nutrientes"),
                    "Aporta fibra y energía",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )

        ),
        Plato(
            "Pollo asado con patatas",
            450.0,
            listOf(
                Ingrediente("pollo", "Pollo", "1 pechuga", "Proteínas, vitaminas del grupo B"),
                Ingrediente("patatas", "Patatas", "2", "Carbohidratos, potasio, vitamina C"),
                Ingrediente("cebolla", "Cebolla", "1/2", "Antioxidantes, vitaminas C y B6"),
                Ingrediente("ajo", "Ajo", "2 dientes", "Antioxidantes"),
                Ingrediente("especias", "Especias", "al gusto", "Antioxidantes, según la especie")
            ),
            "pollo_patatas",
            "1. Precalentar el horno a 200 grados.\n2. Salpimentar el pollo y colocarlo en una bandeja de horno.\n3. Pelar y cortar las patatas en rodajas.\n4. Picar la cebolla y el ajo.\n5. Colocar las patatas, la cebolla y el ajo alrededor del pollo.\n6. Añadir las especias al gusto.\n7. Hornear durante 45 minutos o hasta que el pollo esté dorado y las patatas estén tiernas.\n8. Servir.",
            preguntas = listOf(
                Pregunta(
                    "¿Cuál es el principal nutriente que aporta el pollo?",
                    listOf("Carbohidratos", "Proteínas", "Fibra", "Azúcares"),
                    "Proteínas",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Por qué es recomendable incluir patatas en este plato?",
                    listOf("Son ricas en carbohidratos y energía", "Aportan muchas proteínas", "Son bajas en fibra", "No tienen ningún beneficio"),
                    "Son ricas en carbohidratos y energía",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué ingrediente de la receta es una buena fuente de antioxidantes?",
                    listOf("Pollo", "Cebolla", "Patatas", "Sal"),
                    "Cebolla",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )
        ),
        Plato(
            "Salteado de verduras con tofu",
            320.0,
            listOf(
                Ingrediente("tofu", "Tofu", "200g", "Proteínas, calcio, hierro"),
                Ingrediente("zanahoria", "Zanahoria", "1", "Vitamina A, fibra"),
                Ingrediente("brocoli", "Brócoli", "100g", "Vitaminas C y K, fibra"),
                Ingrediente("pimiento", "Pimiento", "1/2", "Vitaminas C y A, antioxidantes"),
                Ingrediente("salsa_soja", "Salsa de soja", "al gusto", "Sodio")
            ),
            "salteado_verduras",
            "1. Cortar el tofu en cubos y dorarlo en una sartén con aceite.\n2. Lavar y cortar la zanahoria, el brócoli y el pimiento en trozos pequeños.\n3. Saltear las verduras en la sartén hasta que estén tiernas pero crujientes.\n4. Añadir el tofu y un chorrito de salsa de soja.\n5. Cocinar por unos minutos más y servir caliente.",
            preguntas = listOf(
                Pregunta(
                    "¿Cuál es el principal nutriente que aporta el tofu?",
                    listOf("Proteínas", "Carbohidratos", "Vitaminas", "Azúcares"),
                    "Proteínas",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué verdura de la receta es una buena fuente de vitamina A?",
                    listOf("Brócoli", "Pimiento", "Zanahoria", "Tofu"),
                    "Zanahoria",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Por qué se recomienda no cocinar demasiado las verduras en un salteado?",
                    listOf("Para mantener su textura crujiente", "Para que queden blandas", "Para que absorban más aceite", "Para cambiar su sabor"),
                    "Para mantener su textura crujiente",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )

        ),
        Plato(
            "Lentejas estofadas",
            400.0,
            listOf(
                Ingrediente("lentejas", "Lentejas", "150g", "Proteínas, hierro, fibra"),
                Ingrediente("zanahoria", "Zanahoria", "1", "Vitamina A, fibra"),
                Ingrediente("patatas", "Patatas", "1", "Carbohidratos, potasio, vitamina C"),
                Ingrediente("cebolla", "Cebolla", "1/2", "Antioxidantes, vitaminas C y B6"),
                Ingrediente("ajo", "Ajo", "2 dientes", "Antioxidantes, compuestos sulfurados"),
                Ingrediente("especias", "Especias", "al gusto", "Antioxidantes, según la especia")
            ),
            "lentejas_estofadas",
            "1. Lavar las lentejas.\n2. Pelar y cortar la zanahoria, la patata y la cebolla en trozos pequeños.\n3. Picar el ajo.\n4. Cocinar todos los ingredientes en una olla con agua y especias al gusto hasta que las lentejas estén tiernas.\n5. Servir caliente.",
            preguntas = listOf(
                Pregunta(
                    "¿Cuál es el principal nutriente que aportan las lentejas?",
                    listOf("Carbohidratos", "Proteínas", "Vitaminas", "Grasas"),
                    "Proteínas",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué ingrediente de la receta es una buena fuente de hierro?",
                    listOf("Zanahoria", "Patatas", "Lentejas", "Ajo"),
                    "Lentejas",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Por qué es recomendable añadir zanahoria a las lentejas?",
                    listOf("Aporta vitamina A y fibra", "Hace el plato más dulce", "Ayuda a espesar el caldo", "No tiene ningún beneficio"),
                    "Aporta vitamina A y fibra",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )
        )
    )

    private val platosMeriendas = mutableListOf(
        Plato(
            "Smoothie de frutas",
            150.0,
            listOf(
                Ingrediente("platano", "Plátano", "1", "Potasio, vitaminas B6 y C"),
                Ingrediente("fresas", "Fresas", "100g", "Vitamina C, antioxidantes"),
                Ingrediente("leche_almendras", "Leche de almendras", "150ml", "Vitaminas E y D, calcio")
            ),
            "smoothie",
            "1. Pelar y cortar el plátano.\n2. Lavar y cortar las fresas.\n3. Colocar el plátano, las fresas y la leche de almendras en una licuadora.\n4. Licuar hasta obtener una mezcla suave.\n5. Servir frío.",
            preguntas = listOf(
                Pregunta(
                    "¿Qué ingrediente es rico en vitamina C?",
                    listOf("Plátano", "Fresas", "Leche de almendras", "Ninguno"),
                    "Fresas",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué beneficio tiene la leche de almendras?",
                    listOf("Es rica en calcio", "Aporta proteínas", "Es una buena fuente de vitamina B12", "No tiene beneficios"),
                    "Es rica en calcio",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Cuál es la principal fuente de potasio en este smoothie?",
                    listOf("Plátano", "Fresas", "Leche de almendras", "No hay fuente de potasio"),
                    "Plátano",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )
        ),
        Plato(
            "Sándwich de jamón y queso",
            250.0,
            listOf(
                Ingrediente("pan", "Pan", "2 rebanadas", "Carbohidratos, fibra"),
                Ingrediente("jamon", "Jamón", "50g", "Proteínas, vitaminas del grupo B"),
                Ingrediente("queso", "Queso", "50g", "Proteínas, calcio, vitaminas A y D"),
                Ingrediente("tomate", "Tomate", "1/2", "Vitaminas C y K, antioxidantes"),
                Ingrediente("lechuga", "Lechuga", "al gusto", "Vitaminas A y K, fibra")
            ),
            "sandwich",
            "1. Tostar el pan (opcional).\n2. Colocar el jamón y el queso sobre una rebanada de pan.\n3. Lavar y cortar el tomate y la lechuga.\n4. Añadir el tomate y la lechuga sobre el jamón y el queso.\n5. Cubrir con la otra rebanada de pan.\n6. Servir.",
            preguntas = listOf(
                Pregunta(
                    "¿Cuál es el ingrediente principal de un sándwich de jamón y queso?",
                    listOf("Pollo", "Jamón", "Atún", "Carne de res"),
                    "Jamón",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué tipo de pan es comúnmente usado para hacer un sándwich de jamón y queso?",
                    listOf("Pan de pita", "Pan de baguette", "Pan de molde", "Pan de maíz"),
                    "Pan de molde",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué tipo de queso se utiliza frecuentemente en un sándwich de jamón y queso?",
                    listOf("Queso cheddar", "Queso fresco", "Queso azul", "Queso ricotta"),
                    "Queso cheddar",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )
        ),
        Plato(
            "Hummus con palitos de zanahoria",
            180.0,
            listOf(
                Ingrediente("hummus", "Hummus", "100g", "Proteínas, fibra, hierro"),
                Ingrediente("zanahoria", "Zanahoria", "2", "Vitamina A, fibra"),
            ),
            "hummus",
            "1. Lavar y pelar las zanahorias.\n2. Cortar las zanahorias en palitos.\n3. Colocar el hummus en un bol pequeño.\n4. Servir los palitos de zanahoria junto con el hummus.",
            preguntas = listOf(
                Pregunta(
                    "¿Qué beneficios tiene el hummus?",
                    listOf("Vitaminas A y C", "Calcio, magnesio", "Ácidos grasos omega-3", "Proteínas, fibra, hierro"),
                    "Proteínas, fibra, hierro",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué vitaminas se encuentran en las zanahorias?",
                    listOf("Vitamina A, fibra", "Vitamina C, hierro", "Vitamina B12, calcio", "Vitamina E, zinc"),
                    "Vitamina A, fibra",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué paso se debe hacer primero al preparar los palitos de zanahoria?",
                    listOf("Cortar las zanahorias", "Lavar y pelar las zanahorias", "Servir el hummus", "Colocar el hummus en un bol"),
                    "Lavar y pelar las zanahorias",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )
        ),
        Plato(
            "Tostadas de mantequilla de maní y plátano",
            200.0,
            listOf(
                Ingrediente("pan", "Pan", "2 rebanadas", "Carbohidratos, fibra"),
                Ingrediente("mantequilla", "Mantequilla de maní", "2 cucharadas", "Proteínas, grasas saludables"),
                Ingrediente("platano", "Plátano", "1", "Potasio, vitaminas B6 y C")
            ),
            "tostadas_platano",
            "1. Tostar el pan integral.\n2. Untar una capa de mantequilla de maní sobre las tostadas.\n3. Cortar el plátano en rodajas finas.\n4. Colocar las rodajas de plátano sobre las tostadas.\n5. Servir.",
            preguntas = listOf(
                Pregunta(
                    "¿Qué beneficios tiene el hummus?",
                    listOf("Proteínas, fibra, hierro", "Vitaminas A y C", "Calcio, magnesio", "Ácidos grasos omega-3"),
                    "Proteínas, fibra, hierro",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué vitaminas se encuentran en las zanahorias?",
                    listOf("Vitamina A, fibra", "Vitamina C, hierro", "Vitamina B12, calcio", "Vitamina E, zinc"),
                    "Vitamina A, fibra",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué paso se debe hacer primero al preparar los palitos de zanahoria?",
                    listOf("Cortar las zanahorias", "Lavar y pelar las zanahorias", "Servir el hummus", "Colocar el hummus en un bol"),
                    "Lavar y pelar las zanahorias",
                    TipoPregunta.OPCION_MULTIPLE
                )
            )

        )
    )

    fun getPlatosDesayunos(): List<Plato> {
        return platosDesayunos
    }

    fun getPlatosAlmuerzos(): List<Plato> {
        return platosAlmuerzos
    }

    fun getPlatosMeriendas(): List<Plato> {
        return platosMeriendas
    }

    fun getPlatoIngredientes(nombrePlato: String): List<Ingrediente> {
        return when (nombrePlato) {
            "Tortilla francesa" -> listOf(
                Ingrediente("huevos", "Huevos","2", "Proteínas, vitaminas A, D, E y B12"),
                Ingrediente("leche", "Leche","50ml", "Calcio, proteínas, vitaminas A y D"),
                Ingrediente("sal", "Sal","al gusto", "Sodio"),
                Ingrediente("pimienta", "Pimienta","al gusto", "Antioxidantes")
            )
            "Tostada con aguacate" -> listOf(
                Ingrediente("pan", "Pan","2 rebanadas", "Carbohidratos, fibra"),
                Ingrediente("aguacate", "Aguacate","1/2", "Grasas saludables, vitaminas C, E, K y B6"),
                Ingrediente("sal", "Sal","al gusto", "Sodio"),
                Ingrediente("pimienta", "Pimienta","al gusto", "Antioxidantes")
            )
            "Batido verde" -> listOf(
                Ingrediente("espinacas", "Espinacas","50g", "Vitaminas A, C y K, hierro"),
                Ingrediente("platano", "Plátano","1", "Potasio, vitaminas B6 y C"),
                Ingrediente("leche_almendras", "Leche de almendras","200ml", "Vitaminas E y D, calcio"),
                Ingrediente("semillas", "Semillas de chía","1 cucharada", "Fibra, omega-3, calcio")
            )
            "Yogur con frutas y granola" -> listOf(
                Ingrediente("yogur", "Yogur natural","150g", "Proteínas, calcio, probióticos"),
                Ingrediente("fresas", "Fresas","50g", "Vitamina C, antioxidantes"),
                Ingrediente("arandanos", "Arándanos","30g", "Antioxidantes, vitaminas C y K"),
                Ingrediente("granola", "Granola","2 cucharadas", "Fibra, carbohidratos")
            )
            "Ensalada de pasta" -> listOf(
                Ingrediente("pasta", "Pasta","100g", "Carbohidratos"),
                Ingrediente("tomate", "Tomate","1", "Vitaminas C y K, antioxidantes"),
                Ingrediente("lechuga", "Lechuga","50g", "Vitaminas A y K, fibra"),
                Ingrediente("atun", "Atún","80g", "Proteínas, omega-3"),
                Ingrediente("aceiteoliva", "Aceite de oliva","al gusto", "Grasas monoinsaturadas, antioxidantes")
            )
            "Pollo asado con patatas" -> listOf(
                Ingrediente("pollo", "Pollo","1 pechuga", "Proteínas, vitaminas del grupo B"),
                Ingrediente("patatas", "Patatas","2", "Carbohidratos, potasio, vitamina C"),
                Ingrediente("cebolla", "Cebolla","1/2", "Antioxidantes, vitaminas C y B6"),
                Ingrediente("ajo", "Ajo","2 dientes", "Antioxidantes"),
                Ingrediente("especias", "Especias","al gusto", "Antioxidantes, según la especie")
            )
            "Salteado de verduras con tofu" -> listOf(
                Ingrediente("tofu", "Tofu","200g", "Proteínas, calcio, hierro"),
                Ingrediente("zanahoria", "Zanahoria","1", "Vitamina A, fibra"),
                Ingrediente("brocoli", "Brócoli","100g", "Vitaminas C y K, fibra"),
                Ingrediente("pimiento", "Pimiento","1/2", "Vitaminas C y A, antioxidantes"),
                Ingrediente("salsa_soja", "Salsa de soja","al gusto", "Sodio")
            )
            "Lentejas estofadas" -> listOf(
                Ingrediente("lentejas", "Lentejas","150g", "Proteínas, hierro, fibra"),
                Ingrediente("zanahoria", "Zanahoria","1", "Vitamina A, fibra"),
                Ingrediente("patatas", "Patatas","1", "Carbohidratos, potasio, vitamina C"),
                Ingrediente("cebolla", "Cebolla","1/2", "Antioxidantes, vitaminas C y B6"),
                Ingrediente("ajo", "Ajo","2 dientes", "Antioxidantes, compuestos sulfurados"),
                Ingrediente("especias", "Especias","al gusto", "Antioxidantes, según la especia")
            )
            "Smoothie de frutas" -> listOf(
                Ingrediente("platano", "Plátano","1", "Potasio, vitaminas B6 y C"),
                Ingrediente("fresa", "Fresas","100g", "Vitamina C, antioxidantes"),
                Ingrediente("leche_almendras", "Leche de almendras","150ml", "Vitaminas E y D, calcio")
            )
            "Sándwich de jamón y queso" -> listOf(
                Ingrediente("pan", "Pan","2 rebanadas", "Carbohidratos, fibra"),
                Ingrediente("jamon", "Jamón","50g", "Proteínas, vitaminas del grupo B"),
                Ingrediente("queso", "Queso","50g", "Proteínas, calcio, vitaminas A y D"),
                Ingrediente("tomate", "Tomate","1/2", "Vitaminas C y K, antioxidantes"),
                Ingrediente("lechuga", "Lechuga","al gusto", "Vitaminas A y K, fibra")
            )
            "Hummus con palitos de zanahoria" -> listOf(
                Ingrediente("hummus", "Hummus","100g", "Proteínas, fibra, hierro"),
                Ingrediente("zanahoria", "Zanahoria","2", "Vitamina A, fibra")
            )
            "Tostadas de mantequilla de maní y plátano" -> listOf(
                Ingrediente("pan", "Pan","2 rebanadas", "Carbohidratos, fibra"),
                Ingrediente("mantequilla", "Mantequilla de maní","2 cucharadas", "Proteínas, grasas saludables"),
                Ingrediente("platano", "Plátano","1", "Potasio, vitaminas B6 y C")
            )
            else -> emptyList()
        }
    }
}