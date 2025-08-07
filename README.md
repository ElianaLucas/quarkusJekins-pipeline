# Video explicativo del proyecto
https://youtu.be/i0EvcbaKK9E

# quarkusJekins-app

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkusJekins-app-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- SmallRye Health ([guide](https://quarkus.io/guides/smallrye-health)): Monitor service health
- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### SmallRye Health

Monitor your application's health using SmallRye Health

[Related guide section...](https://quarkus.io/guides/smallrye-health)

//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
Quarkus Jenkins y Despliegue en Azure - Taller Práctico
Este repositorio contiene la práctica para automatizar la construcción y despliegue de una aplicación Quarkus usando Jenkins, Docker y Azure App Service.

ETAPA 1: Jenkins + Docker (Local)
Paso 1: Instalar Jenkins con Docker
bash
Copiar
Editar
docker run -d --name jenkinslocal -p 8080:8080 -p 50000:50000 jenkins/jenkins
docker exec jenkinslocal cat /var/jenkins_home/secrets/initialAdminPassword
Ingresa a http://localhost:8080 con la contraseña obtenida.

Instala plugins sugeridos.

Crea usuario administrador.

Paso 2: Crear Jenkinsfile y Pipeline
Crear un pipeline que:

Clone el repo Quarkus.

Compile con Maven: ./mvnw clean package.

Construya imagen Docker con Dockerfile.

Ejecute contenedor localmente en puerto 8081.

Comandos Docker para Quarkus local
bash
Copiar
Editar
docker build -t quarkusjenkins-app .
docker run -d -p 8081:8080 --name quarkus-container quarkusjenkins-app
Verificar endpoints de salud Quarkus
http://localhost:8081/q/live

http://localhost:8081/q/ready

http://localhost:8081/q/started

ETAPA 2: Despliegue en Azure App Service con Docker
Pre-requisitos
Tener Azure CLI instalado y logueado (az login).

Imagen Docker subida a Docker Hub.

Paso 1: Subir imagen Docker a Docker Hub
bash
Copiar
Editar
docker login
docker tag quarkusjenkins-app eliana2004/quarkus-app:latest
docker push eliana2004/quarkus-app:latest
Paso 2: Crear recursos en Azure
bash
Copiar
Editar
az group create --name quarkus-group --location eastus

az appservice plan create --name quarkus-plan --resource-group quarkus-group --is-linux --sku B1

# Si da error de namespace ejecutar:
az provider register --namespace Microsoft.Web
Paso 3: Crear Web App con contenedor Docker
bash
Copiar
Editar
az webapp create --resource-group quarkus-group --plan quarkus-plan --name quarkusjekinsapp --deployment-container-image-name eliana2004/quarkus-app:latest
Paso 4: Obtener URL de la app y abrir en navegador
bash
Copiar
Editar
az webapp show --resource-group quarkus-group --name quarkusjekinsapp --query defaultHostName -o tsv
# Salida: quarkusjekinsapp.azurewebsites.net
URL final:
https://quarkusjekinsapp.azurewebsites.net

Paso 5: Actualizar imagen y reiniciar app
bash
Copiar
Editar
docker build -t eliana2004/quarkus-app:latest .
docker push eliana2004/quarkus-app:latest
az webapp restart --resource-group quarkus-group --name quarkusjekinsapp
Notas importantes
Configura el puerto en Quarkus para que use la variable PORT:

properties
Copiar
Editar
quarkus.http.port=${PORT:8080}
Azure App Service expone el puerto 80 por defecto, asegúrate que Quarkus use ese puerto o configura WEBSITES_PORT en Azure.

Repositorio GitHub
https://github.com/ElianaLucas/quarkusJekins-pipeline.git
