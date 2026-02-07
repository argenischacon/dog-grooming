# Dog Grooming

![Java](https://img.shields.io/badge/Java-21-orange)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven)
![JPA](https://img.shields.io/badge/JPA-EclipseLink-blue)
![Swing](https://img.shields.io/badge/Swing-FlatLaf-blue)
![Database](https://img.shields.io/badge/Database-H2-green?logo=h2database)
![License](https://img.shields.io/badge/License-MIT-green)

Una aplicación de escritorio portable para la gestión de peluquería canina, desarrollada en Java con interfaz gráfica Swing moderna. Permite administrar dueños y sus mascotas, con una base de datos embebida H2 para portabilidad completa.

## Características

- **Gestión de Dueños**: Crear, editar, ver y eliminar dueños.
- **Gestión de Perros**: Crear, editar, ver y eliminar perros.
- **Interfaz Moderna**: Utiliza FlatLaf para un look and feel moderno y personalizable.
- **Base de Datos Embebida**: H2 database para portabilidad sin necesidad de servidor externo.
- **Validaciones**: Integración con Jakarta Validation para datos consistentes.
- **Logging**: Configurado con Logback para seguimiento de eventos.

## Tecnologías Utilizadas

- **Java 21**: Lenguaje principal.
- **Maven**: Gestión de dependencias y build.
- **JPA (EclipseLink)**: Persistencia de datos.
- **Swing + FlatLaf**: Interfaz gráfica.
- **H2 Database**: Base de datos embebida.
- **Jakarta Validation + Hibernate Validator**: Validaciones.
- **SLF4J + Logback**: Logging.
- **Lombok**: Reducción de boilerplate.
- **MapStruct**: Mapeo de objetos.
- **JCalendar**: Componentes de calendario.
- **Spinner Progress**: Componente de carga animado por [DJ-Raven](https://github.com/DJ-Raven/spinner-progress) (MIT License).

## Instalación

### Requisitos Previos

- **Java 21** o superior instalado.
- **Maven 3.6+** para gestión de dependencias.

### Pasos de Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/argenischacon/dog-grooming.git
   cd dog-grooming
   ```

2. Compila el proyecto:
   ```bash
   mvn clean compile
   ```

3. Ejecuta la aplicación:
   ```bash
   mvn exec:java -Dexec.mainClass="com.argenischacon.DogGrooming"
   ```

   O genera un JAR ejecutable:
   ```bash
   mvn clean package
   java -jar target/dog-grooming-1.0-SNAPSHOT.jar
   ```

## Uso

La aplicación se ejecuta como una aplicación de escritorio. Al iniciar, muestra una pantalla de splash mientras inicializa la base de datos. Luego, presenta la interfaz principal para gestionar dueños y perros.

- **Agregar Dueño**: Usa el panel de dueños para crear nuevos registros.
- **Agregar Perro**: Asocia perros a dueños existentes.
- **Editar/Ver**: Selecciona registros para modificar o visualizar detalles.

La base de datos se crea automáticamente en `./data/doggrooming.mv.db` para portabilidad.

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

## Autor

**Argenis Chacón**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Argenis_Chacon-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/argenischaconb)
[![GitHub](https://img.shields.io/badge/GitHub-@argenischacon-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/argenischacon)


⭐ Si este proyecto te resultó útil, ¡dale una estrella!
