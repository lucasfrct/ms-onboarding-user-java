# ## Maven development container 
# ## etapa de build do projeto
FROM maven:3.6.3-openjdk-11 AS ms-onbloarding-user-java-build

# ## configurando variáveis de Ambiente
ENV APP_HOME=/tmp

# ## diretorio de trabalho
WORKDIR "$APP_HOME"

# ## copia as dependencias
COPY pom.xml ./

# ## instala as dependencias
RUN mvn clean install

# ## copia o projeto
COPY ./ ./

# ## empacota o projeto para um .jar
RUN mvn package

# # ## máquina de producao
# FROM openjdk
FROM gcr.io/distroless/java11-debian11

# ## configurando variáveis de Ambiente
ENV APP_HOME=/data

# ## diretorio de trabalho
WORKDIR "$APP_HOME"

# # ## copia o pacote o projeto
COPY --from=ms-onbloarding-user-java-build /tmp/target/environment-java-0.1.0.jar /data/environment-java-0.1.0.jar

# ## cria volume para diretorio padrao
VOLUME "$APP_HOME"

# #expose port 8080
EXPOSE 8080

# # ## inicia o projeto
CMD ["/data/environment-java-0.1.0.jar"]
