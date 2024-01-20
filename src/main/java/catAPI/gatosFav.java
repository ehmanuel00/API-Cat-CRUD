package catAPI;

import java.awt.*;

public class gatosFav {
    String id;
    String image_id;
    String apikey="aqui va nuestra apikey";
    imagex image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public imagex getImage() {
        return image;
    }

    public void setImage(imagex image) {
        this.image = image;
    }
}
