pipeline {
    agent { label 'windows' }
    environment {
        DOCKER_IMAGE = 'quarkusjenkins-app'
    }

    stages {
        stage('Checkout') {
            steps {
                // Hacer checkout explicito de la rama main
                git branch: 'main', url: 'https://github.com/ElianaLucas/quarkusJekins-pipeline.git'
            }
        }

        stage('Build Maven') {
            steps {
                // Comando para Windows para construir con Maven, sin test para acelerar
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Construir la imagen Docker en Windows (usa variables con %VARIABLE%)
                bat 'docker build -t %DOCKER_IMAGE% .'
            }
        }

        stage('Run Docker Container') {
            steps {
                // Ejecutar el contenedor con el puerto 8080
                bat '''
                docker stop quarkus-container || echo No estaba corriendo
                docker rm quarkus-container || echo No estaba removido
                docker run -d -p 8081:8080 --name quarkus-container %DOCKER_IMAGE%
                '''
            }
        }

        stage('Clean Up (opcional)') {
            steps {
                script {
                    echo "Puedes agregar aquí pasos de limpieza si es necesario."
                }
            }
        }
    }
}
