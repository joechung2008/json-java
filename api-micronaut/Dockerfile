# Build stage
FROM eclipse-temurin:24-jdk AS build
ENV MAVEN_VERSION=3.9.11
ENV MAVEN_HOME=/opt/maven
RUN apt-get update \
	&& apt-get install -y curl tar \
	&& curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar -xz -C /opt \
	&& mv /opt/apache-maven-${MAVEN_VERSION} ${MAVEN_HOME} \
	&& ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn \
	&& apt-get clean && rm -rf /var/lib/apt/lists/*
ENV PATH=$MAVEN_HOME/bin:$PATH
WORKDIR /app
COPY . .
RUN ./mvnw -pl api-micronaut -am -DskipTests clean package || mvn -pl api-micronaut -am -DskipTests clean package

# Runner stage
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /app/api-micronaut/target/api-micronaut-1.0-SNAPSHOT.jar app.jar
RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chown appuser:appuser app.jar
USER appuser
ENTRYPOINT ["java", "-jar", "app.jar"]
