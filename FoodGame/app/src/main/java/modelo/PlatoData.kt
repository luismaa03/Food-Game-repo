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
                    "¿Qué tipo de huevos has usado?",
                    listOf("De gallina", "De codorniz", "Ecológicos", "Otro"),
                    "De gallina",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido algún ingrediente extra?",
                    listOf("Sí", "No"),
                    "No",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Cómo has cocinado la tortilla?",
                    listOf("Poco hecha", "En su punto", "Muy hecha"),
                    "En su punto",
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
                    "¿Qué tipo de pan has usado?",
                    listOf("Blanco", "Integral", "Centeno", "Otro"),
                    "Blanco",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿El aguacate estaba maduro?",
                    listOf("Sí", "No"),
                    "Sí",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido algún otro ingrediente?",
                    listOf("Sí", "No"),
                    "No",
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
                    "¿Qué tipo de leche has usado?",
                    listOf("Almendras", "Vaca", "Soja", "Otro"),
                    "Almendras",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido algún otro ingrediente?",
                    listOf("Sí", "No"),
                    "No",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Estaba frío el batido?",
                    listOf("Sí", "No"),
                    "Sí",
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
                    "¿Qué tipo de yogur has usado?",
                    listOf("Natural", "Griego", "De sabores", "Otro"),
                    "Natural",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Las frutas estaban frescas?",
                    listOf("Sí", "No"),
                    "Sí",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido algún otro ingrediente?",
                    listOf("Sí", "No"),
                    "No",
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
                    "¿Qué tipo de pasta has usado?",
                    listOf("Macarrones", "Espaguetis", "Lazos", "Otro"),
                    "Macarrones",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido algún otro ingrediente?",
                    listOf("Sí", "No"),
                    "No",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Estaba fría la ensalada?",
                    listOf("Sí", "No"),
                    "Sí",
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
                    "¿Qué parte del pollo has usado?",
                    listOf("Pechuga", "Muslo", "Entero", "Otro"),
                    "Pechuga",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido alguna especia?",
                    listOf("Sí", "No"),
                    "No",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Estaba bien cocinado el pollo?",
                    listOf("Sí", "No"),
                    "Sí",
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
                    "¿Qué tipo de tofu has usado?",
                    listOf("Firme", "Sedoso", "Otro"),
                    "Firme",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido alguna otra verdura?",
                    listOf("Sí", "No"),
                    "No",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Estaban crujientes las verduras?",
                    listOf("Sí", "No"),
                    "Sí",
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
                    "¿Qué tipo de lentejas has usado?",
                    listOf("Pardinas", "Castellanas", "Verdinas", "Otro"),
                    "Pardinas",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido algún otro ingrediente?",
                    listOf("Sí", "No"),
                    "No",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Estaban bien cocinadas las lentejas?",
                    listOf("Sí", "No"),
                    "Sí",
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
                    "¿Qué tipo de leche has usado?",
                    listOf("Almendras", "Vaca", "Soja", "Otro"),
                    "Almendras",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido algún otro ingrediente?",
                    listOf("Sí", "No"),
                    "No",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Estaba frío el smoothie?",
                    listOf("Sí", "No"),
                    "Sí",
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
                    "¿Qué tipo de pan has usado?",
                    listOf("Blanco", "Integral", "Centeno", "Otro"),
                    "Blanco",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Qué tipo de jamón y queso has usado?",
                    listOf("York", "Serrano", "Cheddar", "Otro"),
                    "York",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido algún otro ingrediente?",
                    listOf("Sí", "No"),
                    "No",
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
                Pregunta("¿El hummus era casero?",
                    listOf("Sí", "No"),
                    "No",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Las zanahorias estaban frescas?",
                    listOf("Sí", "No"),
                    "Sí",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿Has añadido algún otro ingrediente?",
                    listOf("Sí", "No"),
                    "No",
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
                    "¿Qué tipo de pan has usado?",
                    listOf("Blanco", "Integral", "Centeno", "Otro"),
                    "Integral",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿La mantequilla de maní era natural?",
                    listOf("Sí", "No"),
                    "Sí",
                    TipoPregunta.OPCION_MULTIPLE
                ),
                Pregunta(
                    "¿El plátano estaba maduro?",
                    listOf("Sí", "No"),
                    "Sí",
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