package ni.edu.uam.fact_app.model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Categoria {
    private Integer id;
    private String nombre;
    private boolean activa;


    @Override
    public String toString(){
        return nombre;
    }
}
