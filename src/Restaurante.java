import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Restaurante {
    public static final String RESET = "\u001B[0m";
    public static final String ROJO_ERROR = "\u001B[31m";
    public static final String VERDE_EXITO = "\u001B[32m";
    public static final String AMARILLO_ADVERTENCIA = "\u001B[33m";
    public static final String AZUL_PRINCIPAL = "\u001B[34m";
    public static final String MAGENTA_PAGO = "\u001B[35m";
    public static final String CIAN_INFO = "\u001B[36m";

    private List<Plato> menu;
    private List<Bebida> menuBar;
    private Map<Integer, Comanda> comandasActivas;
    private Map<Integer, Boolean> estadoMesas;
    private int numeroMesasDisponibles = 10;

    public Restaurante() {
        this.menu = crearMenu();
        this.menuBar = crearMenuBar();
        this.comandasActivas = new HashMap<>();
        this.estadoMesas = inicializarMesas(numeroMesasDisponibles);
    }

    private Map<Integer, Boolean> inicializarMesas(int numMesas) {
        Map<Integer, Boolean> mesas = new HashMap<>();
        for (int i = 1; i <= numMesas; i++) {
            mesas.put(i, false);
        }
        return mesas;
    }

    private List<Plato> crearMenu() {
        List<Plato> menuInicial = new ArrayList<>();
        menuInicial.add(new Plato("Hamburguesa          ", 18500.0, "parrilla"));
        menuInicial.add(new Plato("Papas Fritas         ", 4000.0, "caliente"));
        menuInicial.add(new Plato("Ensalada César       ", 17000.0, "caliente"));
        menuInicial.add(new Plato("Filete de Res        ", 20000.0, "parrilla"));
        menuInicial.add(new Plato("Pollo a la Brasa     ", 15000.0, "parrilla"));
        menuInicial.add(new Plato("Sopa de Tomate       ", 12500.0, "caliente"));
        return menuInicial;
    }

    private List<Bebida> crearMenuBar() {
        List<Bebida> menuBarInicial = new ArrayList<>();
        menuBarInicial.add(new Bebida("Agua                 ", 2500.0));
        menuBarInicial.add(new Bebida("Gaseosa              ", 3500.0));
        menuBarInicial.add(new Bebida("Jugo Natural         ", 5000.0));
        menuBarInicial.add(new Bebida("Cerveza             ", 6000.0));
        menuBarInicial.add(new Bebida("Vino Tinto (Copa)   ", 8000.0));
        return menuBarInicial;
    }

    public void mostrarMenu() {
        System.out.println(AZUL_PRINCIPAL + "\n        Menú del Restaurante " + RESET);
        System.out.println(AZUL_PRINCIPAL + "Platos:" + RESET);
        for (int i = 0; i < menu.size(); i++) {
            System.out.println(VERDE_EXITO + (i + 1) + ". " + menu.get(i) + RESET);
        }
        System.out.println(AZUL_PRINCIPAL + "---------------------------------" + RESET);
        System.out.println(AZUL_PRINCIPAL + "Bebidas:" + RESET);
        for (int i = 0; i < menuBar.size(); i++) {
            System.out.println(VERDE_EXITO + (menu.size() + i + 1) + ". " + menuBar.get(i) + RESET);
        }
        System.out.println(AZUL_PRINCIPAL + "---------------------------------\n" + RESET);
    }

    public void mostrarEstadoMesas() {
        System.out.println(AZUL_PRINCIPAL + "\n        Estado de Mesas " + RESET);
        for (int i = 1; i <= numeroMesasDisponibles; i++) {
            System.out.println(CIAN_INFO + "Mesa " + i + ": " + (estadoMesas.get(i) ? ROJO_ERROR + "Ocupada" + RESET : VERDE_EXITO + "Libre" + RESET));
        }
    }

    public void crearNuevaComanda(int numeroMesa) {
        if (numeroMesa < 1 || numeroMesa > numeroMesasDisponibles) {
            System.out.println(ROJO_ERROR + "Error: Número de mesa inválido." + RESET);
            return;
        }
        if (estadoMesas.getOrDefault(numeroMesa, false)) {
            System.out.println(ROJO_ERROR + "Error: La mesa " + numeroMesa + " ya está ocupada." + RESET);
            return;
        }
        Comanda comanda = new Comanda(numeroMesa);
        comandasActivas.put(numeroMesa, comanda);
        estadoMesas.put(numeroMesa, true);
        System.out.println(VERDE_EXITO + "Se ha creado una nueva comanda para la mesa " + numeroMesa + "." + RESET);
    }

    public void agregarItemAComanda(int numeroMesa, int opcion) {
        if (numeroMesa < 1 || numeroMesa > numeroMesasDisponibles || !estadoMesas.getOrDefault(numeroMesa, false)) {
            System.out.println(ROJO_ERROR + "Error: No hay una comanda activa para la mesa " + numeroMesa + "." + RESET);
            return;
        }
        Comanda comanda = comandasActivas.get(numeroMesa);
        if (comanda != null) {
            if (opcion > 0 && opcion <= menu.size()) {
                Plato platoSeleccionado = menu.get(opcion - 1);
                Pedido item = new Pedido(platoSeleccionado.getNombre(), platoSeleccionado.getPrecio(), platoSeleccionado.getCocina());
                comanda.agregarItem(item);
                System.out.println(VERDE_EXITO + "✔ Se agregó: " + platoSeleccionado.getNombre() + " a la comanda de la mesa " + numeroMesa + "." + RESET);
            } else if (opcion > menu.size() && opcion <= menu.size() + menuBar.size()) {
                Bebida bebidaSeleccionada = menuBar.get(opcion - 1 - menu.size());
                Pedido item = new Pedido(bebidaSeleccionada.getNombre(), bebidaSeleccionada.getPrecio(), "bar");
                comanda.agregarItem(item);
                System.out.println(VERDE_EXITO + "✔ Se agregó: " + bebidaSeleccionada.getNombre() + " a la comanda de la mesa " + numeroMesa + "." + RESET);
            } else if (opcion != 0) {
                System.out.println(ROJO_ERROR + "Error: Opción inválida." + RESET);
            }
        }
    }

    public void mostrarComandaPorMesa(int numeroMesa) {
        if (numeroMesa < 1 || numeroMesa > numeroMesasDisponibles) {
            System.out.println(ROJO_ERROR + "Error: Número de mesa inválido." + RESET);
            return;
        }
        Comanda comanda = comandasActivas.get(numeroMesa);
        if (comanda != null) {
            System.out.println(AZUL_PRINCIPAL + "\n     Comanda para la Mesa: " + numeroMesa  + RESET);
            System.out.println(CIAN_INFO + "Detalle general:" + RESET);
            System.out.println(comanda);
            mostrarComandaPorCocina(comanda);
        } else {
            System.out.println(ROJO_ERROR + "La mesa " + numeroMesa + " no tiene una comanda activa." + RESET);
        }
    }

    public void pagarMesa(int numeroMesa, Scanner scanner) {
        if (numeroMesa < 1 || numeroMesa > numeroMesasDisponibles) {
            System.out.println(ROJO_ERROR + "Error: Número de mesa inválido." + RESET);
            return;
        }
        if (estadoMesas.getOrDefault(numeroMesa, false)) {
            Comanda comanda = comandasActivas.get(numeroMesa);
            if (comanda != null) {
                System.out.println(AZUL_PRINCIPAL + "\n     Detalle de la Cuenta para la Mesa: " + numeroMesa + RESET);
                double totalCuenta = 0;
                for (Pedido item : comanda.getItems()) {
                    System.out.println(CIAN_INFO + "- " + item.getNombre() + ": $" + String.format("%.3f", item.getPrecio()) + RESET);
                    totalCuenta += item.getPrecio();
                }
                System.out.println(AZUL_PRINCIPAL + "-----------------------------------------" + RESET);
                System.out.println(AMARILLO_ADVERTENCIA + "Total a pagar:        $" + String.format("%.3f", totalCuenta) + RESET);
                System.out.println(AZUL_PRINCIPAL + "-----------------------------------------" + RESET);

                System.out.print(CIAN_INFO + "¿Confirmar pago y liberar la mesa? (si/no): " + RESET);
                String respuesta = scanner.next().toLowerCase();

                if (respuesta.equals("si")) {
                    System.out.print(CIAN_INFO + "Ingrese el valor con el que va a pagar: $" + RESET);
                    double valorPagado = scanner.nextDouble();

                    if (valorPagado >= totalCuenta) {
                        double vueltas = valorPagado - totalCuenta;
                        System.out.println(MAGENTA_PAGO + "Valor pagado:           $" + String.format("%.3f", valorPagado) + RESET);
                        System.out.println(MAGENTA_PAGO + "Vueltas:                $" + String.format("%.3f", vueltas) + RESET);
                        comandasActivas.remove(numeroMesa);
                        estadoMesas.put(numeroMesa, false);
                        System.out.println(VERDE_EXITO + "\n¡Pago exitoso! La mesa " + numeroMesa + " está ahora libre. ¡Gracias por su visita, vuelva pronto!" + RESET);
                    } else {
                        System.out.println(ROJO_ERROR + "¡Error! El valor ingresado es insuficiente. Faltan $" + String.format("%.3f", (totalCuenta - valorPagado)) + "." + RESET);
                        System.out.println(ROJO_ERROR + "Pago cancelado. La mesa " + numeroMesa + " sigue ocupada." + RESET);
                    }
                } else {
                    System.out.println(AMARILLO_ADVERTENCIA + "Pago cancelado por el usuario. La mesa " + numeroMesa + " sigue ocupada." + RESET);
                }
            } else {
                System.out.println(ROJO_ERROR + "La mesa " + numeroMesa + " no tiene una comanda activa para pagar." + RESET);
            }
        } else {
            System.out.println(AMARILLO_ADVERTENCIA + "La mesa " + numeroMesa + " ya está libre o no tiene una comanda activa." + RESET);
        }
    }

    public void mostrarMenuMesas() {
        System.out.println(AZUL_PRINCIPAL + "\n       Menú de Gestión de Mesas " + RESET);
        System.out.println(VERDE_EXITO + "1. Mostrar estado de mesas" + RESET);
        System.out.println(VERDE_EXITO + "2. Crear nueva comanda" + RESET);
        System.out.println(VERDE_EXITO + "3. Ver comanda por mesa" + RESET);
        System.out.println(VERDE_EXITO + "4. Pagar mesa" + RESET);
        System.out.println(VERDE_EXITO + "0. Volver al menú principal" + RESET);
        System.out.print(CIAN_INFO + "Seleccione una opción: " + RESET);
    }

    public void mostrarComandaPorCocina(Comanda comanda) {
        List<Pedido> itemsParrilla = comanda.getItemsPorCocina("parrilla");
        List<Pedido> itemsCaliente = comanda.getItemsPorCocina("caliente");
        List<Pedido> itemsBar = comanda.getItemsPorCocina("bar");

        System.out.println(AZUL_PRINCIPAL + "\n    Comanda por Sección:" + RESET);

        System.out.println(CIAN_INFO + "   Parrilla:" + RESET);
        if (!itemsParrilla.isEmpty()) {
            for (Pedido item : itemsParrilla) {
                System.out.println(VERDE_EXITO + "    - " + item.getNombre() + ": $" + String.format("%.3f", item.getPrecio()) + RESET);
            }
        } else {
            System.out.println(AMARILLO_ADVERTENCIA + "    (No hay pedidos para parrilla)" + RESET);
        }

        System.out.println(CIAN_INFO + "\n   Cocina Caliente:" + RESET);
        if (!itemsCaliente.isEmpty()) {
            for (Pedido item : itemsCaliente) {
                System.out.println(VERDE_EXITO + "    - " + item.getNombre() + ": $" + String.format("%.3f", item.getPrecio()) + RESET);
            }
        } else {
            System.out.println(AMARILLO_ADVERTENCIA + "    (No hay pedidos para cocina caliente)" + RESET);
        }

        System.out.println(CIAN_INFO + "\n   Bar:" + RESET);
        if (!itemsBar.isEmpty()) {
            for (Pedido item : itemsBar) {
                System.out.println(VERDE_EXITO + "    - " + item.getNombre() + ": $" + String.format("%.3f", item.getPrecio()) + RESET);
            }
        } else {
            System.out.println(AMARILLO_ADVERTENCIA + "    (No hay pedidos para bar)" + RESET);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Restaurante miRestaurante = new Restaurante();

        int opcionPrincipal;
        do {
            System.out.println(VERDE_EXITO + "\n        Menú Principal del Restaurante " + RESET);
            System.out.println(VERDE_EXITO + "1. Ver Menú" + RESET);
            System.out.println(VERDE_EXITO + "2. Gestión de Mesas" + RESET);
            System.out.println(VERDE_EXITO + "0. Salir" + RESET);
            System.out.print(CIAN_INFO + "Seleccione una opción: " + RESET);
            opcionPrincipal = scanner.nextInt();

            switch (opcionPrincipal) {
                case 1:
                    miRestaurante.mostrarMenu();
                    System.out.print(CIAN_INFO + "Ingrese el número de mesa para realizar un pedido: " + RESET);
                    int numeroMesaPedido = scanner.nextInt();
                    if (!miRestaurante.estadoMesas.getOrDefault(numeroMesaPedido, false)) {
                        miRestaurante.crearNuevaComanda(numeroMesaPedido);
                        int opcionPedido;
                        do {
                            System.out.print(CIAN_INFO + "Seleccione un plato o bebida por su número (0 para terminar el pedido): " + RESET);
                            opcionPedido = scanner.nextInt();
                            if (opcionPedido > 0 && opcionPedido <= miRestaurante.menu.size() + miRestaurante.menuBar.size()) {
                                miRestaurante.agregarItemAComanda(numeroMesaPedido, opcionPedido);
                            } else if (opcionPedido != 0) {
                                System.out.println(ROJO_ERROR + "Opción inválida." + RESET);
                            }
                        } while (opcionPedido != 0);
                    } else {
                        System.out.println(ROJO_ERROR + "La mesa " + numeroMesaPedido + " ya está ocupada. No se puede crear una nueva comanda." + RESET);
                    }
                    break;
                case 2:
                    int opcionMesa;
                    do {
                        miRestaurante.mostrarMenuMesas();
                        opcionMesa = scanner.nextInt();
                        switch (opcionMesa) {
                            case 1:
                                miRestaurante.mostrarEstadoMesas();
                                break;
                            case 2:
                                System.out.print(CIAN_INFO + "Ingrese el número de mesa para la nueva comanda: " + RESET);
                                int nuevaMesa = scanner.nextInt();
                                miRestaurante.crearNuevaComanda(nuevaMesa);
                                break;
                            case 3:
                                System.out.print(CIAN_INFO + "Ingrese el número de mesa para ver la comanda: " + RESET);
                                int mesaConsulta = scanner.nextInt();
                                miRestaurante.mostrarComandaPorMesa(mesaConsulta);
                                break;
                            case 4:
                                System.out.print(CIAN_INFO + "Ingrese el número de mesa para pagar: " + RESET);
                                int mesaPago = scanner.nextInt();
                                miRestaurante.pagarMesa(mesaPago, scanner);
                                break;
                            case 0:
                                System.out.println(AMARILLO_ADVERTENCIA + "Volviendo al menú principal." + RESET);
                                break;
                            default:
                                System.out.println(ROJO_ERROR + "Opción inválida." + RESET);
                        }
                    } while (opcionMesa != 0);
                    break;
                case 0:
                    System.out.println(AMARILLO_ADVERTENCIA + "Saliendo del sistema del restaurante. ¡Hasta pronto!" + RESET);
                    break;
                default:
                    System.out.println(ROJO_ERROR + "Opción inválida. Por favor, intente de nuevo." + RESET);
            }
        } while (opcionPrincipal != 0);

        scanner.close();
    }
}