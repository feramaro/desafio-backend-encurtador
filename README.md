
# Encurtador de URLs
### Sobre o projeto
Encurtador de Url utilizando Java com Spring Boot e banco de dados Amazon DynamoDB.

Projeto baseado no [desafio backend](https://github.com/backend-br/desafios)


## Exemplo de Uso

Para fazer o deploy desse projeto rode

```http
  POST http://localhost:8080/shorten-url
  Content-Type: application/json

  {
    "url" : "https://www.youtube.com/watch?v=UJrEZTjHoIk"
  }
```
Retorno:

```json
{
  "url" : "http://localhost:8080/oFMA6o"
}
```

Acessando o endereço vai ser redirecionado para esse [video](https://www.youtube.com/watch?v=UJrEZTjHoIk)




