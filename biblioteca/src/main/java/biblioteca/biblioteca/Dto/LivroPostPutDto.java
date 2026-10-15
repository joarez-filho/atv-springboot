package biblioteca.biblioteca.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivroPostPutDto{
    
    @NotBlank
    @JsonProperty("isbn")
    private String isbn;

    @NotBlank
    @JsonProperty("titulo")
    private String titulo;

    
    @JsonProperty("ano")
    private Integer ano;

   
    @JsonProperty("edicao")
    private String edicao;

    @NotNull
    @JsonProperty("id_autor")
    private Long idAutor;
    


}