# Challenge Back End - API REST con Spring

¡Bienvenido al desarrollo de desafío del Challenge Back End!

En esta ocasión el reto es crear un API REST con Spring para un foro.
## Funcionalidades
Nuestra API va a centrarse específicamente en los tópicos, y debe permitir a los usuarios realizar las siguientes acciones:

### Crear un nuevo tópico
La API debe tener un endpoint para el registro de nuevos tópicos y debe aceptar solicitudes POST para el URI `/topicos`.

Los datos del tópico (título, mensaje, autor y curso) deben enviarse en el cuerpo de la solicitud, en formato JSON.

#### Reglas del negocio
- Todos los campos son obligatorios.
- La API no debe permitir los registros duplicados (que contengan el mismo título y mensaje).

### Mostrar todos los tópicos creados
La API debe tener un endpoint para la lista de todos los tópicos y debe aceptar solicitudes GET para el URI `/topicos`.

Los datos del tópico (título, mensaje, fecha de creación, estatus autor y curso) deben devolverse en el cuerpo de la respuesta, en formato JSON.

### Mostrar un tópico específico
La API debe tener un endpoint para la lista de todos los tópicos y debe aceptar solicitudes GET para el URI `/topicos/{id}`.

Los datos del tópico (título, mensaje, fecha de creación, estatus autor y curso) deben devolverse en el cuerpo de la respuesta, en formato JSON.

### Actualizar un tópico
La API debe tener un endpoint para el registro de nuevos tópicos y debe aceptar solicitudes PUT para el URI `/topicos/{id}`.

#### Observación
Las mismas reglas de negocio para el registro de un tópico deben ser aplicadas en la actualización del mismo.

### Eliminar un tópico
La API debe tener un endpoint para la eliminación de tópicos y debe aceptar solicitudes DELETE para el URI `/topicos/{id}`.

## Tecnologías
- Java 17
- Spring Boot 3
- Base de datos para la persistencia de la información
- Flyway para la migración de Base de Datos
