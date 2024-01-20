package catAPI;

import com.google.gson.Gson;
import com.squareup.okhttp.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class gatoService {
    public static void verGatos() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .get()
                .build();
        Response response = client.newCall(request).execute();

        String elJSon = response.body().string();

        elJSon = elJSon.substring(1, elJSon.length());
        elJSon = elJSon.substring(0,elJSon.length()-1);

        Gson gson = new Gson();
        gatos gatos = gson.fromJson(elJSon, catAPI.gatos.class);

        Image image = null;

        try{
            URL url = new URL(gatos.getUrl());
            image = ImageIO.read(url);

            ImageIcon fondoGato = new ImageIcon(image);

            if(fondoGato.getIconWidth()>800){
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }

            String[] botones = {"ver otra imagen","favorito","volver"};
            String id_Gato = String.valueOf(gatos.getId());
            String opcion = (String) JOptionPane.showInputDialog(null,"GATOS FAVORITOS",id_Gato,JOptionPane.INFORMATION_MESSAGE,fondoGato,botones,botones[0]);

            int seleccion = -1;

            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])){
                    seleccion = i;
                }
            }

            switch (seleccion){
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGatos(gatos);
                    break;
                default:
                    break;
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void favoritoGatos(gatos gatos){
        try{
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"image_id\":\""+gatos.getId()+"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gatos.getApikey())
                    .build();
            Response response = client.newCall(request).execute();


        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void verFavoritos(String apikey) throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .get()
                .addHeader("x-api-key", apikey)
                .build();
        Response response = client.newCall(request).execute();

        String elJson = response.body().string();

        Gson gson = new Gson();

        gatosFav[] gatosFavsArray = gson.fromJson(elJson,gatosFav[].class);

        if (gatosFavsArray.length>0){
            int min = 1;
            int max = gatosFavsArray.length;
            int aleatorio = (int) (Math.random() * (max - min + 1)) + min;
            int indice = aleatorio-1;

            gatosFav gatoFav = gatosFavsArray[indice];

            Image image = null;

            try{
                URL url = new URL(gatoFav.image.getUrl());
                image = ImageIO.read(url);

                ImageIcon fondoGato = new ImageIcon(image);

                if(fondoGato.getIconWidth()>800){
                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);
                }

                String[] botones = {"ver otra imagen","eliminar favorito","volver"};
                String id_Gato = String.valueOf(gatoFav.getId());
                String opcion = (String) JOptionPane.showInputDialog(null,"GATOS FAVORITOS",id_Gato,JOptionPane.INFORMATION_MESSAGE,fondoGato,botones,botones[0]);

                int seleccion = -1;

                for (int i = 0; i < botones.length; i++) {
                    if (opcion.equals(botones[i])){
                        seleccion = i;
                    }
                }

                switch (seleccion){
                    case 0:
                        verFavoritos(apikey);
                        break;
                    case 1:
                        borrarFavorito(gatoFav);
                        break;
                    default:
                        break;
                }

            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    public static void  borrarFavorito(gatosFav gatoFav) throws IOException{
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/"+gatoFav.getId())
                    .delete()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gatoFav.getApikey())
                    .build();
            Response response = client.newCall(request).execute();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
