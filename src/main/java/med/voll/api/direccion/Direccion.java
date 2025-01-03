package med.voll.api.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter

@AllArgsConstructor
public class Direccion {

    private String calle;
    private String districto;
    private String ciudad;
    private String  numero;
    private String complemento;

    public Direccion() {
        // Constructor sin argumentos requerido por Hibernate
    }
    public Direccion(DatosDireccion direccion) {
        this.calle= direccion.calle();
        this.ciudad= direccion.ciudad();
        this.complemento=direccion.complemento();
        this.numero= direccion.numero();
        this.districto=direccion.districto();
    }
}
