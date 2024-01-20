package catAPI;

import javax.swing.*;
import java.io.IOException;

public class inicio {

    public static void main(String[] args) throws IOException {
        int opcion_menu = -1;
        String[] botones = {"ver gatos","ver favoritos", "salir"};

        do {
            String opcion = (String) JOptionPane.showInputDialog(null, "MENU", "APICAT JAVA", JOptionPane.INFORMATION_MESSAGE, null, botones, botones[0]);

            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    opcion_menu = i;
                }
            }

            switch(opcion_menu){
                case 0:
                    gatoService.verGatos();
                    break;
                case 1:
                    gatos gatos = new gatos();
                    gatoService.verFavoritos(gatos.getApikey());
                default:
                    break;
            }
        } while (opcion_menu != 1);
    }
}
