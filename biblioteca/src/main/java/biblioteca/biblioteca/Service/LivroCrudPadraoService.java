package biblioteca.biblioteca.Service;

import biblioteca.biblioteca.Dto.AutorPostPutDto;
import biblioteca.biblioteca.Dto.AutorResponseDto;
import biblioteca.biblioteca.Model.Autor;
import biblioteca.biblioteca.Repository.AutorRepository;
import biblioteca.biblioteca.Dto.LivroPostPutDto;
import biblioteca.biblioteca.Dto.LivroResponseDto;
import biblioteca.biblioteca.Model.Livro;
import biblioteca.biblioteca.Repository.LivroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LivroCrudPadraoService implements LivroCrudService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Override
    public LivroResponseDto criarLivro(LivroPostPutDto livroPostPutDto){
        Autor autor = autorRepository.findById(livroPostPutDto.getIdAutor())
            .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado"));

        Livro livro = Livro.builder()
            .isbn(livroPostPutDto.getIsbn())
            .titulo(livroPostPutDto.getTitulo())
            .ano(livroPostPutDto.getAno())
            .edicao(livroPostPutDto.getEdicao())
            .autor(autor)
            .build();

        Livro livroSalvo = livroRepository.save(livro);

        return converterParaResponse(livroSalvo);
    }

    @Override
    public LivroResponseDto atualizarLivro(String isbn, LivroPostPutDto livroPostPutDto ){
            Livro livroExis = livroRepository.findById(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

             Autor autor = autorRepository.findById(livroPostPutDto.getIdAutor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Autor não encontrado"));

                livroExis.setTitulo(livroPostPutDto.getTitulo());
                livroExis.setAno(livroPostPutDto.getAno());
                livroExis.setEdicao(livroPostPutDto.getEdicao());
                livroExis.setAutor(autor);

                Livro livroAtualizado = livroRepository.save(livroExis);

            return converterParaResponse(livroAtualizado);
    }

    @Override
    public List<LivroResponseDto> buscarLivroIsbn(String isbn){
        return livroRepository.findByIsbnContainingIgnoreCase(isbn)
            .stream()
            .map(livro -> converterParaResponse(livro))
            .toList();
    }

    @Override
    public List<LivroResponseDto> buscarLivroTitulo(String titulo){
        return livroRepository.findByTituloContainingIgnoreCase(titulo)
            .stream()
            .map(livro -> converterParaResponse(livro))
            .toList();

    }

    @Override
    public List<LivroResponseDto>  buscarTodosLivros (){
         return livroRepository.findAll()
                .stream()
                .map(livro -> converterParaResponse(livro))
                .toList();
    }

    @Override
    public void removerLivro(String isbn){
        livroRepository.delete(livroRepository.findById(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado")));
    }

    private LivroResponseDto converterParaResponse(Livro livro) {

        LivroResponseDto response = modelMapper.map(livro, LivroResponseDto.class);

        response.setIdAutor(livro.getAutor().getId());

        return response;
    }

}