package biblioteca.biblioteca.Service;

import biblioteca.biblioteca.Dto.LivroPostPutDto;
import biblioteca.biblioteca.Dto.LivroResponseDto;

import java.util.List;


public interface LivroCrudService{

    LivroResponseDto criarLivro(LivroPostPutDto livroPostPutDto);

    LivroResponseDto atualizarLivro(String isbn, LivroPostPutDto livroPostPutDto );

    List<LivroResponseDto> buscarLivroIsbn(String isbn);

    List<LivroResponseDto> buscarLivroTitulo(String titulo);

    List<LivroResponseDto>  buscarTodosLivros ();

    void removerLivro(String isbn);
}