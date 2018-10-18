pipeline {
    environment {
        DOCKER_IMAGE_NAME = "moduo/devops"
        DOCKER_IMAGE_FULL_NAME = "${DOCKER_IMAGE_NAME}:v${BUILD_NUMBER}"
        REGISTRY_CREDENTIALS = "DockerHub"
        DOCKER_IMAGE = ''
    }
    agent {
        docker {
            image 'maven:3-jdk-11'
            args '-u root -v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Package') {
            steps{
                sh 'docker ps'
                script {
                    DOCKER_IMAGE = docker.build DOCKER_IMAGE_FULL_NAME
                }
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                  docker.withRegistry('', REGISTRY_CREDENTIALS ) {
                    DOCKER_IMAGE.push()
                  }
                }
            }
        }
    }
}