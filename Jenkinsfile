pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'quarkusjenkins-app'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/ElianaLucas/quarkusJekins-pipeline.git' 
            }
        }

        stage('Build Maven') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %DOCKER_IMAGE% ."
            }
        }

        stage('Run Docker Container') {
            steps {
                bat "docker run -d -p 8080:8080 --name quarkus-container %DOCKER_IMAGE%"
            }
        }

        stage('Clean Up (opcional)') {
            steps {
                script {
                    echo "Puedes agregar limpieza si es necesario"
                }
            }
        }
    }
}
