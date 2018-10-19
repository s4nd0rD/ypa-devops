#!/usr/bin/env groovy
pipeline {
    environment {
        DOCKER_IMAGE_NAME = "moduo/devops"
        DOCKER_IMAGE_FULL_NAME = ''
        REGISTRY_CREDENTIALS = "DockerHub"
        BRANCH_ENVIRONMENT = getEnvironmentBranch()
        DOCKER_IMAGE = ''
        FULL_VERSION = ''
    }
    agent any
    stages {
        stage('Prepare') {
            steps {
                script {
                    if (BRANCH_ENVIRONMENT == 'release') {
                        def branchTokens = env.BRANCH_NAME.split('/')
                        if (branchTokens.length == 2) {
                            setVersion(branchTokens)
                            RELEASE_VERSION = "${VERSION_MAJOR}-${VERSION_MINOR}-${VERSION_PATCH}"
                            FULL_VERSION = "${VERSION_MAJOR}.${VERSION_MINOR}.${VERSION_PATCH}"
                        } else {
                            FULL_VERSION = env.BRANCH_NAME.split('/')[1]
                        }
                        currentBuild.displayName = "${FULL_VERSION} #${BUILD_NUMBER}"
                        env.DOCKER_IMAGE_FULL_NAME = "${DOCKER_IMAGE_NAME}:${FULL_VERSION}-${BUILD_NUMBER}"
                    }
                }
            }
        }
        stage('Build') {
            agent {
                docker {
                    image 'maven:3-jdk-11'
                    args '-u root -v /root/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Package') {
            steps {
                script {
                    DOCKER_IMAGE = docker.build DOCKER_IMAGE_FULL_NAME
                }
            }
        }
        stage('Test') {
            agent {
                docker {
                    image 'maven:3-jdk-11'
                    args '-u root -v /root/.m2:/root/.m2'
                    reuseNode true
                }
            }
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
                    docker.withRegistry('', REGISTRY_CREDENTIALS) {
                        DOCKER_IMAGE.push()
                    }
                }
            }
        }
    }
}

def setVersion(branchTokens) {
    def versionTokens = branchTokens[1].split('\\.')

    if (versionTokens.length != 3) {
        error "INVALID version ${branchTokens[1]}."
    }
    VERSION_MAJOR = versionTokens[0]
    VERSION_MINOR = versionTokens[1]
    VERSION_PATCH = versionTokens[2]
    echo "VERSION: ${VERSION_MAJOR}.${VERSION_MINOR}.${VERSION_PATCH}s"
}

def getEnvironmentBranch() {
    def getEnvironmentBranch = "feature"
    def gitBranch = env.BRANCH_NAME

    if (gitBranch.contains("release/")) {
        getEnvironmentBranch = "release"
    }

    return getEnvironmentBranch
}
