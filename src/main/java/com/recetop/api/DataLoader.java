// In src/main/java/com/recetop/api/DataLoader.java
package com.recetop.api;

import com.recetop.api.dto.RecipeDto;
import com.recetop.api.repository.RecipeRepository;
import com.recetop.api.service.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository; // Inject the repository

    // Update the constructor to accept the repository
    public DataLoader(RecipeService recipeService, RecipeRepository recipeRepository) {
        this.recipeService = recipeService;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Checking if recipe data needs to be loaded...");

        // Use the repository's count() method for an efficient check
        if (recipeRepository.count() == 0) {
            System.out.println("Database is empty. Loading initial recipe data...");
            loadRecipes();
            System.out.println("Finished loading initial recipe data.");
        } else {
            System.out.println("Recipe data already exists, skipping data load.");
        }
    }

    private void loadRecipes() {
        // Create DTOs and use the service to save them
        createRecipeIfNotExists("Tarta de Espinaca", "https://example.com/espinaca.jpg", List.of("Plato Principal"), List.of("Espinaca", "Queso"), List.of("Cocinar"), "10m", "30m");
        createRecipeIfNotExists("Pollo al Horno", "https://example.com/pollo.jpg", List.of("Plato Principal"), List.of("Pollo", "Limón", "Papas"), List.of("Marinar y hornear"), "15m", "60m");
        createRecipeIfNotExists("Ensalada César", "https://example.com/cesar.jpg", List.of("Entrada", "Ensalada"), List.of("Lechuga", "Pollo", "Crutones", "Aderezo César"), List.of("Mezclar ingredientes"), "15m", "0m");
        createRecipeIfNotExists("Sopa de Tomate", "https://example.com/sopa.jpg", List.of("Entrada", "Sopa"), List.of("Tomate", "Albahaca", "Caldo"), List.of("Hervir y licuar"), "5m", "25m");
        createRecipeIfNotExists("Flan Casero", "https://example.com/flan.jpg", List.of("Postre"), List.of("Huevos", "Leche", "Azúcar"), List.of("Hacer caramelo, mezclar y hornear a baño maría"), "10m", "50m");
        createRecipeIfNotExists("Lentejas a la Española", "https://example.com/lentejas.jpg", List.of("Plato Principal", "Legumbres"), List.of("Lentejas", "Chorizo", "Verduras"), List.of("Cocinar todo junto a fuego lento"), "10m", "45m");
        createRecipeIfNotExists("Milanesa a la Napolitana", "https://example.com/milanesa.jpg", List.of("Plato Principal", "Carne"), List.of("Carne de ternera", "Pan rallado", "Huevo", "Salsa de tomate", "Jamón", "Queso"), List.of("Empanar y freír la carne, cubrir con salsa, jamón y queso, y gratinar."), "20m", "25m");
createRecipeIfNotExists("Empanadas de Carne", "https://example.com/empanadas.jpg", List.of("Entrada", "Carne"), List.of("Carne picada", "Cebolla", "Huevo duro", "Aceitunas", "Tapas de empanada"), List.of("Hacer el relleno, rellenar las tapas y hornear."), "30m", "20m");
createRecipeIfNotExists("Locro Criollo", "https://example.com/locro.jpg", List.of("Plato Principal", "Guiso"), List.of("Maíz blanco", "Porotos", "Panceta", "Chorizo colorado", "Zapallo"), List.of("Hervir todos los ingredientes a fuego lento por varias horas."), "30m", "180m");
createRecipeIfNotExists("Pastel de Papas", "https://example.com/pastel-de-papas.jpg", List.of("Plato Principal", "Carne"), List.of("Carne picada", "Papa", "Cebolla", "Huevo duro", "Queso rallado"), List.of("Hacer un puré y un relleno de carne, armar en capas y gratinar."), "25m", "40m");
createRecipeIfNotExists("Risotto de Hongos", "https://example.com/risotto.jpg", List.of("Plato Principal", "Arroz", "Vegetariano"), List.of("Arroz carnaroli", "Hongos secos", "Cebolla", "Vino blanco", "Caldo de verduras", "Queso parmesano"), List.of("Sofreír la cebolla, agregar el arroz y el vino, luego el caldo poco a poco."), "10m", "30m");
createRecipeIfNotExists("Gnocchi de Papa con Salsa Rosa", "https://example.com/gnocchi.jpg", List.of("Plato Principal", "Pasta"), List.of("Papa", "Harina", "Huevo", "Salsa de tomate", "Crema de leche"), List.of("Hacer los ñoquis, hervirlos y servirlos con la salsa rosa."), "40m", "15m");
createRecipeIfNotExists("Pizza de Muzzarella", "https://example.com/pizza.jpg", List.of("Plato Principal"), List.of("Harina", "Levadura", "Salsa de tomate", "Queso muzzarella", "Aceitunas"), List.of("Amasar la masa, dejar levar, agregar salsa y queso, y hornear."), "20m", "15m");
createRecipeIfNotExists("Tacos al Pastor", "https://example.com/tacos.jpg", List.of("Plato Principal", "Mexicana"), List.of("Carne de cerdo", "Achiote", "Piña", "Cebolla", "Cilantro", "Tortillas de maíz"), List.of("Marinar y cocinar la carne, servir en tortillas con piña y aderezos."), "30m", "20m");
createRecipeIfNotExists("Ceviche Peruano", "https://example.com/ceviche.jpg", List.of("Entrada", "Pescado"), List.of("Pescado blanco", "Limón", "Cebolla morada", "Cilantro", "Ají"), List.of("Cortar el pescado y marinar en jugo de limón con los demás ingredientes."), "20m", "15m");
createRecipeIfNotExists("Sushi Rolls (Maki)", "https://example.com/sushi.jpg", List.of("Plato Principal", "Japonesa"), List.of("Arroz de sushi", "Alga nori", "Pescado crudo", "Palta", "Pepino"), List.of("Extender el arroz sobre el alga, agregar rellenos y enrollar."), "45m", "0m");
createRecipeIfNotExists("Paella Valenciana", "https://example.com/paella.jpg", List.of("Plato Principal", "Española"), List.of("Arroz bomba", "Pollo", "Conejo", "Judías verdes", "Azafrán", "Caldo"), List.of("Sofreír las carnes y verduras, agregar el arroz y el caldo, y cocinar sin remover."), "20m", "40m");
createRecipeIfNotExists("Lasaña de Carne", "https://example.com/lasana.jpg", List.of("Plato Principal", "Pasta"), List.of("Láminas de lasaña", "Carne picada", "Salsa de tomate", "Salsa bechamel", "Queso"), List.of("Intercalar capas de pasta, relleno de carne, bechamel y queso. Hornear."), "30m", "45m");
createRecipeIfNotExists("Sándwich de Bondiola", "https://example.com/bondiola.jpg", List.of("Sándwich"), List.of("Bondiola de cerdo", "Pan ciabatta", "Cebolla caramelizada", "Rúcula"), List.of("Cocinar la bondiola a fuego lento, desmechar y servir en el pan."), "15m", "120m");
createRecipeIfNotExists("Polenta con Tuco y Queso", "https://example.com/polenta.jpg", List.of("Plato Principal"), List.of("Polenta", "Salsa de tomate", "Carne", "Queso cremoso"), List.of("Preparar la polenta y el tuco, servir con un trozo de queso."), "5m", "30m");
createRecipeIfNotExists("Humita en Chala", "https://example.com/humita.jpg", List.of("Plato Principal", "Regional"), List.of("Choclo", "Zapallo", "Cebolla", "Queso de cabra", "Chalas de choclo"), List.of("Rallar el choclo y zapallo, cocinar el relleno y armar en las chalas. Hervir."), "40m", "30m");
createRecipeIfNotExists("Chocotorta", "https://example.com/chocotorta.jpg", List.of("Postre"), List.of("Galletitas de chocolate", "Dulce de leche", "Queso crema"), List.of("Mezclar el dulce de leche con el queso crema, intercalar capas con galletitas humedecidas."), "20m", "0m");
createRecipeIfNotExists("Tiramisú", "https://example.com/tiramisu.jpg", List.of("Postre", "Italiana"), List.of("Vainillas", "Queso mascarpone", "Huevos", "Café", "Cacao en polvo"), List.of("Preparar la crema de mascarpone, mojar las vainillas en café y armar en capas."), "30m", "0m");
createRecipeIfNotExists("Lemon Pie", "https://example.com/lemon-pie.jpg", List.of("Postre", "Tarta"), List.of("Masa brisée", "Limón", "Leche condensada", "Huevos", "Merengue italiano"), List.of("Hacer una base de masa, rellenar con crema de limón y cubrir con merengue."), "30m", "20m");
createRecipeIfNotExists("Pancakes con Jarabe de Arce", "https://example.com/pancakes.jpg", List.of("Desayuno"), List.of("Harina", "Leche", "Huevo", "Polvo de hornear", "Jarabe de arce"), List.of("Mezclar los ingredientes y cocinar en una sartén caliente."), "10m", "15m");
createRecipeIfNotExists("Huevos Benedictinos", "https://example.com/benedictinos.jpg", List.of("Desayuno", "Brunch"), List.of("Pan de molde", "Jamón", "Huevo", "Manteca", "Limón"), List.of("Tostar el pan, pochar los huevos y cubrir con salsa holandesa."), "15m", "15m");
createRecipeIfNotExists("Moussaka Griega", "https://example.com/moussaka.jpg", List.of("Plato Principal", "Griega"), List.of("Berenjenas", "Carne de cordero picada", "Tomate", "Cebolla", "Salsa bechamel"), List.of("Freír las berenjenas, preparar el relleno y la bechamel, y hornear en capas."), "40m", "50m");
createRecipeIfNotExists("Pad Thai", "https://example.com/pad-thai.jpg", List.of("Plato Principal", "Tailandesa"), List.of("Fideos de arroz", "Langostinos", "Tofu", "Brotes de soja", "Salsa de tamarindo", "Maní"), List.of("Saltear los ingredientes en un wok y mezclar con la salsa."), "20m", "15m");
createRecipeIfNotExists("Gazpacho Andaluz", "https://example.com/gazpacho.jpg", List.of("Sopa", "Entrada", "Española"), List.of("Tomate", "Pepino", "Pimiento", "Ajo", "Aceite de oliva"), List.of("Licuar todos los ingredientes en frío."), "15m", "0m");
createRecipeIfNotExists("Falafel con Salsa Tahini", "https://example.com/falafel.jpg", List.of("Entrada", "Vegetariano"), List.of("Garbanzos", "Cilantro", "Perejil", "Ajo", "Salsa Tahini"), List.of("Procesar los ingredientes, formar bolitas y freír."), "20m", "10m");
createRecipeIfNotExists("Curry de Pollo", "https://example.com/curry-pollo.jpg", List.of("Plato Principal", "India"), List.of("Pollo", "Leche de coco", "Pasta de curry", "Cebolla", "Arroz basmati"), List.of("Sofreír y cocinar el pollo con la pasta de curry y la leche de coco."), "15m", "30m");
createRecipeIfNotExists("Chili con Carne", "https://example.com/chili.jpg", List.of("Plato Principal", "Guiso"), List.of("Carne picada", "Frijoles rojos", "Tomate", "Ají en polvo", "Comino"), List.of("Cocinar la carne con especias y tomate, agregar los frijoles al final."), "15m", "60m");
createRecipeIfNotExists("Pastel de Zanahoria", "https://example.com/carrot-cake.jpg", List.of("Postre"), List.of("Zanahoria rallada", "Harina", "Azúcar", "Nueces", "Queso crema"), List.of("Preparar el bizcocho y cubrir con frosting de queso crema."), "20m", "50m");
createRecipeIfNotExists("Brownies de Chocolate", "https://example.com/brownies.jpg", List.of("Postre"), List.of("Chocolate", "Manteca", "Azúcar", "Huevos", "Harina"), List.of("Derretir chocolate y manteca, mezclar con los demás ingredientes y hornear."), "15m", "25m");
createRecipeIfNotExists("Sopa Miso", "https://example.com/sopa-miso.jpg", List.of("Sopa", "Entrada", "Japonesa"), List.of("Caldo dashi", "Pasta de miso", "Tofu", "Alga wakame"), List.of("Disolver el miso en el caldo caliente, agregar tofu y algas."), "5m", "5m");
createRecipeIfNotExists("Ensalada Griega", "https://example.com/ensalada-griega.jpg", List.of("Ensalada"), List.of("Tomate", "Pepino", "Cebolla morada", "Queso feta", "Aceitunas negras"), List.of("Cortar y mezclar los vegetales, agregar queso y aceitunas."), "15m", "0m");
createRecipeIfNotExists("Papas Bravas", "https://example.com/papas-bravas.jpg", List.of("Entrada", "Tapa", "Española"), List.of("Papas", "Salsa brava", "Alioli"), List.of("Freír las papas y servirlas con las dos salsas."), "10m", "15m");
createRecipeIfNotExists("Croquetas de Jamón", "https://example.com/croquetas.jpg", List.of("Entrada", "Tapa", "Española"), List.of("Jamón serrano", "Leche", "Harina", "Manteca", "Pan rallado"), List.of("Hacer una bechamel espesa con jamón, enfriar, formar croquetas, empanar y freír."), "20m", "15m");
createRecipeIfNotExists("Causa Limeña", "https://example.com/causa.jpg", List.of("Entrada", "Peruana"), List.of("Papa amarilla", "Ají amarillo", "Limón", "Pollo", "Mayonesa"), List.of("Hacer un puré de papa sazonado, rellenar con ensalada de pollo."), "30m", "20m");
createRecipeIfNotExists("Feijoada Brasileña", "https://example.com/feijoada.jpg", List.of("Plato Principal", "Brasileña"), List.of("Frijoles negros", "Carne de cerdo", "Chorizo", "Naranja", "Arroz"), List.of("Cocinar los frijoles con las carnes por varias horas."), "20m", "180m");
createRecipeIfNotExists("Arepas Rellenas", "https://example.com/arepas.jpg", List.of("Plato Principal", "Venezolana"), List.of("Harina de maíz precocida", "Agua", "Sal", "Queso", "Carne desmechada"), List.of("Hacer la masa, formar las arepas, cocinarlas y rellenarlas."), "15m", "15m");
createRecipeIfNotExists("Ratatouille", "https://example.com/ratatouille.jpg", List.of("Plato Principal", "Vegetariano", "Francesa"), List.of("Berenjena", "Zucchini", "Tomate", "Pimiento", "Hierbas de Provenza"), List.of("Cortar los vegetales en rodajas finas y hornear sobre una base de salsa."), "30m", "50m");
createRecipeIfNotExists("Crème Brûlée", "https://example.com/creme-brulee.jpg", List.of("Postre", "Francesa"), List.of("Crema de leche", "Yemas de huevo", "Azúcar", "Vainilla"), List.of("Hornear la crema a baño maría, enfriar y caramelizar azúcar en la superficie."), "15m", "40m");
createRecipeIfNotExists("Scones Ingleses", "https://example.com/scones.jpg", List.of("Desayuno", "Merienda"), List.of("Harina", "Manteca", "Azúcar", "Leche", "Crema coagulada", "Mermelada"), List.of("Preparar la masa, cortar los scones y hornear. Servir con crema y mermelada."), "15m", "12m");
createRecipeIfNotExists("Revuelto Gramajo", "https://example.com/gramajo.jpg", List.of("Plato Principal", "Argentina"), List.of("Papas pay", "Jamón cocido", "Huevos", "Arvejas", "Cebolla"), List.of("Saltear el jamón y la cebolla, agregar los huevos y revolver. Incorporar las papas y arvejas."), "10m", "10m");
createRecipeIfNotExists("Vitel Toné", "https://example.com/vitel-tone.jpg", List.of("Entrada", "Navidad"), List.of("Peceto", "Atún", "Anchoas", "Mayonesa", "Alcaparras"), List.of("Hervir la carne. Preparar la salsa licuando el resto de los ingredientes. Filetear y salsear."), "15m", "90m");
createRecipeIfNotExists("Matambre a la Pizza", "https://example.com/matambre.jpg", List.of("Plato Principal", "Carne"), List.of("Matambre de ternera", "Salsa de tomate", "Queso muzzarella", "Orégano"), List.of("Tiernizar el matambre, cubrir con salsa y queso, y llevar a la parrilla o al horno."), "10m", "60m");
createRecipeIfNotExists("Alfajores de Maicena", "https://example.com/alfajores.jpg", List.of("Postre"), List.of("Maicena", "Harina", "Manteca", "Dulce de leche", "Coco rallado"), List.of("Hacer las tapitas, hornearlas, rellenar con dulce de leche y pasar por coco rallado."), "30m", "10m");
createRecipeIfNotExists("Ensalada Waldorf", "https://example.com/waldorf.jpg", List.of("Ensalada"), List.of("Manzana", "Apio", "Nueces", "Mayonesa", "Crema de leche"), List.of("Cortar y mezclar los ingredientes principales, aderezar con la salsa."), "15m", "0m");
createRecipeIfNotExists("Arroz con Leche", "https://example.com/arroz-con-leche.jpg", List.of("Postre"), List.of("Arroz", "Leche", "Azúcar", "Canela", "Cáscara de limón"), List.of("Hervir el arroz en la leche con los saborizantes a fuego lento."), "5m", "40m");
    }

    // Helper method to create and save a recipe using your service
    private void createRecipeIfNotExists(String name, String image, List<String> categories, List<String> ingredients, List<String> steps, String prepTime, String cookTime) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName(name);
        recipeDto.setImage(image);
        recipeDto.setCategories(categories);
        recipeDto.setIngredients(ingredients);
        recipeDto.setSteps(steps);
        recipeDto.setPrepTime(prepTime);
        recipeDto.setCookTime(cookTime);

        recipeService.createRecipe(recipeDto);
    }
}